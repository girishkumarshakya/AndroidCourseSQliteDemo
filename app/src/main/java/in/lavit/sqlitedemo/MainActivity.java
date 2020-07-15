package in.lavit.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername,editTextDesignation;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnAdd,btnRetrive,btnUpdate,btnDelete;

    private ArrayList<Employee> employeeArrayList;
    private MyAdapter adapter;

    MyDatabase myDatabase ;
    SQLiteDatabase sq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabase = new MyDatabase(this);
        editTextUsername = findViewById(R.id.editTextName);
        editTextDesignation = findViewById(R.id.editTextDesignation);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);;
        recyclerView.setHasFixedSize(true);
        btnAdd = findViewById(R.id.buttonCreate);
        btnDelete = findViewById(R.id.buttonDelete);
        btnRetrive = findViewById(R.id.buttonRet);
        btnUpdate = findViewById(R.id.buttonUpdate);
    }

    public void add(View view) {
        sq = myDatabase.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MyDatabase.COL_NAME,editTextUsername.getText().toString());
        cv.put(MyDatabase.COL_DESIG,editTextDesignation.getText().toString());

        sq.insert(MyDatabase.TABLE_NAME,null,cv);
        editTextUsername.setText("");
        editTextDesignation.setText("");
        Toast.makeText(this, "Employee added...", Toast.LENGTH_SHORT).show();
    }

    public void retrive(View view) {
        employeeArrayList = new ArrayList<>();
        adapter = new MyAdapter(this,employeeArrayList,editTextUsername,editTextDesignation);
        recyclerView.setAdapter(adapter);

        sq = myDatabase.getReadableDatabase();
        Cursor cursor = sq.rawQuery("select * from "+MyDatabase.TABLE_NAME,null);
        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            String desig = cursor.getString(1);
            Employee employee = new Employee();
            employee.setName(name);
            employee.setDesignation(desig);
            employeeArrayList.add(employee);
            adapter.notifyDataSetChanged();
        }
    }

    public void update(View view) {
       String desig = editTextDesignation.getText().toString();
       sq = myDatabase.getWritableDatabase();
       ContentValues cv = new ContentValues();
       cv.put(MyDatabase.COL_DESIG,desig);
       sq.update(MyDatabase.TABLE_NAME,cv,MyDatabase.COL_NAME+"='"+editTextUsername.getText().toString()+"'",null);
       editTextUsername.setText("");
       editTextDesignation.setText("");
        Toast.makeText(this, "Database Updated...", Toast.LENGTH_SHORT).show();
    }

    public void delete(View view) {
    }
}
