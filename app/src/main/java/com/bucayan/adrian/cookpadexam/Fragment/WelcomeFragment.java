package com.bucayan.adrian.cookpadexam.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bucayan.adrian.cookpadexam.Api.AuthenticationService;
import com.bucayan.adrian.cookpadexam.Api.ServiceGenerator;
import com.bucayan.adrian.cookpadexam.MainActivity;
import com.bucayan.adrian.cookpadexam.Model.Counts;
import com.bucayan.adrian.cookpadexam.Model.User;
import com.bucayan.adrian.cookpadexam.Model.UserInfo;
import com.bucayan.adrian.cookpadexam.R;
import com.bucayan.adrian.cookpadexam.Utils.CircleTransform;
import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends BaseFragment {

    private static final String TAG = WelcomeFragment.class.getSimpleName();

    private User mUser;
    private TextView mTvName;
    private TextView mTvPost;
    private TextView mTvFollowing;
    private TextView mTvFollower;
    private ImageView mIvProfilePic;
    private Button mBtnViewImages;
    private Counts count;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUser = (User) getArguments().getSerializable("user");

        mTvName = (TextView) view.findViewById(R.id.welcome_name);
        mTvName.setText("WELCOME " + mUser.getFull_name() + "!");

        mTvPost = (TextView) view.findViewById(R.id.welcome_post_tv);
        mTvFollowing = (TextView) view.findViewById(R.id.welcome_following_tv);
        mTvFollower = (TextView) view.findViewById(R.id.welcome_followers_tv);

        mIvProfilePic = (ImageView) view.findViewById(R.id.welcome_pic);
        Picasso.with(getActivity())
                .load(mUser.getProfile_picture())
                .transform(new CircleTransform())
                .resize(300, 300)
                .centerCrop()
                .into(mIvProfilePic);
        //.placeholder(R.mipmap.img_profile_pic).error(R.mipmap.img_profile_pic)

        fetchUserDetails();

        mBtnViewImages = (Button) view.findViewById(R.id.welcome_view_post_btn);
        mBtnViewImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoViewImages();
            }
        });
    }

    private void gotoViewImages() {
        ViewImagesFragment viewImagesFragment = new ViewImagesFragment();

        Bundle args = new Bundle();
        args.putSerializable("count", count);
        viewImagesFragment.setArguments(args);

        final MainActivity activity = ((MainActivity) getContext());
        activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .detach(viewImagesFragment)
                .replace(R.id.frmContentFrame, viewImagesFragment,
                        ViewImagesFragment.class.getSimpleName())
                .attach(viewImagesFragment)
                .addToBackStack("viewImagesFragment")
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void fetchUserDetails() {
        final AuthenticationService authenticationService = ServiceGenerator.getInstance().getAuthenticationService();
        Call<UserInfo> getUserDetailsTask = authenticationService.getUserDetails(
                mCookpadExamPreferences.getId(), mCookpadExamPreferences.getInstagramTokenId());

        getUserDetailsTask.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Response<UserInfo> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    UserInfo userInfo = response.body();

                    User user = userInfo.getData();
                    mTvPost.setText(user.getCounts().getMedia() + " Post");
                    mTvFollowing.setText(user.getCounts().getFollows() + " Following");
                    mTvFollower.setText(user.getCounts().getFollowed_by() + " Followers");

                    count = user.getCounts();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


}
