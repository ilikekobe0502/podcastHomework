package com.neo.pod.pages.casts.play

import android.app.Application
import com.neo.pod.repository.Repository
import com.neo.pod.repository.viewModel.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class CastsPlayViewModel(
        application: Application,
        private val compositeDisposable: CompositeDisposable,
        private val repository: Repository
) : BaseViewModel(application, compositeDisposable) {
}