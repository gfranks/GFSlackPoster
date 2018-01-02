package com.github.gfranks.slack.poster.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class SlackLogcatAttachment extends SlackAttachment {

    public SlackLogcatAttachment() {
        super();
    }

    public SlackLogcatAttachment(Builder builder) {
        if (builder.isCrashSummary) {
            setTitle("Crash Summary");
            setColor("#FF4351");
        }
    }

    public void setCrashSummary(boolean crashSummary) {
        if (crashSummary) {
            setTitle("Crash Summary");
            setColor("#FF4351");
        }
    }

    public static class Builder {

        private String text;
        private boolean isCrashSummary;

        public Builder setCrashSummary(boolean crashSummary) {
            isCrashSummary = crashSummary;

            return this;
        }

        public void build(OnSlackLogcatAttachmentCompletionListener onSlackLogcatAttachmentCompletionListener) {
            new LogcatAsyncTask(isCrashSummary, onSlackLogcatAttachmentCompletionListener).execute();
        }

        SlackLogcatAttachment build() {
            return new SlackLogcatAttachment(this);
        }
    }

    public interface OnSlackLogcatAttachmentCompletionListener {
        void onSlackLogcatAttachmentCompleted(SlackLogcatAttachment attachment);
    }

    private static class LogcatAsyncTask extends AsyncTask<Void, Void, SlackLogcatAttachment> {

        private boolean mIsCrashSummary;
        private OnSlackLogcatAttachmentCompletionListener mOnSlackLogcatAttachmentCompletionListener;

        public LogcatAsyncTask(boolean isCrashSummary, OnSlackLogcatAttachmentCompletionListener onSlackLogcatAttachmentCompletionListener) {
            mIsCrashSummary = isCrashSummary;
            mOnSlackLogcatAttachmentCompletionListener = onSlackLogcatAttachmentCompletionListener;
        }

        @Override
        protected SlackLogcatAttachment doInBackground(Void... voids) {
            SlackLogcatAttachment.Builder builder = new SlackLogcatAttachment.Builder();
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
                    if(line.contains(Integer.toString(android.os.Process.myPid()))) {
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
                    return builder.build();
                } else {
                    throw new Exception("Empty log");
                }
            } catch (Throwable t) {
                Log.e("Slack Logcat Attachment", "start failed", t);
                if (mIsCrashSummary) {
                    builder.text = "Failed to capture crash log";
                    return builder.build();
                } else {
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(SlackLogcatAttachment attachment) {
            if (mOnSlackLogcatAttachmentCompletionListener != null) {
                mOnSlackLogcatAttachmentCompletionListener.onSlackLogcatAttachmentCompleted(attachment);
            }
        }
    }
}
