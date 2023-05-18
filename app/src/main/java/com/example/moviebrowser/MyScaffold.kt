package com.example.moviebrowser.composableUtils

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviebrowser.core.utils.Colors.Companion.primaryColor
import com.example.moviebrowser.core.utils.SearchWidgetState
import com.example.moviebrowser.core.utils.SearchedMoviesState
import com.example.moviebrowser.moviescreen.*
import com.example.moviebrowser.moviescreen.viewmodel.AppViewModel
import androidx.compose.material.Text as Text

@Composable
fun MyScaffold(navigationController: NavController, vm: AppViewModel) {

    val searchWidgetState by vm.searchWidgetState
    val searchTextState by vm.searchTextState
    val searchedMovies by vm.searchedMoviesState
    val index by vm.indexScreen
    vm.loadFavMoviesIds()
    val topBarTitle = when (index) {
        0 -> "Popular Movies"
        1 -> "Favourite Movies"
        // For next tabs:
        else -> ""
    }

    Scaffold(
        topBar = {
            MainAppTopBar(
                title = topBarTitle,
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = { vm.updateSearchTextState(it) },
                onCloseClicked = {
                    vm.updateSearchTextState("")
                    vm.updateSearchWidgetState(SearchWidgetState.CLOSED)
                    vm.updateSearchedMoviesState(SearchedMoviesState.HIDE)
                },
                onSearchClicked = {
                    vm.searchMovie(it)
                    vm.updateSearchedMoviesState(SearchedMoviesState.SHOW)
                    if (index == 1) {
                        vm.indexScreen.value = 0
                    }
                },
                onSearchTriggered = { vm.updateSearchWidgetState(SearchWidgetState.OPENED) }
            )
        },
        bottomBar = { MyBottomNavigation(navigationController, vm, index) },
        content = {
            when (vm.indexScreen.value) {
                0 -> HomeBox(vm, searchedMovies)
                1 -> FavouriteBox(vm)
            }
        }
    )
}

@Composable
fun MyBottomNavigation(navigationController: NavController, vm: AppViewModel, index: Int) {

    BottomAppBar(backgroundColor = primaryColor, contentColor = Color.White) {
        BottomNavigationItem(
            selected = index == 0,
            onClick = {
                vm.onIndexChange(0)
                navigationController.navigate("popularMovies")
            }, icon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = "home")
            }, label = { Text(text = "Home") })

        BottomNavigationItem(
            selected = index == 1,
            onClick = {
//                vm.loadFavMoviesIds()
                vm.onIndexChange(1)
                navigationController.navigate("favouriteMovies")
            }, icon = {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "favourites")
            }, label = { Text(text = "Favourites") })
    }
}

@Composable
fun DefaultTopAppBar(title: String, onSearchClicked: () -> Unit) {
    TopAppBar(
        backgroundColor = primaryColor,
        contentColor = Color.White,
        title = { Text(title) },
        actions = {
            IconButton(onClick = { onSearchClicked() }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search")
            }
        }
    )
}

@Composable
fun MainAppTopBar(
    title: String,
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultTopAppBar(title, onSearchClicked = onSearchTriggered)
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = primaryColor
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search movies here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(fontSize = 16.sp),
            singleLine = true,
            leadingIcon = {
                IconButton(modifier = Modifier
                    .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        "search icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotEmpty()) onTextChange("")
                    else onCloseClicked()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        "close icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked(text) }),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = primaryColor,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            )
        )
    }
}

@Preview
@Composable
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Some Movie",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}