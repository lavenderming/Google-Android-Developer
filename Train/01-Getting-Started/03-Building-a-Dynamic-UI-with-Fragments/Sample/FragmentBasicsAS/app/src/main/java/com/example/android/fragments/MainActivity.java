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

    /** activity 首次创建时被调用 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);
        
        // 检测 activity 是否使用包含 fragment_container FrameLayout 版本的布局文件。
        // 如果使用，即 fragment_container 不为空，则向 activity 中添加显示标题的 fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // 将 fragment 添加到 'fragment_container' FrameLayout
            getSupportFragmentManager()
                    .beginTransaction()
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

            // Create fragment and give it an argument for the selected article
            ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
}