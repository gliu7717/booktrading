package com.example.booktradinging_login.database

import java.util.Date

data class Book(val bookNo:Int,
                val name:String,
                val owner:String,
                val price:Float,
                val author:String,
                val publisher:String,
                val category: String,
                val image:String,
                val introduction: String
)
