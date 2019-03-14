package utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

//import com.danikula.videocache.HttpProxyCacheServer;

/**
 * 已弃用
 * Created by Ryx on 2019/3/4.
 */
public class VideoViewUtil  {
//    private static final String GG_BASEURL = "http://47.92.254.172";
//
//
//    public static void playVideoTop(Context context,final VideoView view, String path) {
//        HttpProxyCacheServer proxy = getProxy();
//
//        String url = GG_BASEURL+path;
//
//        String proxyUrl = proxy.getProxyUrl(url);
//
//        L.e("videoview:",proxyUrl);
//        view.setVideoPath(proxyUrl);
//        MediaController controller = new MediaController(context);
//        controller.setVisibility(View.GONE);
//        view.requestFocus();
//        view.start();
//        view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                view.start();
//            }
//        });
////        view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
////            @Override
////            public void onCompletion(MediaPlayer mp) {
//////                view.stopPlayback();
////            }
////        });
//    }
//    private static HttpProxyCacheServer getProxy() {
//        return MyApplicationContext.getProxy(MyApplicationContext.getInstance());
//    }
}
