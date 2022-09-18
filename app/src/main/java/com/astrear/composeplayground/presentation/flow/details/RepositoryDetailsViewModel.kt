package com.astrear.composeplayground.presentation.flow.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astrear.composeplayground.data.models.Outcome
import com.astrear.composeplayground.domain.models.GithubRepository
import com.astrear.composeplayground.domain.usecases.GetRepositoryReadmeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class RepositoryDetailsViewModel(
    private val getRepositoryReadmeUseCase: GetRepositoryReadmeUseCase
) : ViewModel(), RepositoryDetailsContract<RepositoryDetailsViewModel.RepositoryReadmeViewState> {
    private var _viewState = MutableSharedFlow<RepositoryReadmeViewState>(replay = 0)
    override val viewState: SharedFlow<RepositoryReadmeViewState> = _viewState

    override fun getRepositoryReadme(repository: GithubRepository) {
        viewModelScope.launch {
            _viewState.emit(RepositoryReadmeViewState.IsLoading)

            val url = buildRepositoryMarkdownUrl(repository)
            val result = getRepositoryReadmeUseCase.getReadme(url)

            when (result) {
                is Outcome.Success -> {
                    _viewState.emit(RepositoryReadmeViewState.HasReadme(result.data))
                }
                is Outcome.Error -> {
                    _viewState.emit(RepositoryReadmeViewState.DontHaveReadme)
                }
            }
        }
    }

    private fun buildRepositoryMarkdownUrl(repository: GithubRepository): String {
        return StringBuilder().apply {
            append(BASE_URL)
            append("/${repository.ownerName}")
            append("/${repository.name}")
            append("/${repository.branch}")
            append("/$DEFAULT_README_FILE_NAME")
        }.toString()
    }

    sealed class RepositoryReadmeViewState {
        object IsLoading : RepositoryReadmeViewState()
        class HasReadme(val content: String) : RepositoryReadmeViewState()
        object DontHaveReadme : RepositoryReadmeViewState()
    }

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com"
        const val DEFAULT_README_FILE_NAME = "README.md"
    }
}