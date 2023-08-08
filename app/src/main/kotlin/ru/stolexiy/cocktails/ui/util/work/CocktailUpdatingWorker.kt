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
class CocktailUpdatingWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val cocktailRepository: CocktailCrudRepository,
) : AbstractWorker<DomainCocktail, Unit>(
    WorkUtils.UPDATING_ENTITY_NOTIFICATION_ID,
    appContext.getString(R.string.cocktail_updating),
    "Cocktail updating",
    { action(cocktailRepository, it) },
    DomainCocktail::class,
    appContext,
    workerParams
) {
    companion object {
        fun createWorkRequest(entity: DomainCocktail): OneTimeWorkRequest {
            return createWorkRequest<CocktailUpdatingWorker, DomainCocktail, Unit>(entity)
        }

        private suspend fun action(
            cocktailRepository: CocktailCrudRepository,
            cocktail: DomainCocktail
        ): kotlin.Result<Unit> {
            return cocktailRepository.update(cocktail)
        }
    }
}
