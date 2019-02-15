package com.example.administrator.ktplayandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.administrator.ktplayandroid.R
import com.example.handsomelibrary.model.PubNumChildBean
import kotlin.random.Random

/**
 * @author Stefan
 * Created on 2019/2/15 14:56
 */
class PubNumChildAdapter(data: List<PubNumChildBean.DatasBean>) :
    BaseQuickAdapter<PubNumChildBean.DatasBean, BaseViewHolder>(R.layout.item_knowledge_child, data) {

    override fun convert(helper: BaseViewHolder?, item: PubNumChildBean.DatasBean?) {
        helper?.let {
            item?.let {
                helper.setText(R.id.tv_name, item.author)//作者名字
                    .setText(R.id.tv_time, item.niceDate)//时间
                    .setText(R.id.tv_title, item.title)//标题
                    .setText(R.id.tv_superChapterName, item.superChapterName)
                    .setText(R.id.tv_chapterName, item.chapterName)
            }
            val myRandom = Random
            val ranColor: Int = -0x1000000 or myRandom.nextInt(0x00ffffff)
            helper.setTextColor(R.id.tv_name, ranColor)
        }


    }
}