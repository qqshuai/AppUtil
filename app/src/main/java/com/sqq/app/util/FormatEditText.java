package com.sqq.app.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 * 格式化输入框
 *
 * @author shuaiqiangqiang
 * @version 1.0 2018/3/30
 * @since JDK 1.7
 */
public class FormatEditText extends EditText {

    private static final char BLANK = ' ';
    private static final int[] PATTERN = {3, 4, 4};
    private OnTextChangeListener mListener;
    private boolean callBackSwitch;

    public FormatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FormatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        addTextChangedListener(formatTextWatcher);
    }

    // 输入监听
    private TextWatcher formatTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            callBackSwitch = true;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (checkIndex(i) && s.charAt(i) == BLANK) {
                    Log.i(getClass().getSimpleName(), String.valueOf(i));
                } else {
                    sb.append(s.charAt(i));
                    if (checkLength(sb.length()) && sb.charAt(sb.length() - 1) != BLANK) {
                        sb.insert(sb.length() - 1, BLANK);
                    }
                }
            }
            if (!TextUtils.equals(sb, s)) {
                int index = start + 1;
                if (start < sb.length()) {
                    if (sb.charAt(start) == BLANK) {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                } else {
                    index = start;
                }
                setText(sb.toString());
                setSelection(index);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (mListener != null && callBackSwitch) {   // 回调处理
                mListener.onTextChange(editable.toString().replaceAll("\\s*", ""), editable);
                callBackSwitch = false;
            }
        }
    };

    // 校验索引
    private boolean checkIndex(int index) {
        int count = PATTERN[0];
        boolean result = index != count;
        for (int i = 0; i < PATTERN.length; i++) {
            if (i == 0) {
                continue;
            }
            count += PATTERN[i] + 1;
            result = result && index != count;
        }
        return result;
    }

    // 校验长度
    private boolean checkLength(int length) {
        boolean result = false;
        int count = 0;
        for (int i : PATTERN) {
            count += i + 1;
            result = result || length == count;
        }
        return result;
    }

    public void setOnTextChangeListener(OnTextChangeListener listener) {
        mListener = listener;
    }

    public interface OnTextChangeListener {
        /**
         * 文本变化回调
         *
         * @param origin 不带格式化的文本
         * @param format 格式化后的文本
         */
        void onTextChange(CharSequence origin, CharSequence format);
    }
}