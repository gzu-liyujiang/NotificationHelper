package cn.qqtheme.NotifyHelperDemo;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
    }

    protected void onNewIntent(Intent paramIntent) {
        super.onNewIntent(paramIntent);
        String str = paramIntent.getAction();
        if ((str != null) && (str.equals(NotifyHelper.ACTION_NOTIFICATION_CLICK_INTENT)))
            Toast.makeText(this, paramIntent.getStringExtra("test"), Toast.LENGTH_SHORT).show();
    }

    public void showNotify(View paramView) {
        NotifyHelper.with(this)
                .autoCancel(true)
                .when(System.currentTimeMillis())
                .defaults(Notification.DEFAULT_LIGHTS)
                .title("Title")
                .message("Content Content Content Content Content")
                .ticker("New Message")
                .smallIcon(R.drawable.ic_launcher)
                .largeIcon(R.drawable.liyujiang)
                .show();
    }

    public void showNotifyWithData(View paramView) {
        Bundle data = new Bundle();
        data.putString("test", "hello");
        NotifyHelper.with(this)
                .ongoing(true)
                .autoCancel(true)
                .title("title")
                .message("this is content")
                .click(MainActivity.class, data)
                .show();
    }

    public void showNotifyCanClear(View paramView) {
        NotifyHelper.with(this)
                .identifier(10086)
                .flags(Notification.FLAG_NO_CLEAR)
                .title("title")
                .message("this is content")
                .show();
    }

    public void clearNotify(View paramView) {
        NotifyHelper.with(this).cancel(10086);
    }

}
