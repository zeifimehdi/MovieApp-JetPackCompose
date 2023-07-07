package com.example.movieappusingcompose.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.example.movieappusingcompose.R
import com.example.movieappusingcompose.model.Movies
import com.example.movieappusingcompose.model.getMovies
import com.example.movieappusingcompose.navigation.NavigationScreens


@Composable
fun HomeScreen(navController: NavController){

    MainContent(navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    navController: NavController,
    movieList: List<Movies> = getMovies()
){
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Movie App")
        }, colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White)
        )
    }, content = {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            LazyColumn(){
                items(movieList){
                    ItemRow(item = it){
                        movie-> navController.navigate(NavigationScreens.DetailsScreen.name+"/${movie.imdbID}")
                    }
                }
            }
        }
    })

}

@Preview
@Composable
fun ItemRow(item : Movies = getMovies()[0] , onItemClick: (Movies) -> Unit = {}){

    var expanded by remember{
        mutableStateOf(false)
    }
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxSize()
        .clickable {
            onItemClick(item)
        },
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 15.dp
        ),
        colors = CardDefaults.cardColors(
            Color.White
        )

    ) {

        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxSize(),
            Arrangement.Start) {
            Surface(modifier = Modifier
                .padding(5.dp)
                .size(100.dp),
                color = Color.White
            ) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.images[0])
                        .size(Size.ORIGINAL) // Set the target size to load the image at.
                        .crossfade(true)
                        .transformations(CircleCropTransformation())
                        .build()
                )

                if (painter.state is AsyncImagePainter.State.Success) {
                    // This will be executed during the first composition if the image is in the memory cache.
                }

                Image(
                    painter = painter,
                    contentDescription ="Movie Poster"
                )
            }

            Column() {
                Text(text = item.title , modifier = Modifier.padding(start = 10.dp, bottom = 5.dp, top= 10.dp),
                    style =  MaterialTheme.typography.titleLarge)
                Text(text = "Director : ${item.director}" , modifier = Modifier.padding(start = 10.dp),
                    style =  MaterialTheme.typography.bodyMedium)
                Text(text = "Released : ${item.released}" , modifier = Modifier.padding(start = 10.dp, bottom = 5.dp),
                    style =  MaterialTheme.typography.bodyMedium)

                AnimatedVisibility(visible = expanded) {
                    Column() {

                        Text(text = "Rated : ${item.rated}" , modifier = Modifier.padding(start = 10.dp),
                            style =  MaterialTheme.typography.bodyMedium)

                        Text(text = "imbd Rating : ${item.imdbRating}" , modifier = Modifier.padding(start = 10.dp),
                            style =  MaterialTheme.typography.bodyMedium)

                        Divider()

                        Text(buildAnnotatedString {

                            withStyle(style = SpanStyle(Color.Black, fontWeight = FontWeight.Black)){
                                append("Plot: ")
                            }
                        }, modifier = Modifier.padding(start = 10.dp, top = 5.dp))

                        Text(buildAnnotatedString {

                            withStyle(style = SpanStyle(Color.Black, fontWeight = FontWeight.Light)){
                                append("${item.plot}")
                            }
                        }, modifier = Modifier.padding(start = 10.dp))
                    }
                }


                Icon(imageVector = if(expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Expand item",
                tint = Color.Gray,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        expanded = !expanded
                    })
            }


        }
    }
}