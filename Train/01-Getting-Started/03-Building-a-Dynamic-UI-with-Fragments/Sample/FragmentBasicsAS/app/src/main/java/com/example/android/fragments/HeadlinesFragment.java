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

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeadlinesFragment extends ListFragment {
    OnHeadlineSelectedListener mCallback;

    // 父 activity 必须实现这个接口让 fragment 可以传递信息
    public interface OnHeadlineSelectedListener {
        /** 当列表中的 item 被选中时由 HeadlinesFragment 调用 */
        public void onArticleSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // 用 Ipsum 的 Headlines 数组 为 list view 创建一个 array adapter
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));
    }

    @Override
    public void onStart() {
        super.onStart();

        // 当处于双面板布局时，设置 listview 的选中模式为单项模式
        // （在 onStart 中设置的原因是此时 listview 可用）
        if (getFragmentManager().findFragmentById(R.id.article_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    /** onAttach 在 fragment 第一次被联系到它的 activity 上时被调用 */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // 确保父 activity 实现回调接口，如果没实现，抛出异常
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // 通知父 activity 被选择的 item
        mCallback.onArticleSelected(position);

        /**
         * 设置点击的 item 被选中，这样被点击的 item 有高亮效果
         * setItemChecked() 会先检测 listview 的选中模式，
         * 由于前面只在双面板布局时设置了选中模式，所以只有在双面板布局时才有高亮效果
         */
        getListView().setItemChecked(position, true);
    }
}