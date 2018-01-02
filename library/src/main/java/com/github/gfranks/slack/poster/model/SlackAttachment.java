package com.github.gfranks.slack.poster.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SlackAttachment {

    @SerializedName("fallback")
    private String fallback;
    @SerializedName("color")
    private String color;
    @SerializedName("ts")
    private Long ts;
    @SerializedName("title")
    private String title;
    @SerializedName("text")
    private String text;
    @SerializedName("fields")
    private List<SlackField> fields;
    @SerializedName("footer")
    private String footer;
    @SerializedName("footer_icon")
    private String footerIcon;

    public SlackAttachment() {
        fallback = "Logcat Summary";
        color = "#5DA7C1";
        fields = new ArrayList<>();
        fields.add(new SlackField());

        setIncludeFooter(true);
    }

    public SlackAttachment(Builder builder) {
        this();
        if (builder.color != null && builder.color.length() > 0) {
            this.color = builder.color;
        }
        if (builder.title != null && builder.title.length() > 0) {
            this.title = builder.title;
        } else {
            this.title = "Logcat Summary";
        }
        this.text = builder.text;
        setIncludeFooter(builder.includeFooter);
    }

    public String getFallback() {
        return fallback;
    }

    public void setFallback(String fallback) {
        this.fallback = fallback;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<SlackField> getFields() {
        return fields;
    }

    public void setFields(List<SlackField> fields) {
        this.fields = fields;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getFooterIcon() {
        return footerIcon;
    }

    public void setFooterIcon(String footerIcon) {
        this.footerIcon = footerIcon;
    }

    public void setIncludeFooter(boolean includeFooter) {
        if (includeFooter) {
            footer = "LawnTap Slack API";
            footerIcon = "https://www.lawntap.com/wp-content/uploads/2017/11/Lawntap-Logo-e1511320770929.png";
            ts = System.currentTimeMillis() / 1000;
        } else {
            footer = null;
            footerIcon = null;
            ts = null;
        }
    }

    public static class Builder {

        private String color;
        private String title;
        private String text;
        private boolean includeFooter;

        public Builder setColor(String color) {
            this.color = color;

            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;

            return this;
        }

        public Builder setText(String text) {
            this.text = text;

            return this;
        }

        public Builder setIncludeFooter(boolean includeFooter) {
            this.includeFooter = includeFooter;

            return this;
        }

        public SlackAttachment build() {
            return new SlackAttachment(this);
        }
    }

    public static class SlackField {

        @SerializedName("title")
        private String title;
        @SerializedName("value")
        private String value;
        @SerializedName("short")
        private boolean isShort;

        public SlackField() {
            isShort = true;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isShort() {
            return isShort;
        }

        public void setShort(boolean aShort) {
            isShort = aShort;
        }
    }
}
