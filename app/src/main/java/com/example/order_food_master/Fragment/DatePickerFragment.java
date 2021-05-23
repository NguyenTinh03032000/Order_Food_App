package com.example.order_food_master.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.order_food_master.R;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,day,month,year);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        EditText etBirthday = (EditText) getActivity().findViewById(R.id.et_birthday);
        String birthday = dayOfMonth + "/" + (month + 1) + "/" + year;
        etBirthday.setText(birthday);
    }
}
