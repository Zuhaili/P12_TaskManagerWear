package sg.edu.rp.c346.id19004781.p12_taskmanagerwear;//package sg.edu.rp.c346.id19004781.c347_ps11_taskmanager;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class TaskAdapter extends ArrayAdapter<Task> {
//
//    ArrayList<Task> tasks;
//    Context context;
//    TextView tvCombine, tvDescription;
//
//    public TaskAdapter(Context context, int resource, ArrayList<Task> objects) {
//        super(context, resource, objects);
//        tasks = objects;
//        this.context = context;
//    }
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.row, parent, false);
//
//        tvCombine = rowView.findViewById(R.id.tvCombine);
//        tvDescription = rowView.findViewById(R.id.tvDescription);
//
//        Task currentTask = tasks.get(position);
//
//        tvCombine.setText(currentTask.getId() + " " + currentTask.getName());
//        tvDescription.setText(currentTask.getDesc());
//
//        return rowView;
//    }
//}