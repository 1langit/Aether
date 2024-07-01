package com.pkmaether.aether.ui.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pkmaether.aether.data.models.Article
import com.pkmaether.aether.data.seeder.InformationSeeder

object InformationViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    fun setDummyArticles() {
        val seeder = InformationSeeder()
        _articles.value = seeder.getArticles()
    }
}