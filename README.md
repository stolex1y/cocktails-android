# Cocktail Bar App
Cocktail Bar -  это приложение, которое позволяет пользователям создавать и сохранять свои любимые коктейли в одном месте.
## Функциональность
Реализованные экраны:
- [Экран "Мои коктейли"](app/src/main/kotlin/ru/stolexiy/cocktails/ui/cocktails/CocktailsScreen.kt). Реализовано два варианта отображения экрана: с коктейлями и без. С данного экрана возможен переход на экран с описанием рецепта и на экран с добавлением рецепта.

<img src="screenshots/my_cocktails.png" height=600 width=330 alt="My cocktails" />  <img src="screenshots/my_cocktails_2.png" height=600 width=300 alt="My cocktails list" /> 

- [Экран "Подробности рецепта"](app/src/main/kotlin/ru/stolexiy/cocktails/ui/cocktail/details/CocktailDetailsScreen.kt). Реализовано отображение всех данных рецепта (кроме изображения).
Помимо данных рецепта есть две кнопки: "Изменить" и "Удалить". При нажатии на кнопку "Удалить" отображается диалоговое окно для подтверждения операции.

Пример удаления рецепта:

<img src="screenshots/delete_cocktail.gif" height=600 width=300 alt="Deleting cocktail sample" /> 

- [Экран "Изменение/добавление рецепта"](app/src/main/kotlin/ru/stolexiy/cocktails/ui/cocktail/edit/CocktailEditDialogScreen.kt). Данный экран отображает поля для внесения изменений в рецепт, при этом на данный экран можно попасть двумя способами:
1) из экрана с подробностями рецепта, тогда поля предавритально заполнены данными рецепта и при нажатии на кнопку "Сохранить" произойдет изменение рецепта;

Пример изменения рецепта:

<img src="screenshots/update_cocktail.gif" height=600 width=300 alt="Updating cocktail sample" /> 

2) из экрана со списком коктейлей, тогда поля пустые и при нажатии на кнопку "Сохранить" добавится новый рецепт;

Пример добавления рецепта: 

<img src="screenshots/add_cocktail.gif" height=600 width=300 alt="Adding cocktail sample" /> 

### Осталось не реализовано:
- Выбор изображения для коктейля
- Шаринг списка коктейлей

## Реализация
Для создания приложения использовалась Чистая архитектура, а также паттерн UDF. Таким образом, есть три слоя:
- [__Domain__](app/src/main/kotlin/ru/stolexiy/cocktails/domain): содержит основную модель данных приложения, а также интерфейсы репозиториев для доступа к данным.
- [__Data__](app/src/main/kotlin/ru/stolexiy/cocktails/data): содержит классы и сущности для доступа к локальному хранилищу, представленному базой данных. Создание таблиц и получение DAO интерфейсов для доступа к данным в БД реализовано с использованием библиотеки Room.
- [__UI__](app/src/main/kotlin/ru/stolexiy/cocktails/ui): содержит компоненты и экраны пользовательского интерфейса. Реализован с использованием Jetpack Compose. Кроме того, для связи с доменным слоем реализованы классы ViewModel, в которые поступают события из UI, обрабатываются в новое состояние и данные, которые передаются обратно в UI.
Также за основу была взята [тема](app/src/main/kotlin/ru/stolexiy/cocktails/ui/theme) MaterialDesign с модифицированной цветовой схемой.
Для связи всех компонентов в приложении использовался DI фреймворк Hilt (например, [модуль](app/src/main/kotlin/ru/stolexiy/cocktails/data/di/RepositoryModule.kt) для репозиториев).
Для выполнения фоновой работы, которая не должна быть отменена: WorkManager (см. [примеры](app/src/main/kotlin/ru/stolexiy/cocktails/ui/util/work) Worker).
