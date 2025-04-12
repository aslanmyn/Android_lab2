package com.example.libchat

import android.app.Activity
import android.content.Context
import android.content.Intent
import kotlin.jvm.java

object ChatStarter {
    fun start(context: Context) {
        val intent = Intent(context, ChatActivity::class.java)
        if(context !is Activity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}