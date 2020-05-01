/**
 * <h1> creditDialog </h1>
 * This is a auto-generated code for the created by button.
 * Created by: Daniel Ramirez, Robert Sosa
 * Date: 3/25/2020
 */

package com.example.bomb_defuser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class creditDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Created By")
                .setMessage("The Amazing Robert Sosa and The Incredible Daniel Ramirez")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //It will do nothing when ok is pressed
                    }
                });
        return builder.create();
    }
}
