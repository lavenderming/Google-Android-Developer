package io.github.lavenderming.usingviewpagerforscreenslides;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by 阿懂 on 2017/12/21.
 */

public class ScreenSlidePageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
    }
}
