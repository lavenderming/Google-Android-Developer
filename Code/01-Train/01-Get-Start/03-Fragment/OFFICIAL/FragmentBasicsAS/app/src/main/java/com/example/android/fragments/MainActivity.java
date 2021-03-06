/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity
        implements HeadlinesFragment.OnHeadlineSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);
        
        // 检测 activity 是否使用包含 fragment_container FrameLayout 版本的布局文件。
        // 如果使用，即 fragment_container 不为空（用于手持设备的单面板布局），
        // 则向 activity 中添加显示文章标题的 fragment
        if (findViewById(R.id.fragment_container) != null) {

            // 如果 activity 是从之前的状态恢复，则跳过向 activity 中添加 fragment 的操作。
            if (savedInstanceState != null) {
                return;
            }

            // 创建用于显示文章标题的 HeadlinesFragment 的实例
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            // 如果该 activity 由来自 Intent 的特殊指令启动，
            // 传入 Intent 的 extras 作为 fragment 的参数
            firstFragment.setArguments(getIntent().getExtras());

            // 将 fragment 添加到 'fragment_container' FrameLayout
            getSupportFragmentManager()     // 获取 FragmentManager
                    .beginTransaction()     // 获取 FragmentTransaction
                    .add(R.id.fragment_container, firstFragment)
                    .commit();
        }
    }

    /**
     * Activity 实现 HeadlinesFragment.OnHeadlineSelectedListener 接口
     * 该接口将使 HeadlinesFragment 不直接操作其它 fragment
     * @param position
     */
    public void onArticleSelected(int position) {
        // 用户从 HeadlinesFragment 中选中了一篇文章的标题

        // 从 activity 布局中查找 article fragment
        ArticleFragment articleFrag = (ArticleFragment)
                getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        if (articleFrag != null) {
            // 若 article frag 存在, 说明处于双面板布局

            // 调用显示文章内容的 ArticleFragment 中的方法更新它的内容
            articleFrag.updateArticleView(position);

        } else {
            // 若不存在，说明处于单面板布局，则需要变换 fragment

            // 创建 fragment 并设置其参数
            ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newFragment.setArguments(args);

            // 创建 fragment 事务
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // 不论当前的 fragment_container view 是什么，将其替换为新的 fragment
            // 将事务添加到 back stack 让用户可以回退
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // 提交事务
            transaction.commit();
        }
    }
}