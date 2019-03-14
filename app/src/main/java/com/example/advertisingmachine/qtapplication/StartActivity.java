package com.example.advertisingmachine.qtapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.qtapplication.R;
import java.io.Serializable;
import java.util.List;

import bean.InfoModel;
import http.HttpRequest;
import http.ServerResultListener;
import utils.JsonUtil;
import utils.MyApplicationContext;
import utils.ToastUtil;

public class StartActivity extends AppCompatActivity {
    private String deviceId;
    private BindDialog dialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private EditText editText;
    private List<InfoModel.DataBean> beans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = this.getSharedPreferences("data_id", MODE_PRIVATE);
        deviceId = preferences.getString("deviceid", "");
        if (MyApplicationContext.getInstance().isFirst()) {
            MyApplicationContext.getInstance().getSharedPreferences().edit().putBoolean("first",false).apply();
            doFirstData();
        } else {
            doData();
        }
    }

    /**
     * 第一次启动
     * 弹出输入框
     * 处理输入框点击逻辑
     */
    private void doFirstData() {

        dialog = new BindDialog(StartActivity.this, new BindDialog.LeaveLisenner() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_bind:

                        editText = (EditText) dialog.findViewById(R.id.et_mac);
                        deviceId = editText.getText().toString();
                        editor = preferences.edit();
                        editor.putString("deviceid", deviceId);
                        editor.commit();

                        if (MyApplicationContext.isNetworkAvailable(StartActivity.this)) {
                            requestInfo(deviceId);
                        } else {
                            ToastUtil.showMessage(R.string.not_connect_net);
                            return;
                        }
                        break;
                    case R.id.btn_cancel:
                        MyApplicationContext.getInstance().getSharedPreferences().edit().putBoolean("first", true).apply();
                        finish();
                        dialog.dismiss();
                        break;
                }

            }

        });
        dialog.show();
    }
    /**
     * 不是第一次启动
     */
    private void doData() {
        if (MyApplicationContext.isNetworkAvailable(this)) {
            requestInfo(deviceId);
        } else {
            ToastUtil.showMessage(R.string.not_connect_net);
        }

    }
    /**
     * 请求服务器，处理结果
     * @param deviceId
     */
    public void requestInfo(final String deviceId) {
        HttpRequest.getModeId(StartActivity.this, deviceId, new ServerResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json.equals("")) {
                    ToastUtil.showMessage("设备码错误，请重新输入");
                    dialog.clearEditText();
                } else {
                    beans = JsonUtil.jsonToDto(json, InfoModel.DataBean.class);
                    nextActivity(beans);
                    MyApplicationContext.getInstance().getSharedPreferences().edit().putBoolean("first", false).apply();

                }

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.showMessage("获取广告信息失败");
            }
        });
    }

    /**
     * 根据服务器返回的json跳转不同模板
     *
     * @param mList
     */
    public void nextActivity(List<InfoModel.DataBean> mList) {
        int type = mList.get(0).getType();

        switch (type) {
            case 1:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataBean", (Serializable) mList);
                intent.setClass(StartActivity.this, FirstModesActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;

            case 2:
                Intent i = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("dataBean", (Serializable) mList);

                i.setClass(StartActivity.this, SecondModesActivity.class);
                i.putExtras(b);
                startActivity(i);
                finish();
                break;

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }

    }

}