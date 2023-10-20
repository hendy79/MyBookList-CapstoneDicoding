package com.hendyapp.mybooklist.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.hendyapp.mybooklist.R
import com.hendyapp.mybooklist.databinding.ActivityMainBinding
import com.hendyapp.mybooklist.main.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val viewModel: MainViewModel by viewModels()

    private var loadFavoriteFragment: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.navigationHome -> {
                    loadFragment(HomeFragment())
                }
                R.id.navigationFavorite -> {
                    loadFavoriteFragment = {
                        try {
                            it.isChecked = true
                            val favoriteFragment =
                                Class.forName("com.hendyapp.mblfavorite.FavoriteFragment")
                                    .getDeclaredConstructor().newInstance() as Fragment
                            loadFragment(favoriteFragment)
                        } catch (e: Exception) {
                            Toast.makeText(this, "Failed to access favorite feature", Toast.LENGTH_SHORT).show()
                        }
                    }
                    return@setOnItemSelectedListener safelyOpenFavoriteModule()
                }
            }
            return@setOnItemSelectedListener true
        }

        loadFragment(HomeFragment())
    }

    private fun safelyOpenFavoriteModule(): Boolean {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val modulePhoto = "mblfavorite"
        if (splitInstallManager.installedModules.contains(modulePhoto)) {
            loadFavoriteFragment?.invoke()
            return true
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(modulePhoto)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    loadFavoriteFragment?.invoke()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error installing favorite feature", Toast.LENGTH_SHORT).show()
                }
        }
        return false
    }

    private fun loadFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
}