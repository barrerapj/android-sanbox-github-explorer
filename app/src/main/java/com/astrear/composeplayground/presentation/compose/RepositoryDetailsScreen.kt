package com.astrear.composeplayground.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.astrear.composeplayground.R
import com.astrear.composeplayground.domain.models.GithubRepository
import com.astrear.composeplayground.presentation.compose.contants.PreviewConstants
import com.astrear.composeplayground.presentation.flow.details.RepositoryDetailsContract
import com.astrear.composeplayground.presentation.flow.details.RepositoryDetailsViewModel
import com.astrear.composeplayground.presentation.flow.details.RepositoryDetailsViewModel.RepositoryReadmeViewState
import com.astrear.composeplayground.presentation.utils.toast
import com.astrear.composeplayground.ui.theme.ComposePlaygroundTheme
import com.astrear.composeplayground.ui.theme.Typography
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryDetailsScreen(
    modifier: Modifier = Modifier,
    repository: GithubRepository,
    initialReadmeContent: String = "",
    contract: RepositoryDetailsContract<RepositoryReadmeViewState>
    = koinViewModel<RepositoryDetailsViewModel>()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .verticalScroll(rememberScrollState())
    ) {
        val context = LocalContext.current
        val dialogState = remember { mutableStateOf(false) }
        val readmeContent = remember { mutableStateOf("") }

        // Set initial value only for preview
        if (LocalInspectionMode.current) {
            readmeContent.value = initialReadmeContent
        }

        LocalLifecycleOwner.current.lifecycleScope.launchWhenCreated {
            contract.viewState.collectLatest { state ->
                when (state) {
                    RepositoryReadmeViewState.IsLoading -> {
                        dialogState.value = true
                    }
                    RepositoryReadmeViewState.DontHaveReadme -> {
                        dialogState.value = false
                        toast(context, "Couldn't get repository README.")
                    }
                    is RepositoryReadmeViewState.HasReadme -> {
                        dialogState.value = false
                        readmeContent.value = state.content
                    }
                }
            }
        }

        LaunchedEffect(false) {
            contract.getRepositoryReadme(repository)
        }

        Loader(dialogState = dialogState)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp)
                .padding(horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = repository.name,
                    style = Typography.titleLarge,
                )
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .padding(horizontal = 25.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 2.dp),
                        text = stringResource(
                            id = R.string.formatter_branch_name,
                            repository.branch
                        ),
                        style = Typography.bodySmall,
                        textAlign = TextAlign.End,
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 2.dp),
                        text = stringResource(
                            id = R.string.formatter_size,
                            (repository.size / 1000)
                        ),
                        style = Typography.bodySmall,
                        textAlign = TextAlign.Start,
                    )
                }
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    val tagsSublist = repository.tags.take(4)
                    for (tag in tagsSublist) {
                        SimpleChip(modifier = Modifier.padding(horizontal = 2.dp), text = tag)
                    }
                }
            }
        }

        if (readmeContent.value.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Column(modifier = Modifier.padding(25.dp)) {
                    MarkdownText(
                        modifier = Modifier.fillMaxWidth(),
                        markdown = readmeContent.value
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xff000000)
@Composable
fun RepositoryDetailsPreview() {
    val contract = object : RepositoryDetailsContract<RepositoryReadmeViewState> {
        override val viewState: SharedFlow<RepositoryReadmeViewState> = MutableSharedFlow()

        override fun getRepositoryReadme(repository: GithubRepository) {
            // Nothing
        }

    }
    ComposePlaygroundTheme(dynamicColor = false) {
        RepositoryDetailsScreen(
            initialReadmeContent = PreviewConstants.README,
            repository = PreviewConstants.Repository,
            contract = contract
        )
    }
}
