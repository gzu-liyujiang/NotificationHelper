# What It ?
A lite of "pugnotification" for creating notifications in android platform.
# Thanks
https://github.com/halysongoncalves/pugnotification
# Sample
###example 1
```java
NotifyHelper.with(context)
 .autoCancel(true)
 .when(System.currentTimeMillis())
 .defaults(NotificationCompat.DEFAULT_LIGHTS)
 .title("Title")
 .message("Content Content Content Content Content")
 .ticker("New Message")
 .smallIcon(R.drawable.ic_launcher)
 .largeIcon(R.drawable.ic_launcher)
 .click(MyActivity.class)
 .show();
 ```
###example 2
```java
        Bundle data = new Bundle();
        data.putString("test", "hello");
        NotifyHelper.with(this)
                .ongoing(true)
                .autoCancel(true)
                .title("title")
                .message("this is content")
                .click(MainActivity.class, data)
                .show();
     protected void onNewIntent(Intent paramIntent) {
        super.onNewIntent(paramIntent);
        String str = paramIntent.getAction();
        if ((str != null) && (str.equals(NotifyHelper.ACTION_NOTIFICATION_CLICK_INTENT))) {
            Toast.makeText(this, paramIntent.getStringExtra("test"), Toast.LENGTH_SHORT).show();
         }
     }

 ```
###example 3
```java
     NotifyHelper.with(this)
                .identifier(10086)
                .flags(Notification.FLAG_NO_CLEAR)
                .title("title")
                .message("this is content")
                .show();


        NotifyHelper.with(this).cancel(10086);
 ```
