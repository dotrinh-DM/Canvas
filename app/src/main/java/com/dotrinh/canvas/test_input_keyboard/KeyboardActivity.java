/*
 * Created by dotrinh on 16:05, 11/11/2022
 * Copyright (c) 2022. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.test_input_keyboard;

import static com.dotrinh.canvas.tool.LogUtil.LogI;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.dotrinh.canvas.R;
import com.dotrinh.canvas.databinding.TestKeyboardBinding;

public class KeyboardActivity extends AppCompatActivity {

    public Context ctx;
    public TestKeyboardBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_keyboard);
        ctx = this;
        final EditText mEditText = findViewById(R.id.editTextTextPersonName);
        mEditText.setFilters(new InputFilter[] { filter });
        mEditText.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            LogI("11111111111 a enter");
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                LogI("3333333333 a enter " + v.getText());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
                return true;
            }
            if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                LogI("2222222222 a enter");
                handled = true;
            }
            return handled;
        });
    }

    private InputFilter filter = (source, start, end, dest, dstart, dend) -> {
        String blockCharacterSet = getString(R.string.NameRule);
        if (source != null && !blockCharacterSet.contains(("" + source))) {
            return "";
        }
        return null;
    };

}