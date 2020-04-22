package com.neo.pod

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.neo.pod.pages.casts.CastsFragment
import com.neo.pod.pages.casts.CastsViewModel
import com.neo.pod.pages.casts.detail.CastsDetailFragment
import com.neo.pod.pages.casts.detail.CastsDetailViewModel
import com.neo.pod.pages.casts.play.CastsPlayViewModel
import com.neo.pod.repository.Repository
import com.neo.pod.repository.provider.preferences.SharedPreferencesProvider
import com.neo.pod.repository.provider.resource.ResourceProvider
import io.reactivex.disposables.CompositeDisposable


class ViewModelFactory(private val application: Application,
                       private val repository: Repository,
                       private val preferences: SharedPreferencesProvider,
                       private val resource: ResourceProvider
) : ViewModelProvider.NewInstanceFactory() {

    //ViewModel需要的model再打進去

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(CastsViewModel::class.java) -> CastsViewModel(application, CompositeDisposable(), repository)
                isAssignableFrom(CastsDetailViewModel::class.java) -> CastsDetailViewModel(application, CompositeDisposable(), repository)
                isAssignableFrom(CastsPlayViewModel::class.java) -> CastsPlayViewModel(application, CompositeDisposable(), repository)
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            } as T
        }
    }
}