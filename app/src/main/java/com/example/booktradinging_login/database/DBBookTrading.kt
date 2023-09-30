package com.example.booktradinging_login.database

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

public class DBBookTrading {
    companion object {
        var conn: Connection? = null
        var SQhelper:MyDatabaseHelper? = null
        fun getConntion(): Connection? {
            if(conn!=null)
                return conn
            val driver = "com.mysql.jdbc.Driver"
            val url =
                "jdbc:mysql://150.136.175.102:3306/booktrading?characterEncoding=latin1&useConfigs=maxPerformance"
            val user = "gliu"
            val password = "benray1110"
                Class.forName(driver).newInstance()
            conn = DriverManager.getConnection(url, user,password )
            return conn
        }

        fun insertUser(user:User)
        {
            CoroutineScope(Dispatchers.IO).launch {
                val connection: Connection? = getConntion()
                val sql_1 = "INSERT INTO userinfo(PHONE, NAME,EMAIL) " +
                        "VALUES('${user.phone}','${user.name}','${user.email}')"
                val sql_2 = "INSERT INTO user(PHONE, pass) " +
                        "VALUES('${user.phone}','${user.password}')"
                val statement: Statement = connection!!.createStatement()
                statement.executeUpdate(sql_1)
                statement.executeUpdate(sql_2)
                statement.close()
            }
        }

        fun login(context: Context, input_phone:String, input_password:String, remember_pass: Boolean, callback: () -> Unit)
        {
            CoroutineScope(Dispatchers.IO).launch{
                val connection: Connection? = getConntion()
                val sql =
                    "SELECT user.pass AS pass, info.name AS name FROM user AS user, userinfo as info WHERE user.phone='$input_phone' AND info.phone='$input_phone'limit 1"
                val st: Statement
                st = connection!!.createStatement()
                val rs = st.executeQuery(sql)
                if (rs.first()) {
                    val pass = rs.getString("pass")
                    val name = rs.getString("name")
                    withContext(Dispatchers.Main){
                        if (input_password != pass) {
                            Toast.makeText(context, "wrong password", Toast.LENGTH_LONG).show()
                        }
                        else {
                            if(SQhelper == null)
                                SQhelper = MyDatabaseHelper(context, "rememberAccount", null, 1)

                            val db = SQhelper!!.writableDatabase
                            if (remember_pass) {
                                val values = ContentValues()
                                values.put("account", input_phone)
                                values.put("password", input_password)
                                db.insert("rememberAccount", null, values)
                            }
                            Toast.makeText(context, "login successfully", Toast.LENGTH_SHORT)
                                .show()
                            callback()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "User does not exist", Toast.LENGTH_LONG).show()
                    }
                }
                st.close()
                //connection!!.close()
                //conn = null
            }
        }

        fun insertBook(book:Book)
        {
            CoroutineScope(Dispatchers.IO).launch {
                val connection: Connection? = getConntion()
                val sql = "INSERT INTO book(name, " +
                        "owner,price,author, publisher," +
                        "category, image,introduction, sold) " +
                        "VALUES('${book.name}','${book.owner}', ${book.price}," +
                        " '${book.author}', '${book.publisher}','${book.category}'," +
                         "'${book.image}', '${book.introduction}', 0 )"
                val statement: Statement = connection!!.createStatement()
                statement.executeUpdate(sql)
                statement.close()
            }
        }

        fun loadStoreBooks(context: Context, phone:String, callback: (ArrayList<Book>) -> Unit) : ArrayList<Book>
        {
            var books = ArrayList<Book>()
            CoroutineScope(Dispatchers.IO).launch {
                val connection: Connection? = getConntion()
                var sql = "SELECT bookno,name,owner,price,author,publisher,category,image,introduction from book "
                if(phone!=null && phone.isNotEmpty())
                    sql = sql + " WHERE owner='$phone'"
                else
                    sql = sql + " WHERE sold=0"

                val st: Statement
                st = connection!!.createStatement()
                val rs = st.executeQuery(sql)
                while (rs.next()) {
                    val book =  Book(rs.getInt("bookno"),
                                     rs.getString("name"),
                                     rs.getString("owner"),
                                     rs.getFloat("price"),
                                     rs.getString("author"),
                                     rs.getString("publisher"),
                                     rs.getString("category"),
                                     rs.getString("image"),
                                     rs.getString("introduction"))
                    books.add(book)
                }
                withContext(Dispatchers.Main) {
                    callback(books)
                }
                st.close()
            }
            return books
        }
    }
}