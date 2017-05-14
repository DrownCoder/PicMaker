package com.dzx.picmaker.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.dzx.picmaker.R;
import com.dzx.picmaker.activity.base.BaseActivity;
import com.dzx.picmaker.adapter.PhotoAdapter;
import com.dzx.picmaker.databinding.ActivityPhotoBinding;
import com.dzx.picmaker.model.Album;
import com.dzx.picmaker.model.PhotoItem;
import com.dzx.picmaker.util.ImageUtils;
import com.dzx.picmaker.util.MediaScanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 相册Activity
 */
public class PhotoActivity extends BaseActivity {
    private ActivityPhotoBinding mBinding;
    private PhotoAdapter mAdapter;
    private Map<String, Album> mAlbums;
    private List<PhotoItem> mPhotos;
    private List<PhotoItem> mSelectPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_photo);
        initDatas();
    }


    @Override
    protected void initEvents() {
    }

    @Override
    protected void initDatas() {
        updataMedia();
        mAlbums = ImageUtils.getLocalAllAlbum(PhotoActivity.this);
        mPhotos = new ArrayList<>();
        mSelectPhotos = new ArrayList<>();
        for (String key : mAlbums.keySet()) {
            mPhotos.addAll(mAlbums.get(key).getPhotos());
        }
        mAdapter = new PhotoAdapter(this, mPhotos , mSelectPhotos);
        mBinding.rcyPhoto.setLayoutManager(new GridLayoutManager(this, 4));
        mBinding.rcyPhoto.setAdapter(mAdapter);
    }

    /**
     * 刷新媒体库
     */
    private void updataMedia() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScanner scanner = new MediaScanner(this);
            //scanner.scanFile(new File(Environment.getExternalStorageDirectory()+"/sina/weibo/weibo/"),"jpg");
            scanSdCard();
        } else {
            sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://"
                            + Environment.getExternalStorageDirectory())));
        }
    }
    private void scanSdCard(){
        String file= Environment.getExternalStorageDirectory().toString();
        new ScanTask().execute(file);
    }

    private void fileScan(String file){
        //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
        MediaScannerConnection.scanFile(this, new String[]{file}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                mPhotos.add(new PhotoItem(path));
            }
        });
    }

    /**
     * 目录检索
     * @param path
     * @return
     */
    private int folderScan(String path){
        int size = 0;
        File file = new File(path);

        if(file.exists() && file.isDirectory()){
            File[] array = file.listFiles();

            for(int i=0;i<array.length;i++){
                File f = array[i];

                if(f.isFile()){//FILE TYPE
                    String name = f.getName();

                    if(name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".jpg")){
                        size++;
                        fileScan(f.getAbsolutePath());
                    }
                }
                else {//FOLDER TYPE
                    folderScan(f.getAbsolutePath());
                }
            }
        }
        return size;
    }

    private class ScanTask extends AsyncTask<String,Integer,Integer> {
        @Override
        protected void onPreExecute() {
            showProgressDialog(PhotoActivity.this,"扫描图片中……");
        }

        @Override
        protected Integer doInBackground(String... strings) {
            String path = strings[0];
            return folderScan(path);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            dismissProgressDialog();
            mAdapter.notifyDataSetChanged();
        }
    }

}
