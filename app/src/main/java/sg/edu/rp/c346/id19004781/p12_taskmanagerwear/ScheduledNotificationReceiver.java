package sg.edu.rp.c346.id19004781.p12_taskmanagerwear;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class ScheduledNotificationReceiver extends BroadcastReceiver {

    int reqCode = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getExtras().getString("name");
        String description = intent.getExtras().getString("description");

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel("default", "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, reqCode,
                i, PendingIntent.FLAG_CANCEL_CURRENT);

        // bigText
//      NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//      bigText.setBigContentTitle(name + " (bigtext)");
//      bigText.bigText(description + " (bigtext)\n");

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rare_penguin); // Bitmap for bigPicture

        // bigPicture
        NotificationCompat.BigPictureStyle bigPicture = new NotificationCompat.BigPictureStyle();
        bigPicture.bigPicture(bitmap);
        bigPicture.bigLargeIcon(null);
        bigPicture.setBigContentTitle("Task: " + name);
        bigPicture.setSummaryText("Description: " + description + "\n");

        // Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Task Manager Reminder");
        builder.setContentText(name);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentIntent(pIntent);
//      builder.setStyle(bigText); // bigText (expandable)
        builder.setStyle(bigPicture); // bigPicture
        builder.setLights(0xFFFF0000, 100, 100); // LED
//      builder.setLights(Color.RED, 3000, 3000); // LED (using Color class)
        builder.setDefaults(Notification.DEFAULT_ALL); // Sound
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 }); // Vibration
        builder.setAutoCancel(true);

        Notification n = builder.build();
        notificationManager.notify(123, n);


    }
}