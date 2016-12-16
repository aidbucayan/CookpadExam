package com.bucayan.adrian.cookpadexam.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bucayan.adrian.cookpadexam.Adapter.ImagesAdapter;
import com.bucayan.adrian.cookpadexam.Api.AuthenticationService;
import com.bucayan.adrian.cookpadexam.Api.ServiceGenerator;
import com.bucayan.adrian.cookpadexam.Model.Counts;
import com.bucayan.adrian.cookpadexam.Model.ImageData;
import com.bucayan.adrian.cookpadexam.Model.MediaResponse;
import com.bucayan.adrian.cookpadexam.R;
import com.bucayan.adrian.cookpadexam.Utils.InstagramData;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewImagesFragment extends BaseFragment {

    private static final String TAG = ViewImagesFragment.class.getSimpleName();
    private Counts mCount;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImagesAdapter mImagesAdapter;

    private List<ImageData> mImageDataList;
    private ProgressDialog progress;

    public ViewImagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_images, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCount = (Counts) getArguments().getSerializable("count");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.view_list_recycler);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.view_list_swipe);

        configViews();
        fetchImages();
    }

    private void configViews() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setHasFixedSize(true);

        mImagesAdapter = new ImagesAdapter(new ArrayList<ImageData>(), ViewImagesFragment.this);
        mRecyclerView.setAdapter(mImagesAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimary));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                fetchImages();
            }
        });

    }

    /**
     * Fetching all the Media data of the User thru API calls
     */
    private void fetchImages() {

        progress = ProgressDialog.show(getActivity(), "Loading", "Please wait..", true);
        progress.setCancelable(false);
        progress.show();

        final AuthenticationService authenticationService = ServiceGenerator.getInstance().getAuthenticationService();
        Call<MediaResponse> getUserImagesTask = authenticationService.getUserImages(
                mCookpadExamPreferences.getId(), InstagramData.CLIENT_ID,
                mCount.getMedia(), mCookpadExamPreferences.getInstagramTokenId());

        getUserImagesTask.enqueue(new Callback<MediaResponse>() {
            @Override
            public void onResponse(Response<MediaResponse> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    MediaResponse mediaResponse = response.body();

                    mImageDataList = mediaResponse.getData();
                    mImagesAdapter = new ImagesAdapter(mImageDataList, ViewImagesFragment.this);
                    mRecyclerView.setAdapter(mImagesAdapter);
                    mSwipeRefreshLayout.setRefreshing(false);

                    progress.dismiss();

                }
            }

            @Override
            public void onFailure(Throwable t) {
                progress.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
