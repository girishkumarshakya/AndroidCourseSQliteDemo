package in.lavit.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Employee> employeeArrayList;
    private EditText editTextUsername,editTextDesignation;


    public MyAdapter(Context context, ArrayList<Employee> employeeArrayList, EditText editTextUsername, EditText editTextDesignation){
        this.context = context;
        this.employeeArrayList = employeeArrayList;
        this.editTextDesignation = editTextDesignation;
        this.editTextUsername = editTextUsername;

    }
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, final int position) {
        holder.textViewName.setText(employeeArrayList.get(position).getName());
        holder.textViewDesig.setText(employeeArrayList.get(position).getDesignation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDesignation.setText(employeeArrayList.get(position).getDesignation());
                editTextUsername.setText(employeeArrayList.get(position).getName());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyDatabase myDatabase = new MyDatabase(context);
                SQLiteDatabase sq = myDatabase.getWritableDatabase();
                sq.delete(MyDatabase.TABLE_NAME,MyDatabase.COL_NAME+"='"+employeeArrayList.get(position).getName()+"'",null);
                Toast.makeText(context, "Deleted..", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewDesig;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDesig = itemView.findViewById(R.id.textViewDesig);
            textViewName = itemView.findViewById(R.id.textViewUN);
        }
    }
}
