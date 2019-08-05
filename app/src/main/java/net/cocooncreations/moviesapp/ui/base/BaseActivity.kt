package net.cocooncreations.moviesapp.ui.base

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import net.cocooncreations.moviesapp.R

/**
 * BaseActivity is generated to involve common functions and variables for each activity
 * @property viewModel used to be initialized by every screen
 * @method instantiateViewModel used to return given ViewModel type for each Activity
 */
abstract class BaseActivity<VM:BaseViewModel>:AppCompatActivity() {

    protected lateinit var viewModel:VM
    protected abstract fun instantiateViewModel():VM

    private var mProgressDialog:android.support.v7.app.AlertDialog? = null
    private var alertDialog:android.support.v7.app.AlertDialog? = null


    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeProgressDialog()
        viewModel = instantiateViewModel()
    }

    protected fun initializeProgressDialog(){
        if(mProgressDialog == null){
            val view = LayoutInflater.from(this).inflate(R.layout.layout_progress_dialog,null)
            mProgressDialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        hideProgressDialog()
        mProgressDialog = null
    }

    protected  fun showProgressDialog(title:String){
        hideProgressDialog()
        mProgressDialog?.setTitle(title)
        mProgressDialog?.show()
    }

    protected fun hideProgressDialog(){
        if(mProgressDialog != null){
            mProgressDialog?.hide()
        }
    }


    protected fun showAlertDialog(title:String,message:String, dialogInterface: DialogInterface.OnClickListener?){
        hideAlertDialog()
        alertDialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok,dialogInterface)
            .setCancelable(false)
            .create()
        alertDialog?.show()
    }

    protected fun hideAlertDialog(){
        if(alertDialog!=null){
            alertDialog?.dismiss()
        }
    }


}