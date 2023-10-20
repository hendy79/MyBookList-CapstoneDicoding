package com.hendyapp.mblphotoviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hendyapp.mblphotoviewer.databinding.ActivityPhotoBinding
import com.igreenwood.loupe.Loupe

class PhotoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        intent.getStringExtra("url")?.let {
            if(it.isNotEmpty()) {
                Glide.with(this)
                    .load(it)
                    .fitCenter()
                    .into(binding.image)
            }
        }
        Loupe.create(binding.image, binding.container) {
            onViewTranslateListener = object : Loupe.OnViewTranslateListener {

                override fun onStart(view: ImageView) {
                    // called when the view starts moving
                }

                override fun onViewTranslate(view: ImageView, amount: Float) {
                    // called whenever the view position changed
                }

                override fun onRestore(view: ImageView) {
                    // called when the view drag gesture ended
                }

                override fun onDismiss(view: ImageView) {
                    // called when the view drag gesture ended
                    finish()
                }
            }
        }
    }
}