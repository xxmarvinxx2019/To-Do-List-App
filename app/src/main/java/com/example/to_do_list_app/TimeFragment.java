package com.example.to_do_list_app;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

public class TimeFragment extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}
