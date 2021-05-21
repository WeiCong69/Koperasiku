package com.Koperasiku.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.Koperasiku.R
import com.Koperasiku.firestore.FirestoreClass
import com.Koperasiku.models.Product
import com.Koperasiku.ui.adapters.DashboardItemsListAdapter
import com.Koperasiku.utils.Constants
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.activity_filter_product.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*

class FilterProductActivity : BaseActivity() , View.OnClickListener{

    private lateinit var mCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_product)

        mCategory=Constants.CATEGORY_STATIONARY

        setupActionBar()


    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_filter_product_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_filter_product_activity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onResume() {
        super.onResume()
        if (intent.hasExtra(Constants.CATEGORY)) {
            // Get the user details from intent as a ParcelableExtra.
            mCategory = intent.getStringExtra(Constants.CATEGORY)!!
        }
        ll_stationary1.setOnClickListener(this)
        ll_food1.setOnClickListener(this)
        ll_clothings1.setOnClickListener(this)
        getFilterItemsList()
    }

    private fun getFilterItemsList() {
        // Show the progress dialog.
        //showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getFilterItemsList(this,mCategory)
    }

    fun successFilterItemsList(dashboardItemsList: ArrayList<Product>) {

        // Hide the progress dialog.
        //hideProgressDialog()

        if (dashboardItemsList.size > 0) {
            Log.e("test","kajima")
            rv_filter_product_list.visibility = View.VISIBLE
            tv_no_filter_product_found.visibility = View.INVISIBLE

            rv_filter_product_list.layoutManager = GridLayoutManager(this, 2)
            rv_filter_product_list.setHasFixedSize(true)

            val adapter = DashboardItemsListAdapter(this@FilterProductActivity, dashboardItemsList)
            rv_filter_product_list.adapter = adapter

            adapter.setOnClickListener(object :
                DashboardItemsListAdapter.OnClickListener {
                override fun onClick(position: Int, product: Product) {

                    val intent = Intent(this@FilterProductActivity, ProductDetailsActivity::class.java)
                    intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.product_id)
                    intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID, product.user_id)
                    startActivity(intent)
                }
            })
            // END
        } else {
            rv_filter_product_list.visibility = View.GONE
            tv_no_filter_product_found.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.ll_food1 -> {
                    mCategory=Constants.CATEGORY_FOOD
                    getFilterItemsList()
                }
                R.id.ll_clothings1 -> {
                    mCategory=Constants.CATEGORY_CLOTHINGS
                    getFilterItemsList()

                }
                R.id.ll_stationary1 -> {
                    mCategory=Constants.CATEGORY_STATIONARY
                    getFilterItemsList()
                }
            }
        }
    }

}

