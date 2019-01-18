package com.example.administrator.ktplayandroid

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.administrator.mykotlin.T
import com.example.handsomelibrary.base.BaseActivity
import com.example.handsomelibrary.utils.JumpUtils
import kotlinx.android.synthetic.main.activity_login.*

/**
 *  登 录
 * Created by Stefan on 2019/1/17 17:03
 */

class LoginActivity : BaseActivity() {
    private var userName: String = ""
    private var passWord: String = ""

    override fun getContentView(): Int {
        return R.layout.activity_login
    }

    override fun initData(savedInstanceState: Bundle?) {
        login.setOnClickListener { v -> setClick(v) }

    }

    private fun setClick(view: View) {
        when (view.id) {
            R.id.login -> {
                userName = edit_userName.text.toString()
                passWord = edit_passWord.text.toString()
                getLogin(userName, passWord)
            }
        }
    }

    private val handler = Handler()
    private fun getLogin(userName: String, passWord: String) {
        if (userName == "fan" && passWord == "123") {
            T("登录成功")
            handler.postDelayed({
                JumpUtils.jump(mContext, MainActivity::class.java, null)
            }, 1000)
        } else {
            T("请输入正确的账号密码")
        }
    }
}

