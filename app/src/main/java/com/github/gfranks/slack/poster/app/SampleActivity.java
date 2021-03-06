package com.github.gfranks.slack.poster.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.github.gfranks.slack.poster.GFSlackPoster;
import com.github.gfranks.slack.poster.model.GFSlackAppUsageInfoAttachment;
import com.github.gfranks.slack.poster.model.GFSlackAttachment;
import com.github.gfranks.slack.poster.model.GFSlackBuildInfoAttachment;
import com.github.gfranks.slack.poster.model.GFSlackDeviceInfoAttachment;
import com.github.gfranks.slack.poster.model.GFSlackLogcatAttachment;
import com.github.gfranks.slack.poster.model.GFSlackUserInfoAttachment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SampleActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mWebhook1;
    private EditText mWebhook2;
    private EditText mWebhook3;
    private EditText mChannel;
    private EditText mMessage;
    private CheckBox mIncludeLogcat;

    private GFSlackPoster mSlackPoster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mWebhook1 = (EditText) findViewById(R.id.webhook_path_1);
        mWebhook2 = (EditText) findViewById(R.id.webhook_path_2);
        mWebhook3 = (EditText) findViewById(R.id.webhook_path_3);
        mChannel = (EditText) findViewById(R.id.channel);
        mMessage = (EditText) findViewById(R.id.message);
        mIncludeLogcat = (CheckBox) findViewById(R.id.include_logcat);
        findViewById(R.id.submit).setOnClickListener(this);

        mSlackPoster = GFSlackPoster.get();
    }

    @Override
    public void onClick(View view) {
        mSlackPoster.overrideDefaults(new GFSlackPoster.Builder()
                .setWebhookPath1(mWebhook1.getText().toString())
                .setWebhookPath1(mWebhook2.getText().toString())
                .setWebhookPath1(mWebhook3.getText().toString()));
        if (mIncludeLogcat.isChecked()) {
            new GFSlackLogcatAttachment.Builder()
                    .setProcessId(android.os.Process.myPid())
                    .build(new GFSlackLogcatAttachment.OnSlackLogcatAttachmentAvailableListener() {
                        @Override
                        public void onSlackLogcatAttachmentAvailable(GFSlackLogcatAttachment attachment, Map<String, Object> extras) {
                            List<GFSlackAttachment> attachments = getAttachments();
                            if (attachment != null) {
                                attachment.setTs(System.currentTimeMillis() / 1000);
                                attachments.add(attachment);
                            }

                            mSlackPoster.postToSlack(new GFSlackPoster.SlackBodyBuilder()
                                    .setChannel(mChannel.getText().toString())
                                    .setText(mMessage.getText().toString())
                                    .setAttachments(attachments), getCallback());
                        }
                    });
        } else {
            mSlackPoster.postToSlack(new GFSlackPoster.SlackBodyBuilder()
                    .setChannel(mChannel.getText().toString())
                    .setText(mMessage.getText().toString())
                    .setAttachments(getAttachments()), getCallback());
        }

    }

    private List<GFSlackAttachment> getAttachments() {
        List<GFSlackAttachment> attachments = new ArrayList<>();
        attachments.add(getSampleUserInfoAttachment());
        attachments.add(getBuildInfoAttachment());
        attachments.add(new GFSlackDeviceInfoAttachment.SimpleBuilder(this).build());
        attachments.add(new GFSlackAppUsageInfoAttachment.SimpleBuilder().build());
        return attachments;
    }

    private GFSlackAttachment getBuildInfoAttachment() {
        return new GFSlackBuildInfoAttachment.Builder()
                .setApplicationId(BuildConfig.APPLICATION_ID)
                .setVersionName(BuildConfig.VERSION_NAME)
                .setVersionCode(String.valueOf(BuildConfig.VERSION_CODE))
                .setSha("No Sha")
                .setBuildDate("No build date")
                .build();
    }

    private GFSlackAttachment getSampleUserInfoAttachment() {
        return new GFSlackUserInfoAttachment.Builder()
                .setName("Slack Poster Sample")
                .setId("-1")
                .setEmail("slack_poster_sample@gmail.com")
                .setPhone("123-456-7890")
                .build();
    }

    private Callback<ResponseBody> getCallback() {
        return new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (isFinishing()) {
                    return;
                }

                if (response.isSuccessful()) {
                    Toast.makeText(SampleActivity.this, "Post successful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SampleActivity.this, "Post unsuccessful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (isFinishing()) {
                    return;
                }

                Toast.makeText(SampleActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }
}
