package http;

import org.json.JSONObject;


/**
 * 根据指定的返回规则
 */
public abstract class HttpCallBackImpl implements OKHttpUtils.HttpCallBack {

    @Override
    public final void onResult(boolean success, String response) {
        try {
            if (success) {
                JSONObject jsonObject = new JSONObject(response);
                Object data;
                if (jsonObject.isNull("data")) {
                    data = "";
                } else {
                    data = jsonObject.get("data");
                }

//                if (jsonObject.getBoolean("success")) {
                onRequestResult(true, jsonObject.getString("message"), data);
//                } else {
//                    onRequestResult(false, jsonObject.getString("message"), data);
            }
//            } else {
//                onRequestResult(false, response, null);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public abstract void onRequestResult(boolean success, String message, Object data);
}
