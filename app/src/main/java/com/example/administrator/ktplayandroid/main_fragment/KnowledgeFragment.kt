package com.example.administrator.ktplayandroid.main_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.administrator.ktplayandroid.R
import com.example.administrator.ktplayandroid.adapter.KnowledgeAdapter
import com.example.handsomelibrary.api.ApiService
import com.example.handsomelibrary.base.BaseFragment
import com.example.handsomelibrary.interceptor.Transformer
import com.example.handsomelibrary.model.KnowledgeBean
import com.example.handsomelibrary.retrofit.RxHttpUtils
import com.example.handsomelibrary.retrofit.observer.CommonObserver
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_knowledge.*

/**
 * 知识体系
 * Created by Stefan on 2018/11/21.
 */
class KnowledgeFragment : BaseFragment(), OnRefreshLoadMoreListener {

    private lateinit var mAdapter: KnowledgeAdapter
    private lateinit var list:List<String>

    companion object {
        private const val HOME_FRAGMENT = "home"
        fun newInstance(params: String): Fragment {
            val bundle = Bundle()
            bundle.putString(HOME_FRAGMENT, params)
            val fragment = KnowledgeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutID(): Int = R.layout.fragment_knowledge

    override fun initData() {
        getKnowledgeTree()
    }

    private fun getKnowledgeTree() {
        RxHttpUtils.createApi(ApiService::class.java)
            .knowledgeTree
            .compose(Transformer.switchSchedulers())
            .subscribe(object :CommonObserver<List<KnowledgeBean>>(){
                override fun onSuccess(t: List<KnowledgeBean>) {
                    if (t.isNotEmpty()){
                        mAdapter.setNewData(t)
                    }
                }

                override fun onError(errorMsg: String?) {
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // setData()
        val rv_knowList=view.findViewById<RecyclerView>(R.id.rv_knowList)
        rv_knowList.layoutManager = LinearLayoutManager(mContext)
        mAdapter= KnowledgeAdapter(ArrayList())
        rv_knowList.adapter=mAdapter
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
    }

//    private fun setData(){
//        list= listOf("","","")
//    }

}

