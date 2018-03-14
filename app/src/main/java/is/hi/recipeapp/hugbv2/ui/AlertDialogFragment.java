package is.hi.recipeapp.hugbv2.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Pétur Logi Pétursson on 3/12/2018.
 */

public class AlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("ERROR!")
                .setMessage("There has been an unexpected error in the application");

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
