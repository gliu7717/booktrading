package com.example.booktradinging_login
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booktradinging_login.database.Book
import com.example.booktradinging_login.databinding.HomeBookItemBinding
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class BookHomeAdapter (private val books:List<Book>, val phone:String) : RecyclerView.Adapter<BookHomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHomeViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.home_book_item, parent, false)
        return BookHomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookHomeViewHolder, position: Int) {
        holder.bind (books[position])
    }
}

class BookHomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private lateinit var binding: HomeBookItemBinding

    @OptIn(ExperimentalEncodingApi::class)
    fun bind(book: Book) {
        binding = HomeBookItemBinding.bind(itemView)
        val imageBytes = Base64.decode(book.image)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        binding.bookImage.setImageBitmap(decodedImage)
        binding.bookName.text = book.name
        binding.price.text = "$" + book.price.toString()
        binding.publishName.text = book.publisher
        binding.authorName.text = book.author

    }
}