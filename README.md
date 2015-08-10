# What ?
A lite of ¡°pugnotification¡± for creating notifications in android platform.
# Thanks
https://github.com/halysongoncalves/pugnotification
# Sample
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
