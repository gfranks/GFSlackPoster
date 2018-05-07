package com.github.gfranks.slack.poster.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GFSlackAttachment implements Parcelable, Type {

    public static final Creator<GFSlackAttachment> CREATOR = new Creator<GFSlackAttachment>() {
        @Override
        public GFSlackAttachment createFromParcel(Parcel in) {
            return new GFSlackAttachment(in);
        }

        @Override
        public GFSlackAttachment[] newArray(int size) {
            return new GFSlackAttachment[size];
        }
    };

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
    private Map<String, String> extraTextFields;

    public GFSlackAttachment() {
        fallback = "Attachment";
        color = "#5DA7C1";
        fields = new ArrayList<>();
        fields.add(new SlackField());
        extraTextFields = new HashMap<>();
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
        setExtraTextFields(builder.extraTextFields);
        if ((footer != null && footer.length() > 0) || (footerIcon != null && footerIcon.length() > 0)) {
            ts = System.currentTimeMillis() / 1000;
        }
    }

    protected GFSlackAttachment(Parcel in) {
        fallback = in.readString();
        color = in.readString();
        if (in.readByte() == 0) {
            ts = null;
        } else {
            ts = in.readLong();
        }
        title = in.readString();
        text = in.readString();
        fields = in.createTypedArrayList(SlackField.CREATOR);
        footer = in.readString();
        footerIcon = in.readString();
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
        setExtraTextFields(extraTextFields);
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

    public void setExtraTextFields(Map<String, String> extraTextFields) {
        this.extraTextFields = extraTextFields;

        StringBuilder sb = new StringBuilder();
        if (getText() != null && getText().length() > 0) {
            sb.append(getText());
        }
        if (extraTextFields != null) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            for (String key : extraTextFields.keySet()) {
                sb.append(key);
                sb.append(": ");
                sb.append(extraTextFields.get(key));
                sb.append("\n");
            }
            text = sb.toString();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fallback);
        dest.writeString(color);
        if (ts == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(ts);
        }
        dest.writeString(title);
        dest.writeString(text);
        dest.writeTypedList(fields);
        dest.writeString(footer);
        dest.writeString(footerIcon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static class Builder {

        private String fallback;
        private String color;
        private String title;
        private String text;
        private Map<String, String> extraTextFields;
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

        public Builder setExtraTextFields(Map<String, String> extraTextFields) {
            this.extraTextFields = extraTextFields;

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

    public static class SlackField implements Parcelable, Type {

        public static final Creator<SlackField> CREATOR = new Creator<SlackField>() {
            @Override
            public SlackField createFromParcel(Parcel in) {
                return new SlackField(in);
            }

            @Override
            public SlackField[] newArray(int size) {
                return new SlackField[size];
            }
        };

        @SerializedName("title")
        private String title;
        @SerializedName("value")
        private String value;
        @SerializedName("short")
        private boolean isShort;

        public SlackField() {
            isShort = true;
        }

        protected SlackField(Parcel in) {
            title = in.readString();
            value = in.readString();
            isShort = in.readByte() != 0;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(title);
            parcel.writeString(value);
            parcel.writeByte((byte) (isShort ? 1 : 0));
        }
    }
}
