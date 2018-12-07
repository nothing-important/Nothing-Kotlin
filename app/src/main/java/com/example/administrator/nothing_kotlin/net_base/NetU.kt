package com.example.administrator.nothing_kotlin.net_base

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.utils.DialogU
import com.example.administrator.nothing_kotlin.utils.LogU
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action0
import rx.schedulers.Schedulers

open class NetU<T> {

    var observable : Observable<T>? = null
    var isShowDialog : Boolean = false
    var context : Context? = null

    fun with(context: Context) : NetU<T>{
        this.context = context
        return this
    }

    fun isShowLoadDialog(isShowLoadingDialog : Boolean) : NetU<T>{
        this.isShowDialog = isShowLoadingDialog
        return this
    }

    fun build(observable: Observable<T>) : NetRequest<T> {
        this.observable = observable
        var netRequest = context?.let { NetRequest(it, isShowDialog , observable) }
        return netRequest!!
    }

    class NetRequest<T>(var context: Context, var isShowLoadingDialog: Boolean, var observable: Observable<T>){
        fun request(requestListener: RequestListener<T>){
            var dialog : Dialog = createDialog()
            this.observable.subscribeOn(Schedulers.io())?.
                    doOnSubscribe(NetAction(isShowLoadingDialog , dialog))?.
                    subscribeOn(AndroidSchedulers.mainThread())?.
                    unsubscribeOn(Schedulers.io())?.
                    observeOn(AndroidSchedulers.mainThread())?.
                    subscribe(NetSubscriber(requestListener , dialog))
        }

        fun createDialog() : Dialog{
            var view = LayoutInflater.from(context).inflate(R.layout.dialog_loading , null , false)
            return DialogU.createDialog(context , view , false)
        }
    }

    class NetAction(var isShowDialog: Boolean , var dialog: Dialog) : Action0{
        override fun call() {
            if (isShowDialog){
                dialog.show()
            }
        }
    }

    class NetSubscriber<T>(var requestListener: RequestListener<T> , var dialog: Dialog) : Subscriber<T>() {
        override fun onNext(t: T) {
            requestListener.onRequestSuccess(t)
        }

        override fun onCompleted() {
            dialog.dismiss()
        }

        override fun onError(e: Throwable?) {
            dialog.dismiss()
            LogU.e("retrofit" , e?.message!!)
            requestListener.onSystemError(e?.message!!)
        }

    }

    interface RequestListener<T>{
        fun onRequestSuccess(t : T)
        fun onRequestError(t : T)
        fun onSystemError(errorMsg : String)
    }

}