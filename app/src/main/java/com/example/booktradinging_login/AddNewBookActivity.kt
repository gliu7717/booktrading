package com.example.booktradinging_login

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.booktradinging_login.database.Book
import com.example.booktradinging_login.database.DBBookTrading
import com.example.booktradinging_login.databinding.ActivityAddNewBookBinding
import java.io.ByteArrayOutputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class AddNewBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewBookBinding
    private var phone = ""
    private var encodedImage:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewBookBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val bundle = intent.extras
        phone = bundle?.getString("Phone").toString()

        binding.bookName.setText( "Physics")
        binding.bookAuthor.setText("Chris McMullen")
        binding.bookCategory.setText("Textbook")
        binding.bookPublisher.setText("Zishka Publishing")
        binding.bookPrice.setText("19.99")
        binding.bookIntro.setText("This combination of physics study guide and workbook focuses on essential problem-solving skills and strategies.")


        binding.bookSubmit.setOnClickListener{
            addNewBook()
        }
        binding.addPhoto.setOnClickListener {
            intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pickImage.launch("image/*")
        }
    }

    private fun addNewBook() {
        val name = binding.bookName.text.toString()
        val author = binding.bookAuthor.text.toString()
        val category = binding.bookCategory.text.toString()
        val publisher = binding.bookPublisher.text.toString()
        val price = binding.bookPrice.text.toString()
        val intro = binding.bookIntro.text.toString()

        if( name == null || name.isEmpty()){
            Toast.makeText(this, "Please fill the book name", Toast.LENGTH_SHORT).show()
        }
        else if( price == null || price.isEmpty()){
            Toast.makeText(this, "Please fill the book price", Toast.LENGTH_SHORT).show()
        }
        else {
            val book = Book(0, name, phone, price.toFloat(),author,publisher,category, encodedImage,intro)
            DBBookTrading.insertBook(book)

            // back to MainActivity
            finish()
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun encodeImage(bitmap: Bitmap) :String
    {
        val previewWidth =150
        val previewHight = bitmap.height * previewWidth / bitmap.width
        val previewBitmap = Bitmap.createScaledBitmap(bitmap,previewWidth,previewHight,false)
        val byteArrayOutputStream = ByteArrayOutputStream()
        previewBitmap.compress(Bitmap.CompressFormat.JPEG,50, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encode(bytes)
    }
    val pickImage = registerForActivityResult( ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        val inputStream = uri?.let { contentResolver.openInputStream(it) }
        var bitmap = BitmapFactory.decodeStream(inputStream)
        bitmap = rotateImage(bitmap,90f)
        binding.addPhoto.setImageBitmap(bitmap)
        encodedImage = encodeImage(bitmap)
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }
}