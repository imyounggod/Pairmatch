package com.example.pairmatch.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pairmatch.entites.NewsData
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

class NewsViewModul : ViewModel() {
    private val _news = MutableLiveData<NewsData>()
    val news: MutableLiveData<NewsData> = _news
    val storageRef = FirebaseStorage.getInstance().reference
    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            _news.value = getNews()
            println(_news.value)
        }
    }

    private suspend fun getNews(): NewsData? {
        var json: NewsData? = null

        val islandRef = storageRef.child("news.txt")

        val localFile = withContext(Dispatchers.IO) {
            File.createTempFile("news", "txt")
        }

        val result = islandRef.getFile(localFile).await()
        println(localFile)
        if (result.task.isSuccessful) {
            json = GsonBuilder().create().fromJson(
                localFile.readText(),
                NewsData::class.java
            )
        }

        println(json)
        return json
    }
}