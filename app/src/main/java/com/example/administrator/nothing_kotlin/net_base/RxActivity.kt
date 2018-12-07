package com.example.administrator.nothing_kotlin.net_base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.ActivityLifecycleProvider
import com.trello.rxlifecycle.RxLifecycle
import rx.Observable
import rx.subjects.BehaviorSubject

open class RxActivity : AppCompatActivity() , ActivityLifecycleProvider{

    var lifecycleSubject : BehaviorSubject<ActivityEvent> = BehaviorSubject.create()

    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.asObservable()
    }

    override fun <T : Any?> bindUntilEvent(event: ActivityEvent): Observable.Transformer<T, T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject , event)
    }

    override fun <T : Any?> bindToLifecycle(): Observable.Transformer<T, T> {
        return RxLifecycle.bindActivity(lifecycleSubject)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    override fun onPause() {
        super.onPause()
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifecycleSubject.onNext(ActivityEvent.STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
    }

}