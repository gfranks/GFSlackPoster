package com.github.gfranks.slack.poster.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Type;
import java.util.Map;

public class GFSlackNetworkInfoAttachment extends GFSlackAttachment implements Parcelable, Type {

    public static final Creator<GFSlackNetworkInfoAttachment> CREATOR = new Creator<GFSlackNetworkInfoAttachment>() {
        @Override
        public GFSlackNetworkInfoAttachment createFromParcel(Parcel in) {
            return new GFSlackNetworkInfoAttachment(in);
        }

        @Override
        public GFSlackNetworkInfoAttachment[] newArray(int size) {
            return new GFSlackNetworkInfoAttachment[size];
        }
    };

    private String apiVersion;
    private String environment;

    public GFSlackNetworkInfoAttachment() {
        super();
        init();
    }

    public GFSlackNetworkInfoAttachment(Builder builder) {
        super(builder);
        init();
        apiVersion = builder.apiVersion;
        environment = builder.environment;
        setValues();
    }

    protected GFSlackNetworkInfoAttachment(Parcel in) {
        super(in);
        init();
        apiVersion = in.readString();
        environment = in.readString();
        setValues();
    }

    private void init() {
        setTitle("Networking Info");
        setColor("#3DB766");
        getFields().get(0).setShort(false);
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        setValues();
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
        setValues();
    }

    private void setValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("Api Version: ");
        sb.append(apiVersion);
        sb.append("\n");
        sb.append("Environment: ");
        sb.append(environment);
        setText(sb.toString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apiVersion);
        dest.writeString(environment);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static class Builder extends GFSlackAttachment.Builder {

        private String apiVersion;
        private String environment;

        public Builder setApiVersion(String apiVersion) {
            this.apiVersion = apiVersion;

            return this;
        }

        public Builder setEnvironment(String environment) {
            this.environment = environment;

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
        public GFSlackNetworkInfoAttachment build() {
            return new GFSlackNetworkInfoAttachment(this);
        }
    }
}
