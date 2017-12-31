package io.github.lavenderming.usingviewpagerforscreenslides;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by 阿懂 on 2017/12/21.
 */

public class ScreenSlidePageFragment extends Fragment {
    public static final String ARGUMENTS_BUNDLE_KEY_POSITION = "position";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         ViewGroup viewGroup = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

         Bundle bundle = getArguments();
         if (bundle != null) {
             int position = bundle.getInt(ARGUMENTS_BUNDLE_KEY_POSITION);
             ((TextView)viewGroup.findViewById(R.id.text)).append("" + position);
         }

        return viewGroup;
    }
}
