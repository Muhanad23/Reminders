package com.example.reminders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogBox extends AppCompatDialogFragment {
    private RemindersDbAdapter myDB;
    private EditText reminderMsg;
    private CheckBox isImportant;
    private TextView addEditTitle;
    private Button commit;
    private Button cancel;
    private String title;

    public DialogBox(int val,RemindersDbAdapter myDB){
        this.myDB = myDB;
        // If val == 0 then it's an add reminder else it contains the id of the reminder to be edited
        if (val == 0)
            title = " New Reminder";
        else {
            title = " Edit Reminder";
        }
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
                // TODO: add the reminder to db
                myDB.createReminder(reminderMsg.getText().toString(),isImportant.isChecked());

                getDialog().dismiss();
            }
        });
        return builder.create();
    }
}
