package com.example.booktradinging_login.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(
    val context: Context,
    val name: String,
    val factory: SQLiteDatabase.CursorFactory?,
    val version: Int) :
                       SQLiteOpenHelper(context,name,factory,version){

    val CREATE_REMEMBERACCOUNT = ("create table rememberAccount ("
            + "id integer primary key autoincrement, "
            + "account text, "
            + "password text)")

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_REMEMBERACCOUNT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun getLoginUser() : User?
    {
        var phone : String? = null
        var pass :String? = null
        val db = getWritableDatabase()
        val cursor = db.query(
            "rememberAccount", null, null, null,
            null, null, null, null
        )
        if (cursor.moveToFirst()) {
            var i = cursor.getColumnIndex("account")
            if (i >= 0)  phone = cursor.getString(i)
            i = cursor.getColumnIndex("password")
            if (i >= 0)  pass = cursor.getString(i)
            if(phone!= null && pass!=null) {
                db.delete("rememberAccount", "account=?", arrayOf(phone))
                db.delete("rememberAccount", "password=?", arrayOf(pass))
                return User(phone,"", pass,"")
            }
        }

        return null
    }
}