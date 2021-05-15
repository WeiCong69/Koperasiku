package com.Koperasiku.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Koperasiku.R
import com.Koperasiku.models.Product
import com.Koperasiku.utils.GlideLoader
import kotlinx.android.synthetic.main.item_home_product.view.*
import kotlin.math.round

/**
 * A adapter class for dashboard items list.
 */
open class HomeItemsListAdapter(
        private val context: Context,
        private var list: ArrayList<Product>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // A global variable for OnClickListener interface.
    private var onClickListener: OnClickListener? = null

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_home_product,
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
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            GlideLoader(context).loadProductPicture(
                    model.image,
                    holder.itemView.iv_home_item_image
            )
            holder.itemView.tv_home_item_title.text = model.title
            val rm = "RM"
            //val priceAfterDiscount = String.format("%.3f",model.price.toDouble() * model.discount.toDouble()).toDouble()
            val priceAfterDiscount = round(model.price.toDouble() * model.discount.toDouble())
            val discount =  Math.ceil(100*(1-model.discount.toDouble()))
            Log.e("Price",priceAfterDiscount.toString())
            holder.itemView.tv_original_price.text = "$rm ${model.price}"
            holder.itemView.tv_home_item_price.text = "$rm $priceAfterDiscount"
            holder.itemView.tv_discount.text ="- ${discount} %"

            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, model)
                }
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
     * A function for OnClickListener where the Interface is the expected parameter and assigned to the global variable.
     *
     * @param onClickListener
     */
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    /**
     * An interface for onclick items.
     */
    interface OnClickListener {

        fun onClick(position: Int, product: Product)
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}