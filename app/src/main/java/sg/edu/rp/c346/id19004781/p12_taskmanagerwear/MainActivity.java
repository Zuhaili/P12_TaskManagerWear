package sg.edu.rp.c346.id19004781.p12_taskmanagerwear;

import android.annotation.SuppressLint;
import android.app.PendingIntent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvTasks;
    Button btnAddTask;
    ArrayAdapter aa;
    ArrayList<Task> alTasks;

    Intent intent , intentReply;
    PendingIntent pendingIntentReply;

    @SuppressLint("UnspecifiedImmutableFlag")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTasks = findViewById(R.id.lvTasks);
        btnAddTask = findViewById(R.id.btnAddTask);

        DBHelper dbh = new DBHelper(MainActivity.this);
        alTasks = new ArrayList<>();
        alTasks = dbh.getAllTasks();
        dbh.close();

        aa = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, alTasks);
        lvTasks.setAdapter(aa);

        btnAddTask.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddActivity.class);
            startActivity(i);
        });

        intentReply = new Intent(MainActivity.this,
                ReplyActivity.class);

        pendingIntentReply = PendingIntent.getActivity
                (MainActivity.this, 0, intentReply,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput ri = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String[]{"Done", "Not yet"})
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
        extender.addAction(action2);

    }

    @Override
    protected void onResume() {
        super.onResume();

        alTasks = new ArrayList<>();
        DBHelper dbh = new DBHelper(MainActivity.this);
        alTasks.addAll(dbh.getAllTasks());
        dbh.close();
//        aa = new TaskAdapter(MainActivity.this, R.layout.row, alTasks);
        aa = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, alTasks);
        lvTasks.setAdapter(aa);
    }
}