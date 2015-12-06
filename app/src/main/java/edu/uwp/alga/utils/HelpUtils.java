package edu.uwp.alga.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import edu.uwp.alga.R;

/**
 * Created by Kelly on 12/6/2015.
 */
public class HelpUtils {
    public static void makeToast(Activity context, String message) {
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) context.findViewById(R.id.toastRoot));

        TextView text = (TextView) layout.findViewById(R.id.textToast);
        text.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
