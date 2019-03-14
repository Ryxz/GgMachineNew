package http;

/**
 * 服务器返回结果监听
 */

public abstract class ServerResultListener {

    /**
     * 请求成功
     *
     * @param json
     */
    public abstract void onSuccess(String json, String msg);

    /**
     * 请求失败
     */
    public abstract void onFailure(String msg);


}

