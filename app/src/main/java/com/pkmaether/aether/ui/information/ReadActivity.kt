package com.pkmaether.aether.ui.information

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.pkmaether.aether.R
import com.pkmaether.aether.data.models.Article
import com.pkmaether.aether.databinding.ActivityReadBinding

class ReadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = "Baca"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val article: Article? = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("ARTICLE", Article::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("ARTICLE") as? Article
        }

        with(binding) {
            article?.let {
                txtTitle.text = article.title
                txtAuthor.text = "${article.author} - ${article.date}"
                txtSource.text = article.source
                txtContent.text = article.content
                Glide.with(root).load(article.imageUrl).into(imgCover)
            }
        }
    }
}