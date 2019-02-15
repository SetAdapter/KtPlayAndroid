package com.example.administrator.ktplayandroid.main_fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import butterknife.BindView
import com.example.administrator.ktplayandroid.R
import com.example.handsomelibrary.api.ApiService
import com.example.handsomelibrary.base.BaseFragment
import com.example.handsomelibrary.interceptor.Transformer
import com.example.handsomelibrary.model.WxArticleBean
import com.example.handsomelibrary.retrofit.RxHttpUtils
import com.example.handsomelibrary.retrofit.observer.CommonObserver
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * 首页
 * Created by Stefan on 2018/11/21.
 */
class PublicNumberFragment : BaseFragment(), ViewPager.OnPageChangeListener {

//    private var mTabLayout: TabLayout? = null
//    private var mViewPager: ViewPager? = null

    @BindView(R.id.tabLayout)
    internal var mTabLayout: TabLayout? = null
    @BindView(R.id.ViewPager)
    internal var mViewPager: ViewPager? = null

    companion object {
        private const val HOME_FRAGMENT = "home"
        fun newInstance(params: String): Fragment {
            val bundle = Bundle()
            bundle.putString(HOME_FRAGMENT, params)
            val fragment = PublicNumberFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutID(): Int = R.layout.fragment_public_number

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }

    override fun initData(view: View?) {
        view?.let {
            getWxArticle(view)
        }
    }

    private fun getWxArticle(view: View) {
        RxHttpUtils.createApi(ApiService::class.java)
            .wxArticle
            .compose(Transformer.switchSchedulers())
            .subscribe(object : CommonObserver<List<WxArticleBean>>() {
                override fun onSuccess(t: List<WxArticleBean>) {
                    if (t.isNotEmpty()) {
                        initTab(view, t)
                    }
                }

                override fun onError(errorMsg: String?) {
                }

            })
    }

    private fun initTab(view: View, t: List<WxArticleBean>) {
        mTabLayout = view.findViewById(R.id.tabLayout)
        mViewPager = view.findViewById(R.id.viewPager)
        if (mViewPager != null && mTabLayout != null) {
            mViewPager?.adapter = object : FragmentPagerAdapter(childFragmentManager) {
                override fun getItem(position: Int): Fragment? {
                    //TODO 子Fragment 界面 未完
                    return PublicNumberChildFragment.getInstance()
                }

                override fun getCount(): Int {
                    return t.size
                }

                override fun getPageTitle(position: Int): CharSequence? {
                    return t[position].name
                }
            }
            mViewPager?.offscreenPageLimit = 4 //设置ViewPager预加载 貌似并没有貌似卵用
            mViewPager?.addOnPageChangeListener(this)
            mTabLayout?.setupWithViewPager(mViewPager)

            //TabLayout的Tab选择监听
            mTabLayout?.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                }

            })
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
    }

}

