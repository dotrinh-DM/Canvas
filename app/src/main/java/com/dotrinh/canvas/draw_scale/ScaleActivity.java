/*
 * Created by dotrinh on 13:30, 11/11/2022
 * Copyright (c) 2022. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_scale;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dotrinh.canvas.databinding.ScaleLayoutBinding;

public class ScaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        @NonNull ScaleLayoutBinding binding = ScaleLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
