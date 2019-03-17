package com.examples.android.androidtrainingbzu;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.examples.android.androidtrainingbzu.Models.Employee;
import com.examples.android.androidtrainingbzu.Utils.Utils;

import java.util.Calendar;

/**
 * A fragment representing a single Employee detail screen.
 * This fragment is either contained in a {@link HR_MainActivity}
 * in two-pane mode (on tablets) or a {@link EmployeeDetailActivity}
 * on handsets.
 */
public class EmployeeDetailFragment extends Fragment
        implements DatePickerDialog.OnDateSetListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private TextView txtvu_hireDate;
    /**
     * The dummy content this fragment is presenting.
     */
    private Employee mItem;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EmployeeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            int empIndex = getArguments().getInt(ARG_ITEM_ID);
            if(empIndex>-1)
            mItem = HR_MainActivity.employeeLinkedList.get(empIndex);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                if(empIndex>-1)
                appBarLayout.setTitle(mItem.getName());
                else
                    appBarLayout.setTitle("New Emp!");

            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.employee_info_edit, container, false);

        txtvu_hireDate = ((TextView) rootView.findViewById(R.id.edt_date));
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((ImageView) getActivity().findViewById(R.id.imgvu_emp_pic)).setImageResource(mItem.getPicResID());
            ((EditText) rootView.findViewById(R.id.edt_name)).setText(mItem.getName());
            ((EditText) rootView.findViewById(R.id.edt_email)).setText(mItem.getEmail());
            ((EditText) rootView.findViewById(R.id.edt_phone)).setText(mItem.getPhone());
            ((EditText) rootView.findViewById(R.id.edt_address)).setText(mItem.getAddress());
            txtvu_hireDate.setText(Utils.getDateFormatedString(mItem.getHireDate()));
        }
        txtvu_hireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year, month, day;
                String[] date = txtvu_hireDate.getText().toString().split("\\.");
                if (date.length != 3) {
                    Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);
                } else {
                    year = Integer.parseInt(date[2]);
                    month = Integer.parseInt(date[1]);
                    day = Integer.parseInt(date[0]);
                }

                // Create a new instance of DatePickerDialog and return it.
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), EmployeeDetailFragment.this, year, month, day);
                datePickerDialog.show();
            }
        });

        return rootView;
    }


    /**
     * @param view       the picker associated with the dialog
     * @param year       the selected year
     * @param month      the selected month (0-11 for compatibility with
     *                   {@link Calendar#MONTH})
     * @param dayOfMonth th selected day of the month (1-31, depending on
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        txtvu_hireDate.setText("" + dayOfMonth + "." + (++month) + "." + year);
    }
}
