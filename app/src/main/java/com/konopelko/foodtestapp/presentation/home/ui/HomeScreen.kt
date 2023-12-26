package com.konopelko.foodtestapp.presentation.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.konopelko.foodtestapp.data.api.model.getfood.Food
import com.konopelko.foodtestapp.presentation.common.extension.useDebounce
import com.konopelko.foodtestapp.presentation.common.theme.FoodTestAppTheme
import com.konopelko.foodtestapp.presentation.home.HomeIntent
import com.konopelko.foodtestapp.presentation.home.HomeIntent.FoodNameClicked
import com.konopelko.foodtestapp.presentation.home.HomeIntent.SearchInputChanged
import com.konopelko.foodtestapp.presentation.home.HomeUiState
import com.konopelko.foodtestapp.presentation.home.HomeViewModel

//TODO Unify theme dimensions, text styles, colors
//TODO Add string res

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        modifier = modifier,
        uiState = uiState,
        onIntent = viewModel::acceptIntent
    )
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    SearchBar(
        uiState = uiState,
        onIntent = onIntent
    )

    FoodListContent(
        modifier = Modifier.weight(1f),
        uiState = uiState,
        onIntent = onIntent
    )
}

@Composable
private fun FoodListContent(
    uiState: HomeUiState,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    AnimatedVisibility(visible = uiState.isLoadingFood) {
        CircularProgressIndicator()
    }

    AnimatedVisibility(
        visible = uiState.food.isEmpty() && !uiState.isLoadingFood
    ) {
        Text(
            text = "No food here yet",
            textAlign = TextAlign.Center
        )
    }

    AnimatedVisibility(visible = !uiState.isLoadingFood) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 16.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        ) {
            items(uiState.food) { foodItem ->
                FoodItem(
                    modifier = Modifier.padding(top = 8.dp),
                    foodData = foodItem,
                    onIntent = onIntent
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    uiState: HomeUiState,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf(uiState.searchInput) }

    searchText.useDebounce { newSearchText ->
        onIntent(SearchInputChanged(searchInput = newSearchText))
    }

    LaunchedEffect(uiState.searchInput) {
        if(uiState.searchInput != searchText) {
            searchText = uiState.searchInput
        }
    }

    TextField(
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(text = "Enter food to search") },
        value = searchText,
        onValueChange = { newSearchText ->
            searchText = newSearchText
        }
    )
}

@Composable
private fun FoodItem(
    foodData: Food,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
) {
    Text(
        modifier = Modifier.clickable {
            onIntent(FoodNameClicked(name = foodData.name))
        },
        text = foodData.name
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() = FoodTestAppTheme {
    HomeScreenContent(
        uiState = HomeUiState(
            food = listOf(
                Food(0, "", "Food Name Yammy", 0, 0),
                Food(0, "", "Food Name Yammy", 0, 0),
                Food(0, "", "Food Name Yammy", 0, 0),
                Food(0, "", "Food Name Yammy", 0, 0),
                Food(0, "", "Food Name Yammy", 0, 0),
            ),
        ),
        onIntent = {}
    )
}