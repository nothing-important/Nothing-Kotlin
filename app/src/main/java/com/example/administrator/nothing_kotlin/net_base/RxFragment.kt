package com.example.administrator.nothing_kotlin.net_base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle.FragmentEvent
import com.trello.rxlifecycle.FragmentLifecycleProvider
import com.trello.rxlifecycle.RxLifecycle
import rx.Observable
import rx.subjects.BehaviorSubject

open class RxFragment : Fragment() , FragmentLifecycleProvider{

    var lifecycleSubject : BehaviorSubject<FragmentEvent> = BehaviorSubject.create()

    override fun lifecycle(): Observable<FragmentEvent> {
        return lifecycleSubject
    }

    override fun <T : Any?> bindUntilEvent(event: FragmentEvent): Observable.Transformer<T, T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject , event)
    }

    override fun <T : Any?> bindToLifecycle(): Observable.Transformer<T, T> {
        return RxLifecycle.bindFragment(lifecycleSubject)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        lifecycleSubject.onNext(FragmentEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(FragmentEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        super.onPause()
        lifecycleSubject.onNext(FragmentEvent.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifecycleSubject.onNext(FragmentEvent.STOP)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleSubject.onNext(FragmentEvent.DESTROY)
    }

    override fun onDetach() {
        super.onDetach()
        lifecycleSubject.onNext(FragmentEvent.DETACH)
    }


}