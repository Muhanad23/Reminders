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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogBox extends AppCompatDialogFragment {
    private EditText reminderMsg;
    private CheckBox isImportant;
    private TextView addEditTitle;
    private Button commit;
    private Button cancel;
    private String title;

    public DialogBox(String s){
        this.title = s;
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
                addEditTitle.setText(reminderMsg.getText());
                getDialog().dismiss();
            }
        });
        return builder.create();
    }
}
