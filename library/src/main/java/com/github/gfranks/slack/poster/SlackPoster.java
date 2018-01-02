package com.github.gfranks.slack.poster;

import com.github.gfranks.slack.poster.model.SlackAttachment;
import com.github.gfranks.slack.poster.model.SlackBody;
import com.github.gfranks.slack.poster.service.SlackService;
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

public class SlackPoster {

    public static final String TAG = "slack_poster";

    private String mWebhookPath1, mWebhookPath2, mWebhookPath3;
    private SlackService mSlackService;

    public SlackPoster(String webhookPath1, String webhookPath2, String webhookPath3) {
        mWebhookPath1 = webhookPath1;
        mWebhookPath2 = webhookPath2;
        mWebhookPath3 = webhookPath3;

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
                .build().create(SlackService.class);
    }

    public void postToSlack(String appName, String message, List<SlackAttachment> slackAttachments) {
        postToSlack(appName, message, slackAttachments, false);
    }

    private void postToSlack(String appName, String message, List<SlackAttachment> attachments, boolean isCrashReport) {
        postToSlack(appName, message, attachments, isCrashReport, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    private void postToSlack(String appName, String message, List<SlackAttachment> attachments, boolean isCrashReport, Callback<ResponseBody> callback) {

        SlackBody.Builder slackBodyBuilder= new SlackBody.Builder()
                .setUsername(appName)
                .setAttachments(attachments);

        if (isCrashReport || (message != null && message.length() > 0)) {
            StringBuilder sb = new StringBuilder();
            if (isCrashReport) {
                sb.append("*");
                sb.append("NEW CRASH REPORT");
                sb.append("*");
            }
            if (message != null && message.length() > 0) {
                if (isCrashReport) {
                    sb.append("\n");
                }
                sb.append(message);
            }
            slackBodyBuilder.setText(sb.toString());
        }

        mSlackService.postMessage(mWebhookPath1, mWebhookPath2, mWebhookPath3, slackBodyBuilder.build()).enqueue(callback);
    }
}
