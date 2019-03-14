package http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    /**
     * 获取模板id
     * @param context
     * @param deviceId
     * @param serverResultListener
     */
    public static void getModeId(Context context,String deviceId,ServerResultListener serverResultListener) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",deviceId);
        ServerRequestUtil.requestHttp(context,ServerUrl.getModeIdUrl,map,"获取中...",serverResultListener);

    }

}
