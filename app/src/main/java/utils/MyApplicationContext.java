package utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.danikula.videocache.HttpProxyCacheServer;
import com.example.administrator.qtapplication.R;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.InfoModel;
import http.ServerRequestUtil;
import http.ServerResultListener;
import http.ServerUrl;
import io.vov.vitamio.Vitamio;

public class MyApplicationContext extends Application {

    public static MyApplicationContext myContext;
    private Boolean isFirst;
    private SharedPreferences sharedPreferences;
    private static final String TAG = "MyApplicationContext";
//    private Class activity;
    ArrayList<Activity> list = new ArrayList<Activity>();

//    private HttpProxyCacheServer proxy;
//
//    public static HttpProxyCacheServer getProxy(Context context) {
//        MyApplicationContext app = (MyApplicationContext) context.getApplicationContext();
//        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
//    }
//
//    private HttpProxyCacheServer newProxy() {
//        return new HttpProxyCacheServer(this);
//    }
    /**
     * 判断是否第一次启动APP
     * @return
     */
    public boolean isFirst() {
        isFirst = sharedPreferences.getBoolean("first",true);
        return isFirst;
    }
    @Override
    public void onCreate() {
        super.onCreate();
//        deleteDir(Environment.getExternalStorageDirectory() + "/DCIM");
        sharedPreferences = getSharedPreferences("GgMachine",0);
        myContext = this;
        Vitamio.isInitialized(this);
        UnCeHandler catchExcep = new UnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);


    }
    public void init(){
        //设置该CrashHandler为程序的默认处理器
        UnCeHandler catchExcep = new UnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }

    /**
     * Activity关闭时，删除Activity列表中的Activity对象*/
    public void removeActivity(Activity a){
        list.remove(a);
    }

    /**
     * 向Activity列表中添加Activity对象*/
    public void addActivity(Activity a){
        list.add(a);
    }

    /**
     * 关闭Activity列表中的所有Activity*/
    public void finishActivity(){
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


//    public Class getActivity() {
//        return activity;
//    }
//
//    public void setActivity(Class activity) {
//        this.activity = activity;
//    }

    public static MyApplicationContext getInstance() {
        if (myContext == null)
            return new MyApplicationContext();
        return myContext;
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
//            else if (file.isDirectory())
//                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }

//        dir.delete();// 删除目录本身
    }
    /**
     * 保存缓存用户信息
     *
     * @param beans
     */
    public void saveInfoBean(final List<InfoModel.DataBean> beans) {
        if (beans != null) {
            clearBeanInfo();
            saveGgInfoBeanInfoCache(beans);
        }
    }

    /**
     * 获取历史存储数据
     * @return
     */
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
    /**
     * 清除缓存用户信息数据
     */
    public  void clearBeanInfo() {
        new File(MyApplicationContext.getInstance().getCacheDir().getPath() + "/"
                + "gg_gginfo.bean").delete();
    }

    /**
     * 读取缓存数据
     *
     * @return
     */
    public static InfoModel.DataBean readInfoCache() {
        File file = new File(MyApplicationContext.getInstance().getCacheDir().getPath()
                + "/" + "gg_gginfo.bean");
        if (!file.exists())
            return null;

        if (file.isDirectory())
            return null;

        if (!file.canRead())
            return null;

        try {
            @SuppressWarnings("resource")
            InfoModel.DataBean dataBean = (InfoModel.DataBean) new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(file)))
                    .readObject();
            return dataBean;
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存用户信息至缓存
     *
     * @param beans
     */
    public static void saveGgInfoBeanInfoCache(List<InfoModel.DataBean> beans) {
        try {
            new ObjectOutputStream(new FileOutputStream(new File(MyApplicationContext
                    .getInstance().getCacheDir().getPath()
                    + "/" + "gg_gginfo.bean"))).writeObject(beans);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存用户信息
     *
     * @return
     */
    public InfoModel.DataBean getBean() {
        return readInfoCache() == null ? new InfoModel.DataBean() : readInfoCache();

    }

    /**
     * 判断网络连接状态，连接，则请求服务器
     * @param deviceId
     */
    public  void isNetConnecting(String deviceId){
        if (isNetworkAvailable(MyApplicationContext.getInstance())){
        } else {
            ToastUtil.showMessage(R.string.not_connect_net);
            return;
        }
    }
    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 失效
     * 获取广告模板的ID
     * @param key
     * @param value
     * @param dAdvertId
     */
    public  void getDeviceId(final String key, final String value, final String dAdvertId) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        //请求模板id
        ServerRequestUtil.requestHttp(myContext, ServerUrl.getModeIdUrl, map, "获取中", new ServerResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                L.e("模板id:", json);
            }

            @Override
            public void onFailure(String msg) {
                L.e("error:", msg);
            }
        });

    }
}