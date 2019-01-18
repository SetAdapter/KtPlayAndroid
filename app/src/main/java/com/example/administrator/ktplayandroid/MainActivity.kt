package com.example.administrator.ktplayandroid

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.administrator.ktplayandroid.main_fragment.*
import com.example.administrator.mykotlin.T
import com.example.handsomelibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_drawerlayout.*
import kotlinx.android.synthetic.main.custom_toolbar.*

/**
 * 主页
 * @author Stefan
 * Created on 2019/1/18 10:11
 */

class MainActivity : BaseActivity(),BottomNavigationBar.OnTabSelectedListener {

    private var mFragments=ArrayList<Fragment>()
    override fun getContentView(): Int =R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        tl_custom.title = "WanAndroid"
        tl_custom.setTitleTextColor(Color.parseColor("#ffffff")) //设置标题颜色
        setSupportActionBar(tl_custom)
        supportActionBar!!.setHomeButtonEnabled(true) //设置返回键可用
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        initBottomNavigation()
        initFragment()
        setDefaultFragment()

       val mDrawerToggle = object : ActionBarDrawerToggle(this, mDrawerLayout, tl_custom, R.string.open, R.string.open) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }
        }
        mDrawerToggle.syncState()//该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标
        mDrawerLayout.setDrawerListener(mDrawerToggle)

        //监听添加toolbar 菜单（就是搜索图标）
        tl_custom.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.action_edit -> T("Search")
                }
                return true
            }

        })
    }

    //添加toolbar右侧 搜索图标
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //设置默认Fragment
    private fun setDefaultFragment() {
        val manager: FragmentManager = supportFragmentManager//java : getSupportFragmentManager()
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fl_fragment,mFragments[0])
        transaction.commit()
    }

    //添加Fragment
    private fun initFragment() {
        mFragments.add(HomeFragment.newInstance(""))
        mFragments.add(KnowledgeFragment.newInstance(""))
        mFragments.add(PublicNumberFragment.newInstance(""))
        mFragments.add(NavigationFragment.newInstance(""))
        mFragments.add(ProjectFragment.newInstance(""))
    }


    private fun initBottomNavigation() {
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_SHIFTING)

        bottom_navigation_bar.addItem(BottomNavigationItem(R.drawable.svg_home_n,getString(R.string.home))).setActiveColor(R.color.homeMain)
            .addItem(BottomNavigationItem(R.drawable.svg_knowledge_n,getString(R.string.knowledge)))
            .setActiveColor(R.color.homeMain)
            .addItem(BottomNavigationItem(R.drawable.svg_publicnumber_n, getString(R.string.publicNumber)))
            .setActiveColor(R.color.homeMain)
            .addItem(BottomNavigationItem(R.drawable.svg_navigation_n, getString(R.string.navigation)))
            .setActiveColor(R.color.homeMain)
            .addItem(BottomNavigationItem(R.drawable.svg_project_n, getString(R.string.project)))
            .setActiveColor(R.color.homeMain)
            .setFirstSelectedPosition(0)
            .initialise()
        bottom_navigation_bar.setTabSelectedListener(this)
    }

    override fun onTabReselected(position: Int) {
    }

    override fun onTabUnselected(position: Int) {
        if (mFragments.isNotEmpty()) {
            if (position < mFragments.size) {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                val nextFragment = mFragments[position]
                ft.hide(nextFragment)
                ft.commitAllowingStateLoss()
            }
        }
    }

    override fun onTabSelected(position: Int) {
        if (mFragments.isNotEmpty()){
            if(position<=mFragments.size){
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                val currentFragment = manager.findFragmentById(R.id.fl_fragment)
                val nextFragment = mFragments[position]
                if (nextFragment.isAdded) {
                    transaction.hide(currentFragment).show(nextFragment)
                } else {
                    transaction.hide(currentFragment).add(R.id.fl_fragment, nextFragment)
                    if (nextFragment.isHidden) {
                        transaction.show(nextFragment)
                    }
                }
                transaction.commitAllowingStateLoss()

                when (position) {
                    0 -> tl_custom.title = "WanAndroid"
                    1 -> tl_custom.title = getString(R.string.knowledge)
                    2 -> tl_custom.title = getString(R.string.publicNumber)
                    3 -> tl_custom.title = getString(R.string.navigation)
                    4 -> tl_custom.title = getString(R.string.project)
                }
            }
        }

    }





}
