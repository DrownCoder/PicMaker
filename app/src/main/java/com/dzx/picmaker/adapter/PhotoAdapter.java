package com.dzx.picmaker.adapter;

import android.content.Context;
import android.os.Environment;
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

    public PhotoAdapter(Context context, List<PhotoItem> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(
                mContext).inflate(
                R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(mData.get(position).getImgUri()).into(holder.mIvPic);

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
