package com.Koperasiku.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Koperasiku.R
import com.Koperasiku.models.Order
import com.Koperasiku.ui.activities.MyOrderDetailsActivity
import com.Koperasiku.utils.Constants
import com.Koperasiku.utils.GlideLoader
import kotlinx.android.synthetic.main.item_cart_layout.view.tv_date
import kotlinx.android.synthetic.main.item_order_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

open class MyOrdersListAdapter(
    private val context: Context,
    private var list: ArrayList<Order>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_order_layout,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            GlideLoader(context).loadProductPicture(
                model.image,
                holder.itemView.iv_order_item_image
            )

            holder.itemView.tv_order_title.text = model.title
            holder.itemView.tv_order_item_price.text = "Total  : RM ${model.total_amount}"

            // Date Format in which the date will be displayed in the UI.
            val dateFormat = "dd MMM yyyy HH:mm"
            // Create a DateFormatter object for displaying date in specified format.
            val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = model.order_datetime

            val orderDateTime = formatter.format(calendar.time)
            holder.itemView.tv_date.text = "Date   : $orderDateTime"

            val diffInMilliSeconds: Long = System.currentTimeMillis() - model.order_datetime
            val diffInHours: Long = TimeUnit.MILLISECONDS.toHours(diffInMilliSeconds)
            //Log.e("Difference in Hours", "$diffInHours")

            when {
                diffInHours < 1 -> {
                    holder.itemView.tv_status.text = "Status: Pending"
                }
                diffInHours < 2 -> {
                    holder.itemView.tv_status.text = "Status: In Progress"
                }
                else -> {
                    holder.itemView.tv_status.text = "Status: Delivered"
                }
            }

            //holder.itemView.ib_delete_product.visibility = View.GONE

            holder.itemView.setOnClickListener {
                val intent = Intent(context, MyOrderDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_MY_ORDER_DETAILS, model)
                context.startActivity(intent)
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
// END