package com.example.administrator.nothing_kotlin.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.administrator.nothing_kotlin.R

class DialogU {

    companion object {
        fun createDialog(context: Context , view: View , isCancellable: Boolean) : Dialog{
            var dialog = Dialog(context , R.style.LoadingDialog)
            dialog.setCancelable(isCancellable)
            dialog.setContentView(view)
            return dialog
        }

        fun showLoadingDialog(context: Context) : Dialog{
            var view = LayoutInflater.from(context).inflate(R.layout.dialog_loading , null , false)
            val dialog = createDialog(context, view, false)
            var loadingImg : ImageView = view.findViewById(R.id.loading_img)
            val window = dialog.window
            val attributes = window.attributes
            attributes.width = WindowManager.LayoutParams.WRAP_CONTENT
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = attributes
            dialog.setOnShowListener(object  : DialogInterface.OnShowListener {
                override fun onShow(dialog: DialogInterface?) {
                    val loadAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_anim)
                    loadingImg.startAnimation(loadAnimation)
                }
            })
            dialog.setOnDismissListener(object : DialogInterface.OnDismissListener{
                override fun onDismiss(dialog: DialogInterface?) {
                    loadingImg.clearAnimation()
                }
            })
            return dialog
        }
    }

}