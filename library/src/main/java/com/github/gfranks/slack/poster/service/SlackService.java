package com.github.gfranks.slack.poster.service;

import com.github.gfranks.slack.poster.model.SlackBody;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SlackService {

    @Headers("Content-type: application/json")
    @POST("/services/{webhookPath1}/{webhookPath2}/webhookPath3}")
    Call<ResponseBody> postMessage(
            @Path("webhookPath1") String webhookPath1,
            @Path("webhookPath2") String webhookPath2,
            @Path("webhookPath3") String webhookPath3,
            @Body SlackBody slackBody);
}
