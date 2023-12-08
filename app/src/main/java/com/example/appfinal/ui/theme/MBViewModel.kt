package com.example.appfinal.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfinal.network.MBApi
import com.example.appfinal.network.MBMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MBUiState {
    data class Success(val movies: List<MBMovie>) : MBUiState
    object Error : MBUiState
    object Loading : MBUiState
}

data class UiState(
    val currentMovie: MBMovie?,
    val isShowingListPage: Boolean = true
)

class MBViewModel : ViewModel() {

    var mbUiState: MBUiState by mutableStateOf(MBUiState.Loading)
        private set

    private val _uiState = MutableStateFlow(
        UiState(
            currentMovie = null,
            isShowingListPage = true
        )
    )

    init {
        getMovies()
    }

    val uiState: StateFlow<UiState> = _uiState

    fun updateCurrentMovie(selectedMovie: MBMovie){
        _uiState.update{
            it.copy(currentMovie = selectedMovie)
        }
    }

    fun navigateToListPage(){
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }

    fun navigateToDetailPage() {
        _uiState.update{
            it.copy(isShowingListPage = false)
        }
        println("Navigating to detail page.")
    }

    fun getMovies() {
        viewModelScope.launch {
            mbUiState = try {
                MBUiState.Success(MBApi.retrofitService.getMovies())
            } catch (e: IOException) {
                MBUiState.Error
            }
        }
    }
}

