package utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;

import com.danikula.videocache.HttpProxyCacheServer;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;


/**
 * @Author Ryx on 2018/2/18
 *
 */

public class PlayVideoUtil {
    private static final String GG_BASEURL = "http://47.92.254.172";
//    private static HttpProxyCacheServer proxy; //视频缓存代理

    /**
     * 视频播放
     * @param view
     * @param path
     */
    public static void play(final VideoView view, String path) {
        String url = GG_BASEURL + path;
//        proxy = MyApplicationContext.getProxy(mContext);
//        if (MediaFileUtil.isVideoFileType(url)) {
            Uri uri = Uri.parse(url);
            Uri ui = Uri.parse("cache:" + Environment.getExternalStorageDirectory() + "/DCIM/"+MD5Encrypt.MD5(url)+".mp4:" + uri);
            L.e("vitamio:", url);
            view.setVideoURI(ui);
//        String proxyUrl = proxy.getProxyUrl(url); //视频url拼接日期，实现按日更新
//        view.setVideoPath(proxyUrl); //为videoview设置播放路径，而不是设置播放url

//            view.setMediaController(new MediaController(mContext));
            view.start();
//            view.requestFocus();
//        } else {
//            ToastUtil.showMessage("后台添加数据错误！");
//        }


         //视频加载完成时发生
        view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
//                        mp.start();
            }
        });

        //视频播放出错时回调
        view.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                ToastUtil.showMessage("视频打开失败！");
                                  return false;
            }
        });

         //视频状态更新监听的回调
        view.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {

                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //缓存开始，判断是否正在播放；true:暂停
                        if (mp.isPlaying()) {
                            mp.pause();
//                            bar.setVisibility(View.VISIBLE);
                        }
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //缓存结束，开始播放，响应控件消失
                        mp.start();
//                        bar.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });

    }

}

