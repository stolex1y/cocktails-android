package ru.stolexiy.cocktails.ui.util.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.stolexiy.cocktails.R
import ru.stolexiy.cocktails.domain.model.DomainCocktail
import ru.stolexiy.cocktails.domain.repository.CocktailCrudRepository

@HiltWorker
class CocktailAddingWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val cocktailRepository: CocktailCrudRepository,
) : AbstractWorker<DomainCocktail, Long>(
    WorkUtils.ADDING_ENTITY_NOTIFICATION_ID,
    appContext.getString(R.string.cocktail_adding),
    "Cocktail adding",
    { action(cocktailRepository, it) },
    DomainCocktail::class,
    appContext,
    workerParams
) {
    companion object {
        fun createWorkRequest(entity: DomainCocktail): OneTimeWorkRequest {
            return createWorkRequest<CocktailAddingWorker, DomainCocktail, Long>(entity)
        }

        private suspend fun action(
            cocktailRepository: CocktailCrudRepository,
            cocktail: DomainCocktail
        ): kotlin.Result<Long> {
            return cocktailRepository.add(cocktail).map { it.first() }
        }
    }
}
