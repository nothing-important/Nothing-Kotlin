package com.example.administrator.nothing_kotlin.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import com.example.administrator.nothing_kotlin.R
import io.reactivex.functions.Cancellable

class DialogU {

    companion object {
        fun createDialog(context: Context , view: View , isCancellable: Boolean) : Dialog{
            var dialog = Dialog(context , R.style.LoadingDialog)
            dialog.setCancelable(isCancellable)
            dialog.setContentView(view)
            return dialog
        }
    }

}