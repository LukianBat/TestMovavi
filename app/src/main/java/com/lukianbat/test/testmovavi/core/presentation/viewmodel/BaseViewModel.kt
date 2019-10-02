package com.lukianbat.test.testmovavi.core.presentation.viewmodel

import io.reactivex.disposables.CompositeDisposable
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
