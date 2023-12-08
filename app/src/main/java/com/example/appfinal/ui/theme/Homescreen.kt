package com.example.appfinal.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appfinal.R
import com.example.appfinal.network.MBMovie

@Composable
fun HomeScreen(
    mbUiState: MBUiState,onClick: (MBMovie) -> Unit,retryAction: () -> Unit, modifier: Modifier = Modifier
) {
    val uiState by rememberUpdatedState(newValue = mbUiState)

    when (uiState) {
        is MBUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is MBUiState.Success -> MoviesList((uiState as MBUiState.Success).movies, onClick, modifier = modifier)
        is MBUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun MoviesDetail(movie: MBMovie, onBackPressed: () -> Unit, contentPadding: PaddingValues, modifier: Modifier = Modifier){
    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(top = 8.dp)){
        AsyncImage(model = ImageRequest.Builder(context = LocalContext.current)
            .data(movie.bigImage)
            .crossfade(true)
            .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 4.dp)
                .width(150.dp)
        )
        Column(modifier = Modifier.padding(8.dp)){

            Spacer(Modifier.height(16.dp))

            Text(text = movie.title, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, modifier = Modifier)

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){

                Icon(Icons.Filled.Info, tint = MaterialTheme.colorScheme.secondary, contentDescription = null, modifier = Modifier.padding(end = 2.dp))

                Text(text = movie.contentRating, style = MaterialTheme.typography.titleMedium)

                Text(text = movie.length, style = MaterialTheme.typography.titleMedium)
            }

            Spacer(Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){

                Icon(Icons.Filled.DateRange, tint = MaterialTheme.colorScheme.secondary, contentDescription = null, modifier = Modifier.padding(end = 2.dp))

                Text(text = movie.releaseDate, style = MaterialTheme.typography.titleMedium)
            }

            Spacer(Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){

                Icon(Icons.Filled.Star, tint = MaterialTheme.colorScheme.secondary, contentDescription = null, modifier = Modifier.padding(end = 2.dp))

                Text(text = movie.reviewScore, style = MaterialTheme.typography.titleMedium)
            }

            Spacer(Modifier.height(16.dp))

            Text(text = movie.description, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun MovieCard(movie: MBMovie,onClick: (MBMovie) -> Unit, modifier: Modifier = Modifier){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), modifier = Modifier
            .padding(top = 8.dp)
            .height(180.dp)
            .fillMaxWidth()
            .clickable { onClick(movie) }
    ){
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()){
            AsyncImage(model = ImageRequest.Builder(context = LocalContext.current)
                .data(movie.poster)
                .crossfade(true)
                .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .width(120.dp)
            )
            Column(verticalArrangement = Arrangement.Center,  modifier = Modifier.padding(end = 2.dp)){

                Text(text = movie.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier)

                Spacer(Modifier.height(4.dp))

                Text(text = movie.description, style = MaterialTheme.typography.bodyMedium, maxLines = 4, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(4.dp))

                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){

                    Icon(Icons.Filled.Star, tint = MaterialTheme.colorScheme.secondary, contentDescription = null, modifier = Modifier.padding(end = 2.dp))

                    Text(text = movie.reviewScore, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun MoviesList(
    movies: List<MBMovie>,
    onClick: (MBMovie) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    LazyColumn(contentPadding = contentPadding, modifier = modifier){
        items(movies){movie ->
            MovieCard(movie = movie,
                onClick = { onClick(movie)},
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
}



