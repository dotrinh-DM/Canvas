/*
 * Created by dotrinh on 13:30, 11/11/2022
 * Copyright (c) 2022. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.test_replaceView;

import static com.dotrinh.canvas.tool.LogUtil.LogI;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dotrinh.canvas.databinding.ReplaceLayoutBinding;
import com.dotrinh.canvas.tool.LogUtil;

import java.util.ArrayList;

public class ReplaceViewActivity extends AppCompatActivity {

    ArrayList<RelativeLayout> undoArr = new ArrayList<>();
    int count = 1;
    float scaleTest = 1;
    int xTest = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReplaceLayoutBinding binding = ReplaceLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.addBtn.setOnClickListener(v -> {
            binding.parentID.removeView(binding.parentID.getChildAt(0));
            count++;
            xTest += 30;
            scaleTest -= 0.1f;
            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.setX(xTest);
            // relativeLayout.setScaleX(scaleTest);
            // relativeLayout.setScaleY(scaleTest);
            relativeLayout.setId(View.generateViewId());
            relativeLayout.setBackgroundColor(Color.BLUE);
            TextView textView = new TextView(this);
            textView.setText("" + count);
            textView.setTextSize(50 + (count * 10));
            RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(textViewParams);
            relativeLayout.addView(textView);

            //replace UI
            binding.parentID.addView(relativeLayout);
            //add stack
            undoArr.add(relativeLayout);
        });
        binding.undoBtn.setOnClickListener(v -> {
            if (undoArr.size() > 0) {
                RelativeLayout relativeLayout = undoArr.remove(undoArr.size() - 1);
                TextView textView = (TextView) relativeLayout.getChildAt(0);
                Log.i("aaaaaaaaaaaaaaa ", (String) textView.getText());
                binding.parentID.removeView(binding.parentID.getChildAt(0));
                binding.parentID.addView(relativeLayout);
                count--;
                scaleTest += 0.1f;
                xTest -= 30;
            }
        });
        new Handler().postDelayed(() -> {
            undoArr.add(binding.subID);
        }, 1000);
    }
}
