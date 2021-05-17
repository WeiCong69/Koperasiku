package com.Koperasiku.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.Koperasiku.R
import com.Koperasiku.decoration.BannerSlider
import com.Koperasiku.decoration.SliderIndicator
import com.Koperasiku.ui.adapters.HomeSliderAdapter
import com.Koperasiku.ui.fragments.FragmentSlider
import kotlinx.android.synthetic.main.layout_banner.*
import java.util.*

class HomeActivity : BaseActivity() {
    private var mAdapter: HomeSliderAdapter? = null
    private var mIndicator: SliderIndicator? = null

    private var bannerSlider: BannerSlider? = null
    private var mLinearLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //bannerSlider = findViewById(R.id.sliderView)
        mLinearLayout = findViewById(R.id.pagesContainer)
        setupSlider()

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("No discount","sugoi")
        //getHomeList()
    }

    private fun setupSlider() {
        sliderView!!.setDurationScroll(800)
        val fragments: MutableList<Fragment> = ArrayList()

        //link image
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/19/85531617/85531617_17b56894-2608-4509-a4f4-ad86aa5d3b62.jpg"))
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/19/85531617/85531617_7da28e4c-a14f-4c10-8fec-845578f7d748.jpg"))
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/18/85531617/85531617_87a351f9-b040-4d90-99f4-3f3e846cd7ef.jpg"))
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/20/85531617/85531617_03e76141-3faf-45b3-8bcd-fc0962836db3.jpg"))
        mAdapter = HomeSliderAdapter(supportFragmentManager, fragments)
        sliderView!!.adapter = mAdapter
        mIndicator =
            SliderIndicator(this, mLinearLayout!!, sliderView!!, R.drawable.indicator_circle)
        mIndicator!!.setPageCount(fragments.size)
        mIndicator!!.show()
    }

    override fun onBackPressed() {
        doubleBackToExit()
    }

//    private fun getHomeList(){
//        showProgressDialog("Loading the best deals for you")
//        FirestoreClass().getHomeItemsUnderPromotion(this@HomeActivity)
//    }

//    fun successHomeItemsList(homeItemsList: ArrayList<Product>) {
//        hideProgressDialog()
//        if(homeItemsList.size>0){
//            rv_home_items.layoutManager = LinearLayoutManager(this@HomeActivity)
//            rv_home_items.setHasFixedSize(true)
//
//            val adapter = HomeItemsListAdapter(this, homeItemsList)
//            rv_home_items.adapter=adapter
//
//            adapter.setOnClickListener(object:
//                HomeItemsListAdapter.OnClickListener{
//                override fun onClick(position: Int, product: Product) {
//                    val intent = Intent(this@HomeActivity, ProductDetailsActivity::class.java)
//                    intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.product_id)
//                    intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID, product.user_id)
//                    startActivity(intent)
//                }
//                }
//            )
//        }else{
//            Log.e("No discount","jialat")
//        }
//    }
}