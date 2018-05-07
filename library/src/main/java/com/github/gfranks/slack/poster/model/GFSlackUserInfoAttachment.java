package com.github.gfranks.slack.poster.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Type;
import java.util.Map;

public class GFSlackUserInfoAttachment extends GFSlackAttachment implements Parcelable, Type {

    public static final Creator<GFSlackUserInfoAttachment> CREATOR = new Creator<GFSlackUserInfoAttachment>() {
        @Override
        public GFSlackUserInfoAttachment createFromParcel(Parcel in) {
            return new GFSlackUserInfoAttachment(in);
        }

        @Override
        public GFSlackUserInfoAttachment[] newArray(int size) {
            return new GFSlackUserInfoAttachment[size];
        }
    };

    private String name;
    private String id;
    private String email;
    private String phone;

    public GFSlackUserInfoAttachment() {
        super();
        init();
    }

    public GFSlackUserInfoAttachment(Builder builder) {
        super(builder);
        init();
        name = builder.name;
        id = builder.id;
        email = builder.email;
        phone = builder.phone;
        setValues();
    }

    protected GFSlackUserInfoAttachment(Parcel in) {
        super(in);
        init();
        name = in.readString();
        id = in.readString();
        email = in.readString();
        phone = in.readString();
        setValues();
    }

    private void init() {
        setTitle("User Info");
        setColor("#000000");
        getFields().get(0).setShort(false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setValues();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        setValues();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        setValues();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        setValues();
    }

    private void setValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ");
        sb.append(name);
        sb.append("\n");
        sb.append("Id: ");
        sb.append(id);
        sb.append("\n");
        sb.append("Email: ");
        sb.append(email);
        sb.append("\n");
        sb.append("Phone: ");
        sb.append(phone);
        setText(sb.toString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static class Builder extends GFSlackAttachment.Builder {

        private String name;
        private String id;
        private String email;
        private String phone;

        public Builder setName(String name) {
            this.name = name;

            return this;
        }

        public Builder setId(String id) {
            this.id = id;

            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;

            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;

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
        public GFSlackUserInfoAttachment build() {
            return new GFSlackUserInfoAttachment(this);
        }
    }
}
