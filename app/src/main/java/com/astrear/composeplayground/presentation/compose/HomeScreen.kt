package com.astrear.composeplayground.presentation.compose

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.astrear.composeplayground.R
import com.astrear.composeplayground.domain.models.GithubRepository
import com.astrear.composeplayground.presentation.compose.contants.PreviewConstants
import com.astrear.composeplayground.presentation.flow.home.HomeContract
import com.astrear.composeplayground.presentation.flow.home.HomeViewModel
import com.astrear.composeplayground.presentation.flow.home.HomeViewModel.HomeViewState
import com.astrear.composeplayground.presentation.utils.toast
import com.astrear.composeplayground.ui.theme.ComposePlaygroundTheme
import com.astrear.composeplayground.ui.theme.PurpleGrey80
import kotlinx.coroutines.flow.*
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    initialItems: List<GithubRepository> = listOf(),
    contract: HomeContract<HomeViewState> = koinViewModel<HomeViewModel>(),
    onDetailsNavigation: (repository: GithubRepository) -> Unit
) {

    val context = LocalContext.current
    val loaderState = remember { mutableStateOf(false) }

    LocalLifecycleOwner.current.lifecycleScope.launchWhenCreated {
        contract.viewState.collectLatest {
            handleHomeViewState(context, it, loaderState)
        }
    }

    val searchQuery = remember { mutableStateOf("") }
    // FIXME: use preview mock data without using logic
    val repositoryList: State<List<GithubRepository>> = if (LocalInspectionMode.current) {
        remember { mutableStateOf(initialItems) }
    } else {
        contract.repositories.collectAsState()
    }
    var selectedId by remember { mutableStateOf(-1L) }
    val scrollState = rememberLazyListState()


    Loader(dialogState = loaderState)

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

        LazyColumn(
            contentPadding = PaddingValues(vertical = 10.dp),
            state = scrollState
        ) {
            items(
                items = repositoryList.value,
                itemContent = { repository ->
                    GithubRepositoryItem(
                        modifier = Modifier.selectable(
                            selected = repository.id == selectedId,
                            onClick = {
                                selectedId = repository.id
                                onDetailsNavigation(repository)
                            }
                        ), data = repository
                    )
                },
            )
        }

        // call the extension function
        scrollState.OnBottomReached {
            if (repositoryList.value.isNotEmpty()) {
                contract.getRepositories()
            }
        }
    }
}

private fun handleHomeViewState(
    context: Context,
    state: HomeViewState,
    dialogState: MutableState<Boolean>
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
        }
        is HomeViewState.DontHaveNewItems -> {
            dialogState.value = false
            toast(context, R.string.text_info_empty_search_result)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val previewMockItems = List(4) {
        PreviewConstants.Repository
    }

    val previewContract = object : HomeContract<HomeViewState> {
        override val viewState: SharedFlow<HomeViewState> =
            MutableSharedFlow<HomeViewState>(replay = 0)

        override val repositories: StateFlow<List<GithubRepository>> =
            MutableStateFlow(previewMockItems)

        override fun getRepositories(query: String) {
            // Nothing
        }
    }

    ComposePlaygroundTheme(dynamicColor = false) {
        HomeScreen(
            initialItems = previewMockItems,
            contract = previewContract,
            onDetailsNavigation = {
                Timber.i("Repository selected: $it")
            },
        )
    }
}

