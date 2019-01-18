package com.example.administrator.ktplayandroid.main_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.administrator.ktplayandroid.R
import com.example.handsomelibrary.base.BaseFragment
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * 知识体系
 * Created by Stefan on 2018/11/21.
 */
class KnowledgeFragment : BaseFragment(), OnRefreshLoadMoreListener {

    companion object {
        private const val HOME_FRAGMENT = "home"
        fun newInstance(params: String): Fragment {
            val bundle = Bundle()
            bundle.putString(HOME_FRAGMENT, params)
            val fragment=KnowledgeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutID(): Int = R.layout.fragment_knowledge

    override fun initData() {

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
    }

}

