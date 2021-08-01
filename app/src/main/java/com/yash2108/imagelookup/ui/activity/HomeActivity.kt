package com.yash2108.imagelookup.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.yash2108.imagelookup.R
import com.yash2108.imagelookup.databinding.ActivityMainBinding
import com.yash2108.imagelookup.ui.fragment.HomeFragment
import com.yash2108.openissuesreader.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewmodel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflateFragment()
    }

    private fun inflateFragment() {
        supportFragmentManager.commit {
            add<HomeFragment>(R.id.fragment_containing_view)
            addToBackStack("homeFragment")
        }
    }
}