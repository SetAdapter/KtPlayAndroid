package com.example.administrator.ktplayandroid.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.administrator.ktplayandroid.R
import com.example.handsomelibrary.model.KnowledgeBean

/**
 * @author Stefan
 * Created on 2019/1/24 15:27
 */

class KnowledgeAdapter(data: List<KnowledgeBean>?) :
    BaseQuickAdapter<KnowledgeBean, BaseViewHolder>(R.layout.item_knowledge, data) {

    override fun convert(helper: BaseViewHolder, item: KnowledgeBean) {
        helper.setText(R.id.tv_title, item.name)
        val tv_content = helper.getView<TextView>(R.id.tv_content)
        val sb = StringBuffer()
        val itemChildren = item.children
        for (t in itemChildren) {
            sb.append(t.name + "   ")
        }
        tv_content.text = sb.toString()
    }
}