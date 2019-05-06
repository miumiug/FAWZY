package com.hqxh.fiamproperty.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_DOCLINK;
import com.hqxh.fiamproperty.model.R_GRLINE;
import com.hqxh.fiamproperty.model.R_WORKREPORTLINE;
import com.hqxh.fiamproperty.ui.adapter.DoclinksAdapter;
import com.hqxh.fiamproperty.ui.adapter.DocuAdapter;
import com.hqxh.fiamproperty.ui.adapter.WorkReportLineAdapter;
import com.hqxh.fiamproperty.unit.AccountUtils;
import com.hqxh.fiamproperty.unit.JsonUnit;
import com.hqxh.fiamproperty.unit.OpenFiles;
import com.hqxh.fiamproperty.unit.Utils;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018\6\22 0022.
 */

public class DocuActivity extends BaseListActivity {
    private static final String TAG = "DocuActivity";
    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String wrnum;//wrnum

    private DocuAdapter docuAdapter;

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("wrnum")) {
            wrnum = getIntent().getExtras().getString("wrnum");
        }

    }
    @Override
    protected void fillData() {
        initAdapter(new ArrayList<R_DOCLINK.DOCLINK>());
        getData();
    }

    @Override
    protected void setOnClick() {
        searchText.setVisibility(View.GONE);
    }

    @Override
    protected String getSubTitle() {
        return "附件";
    }
    /**
     * 获取数据*
     */
    private void initAdapter(final List<R_DOCLINK.DOCLINK> list) {
        docuAdapter = new DocuAdapter(DocuActivity.this, R.layout.activity_fujian, list);
        mRecyclerView.setAdapter(docuAdapter);
        docuAdapter.setOnclicklistener(new DoclinksAdapter.cOnClickListener() {
            @Override
            public void cOnClickListener(int postion, String urlname) {
                uploadFile(GlobalConfig.HTTP_DOCLINKS_URL + "/" + JsonUnit.getSlash(urlname), Utils.getFilePath(DocuActivity.this), JsonUnit.getFile(urlname));
            }
        });
    }
    /**
     * 获取数据
     **/
    private void getData() {
//        String data = HttpManager.getDocu(AccountUtils.getpersonId(this), wrnum, curpage, showcount);
        String data = HttpManager.getDocu("huangzhichun", wrnum, curpage, showcount);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_DOCLINK.class) // 发起获取数据列表的请求，并解析到R_Wfassignemt
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_DOCLINK>() {
                    @Override
                    public void accept(@NonNull R_DOCLINK r_grline) throws Exception {
                    }
                })

                .map(new Function<R_DOCLINK, R_DOCLINK.ResultBean>() {
                    @Override
                    public R_DOCLINK.ResultBean apply(@NonNull R_DOCLINK r_grline) throws Exception {

                        return r_grline.getResult();
                    }
                })
                .map(new Function<R_DOCLINK.ResultBean, List<R_DOCLINK.DOCLINK>>() {
                    @Override
                    public List<R_DOCLINK.DOCLINK> apply(@NonNull R_DOCLINK.ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<R_DOCLINK.DOCLINK>>() {
                    @Override
                    public void accept(@NonNull List<R_DOCLINK.DOCLINK> grline) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (grline == null || grline.isEmpty()) {
                              notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(grline);


                        }
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        notLinearLayout.setVisibility(View.VISIBLE);
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                    }
                });
    }
    /**
     * 添加数据*
     */
    private void addData(final List<R_DOCLINK.DOCLINK> list) {
        docuAdapter.addData(list);
    }

    @Override
    public void onRefresh() {
        curpage = 1;
        docuAdapter.removeAll(docuAdapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(DocuActivity.this, getResources().getString(R.string.all_data_hint));
        } else {
            curpage++;
            getData();
        }
    }
    private void getLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    }
                });

            }
        }, 1000);

    }



    /**
     * 文件下载
     **/

    //http://10.60.15.130/attach2015/1433720355791.pdf

    // http://mobile.faw.com.cn:8091/maximo/file/attach2017/1503645232998.pdf
    private void uploadFile(String url, final String dirPath, final String fileName) {
        Log.e(TAG, "文件下载地址" + url + ",dirPath=" + dirPath + ",fileName=" + fileName);
        AndroidNetworking.download(url, Utils.getFilePath(dirPath), fileName)
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        // do anything with progress
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
                        Log.e(TAG, "下载完成");
                        File dir = new File(dirPath + "/" + fileName);
                        openFile(dir);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        showMiddleToast1(DocuActivity.this, "文件不存在");
                        Log.e(TAG, "下载失败" + error.getMessage());
                    }
                });

    }


    private boolean checkEndsWithInStringArray(String checkItsEnd,
                                               String[] fileEndings) {
        for (String aEnd : fileEndings) {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }


    private void openFile(File currentPath) {
        Log.e(TAG, "currentPath=" + currentPath.toString());
        if (currentPath != null && currentPath.isFile()) {
            String fileName = currentPath.toString();
            Intent intent;
            if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingImage))) {
                try {
                    intent = OpenFiles.getImageFileIntent(DocuActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingWebText))) {

                try {
                    intent = OpenFiles.getHtmlFileIntent(DocuActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }

            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingPackage))) {

                try {
                    intent = OpenFiles.getApkFileIntent(DocuActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }

            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingAudio))) {

                try {
                    intent = OpenFiles.getAudioFileIntent(DocuActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingVideo))) {

                try {
                    intent = OpenFiles.getVideoFileIntent(DocuActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingText))) {

                try {
                    intent = OpenFiles.getTextFileIntent(DocuActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingPdf))) {

                try {
                    intent = OpenFiles.getPdfFileIntent(DocuActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingWord))) {

                try {
                    intent = OpenFiles.getWordFileIntent(DocuActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingExcel))) {

                try {
                    intent = OpenFiles.getExcelFileIntent(DocuActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingPPT))) {
                try {
                    intent = OpenFiles.getPPTFileIntent(DocuActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else {
                showMiddleToast(this, "无法打开，请安装相应的软件！");
            }
        } else {
            showMiddleToast(this, "对不起，这不是文件！");
        }
    }
}
