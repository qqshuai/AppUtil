package com.sqq.app.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends Activity {

    private String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FormatEditText editText = findViewById(R.id.edit_text);
        editText.setOnTextChangeListener(new FormatEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(CharSequence origin, CharSequence format) {
                tel = origin.toString();
                Logger.w(origin.toString() + "%%%%%%%%%");
            }
        });

        final ProgressBar progressBar = findViewById(R.id.progress_bar);
        final TextView textView = findViewById(R.id.text_view);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tel)) {
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .retryOnConnectionFailure(false)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://tcc.taobao.com/")
                        .client(okHttpClient)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();
                ApiService service = retrofit.create(ApiService.class);
                Call<String> call = service.queryTelInfo(tel);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        progressBar.setVisibility(View.GONE);
                        String result = response.body();
                        textView.setText(result);
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Logger.e(t.getMessage());
                        textView.setText(t.getMessage());
                    }
                });
            }
        });
    }
}
