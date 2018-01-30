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

    private static GFSlackPoster sInstance;
    private String mAppName;
    private String mWebhookPath1;
    private String mWebhookPath2;
    private String mWebhookPath3;
    private GFSlackService mSlackService;

    private GFSlackPoster() {
        createService();
    }

    /**
     * Initialize your default webhook paths and app name to be used when posting to slack
     *
     * @param builder {@link Builder} the builder you have applied your app name (optional, if not set, it will not override) and webhook paths to
     */
    public static void initDefaults(Builder builder) {
        sInstance = new GFSlackPoster();
        sInstance.overrideDefaults(builder);
    }

    /**
     * Get an instance of the slack poster to begin posting messages to slack
     *
     * @return {@link GFSlackPoster}
     */
    public static GFSlackPoster get() {
        if (sInstance == null)
            sInstance = new GFSlackPoster();
        return sInstance;
    }

    /**
     *
     * @param appName String, app name to be displayed with the posted slack message
     */
    public void setAppName(String appName) {
        mAppName = appName;
    }

    /**
     *
     * @param builder {@link Builder} the builder you have applied your app name (optional, if not set, it will not override) and webhook paths to
     */
    public void overrideDefaults(Builder builder) {
        if (builder.appName != null && !builder.appName.isEmpty()) {
            mAppName = builder.appName;
        }

        mWebhookPath1 = builder.webhookPath1;
        mWebhookPath2 = builder.webhookPath2;
        mWebhookPath3 = builder.webhookPath3;
    }

    /**
     * The slack body builder with your set channel, message, (Optional) icon emoji, and attachments
     *
     * @param builder {@link SlackBodyBuilder}
     */
    public void postToSlack(SlackBodyBuilder builder) {
        if (mAppName == null) {
            throw new IllegalStateException("You must set an app name or username before creating a slack body");
        }

        postToSlack(new GFSlackBody.Builder()
                .setUsername(mAppName)
                .setChannel(builder.channel)
                .setText(builder.text)
                .setIconEmoji(builder.iconEmoji)
                .setAttachments(builder.attachments).build());
    }

    /**
     * The slack body builder with your set channel, message, (Optional) icon emoji, and attachments
     *
     * @param builder {@link SlackBodyBuilder}
     * @param callback Callback of the request indicating success or failure
     */
    public void postToSlack(SlackBodyBuilder builder, Callback<ResponseBody> callback) {
        if (mAppName == null) {
            throw new IllegalStateException("You must set an app name or username before creating a slack body");
        }

        postToSlack(new GFSlackBody.Builder()
                .setUsername(mAppName)
                .setChannel(builder.channel)
                .setText(builder.text)
                .setIconEmoji(builder.iconEmoji)
                .setAttachments(builder.attachments).build());
    }

    /**
     * Posts the past slack body to your set slack channel
     *
     * @param slackBody {@link GFSlackBody}
     */
    public void postToSlack(GFSlackBody slackBody) {
        postToSlack(slackBody, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    /**
     * Posts the past slack body to your set slack channel
     *
     * @param slackBody {@link GFSlackBody}
     * @param callback Callback of the request indicating success or failure
     */
    public void postToSlack(GFSlackBody slackBody, Callback<ResponseBody> callback) {
        if (slackBody.getChannel() == null || slackBody.getChannel().isEmpty()) {
            throw new IllegalStateException("You must specify a channel in your slack body");
        }

        if (mWebhookPath1 == null || mWebhookPath2 == null || mWebhookPath3 == null) {
            throw new IllegalStateException("You must set your web hook paths before you are able to post any messages");
        }

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

    public static class Builder {

        private String appName;
        private String webhookPath1;
        private String webhookPath2;
        private String webhookPath3;

        /**
         *
         * @param appName String, app name to be displayed with the posted slack message
         * @return {@link Builder}
         */
        public Builder setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        /**
         *
         * @param webhookPath1 String, first path of your webhook
         * @return {@link Builder}
         */
        public Builder setWebhookPath1(String webhookPath1) {
            this.webhookPath1 = webhookPath1;
            return this;
        }

        /**
         *
         * @param webhookPath2 String, middle path of your webhook
         * @return {@link Builder}
         */
        public Builder setWebhookPath2(String webhookPath2) {
            this.webhookPath2 = webhookPath2;
            return this;
        }

        /**
         *
         * @param webhookPath3 String, last path of your webhook
         * @return {@link Builder}
         */
        public Builder setWebhookPath3(String webhookPath3) {
            this.webhookPath3 = webhookPath3;
            return this;
        }
    }

    public static class SlackBodyBuilder {

        private String text;
        private String channel;
        private String iconEmoji;
        private List<GFSlackAttachment> attachments;

        /**
         *
         * @param text Optional message to go with your slack body
         * @return {@link SlackBodyBuilder}
         */
        public SlackBodyBuilder setText(String text) {
            this.text = text;
            return this;
        }

        /**
         *
         * @param channel Required channel your slack body will be posted to
         * @return {@link SlackBodyBuilder}
         */
        public SlackBodyBuilder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        /**
         *
         * @param iconEmoji String, A slack representation of the emoji to use for this slack post (usage :icon:)
         * @return {@link SlackBodyBuilder}
         */
        public SlackBodyBuilder setIconEmoji(String iconEmoji) {
            this.iconEmoji = iconEmoji;
            return this;
        }

        /**
         *
         * @param attachments Array of attachments to be posted with your slack body
         * @return {@link SlackBodyBuilder}
         */
        public SlackBodyBuilder setAttachments(List<GFSlackAttachment> attachments) {
            this.attachments = attachments;
            return this;
        }
    }
}
