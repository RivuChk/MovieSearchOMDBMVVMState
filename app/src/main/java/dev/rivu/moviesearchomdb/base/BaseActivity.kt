package dev.rivu.moviesearchomdb.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<V: BaseView,P: BasePresenter<V>, Binding: ViewBinding> : AppCompatActivity() {

    protected abstract fun bindView(): Binding

    protected abstract fun initView()

    protected abstract fun injectDependencies()

    protected abstract fun bindPresentation()

    protected abstract var presenter: P

    protected lateinit var binding: Binding

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = bindView()
        setContentView(binding.root)
        initView()
        bindPresentation()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}