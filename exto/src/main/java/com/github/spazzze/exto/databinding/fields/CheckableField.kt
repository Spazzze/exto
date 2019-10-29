package com.github.spazzze.exto.databinding.fields

import androidx.databinding.ObservableField
import androidx.annotation.StringRes
import com.github.spazzze.exto.databinding.interfaces.IValidable

/**
 * @author Space
 * @date 22.01.2017
 */


/**
 *  Sets value, checks if it is correct by passed check function and changes corresponding
 *  observableError
 */
class CheckableField<T : Any>(t: T,
                              private val observableError: ObservableField<Int>,
                              @StringRes private val stringRes: Int,
                              private val check: (T) -> Boolean) : ObservableField<T>(t), IValidable<T> {

    @Volatile
    override var fieldValue = t
        @Synchronized set(value) {
            if (field != value) {
                field = value
                validateField(value)
                notifyChange()
            }
        }

    override val isValid: Boolean
        get() = check(fieldValue)

    override fun get() = fieldValue

    override fun set(value: T?) {
        fieldValue = value ?: return
    }

    override fun validateField(value: T) = when (check(value)) {
        true -> {
            observableError.set(null); true
        }
        false -> {
            observableError.set(stringRes); false
        }
    }
}