package com.github.gfranks.slack.poster;

import com.github.gfranks.slack.poster.model.GFSlackAttachment;
import com.github.gfranks.slack.poster.model.GFSlackBody;
import com.github.gfranks.slack.poster.service.GFSlackService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GFSlackPoster {

    private String mWebhookPath1, mWebhookPath2, mWebhookPath3;
    private GFSlackService mSlackService;

    public static GFSlackPoster newInstance(String webhookPath1, String webhookPath2, String webhookPath3) {
        return new GFSlackPoster(webhookPath1, webhookPath2, webhookPath3);
    }

    private GFSlackPoster(String webhookPath1, String webhookPath2, String webhookPath3) {
        mWebhookPath1 = webhookPath1;
        mWebhookPath2 = webhookPath2;
        mWebhookPath3 = webhookPath3;

        createService();
    }

    public GFSlackBody createSlackBody(String appName, String message, List<GFSlackAttachment> slackAttachments) {
        return new GFSlackBody.Builder()
                .setUsername(appName)
                .setText(message)
                .setAttachments(slackAttachments)
                .build();
    }

    public void postToSlack(GFSlackBody slackBody) {
        mSlackService.postMessage(mWebhookPath1, mWebhookPath2, mWebhookPath3, slackBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public void postToSlack(GFSlackBody slackBody, Callback<ResponseBody> callback) {
        mSlackService.postMessage(mWebhookPath1, mWebhookPath2, mWebhookPath3, slackBody).enqueue(callback);
    }

    private void createService() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mSlackService = new Retrofit.Builder()
                .baseUrl("https://hooks.slack.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .setPrettyPrinting()
                        .enableComplexMapKeySerialization()
                        .create()))
                .client(new OkHttpClient.Builder()
                        .addInterceptor(httpLoggingInterceptor)
                        .connectTimeout(7000, TimeUnit.MILLISECONDS)
                        .readTimeout(13000, TimeUnit.MILLISECONDS)
                        .writeTimeout(3000, TimeUnit.MILLISECONDS)
                        .build())
                .build().create(GFSlackService.class);
    }
}
