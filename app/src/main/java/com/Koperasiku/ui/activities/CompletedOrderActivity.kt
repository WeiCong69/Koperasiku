package com.Koperasiku.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.Koperasiku.R
import com.Koperasiku.firestore.FirestoreClass
import com.Koperasiku.models.Order
import com.Koperasiku.ui.adapters.MyOrdersListAdapter
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.activity_completed_order.*
import kotlinx.android.synthetic.main.activity_my_order_details.*
import kotlinx.android.synthetic.main.fragment_orders.*

class CompletedOrderActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_order)

        setupActionBar()
    }

    override fun onResume() {
        super.onResume()
        getMyOrdersList()
    }

    /**
     * A function to get the list of my orders.
     */
    private fun getMyOrdersList() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getMyOrdersListActivity(this)
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_order_list_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_order_list_activity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to get the success result of the my order list from cloud firestore.
     *
     * @param ordersList List of my orders.
     */
    fun populateOrdersListInUI(ordersList: ArrayList<Order>) {

        // Hide the progress dialog.
        hideProgressDialog()

        if (ordersList.size > 0) {
            Log.e("testt","sddfds")
            rv_order_items_list1.visibility = View.VISIBLE
            tv_no_order_item_found1.visibility = View.GONE

            rv_order_items_list1.layoutManager = LinearLayoutManager(this@CompletedOrderActivity)
            rv_order_items_list1.setHasFixedSize(true)

            val myOrdersAdapter = MyOrdersListAdapter(this@CompletedOrderActivity, ordersList)
            rv_order_items_list1.adapter = myOrdersAdapter

        } else {
            Log.e("testt","sddfds123231")
            rv_order_items_list1.visibility = View.GONE
            tv_no_order_item_found1.visibility = View.VISIBLE
        }
    }


}