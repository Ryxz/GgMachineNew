package utils;

import android.widget.Toast;

public class ToastUtil {

    public static Toast mToast;

    public ToastUtil() {

    }

    /**
     * 显示Toast形式提示信息
     * @param resId
     */
    public static void showMessage(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplicationContext.myContext, resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }
    /**
     * 显示Toast形式提示信息
     * @param resString
     */
    public static void showMessage(String resString) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplicationContext.myContext, resString, Toast.LENGTH_LONG);
        } else {
            mToast.setText(resString);
        }
        mToast.show();
    }

}
