package com.example.booktradinging_login

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.booktradinging_login.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(),RadioGroup.OnCheckedChangeListener {
    private lateinit var rg_tab_bar : RadioGroup
    private lateinit var rb_message : RadioButton
    private lateinit var pager : ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = BookTradingAdapter(this)
        adapter.addFragment(HomeFragment.newInstance("",""), "Home")
        adapter.addFragment(MessageFragment.newInstance("",""), "Message")
        adapter.addFragment(StoreFragment.newInstance("",""), "Store")
        pager = findViewById<ViewPager2>(R.id.pager);
        pager.adapter = adapter
        pager.currentItem = 0
        val tabs = findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabs, pager){ tab, position ->
            tab.text = adapter.getTabTitle(position)
        }.attach()
        rb_message = findViewById(R.id.rb_message)
        rg_tab_bar = findViewById(R.id.rg_tab_bar)
        rg_tab_bar.setOnCheckedChangeListener(this);
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        if(checkedId == R.id.rb_home){
            pager.setCurrentItem(0)
        }
        if(checkedId == R.id.rb_message){
            pager.setCurrentItem(1)
        }
        if(checkedId == R.id.rb_store){
            pager.setCurrentItem(2)
        }
    }
}
