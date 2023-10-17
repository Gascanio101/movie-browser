package com.example.moviebrowser.moviescreen.ui.elements

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviebrowser.R
import com.example.moviebrowser.core.utils.Colors
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import java.math.RoundingMode
import java.text.DecimalFormat

@Composable
fun MovieItem(
    movie: PopularMoviesResponse.Result,
    isFavourite: Boolean = false,
    onFavouriteClick: (Int) -> Unit,
//    navController: NavController
) {
    var localIsFavourite by remember { mutableStateOf(isFavourite) }
    val context = LocalContext.current

    Card(
        Modifier
            .fillMaxWidth()
            .background(Colors.backgroundColor)
            .padding(8.dp),
        border = BorderStroke(2.dp, color = Colors.primaryColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.primaryColor)
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val imageUrl = "https://image.tmdb.org/t/p/w1280" + movie.backdropPath

            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            val roundedVoteAverage = df.format(movie.voteAverage)
            Log.i("gabs", "MovieItem: image url = $imageUrl")
            Log.i("gabs2", "MovieItem: backdropPath = ${movie.backdropPath}")
            AsyncImage(
                model = if (imageUrl.contains("null")) {
                    R.drawable.movie_placeholder
                } else imageUrl,
                contentDescription = "movie image",
                placeholder = painterResource(R.drawable.movie_placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .clickable {
                        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
                        Log.i("gabs", "MovieItem: clicked")
                        // TODO: Navigate to detail screen
                        // TODO: Get data either from viewModel or passing it as arguments from here.
                    },
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = movie.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Colors.primaryColor)
            ) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$roundedVoteAverage/10",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }


                    when (localIsFavourite) {
                        true -> Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "favourite",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                localIsFavourite = !localIsFavourite
                                onFavouriteClick(movie.id)
                                Toast.makeText(
                                    context,
                                    "Removed from favourites!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                        false -> Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "favourite",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                localIsFavourite = !localIsFavourite
                                onFavouriteClick(movie.id)
                                Toast.makeText(context, "Added to favourites!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        )
                    }
                }
            }
        }
    }
}