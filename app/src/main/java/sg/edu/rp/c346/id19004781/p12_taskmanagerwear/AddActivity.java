package sg.edu.rp.c346.id19004781.p12_taskmanagerwear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    EditText etName, etDescription, etTime;
    Button btnAddTask, btnCancel;

    int notificationId = 001; // A unique ID for our notification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etTime = findViewById(R.id.etTime);
        btnAddTask = findViewById(R.id.btnAddTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                Integer time = Integer.parseInt(etTime.getText().toString());

                DBHelper db = new DBHelper(AddActivity.this);
                long insertId = db.addTask(name, description);
                db.close();

                if (insertId != -1) {
                    int notificationID = 888;
                    int requestCode = 123;

                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.SECOND, time);

                    Intent intent1 = new Intent(AddActivity.this,
                            ScheduledNotificationReceiver.class);

                    intent1.putExtra("name", name);
                    intent1.putExtra("description", description);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            AddActivity.this, requestCode,
                            intent1, PendingIntent.FLAG_CANCEL_CURRENT);

                    AlarmManager am = (AlarmManager)
                            getSystemService(AddActivity.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                            pendingIntent);

                    finish();
                }




                NotificationManager nm = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new
                            NotificationChannel("default", "Default Channel",
                            NotificationManager.IMPORTANCE_DEFAULT);

                    channel.setDescription("This is for default notification");
                    nm.createNotificationChannel(channel);
                }

                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent =
                        PendingIntent.getActivity(AddActivity.this, 0,
                                intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Action action = new
                        NotificationCompat.Action.Builder(
                        R.mipmap.ic_launcher,
                        "This is an Action",
                        pendingIntent).build();

                Intent intentreply = new Intent(AddActivity.this,
                        ReplyActivity.class);
                @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntentReply = PendingIntent.getActivity
                        (AddActivity.this, 0, intentreply,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                RemoteInput ri = new RemoteInput.Builder("status")
                        .setLabel("Status report")
                        .setChoices(new String [] {"Done", "Not yet"})
                        .build();

                NotificationCompat.Action action2 = new
                        NotificationCompat.Action.Builder(
                        R.mipmap.ic_launcher,
                        "Reply",
                        pendingIntentReply)
                        .addRemoteInput(ri)
                        .build();

                NotificationCompat.WearableExtender extender = new
                        NotificationCompat.WearableExtender();
                extender.addAction(action);
                extender.addAction(action2);

                String text = getString(R.string.basic_notify_msg);
                String title = getString(R.string.notification_title);

                NotificationCompat.Builder builder = new
                        NotificationCompat.Builder(AddActivity.this, "default");
                builder.setContentText(text);
                builder.setContentTitle(title);
                builder.setSmallIcon(android.R.drawable.btn_star_big_off);

                // Attach the action for Wear notification created above
                builder.extend(extender);

                Notification notification = builder.build();

                nm.notify(notificationId, notification);

            }
        });

    }

}