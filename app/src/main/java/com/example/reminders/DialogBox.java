package com.example.reminders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogBox extends AppCompatDialogFragment {
    private RemindersDbAdapter myDB;
    private EditText reminderMsg;
    private CheckBox isImportant;
    private TextView addEditTitle;
    private Button commit;
    private Button cancel;
    private String title;
    private Reminder reminder;
    private RemindersSimpleCursorAdapter adapter;
    private ListView listView;

    public DialogBox(RemindersDbAdapter myDB, Reminder reminder, RemindersSimpleCursorAdapter adapter, ListView listview){
        this.myDB = myDB;
        this.adapter=adapter;
        this.listView=listview;
        // If reminder == null then it's new reminder
        if (reminder == null)
            title=" New Reminder";
        else
            title = " Edit Reminder";
        this.reminder = reminder;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_box,null);
        builder.setView(view);
        reminderMsg = view.findViewById(R.id.add_edit_remainder);
        isImportant = view.findViewById(R.id.checkBox);
        addEditTitle = view.findViewById(R.id.addEditTitle);
        cancel = view.findViewById(R.id.cancel_button);
        commit = view.findViewById(R.id.commit_button);
        // Put content into editText if edit
        if (reminder != null) {
            reminderMsg.setText(reminder.getContent());
            boolean myBool = reminder.getImportant() > 0 ? true:false;
            isImportant.setChecked(myBool);
        }
        addEditTitle.setText(title);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If reminder == null then it's add
                if (reminder == null){
                    myDB.createReminder(reminderMsg.getText().toString(),isImportant.isChecked());
                    updateList();
                    Toast.makeText(getActivity().getApplicationContext(),"Reminder added successfully!",Toast.LENGTH_SHORT).show();
                }
                else {
                    reminder.setContent(reminderMsg.getText().toString());
                    int myInt = isImportant.isChecked() ? 1 : 0;
                    reminder.setImportant(myInt);
                    myDB.updateReminder(reminder);
                    updateList();
                    Toast.makeText(getActivity().getApplicationContext(),"Reminder updated successfully!",Toast.LENGTH_SHORT).show();

                }
                getDialog().dismiss();
            }
        });
        return builder.create();
    }

    public void updateList() {
        adapter.changeCursor(myDB.fetchAllReminders());
        listView.setAdapter(adapter);
    }
}
