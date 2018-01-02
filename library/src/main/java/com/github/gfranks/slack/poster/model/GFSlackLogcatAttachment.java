package com.github.gfranks.slack.poster.model;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Map;

public class GFSlackLogcatAttachment extends GFSlackAttachment {

    private GFSlackLogcatAttachment() {
        super();
    }

    private GFSlackLogcatAttachment(Builder builder) {
        super(builder);
        setTitle("Logcat Summary");
        setColor("#FF4081");
    }

    public void setCrashSummary(boolean crashSummary) {
        setTitle("Logcat Summary");
        setColor("#FF4081");
    }

    public interface OnSlackLogcatAttachmentAvailableListener {
        void onSlackLogcatAttachmentAvailable(GFSlackLogcatAttachment attachment, Map<String, Object> extras);
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
            throw new IllegalStateException("You must call #build(OnSlackLogcatAttachmentCompletionListener onSlackLogcatAttachmentCompletionListener) or #build(Map<String, Object> extras, OnSlackLogcatAttachmentCompletionListener onSlackLogcatAttachmentCompletionListener)");
        }

        public void build(OnSlackLogcatAttachmentAvailableListener onSlackLogcatAttachmentAvailableListener) {
            if (processId == null) {
                throw new IllegalStateException("You must set a process id -- #setProcessId(int) on the builder");
            }
            new LogcatAsyncTask(processId, lineCount, null, onSlackLogcatAttachmentAvailableListener).execute(this);
        }

        public void build(Map<String, Object> extras, OnSlackLogcatAttachmentAvailableListener onSlackLogcatAttachmentAvailableListener) {
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
        private Map<String, Object> mExtras;
        private OnSlackLogcatAttachmentAvailableListener mOnSlackLogcatAttachmentAvailableListener;

        public LogcatAsyncTask(int processId, int lineCount, Map<String, Object> extras, OnSlackLogcatAttachmentAvailableListener onSlackLogcatAttachmentAvailableListener) {
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
