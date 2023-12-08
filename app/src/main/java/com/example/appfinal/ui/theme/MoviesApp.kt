@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.appfinal.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appfinal.R
import com.example.appfinal.network.MBMovie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieBuffsApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val mbViewModel: MBViewModel = viewModel()

    val uiState by mbViewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MBTopAppBar(scrollBehavior = scrollBehavior, isShowingListPage = true, onNavigateBack = mbViewModel::navigateToListPage) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (uiState.isShowingListPage) {
                HomeScreen(
                    mbUiState = mbViewModel.mbUiState,
                    onClick = {
                        println("Clicked on movie: $it")
                        mbViewModel.navigateToDetailPage()
                    },
                    retryAction = mbViewModel::getMovies
                )
            } else {
                println("Navigating to detail page.")
                val selectedMovie = uiState.currentMovie
                selectedMovie?.let {
                    MoviesDetail(
                        movie = it,
                        onBackPressed = { mbViewModel.navigateToListPage() },
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun MBTopAppBar(scrollBehavior: TopAppBarScrollBehavior,
                isShowingListPage: Boolean, onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Movie Buffs",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        navigationIcon = {
            if (!isShowingListPage) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
            }else null
        },
        modifier = modifier
    )
}

