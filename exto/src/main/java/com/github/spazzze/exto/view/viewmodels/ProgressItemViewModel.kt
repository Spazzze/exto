package com.github.spazzze.exto.view.viewmodels

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import com.github.spazzze.exto.databinding.interfaces.IRecyclerPageItemViewModel

/**
 * @author Space
 * @date 18.08.2019
 */

class ProgressItemViewModel : BaseObservable(), IRecyclerPageItemViewModel {

    override val id: String = ""

    override val isProgressForLoadingPageVisible = ObservableBoolean(true)
}