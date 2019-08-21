package com.github.spazzze.exto.databinding.interfaces

/**
 * @author Space
 * @date 28.01.2017
 */

interface IValidable<T> {
    var fieldValue: T
    val isValid: Boolean
    fun validateField(value: T = fieldValue): Boolean
}