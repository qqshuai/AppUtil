package com.sqq.app.util;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author shuaiqiangqiang
 * @version 1.0 2018/3/30
 * @since JDK 1.7
 */
public interface ApiService {

    @GET("cc/json/mobile_tel_segment.htm")
    Call<String> queryTelInfo(@Query("tel") String tel);
}