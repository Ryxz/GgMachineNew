package service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;

import java.io.Serializable;
import java.util.List;

import bean.InfoModel;
import http.HttpRequest;
import http.ServerResultListener;
import utils.JsonUtil;
import utils.ToastUtil;

/**
 * 每小时进行网络请求的service
 */
public class LongRunningService extends Service {
    private List<InfoModel.DataBean> beans;
    private SharedPreferences preferences;
    private String  oldJson;


    @Override
    public void onCreate() {
        super.onCreate();
        mTimer.start();

    }

    public CountDownTimer mTimer  = new CountDownTimer(2*60*60*1000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

            preferences = getSharedPreferences("data_id",MODE_PRIVATE);
            String deviceId = preferences.getString("deviceid","");
            HttpRequest.getModeId(LongRunningService.this, deviceId, new ServerResultListener() {
                @Override
                public void onSuccess(String json, String msg) {
                    if (oldJson==null){
                        oldJson=json;
                        beans = JsonUtil.jsonToDto(oldJson, InfoModel.DataBean.class);
                        Intent intent = new Intent();
                        intent.putExtra("beans", (Serializable) beans);
                        intent.setAction("service.LongRunningService");
                        sendBroadcast(intent);
                    }else {
                        if (!oldJson.equals(json)){
                            beans = JsonUtil.jsonToDto(json, InfoModel.DataBean.class);
                            Intent intent = new Intent();
                            intent.putExtra("beans", (Serializable) beans);
                            intent.setAction("service.LongRunningService");
                            sendBroadcast(intent);
                        }
                        oldJson=json;
                    }

                }

                @Override
                public void onFailure(String msg) {
                    ToastUtil.showMessage("获取广告信息失败");
                }
            });

            mTimer.start();
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
