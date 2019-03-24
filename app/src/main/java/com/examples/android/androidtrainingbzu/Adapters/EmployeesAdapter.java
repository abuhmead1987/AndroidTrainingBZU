package com.examples.android.androidtrainingbzu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.examples.android.androidtrainingbzu.EmployeeDetailActivity;
import com.examples.android.androidtrainingbzu.EmployeeDetailFragment;
import com.examples.android.androidtrainingbzu.EmployeeListActivity;
import com.examples.android.androidtrainingbzu.Models.Employee;
import com.examples.android.androidtrainingbzu.R;
import com.examples.android.androidtrainingbzu.Utils.Utils;

import java.util.LinkedList;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmpInfoViewHolder> {

    private final LinkedList<Employee> employeesList;
    private final LayoutInflater mInflater;
    private boolean mTwoPane;
    EmployeeListActivity mParentActivity;
    public EmployeesAdapter(EmployeeListActivity parent,
                            LinkedList<Employee> employeesList, boolean twoPane) {
        this.employeesList = employeesList;
        mInflater = LayoutInflater.from(parent.getApplicationContext());
        mParentActivity=parent;
        this.mTwoPane=twoPane;
    }

    @NonNull
    @Override
    public EmpInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =mInflater
                .inflate(R.layout.emp_info_list_item, parent, false);
        return new EmpInfoViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpInfoViewHolder holder, int position) {
        Employee employee = employeesList.get(position);
        holder.txtvu_name.setText(employee.getName());
        holder.txtvu_address.setText(employee.getAddress());
        holder.txtvu_phone.setText(employee.getPhone());
        holder.txtvu_email.setText(employee.getEmail());
        holder.txtvu_id.setText(""+employee.getID());
        holder.txtvu_hiredate.setText(
                (Utils.getDateFormatedString(employee.getHireDate())!= null)?
                        Utils.getDateFormatedString(employee.getHireDate()):"DateNull");
        holder.img_avatar.setImageResource(employee.getPicResID());
    }

    @Override
    public int getItemCount() {
        return employeesList.size();
    }


    public class EmpInfoViewHolder extends RecyclerView.ViewHolder{
//            implements View.OnClickListener, View.OnLongClickListener{
        public  final TextView txtvu_name, txtvu_address, txtvu_phone, txtvu_email, txtvu_id, txtvu_hiredate;
        final ImageView  img_avatar;
        final EmployeesAdapter mAdapter;
        public EmpInfoViewHolder(View itemView, EmployeesAdapter adapter) {
            super(itemView);
            this.mAdapter = adapter;
            txtvu_name = (TextView) itemView.findViewById(R.id.txtvu_name);
            txtvu_address = (TextView) itemView.findViewById(R.id.txtvu_address);
            txtvu_phone = (TextView) itemView.findViewById(R.id.txtvu_phone);
            txtvu_email = (TextView) itemView.findViewById(R.id.txtvu_email);
            txtvu_id = (TextView) itemView.findViewById(R.id.txtvu_id);
            txtvu_hiredate = (TextView) itemView.findViewById(R.id.txtvu_hiredate);
            img_avatar = (ImageView) itemView.findViewById(R.id.img_pic);
//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);
        }

//        /**
//         * Called when a view has been clicked.
//         *
//         * @param v The view that was clicked.
//         */
//        @Override
//        public void onClick(View v) {
//            int mPosition = getLayoutPosition();
//            if (mTwoPane) {
//                Bundle arguments = new Bundle();
//                arguments.putInt(EmployeeDetailFragment.ARG_ITEM_ID,mPosition);
//                EmployeeDetailFragment fragment = new EmployeeDetailFragment();
//                fragment.setArguments(arguments);
//                mParentActivity.getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.employee_detail_container, fragment)
//                        .commit();
//            } else {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, EmployeeDetailActivity.class);
//                intent.putExtra(EmployeeDetailFragment.ARG_ITEM_ID, mPosition);
//                context.startActivity(intent);
//            }
//        }

//        /**
//         * Called when a view has been clicked and held.
//         *
//         * @param v The view that was clicked and held.
//         * @return true if the callback consumed the long click, false otherwise.
//         */
//        @Override
//        public boolean onLongClick(View v) {
//            return false;
//        }
    }
}
