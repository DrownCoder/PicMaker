package com.dzx.picmaker.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.dzx.picmaker.model.Album;
import com.dzx.picmaker.model.PhotoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dengzhaoxuan on 2017/4/27.
 * 图片工具类
 */

public class ImageUtils {
    /**
     * 返回以文件夹形式的所有图片
     */
    public static Map<String, Album> getLocalAllAlbum(Context context) {
        String[] projection = new String[]{
                MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED
        };
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,//查询路径
                projection,//查询的字段（也就是数据库中的列）
                null,//查询条件（也就是where语句）
                null,//查询条件中？对应的值
                MediaStore.Images.Media.DATE_ADDED + " desc");//排序顺序
        Map<String, Album> galleries = new HashMap<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String data = cursor.getString(1);
                if (data.lastIndexOf("/") < 1) {
                    continue;
                }
                String sub = data.substring(0, data.lastIndexOf("/"));
                if (!galleries.keySet().contains(sub)) {
                    String name = sub.substring(sub.lastIndexOf("/") + 1, sub.length());
                    galleries.put(sub, new Album(name, sub, new ArrayList<PhotoItem>()));
                }
                galleries.get(sub).getPhotos().add(new PhotoItem(data, (long) (cursor.getInt(2))));
            }

            cursor.close();
            ArrayList<PhotoItem> sysPhotos = FileUtils.getInst().findPicsInDir(
                    FileUtils.getInst().getSystemPhotoPath());
            if (!sysPhotos.isEmpty()) {
                galleries.put(FileUtils.getInst().getSystemPhotoPath(), new Album("胶卷相册", FileUtils
                        .getInst().getSystemPhotoPath(), sysPhotos));
            } else {
                galleries.remove(FileUtils.getInst().getSystemPhotoPath());
            }
            Log.e("PATH", FileUtils.getInst().getSystemPhotoPath());
        }
        return galleries;
    }
}
