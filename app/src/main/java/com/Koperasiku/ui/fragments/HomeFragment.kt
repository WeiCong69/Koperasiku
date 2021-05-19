package com.Koperasiku.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.Koperasiku.R
import com.Koperasiku.decoration.BannerSlider
import com.Koperasiku.decoration.SliderIndicator
import com.Koperasiku.firestore.FirestoreClass
import com.Koperasiku.models.Product
import com.Koperasiku.ui.activities.AddressListActivity
import com.Koperasiku.ui.activities.ProductDetailsActivity
import com.Koperasiku.ui.adapters.HomeItemsListAdapter
import com.Koperasiku.ui.adapters.HomeSliderAdapter
import com.Koperasiku.utils.Constants
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : BaseFragment() , View.OnClickListener{

    private var mAdapter: HomeSliderAdapter? = null
    private var mIndicator: SliderIndicator? = null

    private var bannerSlider: BannerSlider? = null
    private var mLinearLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onResume() {
        super.onResume()
        ll_promotions.setOnClickListener(this@HomeFragment)
        ll_stationary.setOnClickListener(this@HomeFragment)
        ll_food.setOnClickListener(this@HomeFragment)
        ll_clothings.setOnClickListener(this@HomeFragment)
        setupSlider()
        getHomeList()
    }

    private fun setupSlider() {
        sliderView69!!.setDurationScroll(800)
        val fragments: MutableList<Fragment> = ArrayList()

        //link image
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/19/85531617/85531617_17b56894-2608-4509-a4f4-ad86aa5d3b62.jpg"))
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/19/85531617/85531617_7da28e4c-a14f-4c10-8fec-845578f7d748.jpg"))
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/18/85531617/85531617_87a351f9-b040-4d90-99f4-3f3e846cd7ef.jpg"))
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/20/85531617/85531617_03e76141-3faf-45b3-8bcd-fc0962836db3.jpg"))
        mAdapter = HomeSliderAdapter(childFragmentManager, fragments)
        sliderView69!!.adapter = mAdapter
        mIndicator =
            SliderIndicator(
                requireActivity(),
                pagesContainer69!!,
                sliderView69!!,
                R.drawable.indicator_circle
            )
        mIndicator!!.setPageCount(fragments.size)
        mIndicator!!.show()
    }

    private fun getHomeList(){
        showProgressDialog("Loading the best deals for you")
        FirestoreClass().getHomeItemsUnderPromotion(this)
    }



    fun successHomeItemsList(homeItemsList: ArrayList<Product>) {
        hideProgressDialog()
        if(homeItemsList.size>0){
            rv_home_items69.layoutManager = LinearLayoutManager(requireActivity())
            rv_home_items69.setHasFixedSize(true)

            val adapter = HomeItemsListAdapter(requireActivity(), homeItemsList)
            rv_home_items69.adapter=adapter

            adapter.setOnClickListener(object :
                HomeItemsListAdapter.OnClickListener {
                override fun onClick(position: Int, product: Product) {
                    val intent = Intent(context, ProductDetailsActivity::class.java)
                    intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.product_id)
                    intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID, product.user_id)
                    startActivity(intent)
                }
            }
            )
        }else{
            Log.e("No discount", "jialat")
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.ll_promotions -> {
                    val transaction = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.replace(R.id.cl_home, DashboardFragment())
                    transaction?.disallowAddToBackStack()
                    transaction?.commit()
                }
                R.id.ll_clothings -> {
                    Log.e("junji", "hellow")
//                    val intent = Intent(
//                        requireActivity().baseContext,
//                        DashboardFragment::class.java
//                    )
//                    intent.putExtra("message", "id.message")
//                    requireActivity().startActivity(intent)
                }
                R.id.ll_stationary -> {
                    val intent = Intent(context, AddressListActivity::class.java)
                    startActivity(intent)
                }
            }
        }else{
            Log.e("pokai", "hellow")

        }
    }

}