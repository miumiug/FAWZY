package com.hqxh.fiamproperty.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.hqxh.fiamproperty.R;
import com.hqxh.fiamproperty.api.HttpManager;
import com.hqxh.fiamproperty.base.BaseListActivity;
import com.hqxh.fiamproperty.constant.GlobalConfig;
import com.hqxh.fiamproperty.model.R_DOCLINKS;
import com.hqxh.fiamproperty.model.R_DOCLINKS.DOCLINKS;
import com.hqxh.fiamproperty.model.R_DOCLINKS.ResultBean;
import com.hqxh.fiamproperty.ui.adapter.DoclinksAdapter;
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
 * 附件文档的Activity
 **/
public class DoclinksActivity extends BaseListActivity {
    private static final String TAG = "DoclinksActivity";


    private DoclinksAdapter doclinksadapter;

    private int curpage = 1;
    private int showcount = 20;
    private int totalpage;

    private String ownertable;//
    private String ownerid;//
    private String title;//


    @Override
    protected void beforeInit() {
        super.beforeInit();
        if (getIntent().hasExtra("ownertable")) {
            ownertable = getIntent().getExtras().getString("ownertable");
        }
        if (getIntent().hasExtra("ownerid")) {
            ownerid = getIntent().getExtras().getString("ownerid");
        }
        if (getIntent().hasExtra("title")) {
            title = getIntent().getExtras().getString("title");
        }


    }

    @Override
    protected String getSubTitle() {

        return title;
    }


    /**
     * 获取数据
     **/
    private void getData() {
        String data = HttpManager.getDOCLINKSUrl(AccountUtils.getpersonId(this), ownertable, ownerid, curpage, showcount);
        Log.i(TAG, "data=" + data);
        Log.i(TAG, "url=" + GlobalConfig.HTTP_URL_SEARCH);
        Rx2AndroidNetworking.post(GlobalConfig.HTTP_URL_SEARCH)
                .addQueryParameter("data", data)
                .build()
                .getObjectObservable(R_DOCLINKS.class) // 发起获取数据列表的请求，并解析到FootList
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取数据列表的请求结果
                .doOnNext(new Consumer<R_DOCLINKS>() {
                    @Override
                    public void accept(@NonNull R_DOCLINKS r_doclinks) throws Exception {
                    }
                })

                .map(new Function<R_DOCLINKS, ResultBean>() {
                    @Override
                    public ResultBean apply(@NonNull R_DOCLINKS r_doclinks) throws Exception {

                        return r_doclinks.getResult();
                    }
                })
                .map(new Function<ResultBean, List<DOCLINKS>>() {
                    @Override
                    public List<DOCLINKS> apply(@NonNull ResultBean resultBean) throws Exception {
                        totalpage = Integer.valueOf(resultBean.getTotalpage());
                        return resultBean.getResultlist();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DOCLINKS>>() {
                    @Override
                    public void accept(@NonNull List<DOCLINKS> doclinks) throws Exception {
                        mPullLoadMoreRecyclerView.setRefreshing(false);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        if (doclinks == null || doclinks.isEmpty()) {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        } else {

                            addData(doclinks);

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


    @Override
    public void onRefresh() {
        curpage = 1;
        doclinksadapter.removeAll(doclinksadapter.getData());
        getData();

    }

    @Override
    public void onLoadMore() {
        if (totalpage == curpage) {
            getLoadMore();
            showMiddleToast(DoclinksActivity.this, getResources().getString(R.string.all_data_hint));
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


    @Override
    protected void fillData() {
        searchText.setVisibility(View.GONE);
        initAdapter(new ArrayList<DOCLINKS>());
        getData();

    }

    @Override
    protected void setOnClick() {

    }


    /**
     * 获取数据*
     */
    private void initAdapter(final List<DOCLINKS> list) {
        doclinksadapter = new DoclinksAdapter(DoclinksActivity.this, R.layout.list_item_doclinks, list);
        mRecyclerView.setAdapter(doclinksadapter);

        doclinksadapter.setOnclicklistener(new DoclinksAdapter.cOnClickListener() {
            @Override
            public void cOnClickListener(int postion, String urlname) {
                uploadFile(GlobalConfig.HTTP_DOCLINKS_URL + "/" + JsonUnit.getSlash(urlname), Utils.getFilePath(DoclinksActivity.this), JsonUnit.getFile(urlname));
            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<DOCLINKS> list) {
        doclinksadapter.addData(list);
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
                        showMiddleToast1(DoclinksActivity.this, "文件不存在");
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
                    intent = OpenFiles.getImageFileIntent(DoclinksActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingWebText))) {

                try {
                    intent = OpenFiles.getHtmlFileIntent(DoclinksActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }

            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingPackage))) {

                try {
                    intent = OpenFiles.getApkFileIntent(DoclinksActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }

            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingAudio))) {

                try {
                    intent = OpenFiles.getAudioFileIntent(DoclinksActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingVideo))) {

                try {
                    intent = OpenFiles.getVideoFileIntent(DoclinksActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingText))) {

                try {
                    intent = OpenFiles.getTextFileIntent(DoclinksActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingPdf))) {

                try {
                    intent = OpenFiles.getPdfFileIntent(DoclinksActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingWord))) {

                try {
                    intent = OpenFiles.getWordFileIntent(DoclinksActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingExcel))) {

                try {
                    intent = OpenFiles.getExcelFileIntent(DoclinksActivity.this, currentPath);
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    showMiddleToast(this, "无法打开，请安装相应的软件！");
                }
            } else if (checkEndsWithInStringArray(fileName, getResources().
                    getStringArray(R.array.fileEndingPPT))) {
                try {
                    intent = OpenFiles.getPPTFileIntent(DoclinksActivity.this, currentPath);
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
