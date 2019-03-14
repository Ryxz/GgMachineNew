package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import service.LongRunningService;

/**
 * 定时发送网络请求
 */
public class AlarmReceiver extends BroadcastReceiver {
        static final String ALARM_ACTION = "time.TimerReceiver";
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(ALARM_ACTION)) {
                Intent i = new Intent(context, LongRunningService.class);
                context.startService(i);
            }

        }

}
