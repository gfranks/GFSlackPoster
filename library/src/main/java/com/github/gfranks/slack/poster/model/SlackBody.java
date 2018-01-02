package com.github.gfranks.slack.poster.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SlackBody {

    @SerializedName("text")
    private String text;
    @SerializedName("channel")
    private String channel;
    @SerializedName("username")
    private String username;
    @SerializedName("icon_emoji")
    private String iconEmoji;
    @SerializedName("attachments")
    private List<SlackAttachment> attachments;

    public SlackBody() {
        channel = "#notifications";
        iconEmoji = ":leaf:";
    }

    public SlackBody(Builder builder) {
        this();
        text = builder.text;

        if (builder.channel != null && builder.channel.length() > 0) {
            channel = builder.channel;
        }

        if (builder.username != null && builder.username.length() > 0) {
            username = builder.username;
        }

        attachments = builder.attachments;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIconEmoji() {
        return iconEmoji;
    }

    public void setIconEmoji(String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

    public List<SlackAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<SlackAttachment> attachments) {
        this.attachments = attachments;
    }

    public static class Builder {

        private String text;
        private String channel;
        private String username;
        private List<SlackAttachment> attachments;

        public Builder setText(String text) {
            this.text = text;

            return this;
        }

        public Builder setChannel(String channel) {
            this.channel = channel;

            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;

            return this;
        }

        public Builder setAttachments(List<SlackAttachment> attachments) {
            this.attachments = attachments;

            return this;
        }

        public SlackBody build() {
            return new SlackBody(this);
        }
    }
}
