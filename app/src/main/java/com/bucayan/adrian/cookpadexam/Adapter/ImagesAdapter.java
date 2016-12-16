package com.bucayan.adrian.cookpadexam.Adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bucayan.adrian.cookpadexam.Fragment.ViewImagesFragment;
import com.bucayan.adrian.cookpadexam.Model.ImageData;
import com.bucayan.adrian.cookpadexam.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author Adrian Bucayan on 12/16/16.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.Holder>  {

    private List<ImageData> mImageDataList;
    private Fragment mFragment;

    public ImagesAdapter(List<ImageData> imageDataList, ViewImagesFragment viewImagesFragment) {
        this.mImageDataList = imageDataList;
        this.mFragment = viewImagesFragment;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_images, parent, false);

        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final ImageData finalImageData = mImageDataList.get(position);

        Picasso.with(holder.itemView.getContext())
                .load(finalImageData.getImages().getStandard_resolution().getUrl())
                .resize(300, 300)
                .centerCrop()
                .into(holder.mImageView);

        if(finalImageData.getCaption() != null)
            holder.mCaption.setText(finalImageData.getCaption().getText() + "");

        if(finalImageData.getLikes() != null)
            holder.mLikes.setText(finalImageData.getLikes().getCount() + " Likes");
    }

    @Override
    public int getItemCount() {
        return mImageDataList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public TextView mCaption, mLikes;
        public CardView mCardView;
        public ImageView mImageView;

        public Holder(View itemView) {
            super(itemView);

            mImageView  = (ImageView) itemView.findViewById(R.id.list_view_images_url);
            mCaption  = (TextView) itemView.findViewById(R.id.list_view_caption);
            mLikes  = (TextView) itemView.findViewById(R.id.list_view_likes);
            mCardView = (CardView) itemView.findViewById(R.id.list_view_holder);
        }
    }

}
