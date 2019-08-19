package com.github.spazzze.exto.databinding.interfaces

import android.databinding.ObservableBoolean

/**
 * @author Space
 * @date 18.08.2019
 */

interface IRecyclerPageItemViewModel : IRecyclerItemViewModel {

    val isProgressForLoadingPageVisible: ObservableBoolean
}