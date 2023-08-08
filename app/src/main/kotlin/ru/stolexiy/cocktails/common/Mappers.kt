package ru.stolexiy.cocktails.common

object Mappers {
    inline fun <E, reified D> Array<E>.mapToArray(transform: E.() -> D): Array<D> {
        return map { it: E -> it.transform() }.toTypedArray()
    }
}
