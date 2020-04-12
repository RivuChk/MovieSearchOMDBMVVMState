package dev.rivu.moviesearchomdb.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<V: BaseView,P: BasePresenter<V>> : AppCompatActivity() {
    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun initView()

    protected abstract fun injectDependencies()

    protected abstract fun bind()

    protected abstract var presenter: P

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        setContentView(layoutId())
        initView()
        bind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}