package ru.stolexiy.cocktails.ui.util.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.stolexiy.cocktails.R
import ru.stolexiy.cocktails.domain.repository.CocktailCrudRepository

@HiltWorker
class CocktailDeletingWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val cocktailRepository: CocktailCrudRepository,
) : AbstractWorker<Long, Unit>(
    WorkUtils.UPDATING_ENTITY_NOTIFICATION_ID,
    appContext.getString(R.string.cocktail_deleting),
    "Cocktail deleting",
    { action(cocktailRepository, it) },
    Long::class,
    appContext,
    workerParams
) {
    companion object {
        fun createWorkRequest(cocktailId: Long): OneTimeWorkRequest {
            return createWorkRequest<CocktailDeletingWorker, Long, Unit>(cocktailId)
        }

        private suspend fun action(
            cocktailRepository: CocktailCrudRepository,
            cocktailId: Long
        ): kotlin.Result<Unit> {
            return cocktailRepository.delete(cocktailId).map {}
        }
    }
}
