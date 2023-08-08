package ru.stolexiy.cocktails.ui.cocktails

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.stolexiy.cocktails.common.CoroutineDispatcherNames
import ru.stolexiy.cocktails.common.CoroutineModule
import ru.stolexiy.cocktails.common.FlowExtensions.mapLatestResult
import ru.stolexiy.cocktails.common.FlowExtensions.mapLatestResultList
import ru.stolexiy.cocktails.domain.model.DomainCocktail
import ru.stolexiy.cocktails.domain.repository.CocktailCrudRepository
import ru.stolexiy.cocktails.ui.cocktails.model.Cocktail
import ru.stolexiy.cocktails.ui.cocktails.model.toCocktail
import ru.stolexiy.cocktails.ui.util.udf.AbstractViewModel
import ru.stolexiy.cocktails.ui.util.udf.IData
import ru.stolexiy.cocktails.ui.util.udf.IEvent
import ru.stolexiy.cocktails.ui.util.udf.IState
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

@HiltViewModel
class CocktailsViewModel @Inject constructor(
    private val cocktailRepository: CocktailCrudRepository,
    @Named(CoroutineDispatcherNames.DEFAULT_DISPATCHER) private val defaultDispatcher: CoroutineDispatcher,
    workManager: Provider<WorkManager>,
    @Named(CoroutineModule.APPLICATION_SCOPE) applicationScope: CoroutineScope,
    private val savedStateHandle: SavedStateHandle,
) : AbstractViewModel<CocktailsViewModel.Event, CocktailsViewModel.Data, CocktailsViewModel.State>(
    initData = Data(),
    stateProducer = stateProducer,
    applicationScope = applicationScope,
    workManager = workManager
) {

    override fun dispatchEvent(event: Event) {
        when (event) {
            Event.Load -> startLoadingData()
        }
    }

    override fun loadData(): Flow<Result<Data>> {
        return flow {
            val restored = restoreData()
            if (restored != null)
                emit(Result.success(restored))
            emitAll(
                cocktailsFlow()
                    .mapLatestResult { Data(cocktails = it) }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveData(data.value)
    }

    private fun cocktailsFlow(): Flow<Result<List<Cocktail>>> {
        return cocktailRepository.getAll()
            .mapLatestResultList(DomainCocktail::toCocktail)
            .flowOn(defaultDispatcher)
    }

    fun reloadData() {
        dispatchEvent(Event.Load)
    }

    private fun saveData(data: Data) {
        applicationScope.launch {
            savedStateHandle[COCKTAILS_KEY] = data.cocktails.toTypedArray()
        }
    }

    private fun restoreData(): Data? {
        val cocktails =
            savedStateHandle.get<Array<Cocktail>>(COCKTAILS_KEY)?.toList() ?: return null
        savedStateHandle.remove<Array<Cocktail>>(COCKTAILS_KEY)
        return Data(
            cocktails = cocktails
        )
    }

    companion object {
        private val stateProducer = object : IState.Producer<State> {
            override val initState: State = State.Init
            override val loadedState: State = State.Loaded

            override fun errorState(error: Int): State {
                return State.Error(error)
            }
        }

        private const val COCKTAILS_KEY = "cocktails"
    }

    sealed interface Event : IEvent {
        object Load : Event
    }

    sealed interface State : IState {
        object Init : State
        object Loaded : State
        data class Error(
            @StringRes val error: Int
        ) : State
    }

    data class Data(
        val cocktails: List<Cocktail> = emptyList()
    ) : IData
}
