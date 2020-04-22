package com.neo.pod.pages.casts.detail

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.neo.pod.repository.Repository
import com.neo.pod.repository.data.PodCastCollection
import com.neo.pod.repository.viewModel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CastsDetailViewModel(
        application: Application,
        private val compositeDisposable: CompositeDisposable,
        private val repository: Repository
) : BaseViewModel(application, compositeDisposable) {

    var detailResult = MutableLiveData<PodCastCollection>()

    init {
        getPodCastDetail()
    }

    private fun getPodCastDetail() {
        compositeDisposable.add(repository.getPodCastDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response.data?.collection?.let {
                        detailResult.value = it
                    }
                }, {
                    //Todo Error handler
                }))
    }
}