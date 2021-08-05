package sg.edu.rp.c346.id19004781.p12_taskmanagerwear;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    EditText etName, etDescription, etTime;
    Button btnAddTask, btnCancel;

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
            }
        });

    }

}