package com.example.pairmatch.ui.historyMatch

import androidx.lifecycle.ViewModel
import com.example.pairmatch.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: Repository): ViewModel() {

}