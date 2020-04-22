package com.neo.pod.pages.casts

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.neo.pod.repository.Repository
import com.neo.pod.repository.data.PodCastList
import com.neo.pod.repository.viewModel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CastsViewModel(
        application: Application,
        private val compositeDisposable: CompositeDisposable,
        private val repository: Repository
) : BaseViewModel(application, compositeDisposable) {

    var podCastListResult = MutableLiveData<PodCastList>()

    init {
        getPodCastList()
    }

    private fun getPodCastList() {
        compositeDisposable.add(repository.getPodCastList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    podCastListResult.value = it.data
                }, {
                    //Todo Error handler
                }))
    }
}