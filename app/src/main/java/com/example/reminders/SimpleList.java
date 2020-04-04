package com.example.reminders;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class SimpleList extends AppCompatDialogFragment {
    private RemindersSimpleCursorAdapter adapter;
    private ListView listView;
    private Reminder reminder;
    private RemindersDbAdapter myDB;
    public SimpleList(Reminder x,RemindersDbAdapter myDB,RemindersSimpleCursorAdapter adapter,ListView listView){
        this.adapter=adapter;
        this.listView=listView;
        this.reminder = x;
        this.myDB = myDB;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final CharSequence[] items = {"Edit","Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Edit if item 0 selected
                if(item == 0) {
                    openNewDialog(reminder, myDB);
                }
                else if (item == 1) {
                    myDB.deleteReminderById(reminder.getId());
                    updateList();
                    Toast.makeText(getActivity().getApplicationContext(),"Reminder deleted successfully!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();
    }

    private void openNewDialog(Reminder reminder, RemindersDbAdapter myDB){
        DialogBox dialog = new DialogBox(myDB,reminder,adapter,listView);
        dialog.show(getFragmentManager(),"New reminder");
    }

    public void updateList() {
        adapter.changeCursor(myDB.fetchAllReminders());
        listView.setAdapter(adapter);
    }
}
