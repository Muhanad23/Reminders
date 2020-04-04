package com.example.reminders;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private RemindersDbAdapter myDB;
    private ListView listView;
    private RemindersSimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB=new RemindersDbAdapter(this);
        myDB.open();
        final String[] from = { RemindersDbAdapter.COL_ID, RemindersDbAdapter.COL_CONTENT, RemindersDbAdapter.COL_IMPORTANT};
        final Cursor cursor = myDB.fetchAllReminders();
        adapter = new RemindersSimpleCursorAdapter(this,
                R.layout.reminders_raw, cursor, new String[] {RemindersDbAdapter.COL_CONTENT}, new int[]{R.id.reminderMsg}
                , 0);
        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor myCursor = adapter.getCursor();
                myCursor.moveToPosition(position);
                Reminder reminder = myDB.fetchReminderById(myCursor.getInt(0));
                openSimpleDialog(reminder,myDB);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_reminder:
                openNewDialog(null,myDB);
                break;
            case R.id.exit_app:
                myDB.close();
                finish();
                System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }
    public void openNewDialog(Reminder reminder, RemindersDbAdapter myDB){
        DialogBox dialog = new DialogBox(myDB,reminder,adapter,listView);
        dialog.show(getSupportFragmentManager(),"New reminder");
    }

    public void openSimpleDialog(Reminder reminder,RemindersDbAdapter myDB){
        SimpleList dialog = new SimpleList(reminder,myDB,adapter,listView);
        dialog.show(getSupportFragmentManager(),"New reminder");
    }

}
