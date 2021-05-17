package com.Koperasiku.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.Koperasiku.R
import com.Koperasiku.firestore.FirestoreClass
import com.Koperasiku.models.User
import com.Koperasiku.ui.activities.AddressListActivity
import com.Koperasiku.ui.activities.CartListActivity
import com.Koperasiku.ui.activities.LoginActivity
import com.Koperasiku.ui.activities.UserProfileActivity
import com.Koperasiku.utils.Constants
import com.Koperasiku.utils.GlideLoader
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_user_profile.*


class UserProfileFragment : Fragment() , View.OnClickListener{

    private lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }


    override fun onResume() {
        super.onResume()
        tv_edit.setOnClickListener(this@UserProfileFragment)
        btn_logout.setOnClickListener(this@UserProfileFragment)
        ll_address.setOnClickListener(this@UserProfileFragment)
        rl_cart.setOnClickListener(this@UserProfileFragment)
        rl_order.setOnClickListener(this@UserProfileFragment)
        getUserDetails()
    }

     override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.tv_edit -> {
                    val intent = Intent(context, UserProfileActivity::class.java)
                    intent.putExtra(Constants.EXTRA_USER_DETAILS, mUserDetails)
                    startActivity(intent)
                }

                R.id.ll_address -> {
                    val intent = Intent(context, AddressListActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_logout -> {

                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    //finish()
                }
                R.id.rl_cart ->{
                    startActivity(Intent(context, CartListActivity::class.java))
                }
                R.id.rl_order->{
                    val transaction = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.replace(R.id.cl_test, OrdersFragment())
                    transaction?.disallowAddToBackStack()
                    transaction?.commit()
                }
            }
        }
    }

    private fun getUserDetails() {

        // Show the progress dialog
        //showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of Firestore class to get the user details from firestore which is already created.
        FirestoreClass().getUserDetailsInFragment(this)
    }

    fun userDetailsSuccess(user: User) {

        mUserDetails = user

        // Hide the progress dialog
        //hideProgressDialog()

        // Load the image using the Glide Loader class.
        GlideLoader(requireContext()).loadUserPicture(user.image, iv_user_photo)

        tv_name.text = "${user.firstName} ${user.lastName}"
        tv_gender.text = user.gender
        tv_email.text = user.email
        tv_mobile_number.text = "${user.mobile}"
    }

}