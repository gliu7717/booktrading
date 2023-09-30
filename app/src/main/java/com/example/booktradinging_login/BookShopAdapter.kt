package com.example.booktradinging_login

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.booktradinging_login.database.Book
import com.example.booktradinging_login.databinding.ShopBookItemBinding
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


class BookShopAdapter (val books:List<Book>, val phone:String) : RecyclerView.Adapter<BookShopViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookShopViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.shop_book_item, parent, false)
        val holder =  BookShopViewHolder( view )
        return holder
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookShopViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }
}

class BookShopViewHolder(  val itemView: View) : RecyclerView.ViewHolder(itemView)
{
    private lateinit var binding: ShopBookItemBinding
    @OptIn(ExperimentalEncodingApi::class)
    fun bind(book:Book)
    {
        //binding = ShopBookItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        binding = ShopBookItemBinding.bind(itemView)
        binding.bookImage.setOnClickListener {
            val position: Int = getAdapterPosition()
            val context: Context = itemView.getContext()
            Toast.makeText(context, "Not implemented ", Toast.LENGTH_SHORT).show()
        }
        binding.trash.setOnClickListener {

        }
        binding.edit.setOnClickListener {
            val context: Context = itemView.getContext()
            Toast.makeText(context, "Entering book update page; Not implemented ", Toast.LENGTH_SHORT).show()
        }
        val imageBytes = Base64.decode(book.image)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        binding.bookImage.setImageBitmap(decodedImage)
        binding.bookName.text = book.name
        binding.price.text = "$" + book.price.toString()
        binding.publishName.text = book.publisher
        binding.authorName.text = book.author
    }
}