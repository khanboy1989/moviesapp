package net.cocooncreations.moviesapp.ui.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * BaseViewModel, includes common functions or variables in order to reduce the code duplication and boiler plate code
 */
abstract class BaseViewModel: ViewModel() {

    open fun onViewCreated(){}
    open fun onViewDestroyed(){}

    protected var disposable = CompositeDisposable()

}