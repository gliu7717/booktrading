package com.example.booktradinging_login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booktradinging_login.database.Book
import com.example.booktradinging_login.database.DBBookTrading
import com.example.booktradinging_login.databinding.FragmentStoreBinding
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StoreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mview: View
    private var bookShopAdapter: BookShopAdapter? = null
    private var bookShops: ArrayList<Book> = ArrayList<Book>()
    private var phone: String = ""
    private lateinit var binding: FragmentStoreBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_store, container, false)
        //binding = FragmentStoreBinding.inflate(inflater,container,false)
        binding = FragmentStoreBinding.bind(mview)
        QMUIStatusBarHelper.translucent(activity)
        binding.topbar.setTitle("My Store").setTextColor(
            ContextCompat.getColor(
                activity!!,
                com.qmuiteam.qmui.arch.R.color.qmui_config_color_white
            )
        )
        binding.topbar.setBackgroundColor(
            ContextCompat.getColor(
                activity!!,
                com.qmuiteam.qmui.arch.R.color.qmui_btn_blue_bg
            )
        )
        val intent = this.activity!!.intent
        val bundle = intent.extras
        phone = bundle!!.getString("Phone").toString()
        //addNewBook = mview.findViewById(R.id.putnew)
        binding.putnew.setOnClickListener {
            val parent_intent = activity!!.intent
            val bundle = parent_intent.extras
            val intent = Intent(context, AddNewBookActivity::class.java)
            intent.putExtras(bundle!!)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this.context)
        //recyclerView = mview.findViewById(R.id.book_recycler_view)
        binding.bookRecyclerView.layoutManager = layoutManager
        bookShopAdapter = BookShopAdapter(bookShops, phone)
        binding.bookRecyclerView.adapter = bookShopAdapter
        DBBookTrading.loadStoreBooks(context!!, phone) { it -> updateBookStore(it) }

        return mview
    }


    private fun updateBookStore(books:ArrayList<Book>)
    {
        bookShops.clear()
        bookShops.addAll(books)

        bookShopAdapter!!.notifyDataSetChanged();
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onStart() {
        DBBookTrading.loadStoreBooks(context!!, phone) { it -> updateBookStore(it) }
        super.onStart()
    }
}