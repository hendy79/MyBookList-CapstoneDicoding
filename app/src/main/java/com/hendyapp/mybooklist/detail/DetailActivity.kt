package com.hendyapp.mybooklist.detail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.hendyapp.mblcore.domain.model.BookVolume
import com.hendyapp.mybooklist.R
import com.hendyapp.mybooklist.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "data"
    }

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    private var moveToPhotoActivity: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION") val data: BookVolume? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, BookVolume::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_DATA)
        }

        binding.back.setOnClickListener {
            finish()
        }

        if(!data?.image.isNullOrEmpty()) {
            Glide.with(this)
                .load(data!!.image)
                .fitCenter()
                .placeholder(R.drawable.baseline_book_24)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        moveToPhotoActivity = {
                            try {
                                val intent = Intent(
                                    this@DetailActivity,
                                    Class.forName("com.hendyapp.mblphotoviewer.PhotoActivity")
                                )
                                intent.putExtra("url", data.image)
                                startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(this@DetailActivity, "Failed to access photo preview feature", Toast.LENGTH_SHORT).show()
                            }
                        }
                        binding.imageBook.setOnClickListener {
                            safelyOpenPhotoModule()
                        }
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(binding.imageBook)
        } else {
            Glide.with(this)
                .load(R.drawable.baseline_book_24)
                .fitCenter()
                .into(binding.imageBook)
        }

        binding.titleBook.text = if(data!!.title.isEmpty()) "-" else data.title
        binding.subBook.text = """
            Authors:
            ${data.authors.ifEmpty { "-" }}
            Publishers:
            ${data.publisher.ifEmpty { "-" }}
            Published Date:
            ${data.publishedDate.ifEmpty { "-" }}
            Page Count:
            ${if(data.pagecount > 0) data.pagecount else "-"}
        """.trimIndent()
        binding.descBook.text = data.desc.ifEmpty { "-" }

        binding.favBtnBook.setOnClickListener {
            if(viewModel.stateFavoriteEvent.value)
                viewModel.deleteFavoriteBook(data)
            else
                viewModel.insertFavoriteBook(data)
        }

        lifecycleScope.launch {
            viewModel.stateFavoriteEvent.collect {
                binding.favBtnBook.text = if(it) getString(R.string.remove_fav) else getString(R.string.add_fav)
                binding.favTextBook.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        viewModel.setStateFavoriteEvent(data.favorite)
    }

    private fun safelyOpenPhotoModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val modulePhoto = "mblphotoviewer"
        if (splitInstallManager.installedModules.contains(modulePhoto)) {
            moveToPhotoActivity?.invoke()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(modulePhoto)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    moveToPhotoActivity?.invoke()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error installing photo preview feature", Toast.LENGTH_SHORT).show()
                }
        }
    }
}