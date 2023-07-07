package com.example.movieappusingcompose.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.movieappusingcompose.model.Movies
import com.example.movieappusingcompose.model.getMovies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, movieId: String?){

    val movieList = getMovies().filter { movies ->  movies.imdbID == movieId!! }
    val movie = movieList[0]

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Movie Details", textAlign = TextAlign.Center)
        },
            colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back button", tint = Color.White)
                }
            }
        )
    }, content = {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DisplayImages(movie)

            Text(buildAnnotatedString {

                withStyle(style = SpanStyle(Color.Black, fontWeight = FontWeight.Black)){
                    append("Actors: ")
                }
            }, modifier = Modifier.padding(start = 10.dp, top = 5.dp))

            Text(buildAnnotatedString {

                withStyle(style = SpanStyle(Color.Black, fontWeight = FontWeight.Light)){
                    append("${movie.actors}")
                }
            }, modifier = Modifier.padding(start = 10.dp))

            Text(text = "Award: ${movie.awards}")
            Text(text = "languages: ${movie.language}")

        }
    })

}

@Composable
private fun DisplayImages(movie: Movies) {
    LazyRow{
        items(movie.images) { images ->
            Card(modifier = Modifier
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(5.dp)) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(images)
                        .size(Size.ORIGINAL) // Set the target size to load the image at.
                        .crossfade(true)
                        .build()
                )

                if (painter.state is AsyncImagePainter.State.Success) {
                    // This will be executed during the first composition if the image is in the memory cache.

                    Image(
                        painter = painter,
                        contentDescription = "Movie Poster"
                    )
                }
            }

        }
    }
}