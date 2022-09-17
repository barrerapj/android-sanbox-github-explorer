package com.astrear.composeplayground.presentation.flow.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astrear.composeplayground.data.models.Outcome
import com.astrear.composeplayground.domain.constants.PaginationConstants
import com.astrear.composeplayground.domain.models.GithubPage
import com.astrear.composeplayground.domain.models.GithubRepository
import com.astrear.composeplayground.domain.usecases.SearchRepositoriesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class HomeViewModel(
    private val searchRepositoriesUseCase: SearchRepositoriesUseCase
) : ViewModel(), HomeContract<HomeViewModel.HomeViewState> {
    private val _viewState = MutableSharedFlow<HomeViewState>(replay = 0)
    override val viewState: SharedFlow<HomeViewState> = _viewState

    private var githubCurrentPage: GithubPage? = null
    private var pageLimit: Int = INITIAL_PAGE_LIMIT
    private var nexPageIndex: Int = INITIAL_PAGE_INDEX
    private var searchQuery = ""


    override fun getRepositories(query: String) {
        viewModelScope.launch {
            Timber.i("Emitting values Loading")
            _viewState.emit(HomeViewState.IsLoading)

            resetPageOptions(query)

            if (pageLimit in 1 until nexPageIndex) {
                _viewState.emit(HomeViewState.DontHaveNewItems)
            } else {
                val searchOptions = buildSearchOptions(query, nexPageIndex)
                val result = searchRepositoriesUseCase.getRepositories(searchOptions)

                when (result) {
                    is Outcome.Success -> {
                        nexPageIndex++
                        setPageLimit(result.data.total)
                        githubCurrentPage = result.data
                        _viewState.emit(HomeViewState.HasNewItems(result.data.items))
                    }
                    is Outcome.Error -> {
                        _viewState.emit(HomeViewState.HasGetRepositoriesError)
                    }
                }
            }
        }
    }

    private fun buildSearchOptions(query: String = "", page: Int): Map<String, String> {
        var searchQuery = query

        if (query.isBlank()) {
            searchQuery = getTrendingQuery()
        }

        return mapOf(
            "q" to searchQuery,
            "sort" to "stars",
            "order" to "desc",
            "per_page" to PaginationConstants.ITEMS_PER_PAGE.toString(),
            "page" to page.toString()
        )
    }

    private fun getTrendingQuery(): String {
        val today = LocalDate.now()
        val pastWeek = today.minusDays(WEEK_DAYS)
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val pastWeekFormatted = dateFormatter.format(pastWeek.toDate())

        return "created:>$pastWeekFormatted"
    }

    private fun setPageLimit(totalItems: Int) {
        if (pageLimit < 0) {
            val totalPages = ceil(totalItems / PaginationConstants.ITEMS_PER_PAGE.toFloat()).toInt()
            pageLimit = totalPages
        }
    }

    private suspend fun resetPageOptions(query: String) {
        if (query != searchQuery) {
            pageLimit = INITIAL_PAGE_LIMIT
            nexPageIndex = INITIAL_PAGE_INDEX
            _viewState.emit(HomeViewState.HasChangesInQuery)
        }
    }

    sealed class HomeViewState {
        object IsLoading : HomeViewState()
        class HasNewItems(val items: List<GithubRepository>) : HomeViewState()
        object HasGetRepositoriesError : HomeViewState()
        object DontHaveNewItems : HomeViewState()
        object HasChangesInQuery : HomeViewState()
    }

    companion object {
        const val WEEK_DAYS = 7
        const val INITIAL_PAGE_LIMIT = -1
        const val INITIAL_PAGE_INDEX = 1
    }
}