package com.example.administrator.ktplayandroid.main_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.administrator.ktplayandroid.R
import com.example.administrator.ktplayandroid.adapter.PubNumChildAdapter
import com.example.handsomelibrary.api.ApiService
import com.example.handsomelibrary.base.BaseFragment
import com.example.handsomelibrary.interceptor.Transformer
import com.example.handsomelibrary.model.PubNumChildBean
import com.example.handsomelibrary.retrofit.RxHttpUtils
import com.example.handsomelibrary.retrofit.observer.CommonObserver
import com.scwang.smartrefresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.fragment_public_num_child.*

/**
 * @author Stefan
 * Created on 2019/2/14 11:51
 */
class PublicNumberChildFragment : BaseFragment() {
//    @BindView(R.id.rv_pubNumChildList)
//    internal var rv_pubNumChildList: RecyclerView? = null
   // @BindView(R.id.refreshLayout)
    //internal var refreshLayout: SmartRefreshLayout? = null
    private lateinit var mAdapter: PubNumChildAdapter

    private var mPage: Int = 0

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var fragment: PublicNumberChildFragment

        fun getInstance(): PublicNumberChildFragment {
            fragment = PublicNumberChildFragment()
            return fragment
        }
    }

    override fun getLayoutID(): Int = R.layout.fragment_public_num_child

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }

    override fun initData(view: View?) {
        rv_pubNumChildList.layoutManager=LinearLayoutManager(mContext)
        mAdapter= PubNumChildAdapter(ArrayList())
        rv_pubNumChildList.adapter=mAdapter
        getWxArticleList(408, mPage)
    }

    private fun getWxArticleList(id: Int, page: Int) {
        RxHttpUtils.createApi(ApiService::class.java)
            .getWxArticleList(id, page)
            .compose(Transformer.switchSchedulers())
            .subscribe(object : CommonObserver<PubNumChildBean>() {
                override fun onSuccess(t: PubNumChildBean?) {
                    if (t != null) {
                        when {
                            refreshLayout.state == RefreshState.Refreshing -> {
                                mAdapter.setNewData(t.datas)
                                refreshLayout.finishRefresh()
                            }//正在加载
                            refreshLayout.state == RefreshState.Loading -> {
                                mAdapter.data.addAll((t.datas))
                                refreshLayout.finishLoadMore()
                                mAdapter.notifyDataSetChanged()
                            }
                            else -> mAdapter.setNewData((t.datas))
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
                }

            })
    }
}