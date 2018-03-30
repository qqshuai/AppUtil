package com.sqq.app.util;

import android.app.Activity;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FormatEditText editText = findViewById(R.id.edit_text);
        editText.setOnTextChangeListener(new FormatEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(CharSequence origin, CharSequence format) {
                Logger.w(origin.toString() + "%%%%%%%%%");
            }
        });
    }
}
