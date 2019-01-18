package com.example.administrator.ktplayandroid.main_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.example.administrator.ktplayandroid.R
import com.example.administrator.ktplayandroid.adapter.HomeAdapter
import com.example.handsomelibrary.api.ApiService
import com.example.handsomelibrary.base.BaseFragment
import com.example.handsomelibrary.interceptor.Transformer
import com.example.handsomelibrary.model.ArticleListBean
import com.example.handsomelibrary.retrofit.RxHttpUtils
import com.example.handsomelibrary.retrofit.observer.CommonObserver
import com.example.handsomelibrary.utils.L
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * 首页
 * Created by Stefan on 2018/11/21.
 */
class HomeFragment : BaseFragment(), OnRefreshLoadMoreListener {
     private lateinit var mAdapter: HomeAdapter

    companion object {
        private const val HOME_FRAGMENT = "home"
        fun newInstance(params: String): Fragment {
            val bundle = Bundle()
            bundle.putString(HOME_FRAGMENT, params)
            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutID(): Int = R.layout.fragment_home

    //在onCreateView中获取控件会报空  只能在onViewCreated中通过View 获取  不知啥原因
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = listOf("", "", "")
        getArticleListBean()
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_main)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = HomeAdapter(ArrayList())
        recyclerView.adapter = mAdapter
        //添加头部
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.home_banner, null)
        mAdapter.addHeaderView(inflate)
    }

    override fun initData() {
       // homeRecycleView = Companion.view?.findViewById(R.id.homeRecycleView) as RecyclerView;
    }

    //获取文章列表
    private fun getArticleListBean() {
        RxHttpUtils.createApi(ApiService::class.java)
            .getArticleList(0)
            .compose(Transformer.switchSchedulers())
            .subscribe(object : CommonObserver<ArticleListBean>(){
                override fun onSuccess(t: ArticleListBean) {
                    if (t.datas!!.isNotEmpty()){
                        mAdapter.setNewData(t.datas)
                    }
                }

                override fun onError(errorMsg: String?) {
                    L.i(errorMsg)
                }

            })

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
    }

}

