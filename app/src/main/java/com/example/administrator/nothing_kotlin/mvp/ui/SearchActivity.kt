package com.example.administrator.nothing_kotlin.mvp.ui

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.base.BaseActivity
import com.example.administrator.nothing_kotlin.utils.CommonU

class SearchActivity : BaseActivity(), TextView.OnEditorActionListener {

    var title_edittext : EditText? = null
    override fun getLayoutResourse(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        val title_vertical_line = findViewById<TextView>(R.id.title_vertical_line)
        title_vertical_line.visibility = View.GONE
        val title_question = findViewById<RelativeLayout>(R.id.title_question)
        title_question.visibility = View.GONE
        val title_text = findViewById<TextView>(R.id.title_text)
        title_text.visibility = View.GONE
        title_edittext = findViewById(R.id.title_edittext)
        title_edittext?.visibility = View.VISIBLE
    }

    override fun initData() {
    }

    override fun setListener() {
        title_edittext?.setOnEditorActionListener(this)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            Toast.makeText(this , "暂未开放" , Toast.LENGTH_SHORT).show()
            CommonU.hideSoftInputFromWindow(this)
            return true
        }
        return false
    }
}