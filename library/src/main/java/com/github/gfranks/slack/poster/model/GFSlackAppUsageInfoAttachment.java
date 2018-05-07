package com.github.gfranks.slack.poster.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Type;
import java.util.Map;

public class GFSlackAppUsageInfoAttachment extends GFSlackAttachment implements Parcelable, Type {

    public static final Creator<GFSlackAppUsageInfoAttachment> CREATOR = new Creator<GFSlackAppUsageInfoAttachment>() {
        @Override
        public GFSlackAppUsageInfoAttachment createFromParcel(Parcel in) {
            return new GFSlackAppUsageInfoAttachment(in);
        }

        @Override
        public GFSlackAppUsageInfoAttachment[] newArray(int size) {
            return new GFSlackAppUsageInfoAttachment[size];
        }
    };

    private String usedMemoryInMB;
    private String maxHeapSizeInMB;
    private String availableHeapSizeInMB;

    public GFSlackAppUsageInfoAttachment() {
        super();
        init();
    }

    public GFSlackAppUsageInfoAttachment(Builder builder) {
        super(builder);
        init();
        usedMemoryInMB = builder.usedMemoryInMB;
        maxHeapSizeInMB = builder.maxHeapSizeInMB;
        availableHeapSizeInMB = builder.availableHeapSizeInMB;
        setValues();
    }

    protected GFSlackAppUsageInfoAttachment(Parcel in) {
        super(in);
        init();
        usedMemoryInMB = in.readString();
        maxHeapSizeInMB = in.readString();
        availableHeapSizeInMB = in.readString();
        setValues();
    }

    private void init() {
        setTitle("App Usage");
        setColor("#FFA500");
        getFields().get(0).setShort(false);
    }

    public String getUsedMemoryInMB() {
        return usedMemoryInMB;
    }

    public void setUsedMemoryInMB(String usedMemoryInMB) {
        this.usedMemoryInMB = usedMemoryInMB;
        setValues();
    }

    public String getMaxHeapSizeInMB() {
        return maxHeapSizeInMB;
    }

    public void setMaxHeapSizeInMB(String maxHeapSizeInMB) {
        this.maxHeapSizeInMB = maxHeapSizeInMB;
        setValues();
    }

    public String getAvailableHeapSizeInMB() {
        return availableHeapSizeInMB;
    }

    public void setAvailableHeapSizeInMB(String availableHeapSizeInMB) {
        this.availableHeapSizeInMB = availableHeapSizeInMB;
        setValues();
    }

    private void setValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("Used Memory: ");
        sb.append(usedMemoryInMB);
        sb.append(" MB");
        sb.append("\n");
        sb.append("Max Heap Size: ");
        sb.append(maxHeapSizeInMB);
        sb.append(" MB");
        sb.append("\n");
        sb.append("Available Heap Size: ");
        sb.append(availableHeapSizeInMB);
        sb.append(" MB");
        setText(sb.toString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(usedMemoryInMB);
        dest.writeString(maxHeapSizeInMB);
        dest.writeString(availableHeapSizeInMB);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static class SimpleBuilder extends Builder {

        public SimpleBuilder() {
            final Runtime runtime = Runtime.getRuntime();
            final long usedMemInMB = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
            final long maxHeapSizeInMB = runtime.maxMemory() / 1048576L;

            setUsedMemoryInMB(String.valueOf(usedMemInMB));
            setMaxHeapSizeInMB(String.valueOf(maxHeapSizeInMB));
            setAvailableHeapSizeInMB(String.valueOf(maxHeapSizeInMB - usedMemInMB));
        }

        @Override
        public SimpleBuilder setFallback(String fallback) {
            super.setFallback(fallback);

            return this;
        }

        @Override
        public SimpleBuilder setColor(String color) {
            super.setColor(color);

            return this;
        }

        @Override
        public SimpleBuilder setTitle(String title) {
            super.setTitle(title);

            return this;
        }

        @Override
        public SimpleBuilder setText(String text) {
            super.setText(text);

            return this;
        }

        @Override
        public SimpleBuilder setExtraTextFields(Map<String, String> extraTextFields) {
            super.setExtraTextFields(extraTextFields);

            return this;
        }

        @Override
        public SimpleBuilder setFooter(String footer) {
            super.setFooter(footer);

            return this;
        }

        @Override
        public SimpleBuilder setFooterIcon(String footerIcon) {
            super.setFooterIcon(footerIcon);

            return this;
        }
    }

    public static class Builder extends GFSlackAttachment.Builder {

        private String usedMemoryInMB;
        private String maxHeapSizeInMB;
        private String availableHeapSizeInMB;

        public Builder setUsedMemoryInMB(String usedMemoryInMB) {
            this.usedMemoryInMB = usedMemoryInMB;

            return this;
        }

        public Builder setMaxHeapSizeInMB(String maxHeapSizeInMB) {
            this.maxHeapSizeInMB = maxHeapSizeInMB;

            return this;
        }

        public Builder setAvailableHeapSizeInMB(String availableHeapSizeInMB) {
            this.availableHeapSizeInMB = availableHeapSizeInMB;

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
        public GFSlackAppUsageInfoAttachment build() {
            return new GFSlackAppUsageInfoAttachment(this);
        }
    }
}
