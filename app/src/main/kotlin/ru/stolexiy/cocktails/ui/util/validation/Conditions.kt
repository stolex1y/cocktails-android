package ru.stolexiy.cocktails.ui.util.validation

import androidx.annotation.StringRes
import java.io.Serializable

object Conditions {
    class None<T> : Condition<T>, Serializable {
        override fun validate(value: T): ValidationResult {
            return ValidationResult.valid()
        }
    }

    class RequiredField<T : CharSequence>(@StringRes private val errorStringRes: Int) :
        Condition<T>, Serializable {
        override fun validate(value: T): ValidationResult {
            return if (value.isBlank()) {
                ValidationResult.invalid(errorStringRes)
            } else {
                ValidationResult.valid()
            }
        }
    }

    class NotEmptyList<T : List<V>, V>(@StringRes private val errorStringRes: Int) :
        Condition<T>,
        Serializable {
        override fun validate(value: T): ValidationResult {
            return if (value.isEmpty())
                ValidationResult.invalid(errorStringRes)
            else
                ValidationResult.valid()
        }
    }
}
