package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.advertisingmachine.qtapplication.WelcomeActivity;

import service.LongRunningService;

/**
 * Created by Ryx on 2019/2/18.
 * 开机自启的广播
 */
public class MyReciver extends BroadcastReceiver {
    static final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";
    static final String ALARM_ACTION = "time.TimerReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BOOT_ACTION)) {
            Intent bootIntent = new Intent(context, WelcomeActivity.class);
            bootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(bootIntent);
        }
    }
}
