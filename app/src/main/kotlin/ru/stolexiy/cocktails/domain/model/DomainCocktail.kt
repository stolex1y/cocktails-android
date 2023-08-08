package ru.stolexiy.cocktails.domain.model

data class DomainCocktail(
    val id: Long,
    val title: String,
    val description: String?,
    val recipe: String?,
    val image: String?,
    val ingredients: List<String>,
)
