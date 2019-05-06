package com.hqxh.fiamproperty.unit;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

public class OpenFiles {
    //android获取一个用于打开HTML文件的intent
    public static Intent getHtmlFileIntent(Context context, File file) throws ActivityNotFoundException {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.hqxh.fiamproperty.fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {

            Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString()).build();
            intent.setDataAndType(uri, "text/html");
        }
        return intent;
    }

    //android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(Context context, File file) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.hqxh.fiamproperty.fileProvider", file);
            intent.setDataAndType(contentUri, "image/*");
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "image/*");
        }
        return intent;
    }

    //android获取一个用于打开PDF文件的intent

    public static Intent getPdfFileIntent(Context context, File file) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.hqxh.fiamproperty.fileProvider", file);
            intent.setDataAndType(contentUri, "application/pdf");
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
        }
        return intent;
    }

    //android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(Context context, File file) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.hqxh.fiamproperty.fileProvider", file);
            intent.setDataAndType(contentUri, "text/plain");
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "text/plain");
        }

        return intent;
    }

    //android获取一个用于打开音频文件的intent
    public static Intent getAudioFileIntent(Context context, File file) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.hqxh.fiamproperty.fileProvider", file);
            intent.setDataAndType(contentUri, "audio/*");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("oneshot", 0);
            intent.putExtra("configchange", 0);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "audio/*");
        }
        return intent;
    }

    //android获取一个用于打开视频文件的intent

    public static Intent getVideoFileIntent(Context context, File file) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.hqxh.fiamproperty.fileProvider", file);
            intent.setDataAndType(contentUri, "video/*");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("oneshot", 0);
            intent.putExtra("configchange", 0);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "video/*");
        }
        return intent;
    }


    //android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(Context context, File file) throws ActivityNotFoundException {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.hqxh.fiamproperty.fileProvider", file);
            intent.setDataAndType(contentUri, "application/x-chm");
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/x-chm");
        }
        return intent;
    }


    //android获取一个用于打开Word文件的intent

    public static Intent getWordFileIntent(Context context, File file) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.hqxh.fiamproperty.fileProvider", file);
            intent.setDataAndType(contentUri, "application/msword");
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/msword");
        }
        return intent;
    }

    //android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(Context context, File file) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.hqxh.fiamproperty.fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.ms-excel");
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        }

        return intent;
    }

    //android获取一个用于打开PPT文件的intent
    public static Intent getPPTFileIntent(Context context, File file) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.hqxh.fiamproperty.fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.ms-powerpoint");
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        }
        return intent;
    }

    //android获取一个用于打开apk文件的intent
    public static Intent getApkFileIntent(Context context, File file) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.hqxh.fiamproperty.fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        return intent;
    }


}