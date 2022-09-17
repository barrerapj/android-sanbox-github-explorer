package com.astrear.composeplayground.presentation.compose

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.astrear.composeplayground.R
import com.astrear.composeplayground.domain.models.GithubRepository
import com.astrear.composeplayground.presentation.flow.home.HomeContract
import com.astrear.composeplayground.presentation.flow.home.HomeViewModel
import com.astrear.composeplayground.presentation.flow.home.HomeViewModel.HomeViewState
import com.astrear.composeplayground.presentation.utils.toast
import com.astrear.composeplayground.ui.theme.ComposePlaygroundTheme
import com.astrear.composeplayground.ui.theme.PurpleGrey80
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import java.util.*

@Composable
fun HomeScreen(
    initialItems: List<GithubRepository> = listOf(),
    contract: HomeContract<HomeViewState> = koinViewModel<HomeViewModel>()
) {
    val context = LocalContext.current
    val dialogState = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val items = remember { mutableStateListOf<GithubRepository>() }

    // Load initial data into list for preview preview only
    items.addAll(initialItems)

    LocalLifecycleOwner.current.lifecycleScope.launchWhenCreated {
        contract.viewState.collectLatest {
            handleHomeViewState(context, it, dialogState, items)
        }
    }

    LaunchedEffect(false) {
        contract.getRepositories(searchQuery.value)
    }

    Loader(dialogState = dialogState)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGrey80)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 25.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                label = { Text(stringResource(id = R.string.hint_input_search)) },
            )
            Button(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 5.dp),
                shape = RoundedCornerShape(5.dp),
                contentPadding = PaddingValues(10.dp),
                onClick = {
                    contract.getRepositories(searchQuery.value)
                }
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_search_64px),
                    contentDescription = null
                )
            }
        }

        val listState = rememberLazyListState()

        LazyColumn(
            contentPadding = PaddingValues(vertical = 10.dp),
            state = listState
        ) {

            items(
                items = items,
                itemContent = {
                    GithubRepositoryItem(data = it)
                }
            )
        }

        // call the extension function
        listState.OnBottomReached {
            if (items.isNotEmpty()) {
                contract.getRepositories()
            }
        }
    }
}

private fun handleHomeViewState(
    context: Context,
    state: HomeViewState,
    dialogState: MutableState<Boolean>,
    items: SnapshotStateList<GithubRepository>
) {
    when (state) {
        HomeViewState.HasGetRepositoriesError -> {
            dialogState.value = false
            toast(context, R.string.text_error_search)
        }
        HomeViewState.IsLoading -> {
            dialogState.value = true
        }
        is HomeViewState.HasNewItems -> {
            dialogState.value = false
            items.addAll(state.items)
        }
        is HomeViewState.DontHaveNewItems -> {
            dialogState.value = false
            toast(context, R.string.text_info_empty_search_result)
        }
        is HomeViewState.HasChangesInQuery -> {
            items.clear()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    ComposePlaygroundTheme(dynamicColor = false) {
        HomeScreen(
            List(4) {
                GithubRepository(
                    "Owner",
                    "https://pic.com",
                    "Title",
                    "Content",
                    "C",
                    "public",
                    100,
                    Date(),
                    Date()
                )
            },
            object : HomeContract<HomeViewState> {
                override val viewState: SharedFlow<HomeViewState> =
                    MutableSharedFlow<HomeViewState>(replay = 0)

                override fun getRepositories(query: String) {
                    // Nothing
                }
            })
    }
}

