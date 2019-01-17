package com.example.administrator.mykotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

/**
 * @author Stefan
 * Created on 2019/1/15 17:42
 */

@SuppressLint("ShowToast")
fun Activity.T(msg:String, time:Int=Toast.LENGTH_SHORT){
    Toast.makeText(this,msg,time).show()
}

fun AppCompatActivity.L(msg:String){
    Log.d("D:",msg)
}
