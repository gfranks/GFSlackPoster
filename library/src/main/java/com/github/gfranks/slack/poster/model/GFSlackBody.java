package com.github.gfranks.slack.poster.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GFSlackBody {

    @SerializedName("text")
    private String text;
    @SerializedName("channel")
    private String channel;
    @SerializedName("username")
    private String username;
    @SerializedName("icon_emoji")
    private String iconEmoji;
    @SerializedName("attachments")
    private List<GFSlackAttachment> attachments;

    public GFSlackBody() {
    }

    public GFSlackBody(Builder builder) {
        this();
        text = builder.text;

        if (builder.channel != null && builder.channel.length() > 0) {
            channel = builder.channel;
        }

        if (builder.username != null && builder.username.length() > 0) {
            username = builder.username;
        }

        if (builder.iconEmoji != null && builder.iconEmoji.length() > 0) {
            iconEmoji = builder.iconEmoji;
        }

        attachments = builder.attachments;
    }

    public String getText() {
        return text;
    }

    /**
     *
     * @param text Optional message to go with your slack body
     */
    public void setText(String text) {
        this.text = text;
    }

    public String getChannel() {
        return channel;
    }

    /**
     *
     * @param channel Required channel your slack body will be posted to
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username String used when showing who posted the message, often the app name
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getIconEmoji() {
        return iconEmoji;
    }

    /**
     *
     * @param iconEmoji String, A slack representation of the emoji to use for this slack post (usage :icon:)
     */
    public void setIconEmoji(String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

    public List<GFSlackAttachment> getAttachments() {
        return attachments;
    }

    /**
     *
     * @param attachments Array of attachments to be posted with your slack body
     */
    public void setAttachments(List<GFSlackAttachment> attachments) {
        this.attachments = attachments;
    }

    public static class Builder {

        private String text;
        private String channel;
        private String username;
        private String iconEmoji;
        private List<GFSlackAttachment> attachments;

        /**
         *
         * @param text Optional message to go with your slack body
         * @return {@link Builder}
         */
        public Builder setText(String text) {
            this.text = text;

            return this;
        }

        /**
         *
         * @param channel Required channel your slack body will be posted to
         * @return {@link Builder}
         */
        public Builder setChannel(String channel) {
            this.channel = channel;

            return this;
        }

        /**
         *
         * @param username String used when showing who posted the message, often the app name
         * @return {@link Builder}
         */
        public Builder setUsername(String username) {
            this.username = username;

            return this;
        }

        /**
         *
         * @param iconEmoji String, A slack representation of the emoji to use for this slack post (usage :icon:)
         * @return {@link Builder}
         */
        public Builder setIconEmoji(String iconEmoji) {
            this.iconEmoji = iconEmoji;

            return this;
        }

        /**
         *
         * @param attachments Array of attachments to be posted with your slack body
         * @return {@link Builder}
         */
        public Builder setAttachments(List<GFSlackAttachment> attachments) {
            this.attachments = attachments;

            return this;
        }

        /**
         *
         * @return {@link GFSlackBody}
         */
        public GFSlackBody build() {
            return new GFSlackBody(this);
        }
    }
}
