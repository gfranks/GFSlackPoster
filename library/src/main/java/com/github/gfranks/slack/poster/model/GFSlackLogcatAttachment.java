package com.github.gfranks.slack.poster.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Pattern;

public class GFSlackLogcatAttachment extends GFSlackAttachment {

    private GFSlackLogcatAttachment() {
        super();
    }

    private GFSlackLogcatAttachment(Builder builder) {
        this();
        if (builder.isCrashSummary) {
            setTitle("Crash Summary");
            setColor("#FF4351");
        }
        setText(builder.text);
    }

    public void setCrashSummary(boolean crashSummary) {
        if (crashSummary) {
            setTitle("Crash Summary");
            setColor("#FF4351");
        }
    }

    public static class Builder extends GFSlackAttachment.Builder {

        private Integer processId;
        private String text;
        private boolean isCrashSummary;

        public Builder setCrashSummary(boolean crashSummary) {
            isCrashSummary = crashSummary;

            return this;
        }

        public Builder setProcessId(int processId) {
            this.processId = processId;

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
            new LogcatAsyncTask(processId, isCrashSummary, null, onSlackLogcatAttachmentAvailableListener).execute();
        }

        public void build(Map<String, Object> extras, OnSlackLogcatAttachmentAvailableListener onSlackLogcatAttachmentAvailableListener) {
            if (processId == null) {
                throw new IllegalStateException("You must set a process id -- #setProcessId(int) on the builder");
            }
            new LogcatAsyncTask(processId, isCrashSummary, extras, onSlackLogcatAttachmentAvailableListener).execute();
        }

        GFSlackLogcatAttachment internalBuild() {
            return new GFSlackLogcatAttachment(this);
        }
    }

    public interface OnSlackLogcatAttachmentAvailableListener {
        void onSlackLogcatAttachmentAvailable(GFSlackLogcatAttachment attachment, Map<String, Object> extras);
    }

    private static class LogcatAsyncTask extends AsyncTask<Void, Void, GFSlackLogcatAttachment> {

        private int mProcessId;
        private boolean mIsCrashSummary;
        private Map<String, Object> mExtras;
        private OnSlackLogcatAttachmentAvailableListener mOnSlackLogcatAttachmentAvailableListener;

        public LogcatAsyncTask(int processId, boolean isCrashSummary, Map<String, Object> extras, OnSlackLogcatAttachmentAvailableListener onSlackLogcatAttachmentAvailableListener) {
            mProcessId = processId;
            mIsCrashSummary = isCrashSummary;
            mExtras = extras;
            mOnSlackLogcatAttachmentAvailableListener = onSlackLogcatAttachmentAvailableListener;
        }

        @Override
        protected GFSlackLogcatAttachment doInBackground(Void... voids) {
            GFSlackLogcatAttachment.Builder builder = new GFSlackLogcatAttachment.Builder();
            builder.setCrashSummary(mIsCrashSummary);
            try {
                Process process;
                if (mIsCrashSummary) {
                    process = Runtime.getRuntime().exec("logcat -d");
                } else {
                    process = Runtime.getRuntime().exec("logcat");
                }
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(process.getInputStream()));

                int maxLines = 0;
                int lines = 0;
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if(line.contains(Integer.toString(mProcessId))) {
                        if (line.contains("[") && line.contains("]")) {
                            try {
                                if (Pattern.matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$",
                                        line.substring(line.indexOf("[") + 1, line.indexOf("]")))) {
                                    sb.append("[...]");
                                    --lines;
                                } else if (line.contains("[") && line.contains("]") && line.contains(",") && line.contains(".")) {
                                    sb.append("[...]");
                                    --lines;
                                } else if (line.contains("type") && line.contains("Polygon")) {
                                    sb.append("[...]");
                                    --lines;
                                } else {
                                    sb.append(line);
                                    sb.append("\n");
                                }
                            } catch (Throwable t) {
                                sb.append(line);
                                sb.append("\n");
                            }
                        } else {
                            sb.append(line);
                            sb.append("\n");
                        }

                        if (lines > 200) {
                            break;
                        }

                        ++lines;
                    }

                    if (maxLines > 300) {
                        break;
                    }

                    ++maxLines;
                }

                if (sb.length() > 0) {
                    builder.text = sb.toString();
                    return builder.internalBuild();
                } else {
                    throw new Exception("Empty log");
                }
            } catch (Throwable t) {
                Log.e("Slack Logcat Attachment", "start failed", t);
                if (mIsCrashSummary) {
                    builder.text = "Failed to capture crash log";
                    return builder.internalBuild();
                } else {
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(GFSlackLogcatAttachment attachment) {
            if (mOnSlackLogcatAttachmentAvailableListener != null) {
                mOnSlackLogcatAttachmentAvailableListener.onSlackLogcatAttachmentAvailable(attachment, mExtras);
            }
        }
    }
}
