package ru.stolexiy.cocktails.ui.cocktail

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.stolexiy.cocktails.common.CoroutineDispatcherNames
import ru.stolexiy.cocktails.common.CoroutineModule
import ru.stolexiy.cocktails.common.FlowExtensions.mapLatestResult
import ru.stolexiy.cocktails.common.FlowExtensions.requireNotNullResult
import ru.stolexiy.cocktails.domain.model.DomainCocktail
import ru.stolexiy.cocktails.domain.repository.CocktailCrudRepository
import ru.stolexiy.cocktails.ui.util.udf.AbstractViewModel
import ru.stolexiy.cocktails.ui.util.udf.IData
import ru.stolexiy.cocktails.ui.util.udf.IEvent
import ru.stolexiy.cocktails.ui.util.udf.IState
import ru.stolexiy.cocktails.ui.util.work.CocktailAddingWorker
import ru.stolexiy.cocktails.ui.util.work.CocktailDeletingWorker
import ru.stolexiy.cocktails.ui.util.work.CocktailUpdatingWorker
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val cocktailRepository: CocktailCrudRepository,
    @Named(CoroutineDispatcherNames.DEFAULT_DISPATCHER) private val defaultDispatcher: CoroutineDispatcher,
    workManager: Provider<WorkManager>,
    @Named(CoroutineModule.APPLICATION_SCOPE) applicationScope: CoroutineScope,
    private val savedStateHandle: SavedStateHandle,
) : AbstractViewModel<CocktailViewModel.Event, CocktailViewModel.Data, CocktailViewModel.State>(
    initData = Data(),
    stateProducer = stateProducer,
    applicationScope = applicationScope,
    workManager = workManager
) {
    private val cocktailId: Long? = savedStateHandle[COCKTAIL_ID_ARG]

    override fun dispatchEvent(event: Event) {
        when (event) {
            Event.Load -> startLoadingData()
            is Event.Add -> addCocktail(event.cocktail)
            Event.Delete -> deleteCocktail()
            is Event.Update -> updateCocktail(event.cocktail)
        }
    }

    fun reloadData() {
        dispatchEvent(Event.Load)
    }

    private fun addCocktail(cocktail: DomainCocktail) {
        val workRequest = CocktailAddingWorker.createWorkRequest(cocktail)
        startWork(
            workRequest = workRequest,
            finishState = State.Loaded
        )
    }

    private fun updateCocktail(cocktail: DomainCocktail) {
        val workRequest = CocktailUpdatingWorker.createWorkRequest(cocktail)
        startWork(
            workRequest = workRequest,
            finishState = State.Loaded
        )
    }

    private fun deleteCocktail() {
        requireNotNull(cocktailId)
        val workRequest = CocktailDeletingWorker.createWorkRequest(cocktailId)
        startWork(
            workRequest = workRequest,
            finishState = State.Loaded
        )
    }

    override fun loadData(): Flow<Result<Data>> {
        return flow {
            if (cocktailId == null)
                emit(Result.success(Data()))
            else {
                val restored = restoreData()
                if (restored != null)
                    emit(Result.success(restored))
                emitAll(
                    cocktailFlow().mapLatestResult { Data(cocktail = it) }
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveData(data.value)
    }

    private fun cocktailFlow(): Flow<Result<DomainCocktail>> {
        requireNotNull(cocktailId)
        return cocktailRepository.get(cocktailId)
            .requireNotNullResult()
    }

    private fun saveData(data: Data) {
        applicationScope.launch {
            if (data.cocktail != null)
                savedStateHandle[COCKTAIL_KEY] = data.cocktail
        }
    }

    private fun restoreData(): Data? {
        val cocktail = savedStateHandle.get<DomainCocktail>(COCKTAIL_KEY) ?: return null
        savedStateHandle.remove<DomainCocktail>(COCKTAIL_KEY)
        return Data(
            cocktail = cocktail
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

        private const val COCKTAIL_KEY = "cocktail"
        const val COCKTAIL_ID_ARG = "cocktailId"
    }

    sealed interface Event : IEvent {
        object Load : Event
        data class Add(val cocktail: DomainCocktail) : Event
        data class Update(val cocktail: DomainCocktail) : Event
        object Delete : Event
    }

    sealed interface State : IState {
        object Init : State
        object Loaded : State
        data class Error(
            @StringRes val error: Int
        ) : State
    }

    data class Data(
        val cocktail: DomainCocktail? = null
    ) : IData
}
