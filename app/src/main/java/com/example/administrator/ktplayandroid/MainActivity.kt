package com.example.administrator.ktplayandroid

import android.os.Bundle
import android.view.View
import com.example.administrator.mykotlin.T
import com.example.handsomelibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
/**
 * Created by Stefan on 2019/1/17 17:03
 */

class MainActivity : BaseActivity() {
    private var userName:String=""
    private var passWord:String=""

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        login.setOnClickListener { v -> setClick(v) }


    }

    private fun setClick(view:View){
        when(view.id) {
            R.id.login -> {
                userName = edit_userName.text.toString()
                passWord=edit_passWord.text.toString()
                getLogin(userName,passWord)
            }
        }
    }

    private fun getLogin(userName:String,passWord:String) {
        if(userName=="fan"&&passWord=="123"){
            T("登录成功")
        }else{
            T("请输入正确的账号密码")
        }
    }


}

