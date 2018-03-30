package com.sqq.app.util;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author shuaiqiangqiang
 * @version 1.0 2018/3/30
 * @since JDK 1.7
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)
                .tag("sqq")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}