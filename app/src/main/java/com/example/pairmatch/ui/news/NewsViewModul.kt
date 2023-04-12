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

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            _news.value = getNews()
        }
    }

    private suspend fun getNews(): NewsData? {
        var json: NewsData? = null
        val storageRef = FirebaseStorage.getInstance().reference

        val islandRef = storageRef.child("news.json")

        val localFile = withContext(Dispatchers.IO) {
            File.createTempFile("news", "txt")
        }

        val result = islandRef.getFile(localFile).await()

        if (result.task.isSuccessful) {
            json = GsonBuilder().create().fromJson(
                localFile.readText(),
                NewsData::class.java
            )
        }

        return json
    }
}