package com.bucayan.adrian.cookpadexam.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bucayan.adrian.cookpadexam.Model.User;
import com.bucayan.adrian.cookpadexam.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends BaseFragment {

    private static final String TAG = WelcomeFragment.class.getSimpleName();

    private User mUser;
    private TextView mTvName;
    private ImageView mIvProfilePic;

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

        mIvProfilePic = (ImageView) view.findViewById(R.id.welcome_pic);
        Picasso.with(getActivity())
                .load(mUser.getProfile_picture())
                .resize(300, 300)
                .centerCrop()
                .into(mIvProfilePic);
        //.placeholder(R.mipmap.img_profile_pic).error(R.mipmap.img_profile_pic)
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
