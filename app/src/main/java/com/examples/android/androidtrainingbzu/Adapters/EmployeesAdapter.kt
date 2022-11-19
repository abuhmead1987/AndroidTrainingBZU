package com.examples.android.androidtrainingbzu.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.examples.android.androidtrainingbzu.Adapters.EmployeesAdapter.EmpInfoViewHolder
import com.examples.android.androidtrainingbzu.EmployeeListActivity
import com.examples.android.androidtrainingbzu.Models.Employee
import com.examples.android.androidtrainingbzu.R
import com.examples.android.androidtrainingbzu.Utils.Utils
import java.util.*

class EmployeesAdapter(
    parent: EmployeeListActivity,
    private val employeesList: LinkedList<Employee?>?, twoPane: Boolean,
) : RecyclerView.Adapter<EmpInfoViewHolder>() {
    private val mInflater: LayoutInflater
    private val mTwoPane: Boolean
    var mParentActivity: EmployeeListActivity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpInfoViewHolder {
        val itemView = mInflater
            .inflate(R.layout.emp_info_list_item, parent, false)
        return EmpInfoViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: EmpInfoViewHolder, position: Int) {
        val employee = employeesList!![position]
        holder.txtvu_name.text = employee!!.name
        holder.txtvu_address.text = employee.address
        holder.txtvu_phone.text = employee.phone
        holder.txtvu_email.text = employee.email
        holder.txtvu_id.text = "" + employee.id
        holder.txtvu_hiredate.text =
            if (Utils.getDateFormatedString(employee.hireDate) != null) Utils.getDateFormatedString(
                employee.hireDate) else "DateNull"
        holder.img_avatar.setImageResource(employee.picResID)
    }

    override fun getItemCount(): Int {
        return employeesList!!.size
    }

    inner class EmpInfoViewHolder(itemView: View, val mAdapter: EmployeesAdapter) :
        RecyclerView.ViewHolder(itemView) {
        //            implements View.OnClickListener, View.OnLongClickListener{
        val txtvu_name: TextView
        val txtvu_address: TextView
        val txtvu_phone: TextView
        val txtvu_email: TextView
        val txtvu_id: TextView
        val txtvu_hiredate: TextView
        val img_avatar: ImageView

        init {
            txtvu_name = itemView.findViewById<View>(R.id.txtvu_name) as TextView
            txtvu_address = itemView.findViewById<View>(R.id.txtvu_address) as TextView
            txtvu_phone = itemView.findViewById<View>(R.id.txtvu_phone) as TextView
            txtvu_email = itemView.findViewById<View>(R.id.txtvu_email) as TextView
            txtvu_id = itemView.findViewById<View>(R.id.txtvu_id) as TextView
            txtvu_hiredate = itemView.findViewById<View>(R.id.txtvu_hiredate) as TextView
            img_avatar = itemView.findViewById<View>(R.id.img_pic) as ImageView
            //            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);
        } //        /**
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

    init {
        mInflater = LayoutInflater.from(parent.applicationContext)
        mParentActivity = parent
        mTwoPane = twoPane
    }
}