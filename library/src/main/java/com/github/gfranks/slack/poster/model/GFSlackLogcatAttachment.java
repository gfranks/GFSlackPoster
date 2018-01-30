package com.github.gfranks.slack.poster.model;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

public class GFSlackLogcatAttachment extends GFSlackAttachment implements Parcelable, Type {

    public static final Creator<GFSlackLogcatAttachment> CREATOR = new Creator<GFSlackLogcatAttachment>() {
        @Override
        public GFSlackLogcatAttachment createFromParcel(Parcel in) {
            return new GFSlackLogcatAttachment(in);
        }

        @Override
        public GFSlackLogcatAttachment[] newArray(int size) {
            return new GFSlackLogcatAttachment[size];
        }
    };

    public GFSlackLogcatAttachment() {
        super();
        init();
    }

    protected GFSlackLogcatAttachment(Parcel in) {
        super(in);
        init();
    }

    private GFSlackLogcatAttachment(Builder builder) {
        super(builder);
        init();
    }

    private void init() {
        setTitle("Logcat Summary");
        setColor("#FF4081");
    }

    public interface OnSlackLogcatAttachmentAvailableListener {
        void onSlackLogcatAttachmentAvailable(GFSlackLogcatAttachment attachment, Bundle extras);
    }

    public static class Builder extends GFSlackAttachment.Builder {

        private Integer processId;
        private int lineCount;

        public Builder() {
            lineCount = 125;
        }

        public Builder setProcessId(int processId) {
            this.processId = processId;

            return this;
        }

        public Builder setLineCount(int lineCount) {
            this.lineCount = lineCount;

            return this;
        }

        @Override
        public Builder setFallback(String fallback) {
            super.setFallback(fallback);

            return this;
        }

        @Override
        public Builder setColor(String color) {
            super.setColor(color);

            return this;
        }

        @Override
        public Builder setTitle(String title) {
            super.setTitle(title);

            return this;
        }

        @Override
        public Builder setText(String text) {
            super.setText(text);

            return this;
        }

        @Override
        public Builder setExtraTextFields(Map<String, String> extraTextFields) {
            super.setExtraTextFields(extraTextFields);

            return this;
        }

        @Override
        public Builder setFooter(String footer) {
            super.setFooter(footer);

            return this;
        }

        @Override
        public Builder setFooterIcon(String footerIcon) {
            super.setFooterIcon(footerIcon);

            return this;
        }

        @Override
        public GFSlackAttachment build() {
            throw new IllegalStateException("You must call #build(OnSlackLogcatAttachmentCompletionListener onSlackLogcatAttachmentCompletionListener) or #build(Bundle extras, OnSlackLogcatAttachmentCompletionListener onSlackLogcatAttachmentCompletionListener)");
        }

        public void build(OnSlackLogcatAttachmentAvailableListener onSlackLogcatAttachmentAvailableListener) {
            if (processId == null) {
                throw new IllegalStateException("You must set a process id -- #setProcessId(int) on the builder");
            }
            new LogcatAsyncTask(processId, lineCount, null, onSlackLogcatAttachmentAvailableListener).execute(this);
        }

        public void build(Bundle extras, OnSlackLogcatAttachmentAvailableListener onSlackLogcatAttachmentAvailableListener) {
            if (processId == null) {
                throw new IllegalStateException("You must set a process id -- #setProcessId(int) on the builder");
            }
            new LogcatAsyncTask(processId, lineCount, extras, onSlackLogcatAttachmentAvailableListener).execute(this);
        }

        GFSlackLogcatAttachment internalBuild() {
            return new GFSlackLogcatAttachment(this);
        }
    }

    private static class LogcatAsyncTask extends AsyncTask<GFSlackLogcatAttachment.Builder, Void, GFSlackLogcatAttachment> {

        private int mProcessId;
        private int mLineCount;
        private Bundle mExtras;
        private OnSlackLogcatAttachmentAvailableListener mOnSlackLogcatAttachmentAvailableListener;

        public LogcatAsyncTask(int processId, int lineCount, Bundle extras, OnSlackLogcatAttachmentAvailableListener onSlackLogcatAttachmentAvailableListener) {
            mProcessId = processId;
            mLineCount = lineCount;
            mExtras = extras;
            mOnSlackLogcatAttachmentAvailableListener = onSlackLogcatAttachmentAvailableListener;
        }

        @Override
        protected GFSlackLogcatAttachment doInBackground(GFSlackLogcatAttachment.Builder... builders) {
            GFSlackLogcatAttachment.Builder builder = builders[0];

            try {
                StringBuilder sb = new StringBuilder();
                sb.append(getLogCapture());
                if (sb.length() > 0) {
                    builder.setText(sb.toString());
                    return builder.internalBuild();
                } else {
                    throw new Exception();
                }
            } catch (Throwable t) {
                builder.setText("Unable to read log");
                return builder.internalBuild();
            }
        }

        @Override
        protected void onPostExecute(GFSlackLogcatAttachment attachment) {
            if (mOnSlackLogcatAttachmentAvailableListener != null) {
                mOnSlackLogcatAttachmentAvailableListener.onSlackLogcatAttachmentAvailable(attachment, mExtras);
            }
        }

        private String getLogCapture() throws Exception {
            Process process = Runtime.getRuntime().exec(String.format(Locale.getDefault(), "logcat -t %d", mLineCount));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(Integer.toString(mProcessId))) {
                    sb.append(line);
                    sb.append("\n");
                }
            }

            return sb.toString();
        }
    }
}
