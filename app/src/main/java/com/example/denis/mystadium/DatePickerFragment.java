package com.example.denis.mystadium;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;

import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Utilisateur on 07-12-16.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public DatePickerFragment(){

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
