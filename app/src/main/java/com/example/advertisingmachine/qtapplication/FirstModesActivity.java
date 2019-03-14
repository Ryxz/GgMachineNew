package com.example.advertisingmachine.qtapplication;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.qtapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bean.InfoModel;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.widget.VideoView;
import service.LongRunningService;
import utils.LoadImageUtil;
import utils.MyApplicationContext;
import utils.PlayVideoUtil;


/**
 * Created by Ryx on 2019/2/13.
 */
public class FirstModesActivity  extends AppCompatActivity {
    private static final String TAG = "FirstModesActivity:";
//    private Banner banner1;
//    private Banner banner2;
    private ImageView banner1;
    private ImageView banner2;
    private VideoView videoView_first;
    private VideoView videoView_two;
    private ProgressBar barFirst;
    private ProgressBar barTwo;
    private List<Integer> listTime;
    private static int time;
    private List<InfoModel.DataBean> beans;
    private int count = 0;
    public TimerReceiver receiver=null;
    private MyApplicationContext applicationContext;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_modes_laoyut);
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        applicationContext = (MyApplicationContext) getApplication();
        applicationContext.init();
        applicationContext.addActivity(this);
        startService(new Intent(FirstModesActivity.this,LongRunningService.class));
        receiver = new TimerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("service.LongRunningService");
        FirstModesActivity.this.registerReceiver(receiver,filter);
        beans = (List<InfoModel.DataBean>) this.getIntent().getSerializableExtra("dataBean");

        initView();


    }
//        倒计时结束要执行的方法
    @SuppressLint("HandlerLeak")
    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                            listTime = new ArrayList<>();
                            for (int n=0;n<beans.size();n++) {
                                listTime.add(beans.get(n).getTime());

                            }
                                videoView_first.stopPlayback();
                                videoView_two.stopPlayback();
                            if (count < beans.size()){
                                doData(beans.get(count));
                                time = listTime.get(count);
                                startTime();
                                count++;
                            } else {
                                count = 0;
                                startTime();
                            }
                    break;
            }
        }
    };

    /**
     * 开始计时
     */
    public void startTime() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                L.e("TIME----------------------------------------------------------------------------->>>>>>>>>>"+ time);
                time--;
                if (time<0) {
                    mHandle.sendEmptyMessage(1);
                } else {
                    mHandle.postDelayed(this,1000);

                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     * 视频和图片加载数据
     * 设置控件显示或隐藏
     * @param bean
     */
    public void doData(InfoModel.DataBean bean) {
        String typeTop = bean.getContent1().get(0);
        String typeButtom = bean.getContent2().get(0);
//        L.e("doData---------------------------------------------------------->>>>>>>>>>>>>>>>>>>>>>>" + bean.getTime());
        if (typeTop.equals("1") && typeButtom.equals("2")) {
            videoView_first.setVisibility(View.GONE);
            videoView_two.setVisibility(View.VISIBLE);
            banner1.setVisibility(View.VISIBLE);
            banner2.setVisibility(View.GONE);
//            barFirst.setVisibility(View.GONE);
//            barTwo.setVisibility(View.VISIBLE);
            PlayVideoUtil.play(videoView_two, bean.getContent2().get(1));
//            BannerUtil.bannerImgeOne(banner1, bean.getContent1().get(1));
            LoadImageUtil.loadImage(banner1,bean.getContent1().get(1));
        } else if (typeTop.equals("2") && typeButtom.equals("1")){
            videoView_first.setVisibility(View.VISIBLE);
            videoView_two.setVisibility(View.GONE);
            banner1.setVisibility(View.GONE);
            banner2.setVisibility(View.VISIBLE);
//            barFirst.setVisibility(View.VISIBLE);
//            barTwo.setVisibility(View.GONE);
            PlayVideoUtil.play(videoView_first, bean.getContent1().get(1));
//            BannerUtil.bannerImgeOne(banner2, bean.getContent2().get(1));
            LoadImageUtil.loadImage(banner2,bean.getContent2().get(1));
        } else if (typeTop.equals("1") && typeButtom.equals("1")) {
            videoView_first.setVisibility(View.GONE);
            videoView_two.setVisibility(View.GONE);
            banner1.setVisibility(View.VISIBLE);
            banner2.setVisibility(View.VISIBLE);
//            barFirst.setVisibility(View.GONE);
//            barTwo.setVisibility(View.GONE);

//            BannerUtil.bannerImgeOne(banner1,bean.getContent1().get(1));
//            BannerUtil.bannerImgeTwo(banner2,bean.getContent2().get(1));
            LoadImageUtil.loadImage(banner1,bean.getContent1().get(1));
            LoadImageUtil.loadImage(banner2,bean.getContent2().get(1));
        }
    }
    /**
     * 初始化布局信息
     */
    public void initView() {
        videoView_first = (VideoView) findViewById(R.id.vitamio_fir_top);
//        barFirst = (ProgressBar) findViewById(R.id.probar_first);
        banner1 = (ImageView) findViewById(R.id.banner_view_banner_top);
//        imge_top = (ImageView) findViewById(R.id.image_first_top);

        videoView_two = (VideoView) findViewById(R.id.vitamio_fir_bottom);
//        barTwo = (ProgressBar) findViewById(R.id.probar_two);
        banner2 = (ImageView) findViewById(R.id.banner_view_banner_mid);

        startTime();
    }


    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        dir.exists();
        deleteDirWihtFile(dir);
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
        }

    }
    /**
     * 定时发起网络请求
     */
    public  class TimerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            beans = (List<InfoModel.DataBean>) bundle.getSerializable("beans");
//            deleteDir(Environment.getExternalStorageDirectory() + "/DCIM");
            count=0;
            mHandle.sendEmptyMessage(1);
//            L.e("recevive---------------------------------------------------------->>>>>>>>>>>>>>>>>>>>>>>" + beans.get(0).getTime());
        }
    }
    long startTime = 0;

    /**
     * 结束程序
     */
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - startTime) >= 2000) {
            Toast.makeText(FirstModesActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            startTime = currentTime;
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            stopService(new Intent(FirstModesActivity.this, FirstModesActivity.TimerReceiver.class));
            unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



