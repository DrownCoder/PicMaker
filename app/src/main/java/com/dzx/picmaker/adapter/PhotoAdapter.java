package com.dzx.picmaker.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dzx.picmaker.R;
import com.dzx.picmaker.model.PhotoItem;

import java.util.List;

/**
 *
 * Created by dengzhaoxuan on 2017/4/26.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private Context mContext;
    private List<PhotoItem> mData;
    private int mSelectSize = 1;
    private List<PhotoItem> mSelelctPhotos;
    public interface onItemClickListener{
        void onItemClick(View view);
    }
    private onItemClickListener mItemClickkListener;

    public void setmItemClickkListener(onItemClickListener mItemClickkListener) {
        this.mItemClickkListener = mItemClickkListener;
    }

    public PhotoAdapter(Context context, List<PhotoItem> mPhotos, List<PhotoItem> data) {
        this.mContext = context;
        this.mData = mPhotos;
        this.mSelelctPhotos = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(
                mContext).inflate(
                R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        resetView(holder);
        final PhotoItem item = mData.get(position);
        Glide.with(mContext).load(mData.get(position).getImgUri()).into(holder.mIvPic);

        if(item.isSelected()){
            setPhotoSelected(holder, item);
        }
        holder.mTvNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mData.get(position).isSelected()){
                    mSelectSize++;
                    item.setSelectNum(mSelectSize);
                    mSelelctPhotos.add(item);
                    setPhotoSelected(holder, item);
                }else {
                    mSelectSize--;
                    mSelelctPhotos.remove(item);
                    mData.get(position).setSelected(false);
                    holder.mShade.setVisibility(View.GONE);
                    holder.mTvNum.setText("");
                    holder.mTvNum.setBackgroundResource(R.drawable.circle_hollow_white);
                    notifyDataSetChanged();
                }
            }
        });
    }

    private void setPhotoSelected(ViewHolder holder, PhotoItem item) {
        holder.mShade.setVisibility(View.VISIBLE);
        holder.mTvNum.setText(String.valueOf(mSelelctPhotos.indexOf(item)));
        holder.mTvNum.setBackgroundResource(R.drawable.circle_hollow_pink);
        startAnimation(holder.mTvNum);
        item.setSelected(true);
    }

    private void resetView(ViewHolder holder) {
        holder.mShade.setVisibility(View.GONE);
        holder.mTvNum.setText("");
        holder.mTvNum.setBackgroundResource(R.drawable.circle_hollow_white);
    }

    private void startAnimation(TextView mTvNum) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(mTvNum, "scaleX", 0.7f, 1.3f, 1.0f);
        ObjectAnimator animY = ObjectAnimator.ofFloat(mTvNum, "scaleY", 0.7f, 1.3f, 1.0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(animX, animY);
        set.start();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvNum;
        ImageView mIvPic;
        View mShade;

        public ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View itemView) {
            mTvNum = (TextView) itemView.findViewById(R.id.tv_num);
            mIvPic = (ImageView) itemView.findViewById(R.id.iv_item_photo);
            mShade = itemView.findViewById(R.id.view_shade);
        }
    }
}
