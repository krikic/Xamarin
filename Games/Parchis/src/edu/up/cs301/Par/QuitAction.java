package edu.up.cs301.Par;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class QuitAction extends DialogFragment{
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are You Sure You Want To Quit?")
               .setPositiveButton("Yes, I'm Sure", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       System.exit(0);
                   }
               })
               .setNegativeButton("No, I Do Not", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
