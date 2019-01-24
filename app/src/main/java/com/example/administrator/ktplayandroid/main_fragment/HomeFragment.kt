package com.example.administrator.ktplayandroid.main_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.administrator.ktplayandroid.R
import com.example.administrator.ktplayandroid.adapter.HomeAdapter
import com.example.administrator.ktplayandroid.view.GlideImageLoader
import com.example.administrator.ktplayandroid.view.WebViewActivity
import com.example.handsomelibrary.api.ApiService
import com.example.handsomelibrary.base.BaseFragment
import com.example.handsomelibrary.interceptor.Transformer
import com.example.handsomelibrary.model.ArticleListBean
import com.example.handsomelibrary.model.BannerBean
import com.example.handsomelibrary.retrofit.RxHttpUtils
import com.example.handsomelibrary.retrofit.observer.CommonObserver
import com.example.handsomelibrary.utils.L
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.home_banner.*
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer.DepthPage
import com.youth.banner.listener.OnBannerListener

/**
 * 首页
 * Created by Stefan on 2018/11/21.
 */

class HomeFragment : BaseFragment(), OnRefreshLoadMoreListener {
    private lateinit var mAdapter: HomeAdapter
    private var mPageNo: Int = 0

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
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_main)
        recyclerView.layoutManager = LinearLayoutManager(mContext) as RecyclerView.LayoutManager?
        mAdapter = HomeAdapter(ArrayList())
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshLoadMoreListener(this)//监听刷新
        //添加头部
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.home_banner, null)
        mAdapter.addHeaderView(inflate)
        mAdapter.onItemClickListener =
                BaseQuickAdapter.OnItemClickListener { _, _, position ->
                    val data = mAdapter.data
                    if (data.size != 0 && data.isNotEmpty()) {
                        WebViewActivity.startWebActivity(mContext, data[position].link)
                    }
                }
    }

    override fun initData() {
        getBanner()
        getArticleListBean(mPageNo)
        // homeRecycleView = Companion.view?.findViewById(R.id.homeRecycleView) as RecyclerView;
    }

    //头部banner设置
    private fun getBanner() {
        RxHttpUtils.createApi(ApiService::class.java)
            .banner
            .compose(Transformer.switchSchedulers())
            .subscribe(object : CommonObserver<List<BannerBean>>() {
                override fun onSuccess(t: List<BannerBean>) {
                    setBannerView(t)
                }

                override fun onError(errorMsg: String?) {

                }
            })
    }

    private fun setBannerView(bannerBean: List<BannerBean>) {
        val titleList = ArrayList<String>()
        for (it in 0 until bannerBean.size) {
            titleList.add(bannerBean[it].title)
        }
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
        //设置图片加载器
        banner.setImageLoader(GlideImageLoader())
        //设置图片集合
        banner.setImages(bannerBean)
        //设置banner动画效果
        // banner.setBannerAnimation(Transformer.DepthPage)
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titleList)
        //设置自动轮播，默认为true
        banner.isAutoPlay(true)
        //设置轮播时间
        banner.setDelayTime(1500)
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT)
        //点击事件
        banner.setOnBannerListener { position ->
            WebViewActivity.startWebActivity(mContext,bannerBean[position].url)
        }
        //banner设置方法全部调用完毕时最后调用
        banner.start()
    }

    //获取文章列表
    private fun getArticleListBean(mPageNo: Int) {
        RxHttpUtils.createApi(ApiService::class.java)
            .getArticleList(mPageNo)
            .compose(Transformer.switchSchedulers())
            .subscribe(object : CommonObserver<ArticleListBean>() {
                override fun onSuccess(t: ArticleListBean) {
                    if (t.datas!!.isNotEmpty()) {
                        when {
                            refreshLayout.state == RefreshState.Refreshing -> {
                                mAdapter.setNewData(t.datas)
                                refreshLayout.finishRefresh()
                            }//正在加载
                            refreshLayout.state == RefreshState.Loading -> {
                                mAdapter.data.addAll(t.datas)
                                refreshLayout.finishLoadMore()
                                mAdapter.notifyDataSetChanged()
                            }
                            else -> mAdapter.setNewData(t.datas)
                        }
                    } else {
                        if (refreshLayout.state == RefreshState.Refreshing) {
                            refreshLayout.finishRefresh()
                        } else if (refreshLayout.state == RefreshState.Loading) {
                            refreshLayout.finishLoadMore()
                        }
                    }
                }

                override fun onError(errorMsg: String?) {
                    L.i(errorMsg)
                }
            })
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPageNo += 1
        getArticleListBean(mPageNo)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPageNo = 0
        getArticleListBean(mPageNo)
    }

    override fun onPause() {
        super.onPause()
        if (refreshLayout.state == RefreshState.Refreshing) {
            refreshLayout.finishRefresh()
        }
        if (refreshLayout.state == RefreshState.Loading) {
            refreshLayout.finishLoadMore()
        }
    }
}

