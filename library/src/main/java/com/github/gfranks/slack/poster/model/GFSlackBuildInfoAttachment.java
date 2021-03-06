package com.github.gfranks.slack.poster.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Type;
import java.util.Map;

public class GFSlackBuildInfoAttachment extends GFSlackAttachment implements Parcelable, Type {

    public static final Creator<GFSlackBuildInfoAttachment> CREATOR = new Creator<GFSlackBuildInfoAttachment>() {
        @Override
        public GFSlackBuildInfoAttachment createFromParcel(Parcel in) {
            return new GFSlackBuildInfoAttachment(in);
        }

        @Override
        public GFSlackBuildInfoAttachment[] newArray(int size) {
            return new GFSlackBuildInfoAttachment[size];
        }
    };

    private String applicationId;
    private String versionName;
    private String versionCode;
    private String sha;
    private String buildDate;

    public GFSlackBuildInfoAttachment() {
        super();
        init();
    }

    public GFSlackBuildInfoAttachment(Builder builder) {
        super(builder);
        init();
        applicationId = builder.applicationId;
        versionName = builder.versionName;
        versionCode = builder.versionCode;
        sha = builder.sha;
        buildDate = builder.buildDate;
        setValues();
    }

    protected GFSlackBuildInfoAttachment(Parcel in) {
        super(in);
        init();
        applicationId = in.readString();
        versionName = in.readString();
        versionCode = in.readString();
        sha = in.readString();
        buildDate = in.readString();
        setValues();
    }

    private void init() {
        setTitle("Build Info");
        setColor("#FEC418");
        getFields().get(0).setShort(false);
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        setValues();
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
        setValues();
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
        setValues();
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
        setValues();
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
        setValues();
    }

    private void setValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("Application ID: ");
        sb.append(applicationId);
        sb.append("\n");
        sb.append("Version Name: ");
        sb.append(versionName);
        sb.append("\n");
        sb.append("Version Code: ");
        sb.append(versionCode);
        sb.append("\n");
        sb.append("Sha: ");
        sb.append(sha);
        sb.append("\n");
        sb.append("Date of Build: ");
        sb.append(buildDate);
        setText(sb.toString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(applicationId);
        dest.writeString(versionName);
        dest.writeString(versionCode);
        dest.writeString(sha);
        dest.writeString(buildDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static class Builder extends GFSlackAttachment.Builder {

        private String applicationId;
        private String versionName;
        private String versionCode;
        private String sha;
        private String buildDate;

        public Builder setApplicationId(String applicationId) {
            this.applicationId = applicationId;

            return this;
        }

        public Builder setVersionName(String versionName) {
            this.versionName = versionName;

            return this;
        }

        public Builder setVersionCode(String versionCode) {
            this.versionCode = versionCode;

            return this;
        }

        public Builder setSha(String sha) {
            this.sha = sha;

            return this;
        }

        public Builder setBuildDate(String buildDate) {
            this.buildDate = buildDate;

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
        public GFSlackBuildInfoAttachment build() {
            return new GFSlackBuildInfoAttachment(this);
        }
    }
}
