package cn.qqtheme.NotifyHelperDemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Spanned;

import java.util.HashMap;
import java.util.Random;

/**
 * 参见：https://github.com/halysongoncalves/pugnotification
 * <p/>
 * 示例:
 * NotifyHelper.with(context)
 * .autoCancel(true)
 * .when(System.currentTimeMillis())
 * .defaults(NotificationCompat.DEFAULT_LIGHTS)
 * .title("标题")
 * .message("内容内容内容内容")
 * .ticker("有新消息")
 * .smallIcon(R.drawable.ic_launcher)
 * .largeIcon(R.drawable.ic_launcher)
 * .click(MyActivity.class)
 * .show();
 *
 * @author 李玉江[QQ:1032694760]
 * @since 2015-08-10 17:79
 */
public class NotifyHelper {
    public static final String ACTION_NOTIFICATION_CLICK_INTENT = "liyujiang.notify.intent.action.CLICK";
    public static final String ACTION_NOTIFICATION_DISMISS_INTENT = "liyujiang.notify.intent.action.DISMISS";
    private static NotifyHelper singleton = null;
    private Context context;
    private NotificationCompat.Builder builder;
    private int notificationId;
    private HashMap<Integer, Integer> flagMap = new HashMap<Integer, Integer>();

    private NotifyHelper(Context context) {
        this.context = context;
        notificationId = (new Random()).nextInt(9999);
        builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("");
        builder.setContentText("");
        builder.setSmallIcon(android.R.drawable.sym_action_chat);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(PendingIntent.getBroadcast(context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public static NotifyHelper with(Context context) {
        if (singleton == null) {
            synchronized (NotifyHelper.class) {
                singleton = new NotifyHelper(context);
            }
        }
        return singleton;
    }

    public NotifyHelper identifier(int id) {
        if (id <= 0) {
            throw new IllegalStateException("Notification ID Should Not Be Less Than Or Equal To Zero!");
        }
        flagMap.put(id, flagMap.get(this.notificationId));
        this.notificationId = id;
        return this;
    }

    public NotifyHelper flags(int flags) {
        flagMap.put(notificationId, flags);
        return this;
    }

    public NotifyHelper title(@StringRes int title) {
        if (title <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        builder.setContentTitle(context.getResources().getString(title));
        return this;
    }

    public NotifyHelper title(String title) {
        if (title == null) {
            throw new IllegalStateException("Title Must Not Be Null!");
        }
        if (title.trim().length() == 0) {
            throw new IllegalArgumentException("Title Must Not Be Empty!");
        }
        builder.setContentTitle(title);
        return this;
    }

    public NotifyHelper message(@StringRes int message) {
        if (message <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        builder.setContentText(context.getResources().getString(message));
        return this;
    }

    public NotifyHelper message(@NonNull String message) {
        if (message.trim().length() == 0) {
            throw new IllegalArgumentException("Message Must Not Be Empty!");
        }
        builder.setContentText(message);
        return this;
    }

    public NotifyHelper message(@NonNull Spanned message) {
        if (message.length() == 0) {
            throw new IllegalArgumentException("Message Must Not Be Empty!");
        }
        builder.setContentText(message);
        return this;
    }

    public NotifyHelper color(int color) {
        if (color <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        builder.setColor(color);
        return this;
    }

    public NotifyHelper ticker(@StringRes int ticker) {
        if (ticker <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        builder.setTicker(context.getResources().getString(ticker));
        return this;
    }

    public NotifyHelper ticker(String ticker) {
        if (ticker == null) {
            throw new IllegalStateException("Ticker Must Not Be Null!");
        }
        if (ticker.trim().length() == 0) {
            throw new IllegalArgumentException("Ticker Must Not Be Empty!");
        }
        builder.setTicker(ticker);
        return this;
    }

    public NotifyHelper when(long when) {
        if (when <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        builder.setWhen(when);
        return this;
    }

    public NotifyHelper bigTextStyle(@StringRes int bigTextStyle) {
        if (bigTextStyle <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        return bigTextStyle(context.getResources().getString(
                bigTextStyle), null);
    }

    public NotifyHelper bigTextStyle(@StringRes int bigTextStyle, @StringRes int summaryText) {
        if (bigTextStyle <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        return bigTextStyle(context.getResources().getString(
                bigTextStyle), context.getResources().getString(
                summaryText));
    }


    public NotifyHelper bigTextStyle(@NonNull String bigTextStyle) {
        if (bigTextStyle.trim().length() == 0) {
            throw new IllegalArgumentException("Big Text Style Must Not Be Empty!");
        }
        return bigTextStyle(bigTextStyle, null);
    }

    public NotifyHelper bigTextStyle(@NonNull String bigTextStyle, String summaryText) {
        if (bigTextStyle.trim().length() == 0) {
            throw new IllegalArgumentException("Big Text Style Must Not Be Empty!");
        }
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText(bigTextStyle);
        if (summaryText != null) {
            bigStyle.setSummaryText(summaryText);
        }
        builder.setStyle(bigStyle);
        return this;
    }

    public NotifyHelper bigTextStyle(@NonNull Spanned bigTextStyle, String summaryText) {
        if (bigTextStyle.length() == 0) {
            throw new IllegalArgumentException("Big Text Style Must Not Be Empty!");
        }
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText(bigTextStyle);
        if (summaryText != null) {
            bigStyle.setSummaryText(summaryText);
        }
        builder.setStyle(bigStyle);
        return this;
    }

    public NotifyHelper inboxStyle(@NonNull String[] inboxLines, @NonNull String title, String summary) {
        if (inboxLines.length <= 0) {
            throw new IllegalArgumentException("Inbox Lines Must Have At Least One Text!");
        }
        if (title.trim().length() == 0) {
            throw new IllegalArgumentException("Title Must Not Be Empty!");
        }
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for (String inboxLine : inboxLines) {
            inboxStyle.addLine(inboxLine);
        }
        inboxStyle.setBigContentTitle(title);
        if (summary != null) {
            inboxStyle.setSummaryText(summary);
        }
        builder.setStyle(inboxStyle);
        return this;
    }

    public NotifyHelper autoCancel(boolean autoCancel) {
        builder.setAutoCancel(autoCancel);
        return this;
    }

    public NotifyHelper ongoing(boolean ongoing) {
        builder.setOngoing(ongoing);
        return this;
    }

    public NotifyHelper smallIcon(@DrawableRes int smallIcon) {
        if (smallIcon <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        builder.setSmallIcon(smallIcon);
        return this;
    }

    public NotifyHelper largeIcon(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap Must Not Be Null.");
        }
        builder.setLargeIcon(bitmap);
        return this;
    }

    public NotifyHelper largeIcon(@DrawableRes int largeIcon) {
        if (largeIcon <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), largeIcon);
        builder.setLargeIcon(bitmap);
        return this;
    }

    public NotifyHelper group(@NonNull String groupKey) {
        if (groupKey.trim().length() == 0) {
            throw new IllegalArgumentException("Group Key Must Not Be Empty!");
        }
        builder.setGroup(groupKey);
        return this;
    }

    public NotifyHelper groupSummary(boolean groupSummary) {
        builder.setGroupSummary(groupSummary);
        return this;
    }

    public NotifyHelper number(int number) {
        builder.setNumber(number);
        return this;
    }

    public NotifyHelper vibrate(long[] vibrate) {
        for (long aVibrate : vibrate) {
            if (aVibrate <= 0) {
                throw new IllegalArgumentException("Vibrate Time " + aVibrate + " Invalid!");
            }
        }
        builder.setVibrate(vibrate);
        return this;
    }

    public NotifyHelper lights(int color, int ledOnMs, int ledOfMs) {
        if (ledOnMs < 0) {
            throw new IllegalStateException("Led On Milliseconds Invalid!");
        }
        if (ledOfMs < 0) {
            throw new IllegalStateException("Led Off Milliseconds Invalid!");
        }
        builder.setLights(color, ledOnMs, ledOfMs);
        return this;
    }

    public NotifyHelper sound(Uri sound) {
        if (sound == null) {
            throw new IllegalArgumentException("Sound Must Not Be Null.");
        }
        builder.setSound(sound);
        return this;
    }

    public NotifyHelper onlyAlertOnce(boolean onlyAlertOnce) {
        builder.setOnlyAlertOnce(onlyAlertOnce);
        return this;
    }

    public NotifyHelper addPerson(@NonNull String uri) {
        if (uri.length() == 0) {
            throw new IllegalArgumentException("URI Must Not Be Empty!");
        }
        builder.addPerson(uri);
        return this;
    }

    public NotifyHelper button(@DrawableRes int icon, String title, PendingIntent pendingIntent) {
        if (icon < 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        if (title == null) {
            throw new IllegalStateException("Title Must Not Be Null!");
        }
        if (pendingIntent == null) {
            throw new IllegalArgumentException("PendingIntent Must Not Be Null.");
        }
        builder.addAction(icon, title, pendingIntent);
        return this;
    }

    public NotifyHelper button(@DrawableRes int icon, String title, PendingIntentCallback pendingIntentCallback) {
        if (icon < 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        if (title == null) {
            throw new IllegalStateException("Title Must Not Be Null!");
        }
        if (pendingIntentCallback == null) {
            throw new IllegalArgumentException("PendingIntentNotification Must Not Be Null.");
        }
        builder.addAction(icon, title, pendingIntentCallback.getPendingIntent());
        return this;
    }

    public NotifyHelper button(NotificationCompat.Action action) {
        if (action == null) {
            throw new IllegalArgumentException("Action Must Not Be Null.");
        }
        builder.addAction(action);
        return this;
    }

    public NotifyHelper click(Class<?> activity, Bundle bundle) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity Must Not Be Null.");
        }
        builder.setContentIntent(new ClickPendingIntentActivity(activity, bundle, notificationId).getPendingIntent());
        return this;
    }

    public NotifyHelper click(Class<?> activity) {
        click(activity, null);
        return this;
    }

    public NotifyHelper click(Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Bundle Must Not Be Null.");
        }
        builder.setContentIntent(new ClickPendingIntentBroadcast(bundle, notificationId).getPendingIntent());
        return this;
    }

    public NotifyHelper click(PendingIntentCallback pendingIntentCallback) {
        if (pendingIntentCallback == null) {
            throw new IllegalArgumentException("PendingIntentNotification Must Not Be Null.");
        }
        builder.setContentIntent(pendingIntentCallback.getPendingIntent());
        return this;
    }

    public NotifyHelper priority(int priority) {
        if (priority <= 0) {
            throw new IllegalArgumentException("Priority Should Not Be Less Than Or Equal To Zero!");
        }
        builder.setPriority(priority);
        return this;
    }

    public NotifyHelper defaults(int defaults) {
        if (defaults < 0) {
            throw new IllegalArgumentException("Defaults Should Not Be Less Than Or Equal!");
        }
        builder.setDefaults(defaults);
        return this;
    }


    public NotifyHelper click(@NonNull PendingIntent pendingIntent) {
        builder.setContentIntent(pendingIntent);
        return this;
    }

    public NotifyHelper dismiss(Class<?> activity, Bundle bundle) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity Must Not Be Null.");
        }
        builder.setDeleteIntent(new DismissPendingIntentActivity(activity, bundle, notificationId).getPendingIntent());
        return this;
    }

    public NotifyHelper dismiss(Class<?> activity) {
        dismiss(activity, null);
        return this;
    }

    public NotifyHelper dismiss(Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Bundle Must Not Be Null.");
        }
        builder.setDeleteIntent(new DismissPendingIntentBroadcast(bundle, notificationId).getPendingIntent());
        return this;
    }

    public NotifyHelper dismiss(PendingIntentCallback pendingIntentCallback) {
        if (pendingIntentCallback == null) {
            throw new IllegalArgumentException("Pending Intent Notification Must Not Be Null.");
        }
        builder.setDeleteIntent(pendingIntentCallback.getPendingIntent());
        return this;
    }

    public NotifyHelper dismiss(@NonNull PendingIntent pendingIntent) {
        builder.setDeleteIntent(pendingIntent);
        return this;
    }

    public Notification show() {
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        Notification notification = builder.build();
        if (flagMap.containsKey(notificationId)) {
            notification.flags = flagMap.get(notificationId);
        }
        nm.notify(notificationId, notification);
        singleton = null;//reset, so can build new notification
        return notification;
    }

    public void cancel() {
        cancel(notificationId);
    }

    public void cancel(int id) {
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.cancel(id);
    }

    public interface PendingIntentCallback {
        PendingIntent getPendingIntent();
    }

    public static class ClickPendingIntentActivity implements PendingIntentCallback {
        private final Class<?> activity;
        private final Bundle bundle;
        private final int identifier;

        public ClickPendingIntentActivity(Class<?> activity, Bundle bundle, int identifier) {
            this.activity = activity;
            this.bundle = bundle;
            this.identifier = identifier;
        }

        @Override
        public PendingIntent getPendingIntent() {
            Intent clickIntentActivity = new Intent(singleton.context, activity);
            clickIntentActivity.setAction(ACTION_NOTIFICATION_CLICK_INTENT);
            clickIntentActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            clickIntentActivity.setPackage(singleton.context.getPackageName());
            if (bundle != null) {
                clickIntentActivity.putExtras(bundle);
            }
            return PendingIntent.getActivity(singleton.context, identifier, clickIntentActivity,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

    }

    public static class DismissPendingIntentActivity implements PendingIntentCallback {
        private final Class<?> activity;
        private final Bundle bundle;
        private final int identifier;

        public DismissPendingIntentActivity(Class<?> activity, Bundle bundle, int identifier) {
            this.activity = activity;
            this.bundle = bundle;
            this.identifier = identifier;
        }

        @Override
        public PendingIntent getPendingIntent() {
            Intent dismissIntentActivity = new Intent(singleton.context, activity);
            dismissIntentActivity.setAction(ACTION_NOTIFICATION_DISMISS_INTENT);
            dismissIntentActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            dismissIntentActivity.setPackage(singleton.context.getPackageName());
            if (bundle != null) {
                dismissIntentActivity.putExtras(bundle);
            }
            return PendingIntent.getActivity(singleton.context, identifier, dismissIntentActivity,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

    }

    public static class ClickPendingIntentBroadcast implements PendingIntentCallback {
        private final Bundle bundle;
        private final int identifier;

        public ClickPendingIntentBroadcast(Bundle bundle, int identifier) {
            this.bundle = bundle;
            this.identifier = identifier;
        }

        @Override
        public PendingIntent getPendingIntent() {
            Intent clickIntentBroadcast = new Intent(ACTION_NOTIFICATION_CLICK_INTENT);
            clickIntentBroadcast.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            clickIntentBroadcast.setPackage(singleton.context.getPackageName());
            if (bundle != null) {
                clickIntentBroadcast.putExtras(bundle);
            }
            return PendingIntent.getBroadcast(singleton.context, identifier, clickIntentBroadcast,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

    }

    public static class DismissPendingIntentBroadcast implements PendingIntentCallback {
        private final Bundle bundle;
        private final int identifier;

        public DismissPendingIntentBroadcast(Bundle bundle, int identifier) {
            this.bundle = bundle;
            this.identifier = identifier;
        }

        @Override
        public PendingIntent getPendingIntent() {
            Intent clickIntentBroadcast = new Intent(ACTION_NOTIFICATION_DISMISS_INTENT);
            clickIntentBroadcast.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            clickIntentBroadcast.setPackage(singleton.context.getPackageName());
            if (bundle != null) {
                clickIntentBroadcast.putExtras(bundle);
            }
            return PendingIntent.getBroadcast(singleton.context, identifier, clickIntentBroadcast,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

    }

}
