package com.github.gfranks.slack.poster.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GFSlackAttachment {

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

    public GFSlackAttachment() {
        fallback = "Attachment";
        color = "#5DA7C1";
        fields = new ArrayList<>();
        fields.add(new SlackField());
    }

    public GFSlackAttachment(Builder builder) {
        this();
        if (builder.fallback != null && builder.fallback.length() > 0) {
            this.fallback = builder.fallback;
        }
        if (builder.color != null && builder.color.length() > 0) {
            this.color = builder.color;
        }
        if (builder.title != null && builder.title.length() > 0) {
            this.title = builder.title;
        } else {
            this.title = "Logcat Summary";
        }
        this.text = builder.text;
        setFooter(builder.footer);
        setFooterIcon(builder.footerIcon);
        if ((footer != null && footer.length() > 0) || (footerIcon != null && footerIcon.length() > 0)) {
            ts = System.currentTimeMillis() / 1000;
        }
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

        if (footer != null && footer.length() > 0) {
            ts = System.currentTimeMillis() / 1000;
        }
    }

    public String getFooterIcon() {
        return footerIcon;
    }

    public void setFooterIcon(String footerIcon) {
        this.footerIcon = footerIcon;

        if (footerIcon != null && footerIcon.length() > 0) {
            ts = System.currentTimeMillis() / 1000;
        }
    }

    public static class Builder {

        private String fallback;
        private String color;
        private String title;
        private String text;
        private String footer;
        private String footerIcon;

        public Builder setFallback(String fallback) {
            this.fallback = fallback;

            return this;
        }

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

        public Builder setFooter(String footer) {
            this.footer = footer;

            return this;
        }

        public Builder setFooterIcon(String footerIcon) {
            this.footerIcon = footerIcon;

            return this;
        }

        public GFSlackAttachment build() {
            return new GFSlackAttachment(this);
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
