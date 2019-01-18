package com.example.administrator.ktplayandroid.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.administrator.ktplayandroid.R
import com.example.handsomelibrary.model.ArticleListBean
import java.util.*

/**
 * @author Stefan
 * Created on 2019/1/18 13:54
 */
class HomeAdapter(data: List<ArticleListBean.DatasBean>?) :
    BaseQuickAdapter<ArticleListBean.DatasBean, BaseViewHolder>(R.layout.item_home, data) {

    override fun convert(helper: BaseViewHolder?, item: ArticleListBean.DatasBean?) {
        val label = helper?.getView<TextView>(R.id.tv_label)
        item?.let {
            label?.let {
                val tag = item.tags
                if (tag!!.isNotEmpty()) {
                    label.visibility =View.VISIBLE
                    label.text = tag[0].name
                } else run {
                    label.visibility = View.GONE
                }
            }


            helper?.let {
                helper.setText(R.id.tv_name, item.author)//作者姓名
                    .setText(R.id.tv_time, item.niceDate)//时间
                    .setText(R.id.tv_title, item.title)//标题
                    .setText(R.id.tv_superChapterName, item.superChapterName)
                    .setText(R.id.tv_chapterName, item.chapterName)
                val iv_collection = helper.getView<ImageView>(R.id.iv_collection)
                helper.addOnClickListener(R.id.iv_collection)

                val myRandom=Random()
                val ranColor = -0x1000000 or myRandom.nextInt(0x00ffffff)
                helper.setTextColor(R.id.tv_name, ranColor)

              val iv_img=helper.getView<ImageView>(R.id.iv_img)
                if(!item.envelopePic.isNullOrEmpty()){
                    Glide.with(mContext)                             //配置上下文
                        .load(item.envelopePic)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//缓存全尺寸
                        .into(iv_img)
                } else run { iv_img.visibility = View.GONE }
            }
        }
    }
}