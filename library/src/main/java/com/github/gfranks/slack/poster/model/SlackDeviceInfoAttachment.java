package com.github.gfranks.slack.poster.model;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

public class SlackDeviceInfoAttachment extends SlackAttachment {

    private String make;
    private String model;
    private String deviceResolution;
    private String deviceDensity;
    private String release;
    private String api;

    public SlackDeviceInfoAttachment() {
        super();
        setTitle("Device Info");
        setColor("#03A9F4");
        getFields().get(0).setShort(false);
    }

    public SlackDeviceInfoAttachment(Builder builder) {
        this();
        make = builder.make;
        model = builder.model;
        deviceResolution = builder.deviceResolution;
        deviceDensity = builder.deviceDensity;
        release = builder.release;
        api = builder.api;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDeviceResolution() {
        return deviceResolution;
    }

    public void setDeviceResolution(String deviceResolution) {
        this.deviceResolution = deviceResolution;
    }

    public String getDeviceDensity() {
        return deviceDensity;
    }

    public void setDeviceDensity(String deviceDensity) {
        this.deviceDensity = deviceDensity;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    @Override
    public String getText() {
        SlackAttachment.Builder builder = new SlackAttachment.Builder();
        StringBuilder sb = new StringBuilder();
        sb.append("Make: ");
        sb.append(make);
        sb.append("\n");
        sb.append("Model: ");
        sb.append(model);
        sb.append("\n");

        sb.append("Resolution: ");
        sb.append(deviceResolution);
        sb.append("\n");
        sb.append("Density: ");
        sb.append(deviceDensity);

        sb.append("\n");
        sb.append("Release: ");
        sb.append(release);
        sb.append("\n");
        sb.append("Api: ");
        sb.append(api);
        return sb.toString();
    }

    public static class SimpleBuilder extends Builder {

        public SimpleBuilder(Context context) {
            this(context.getResources().getDisplayMetrics());
        }

        public SimpleBuilder(Resources resources) {
            this(resources.getDisplayMetrics());
        }

        public SimpleBuilder(DisplayMetrics displayMetrics) {
            setMake(truncateAt(Build.MANUFACTURER, 20));
            setModel(truncateAt(Build.MODEL, 20));

            setDeviceResolution(displayMetrics.heightPixels + "x" + displayMetrics.widthPixels);
            buildDensityString(displayMetrics);

            setRelease(Build.VERSION.RELEASE);
            setApi(String.valueOf(Build.VERSION.SDK_INT));
        }

        private String truncateAt(String string, int length) {
            return string.length() > length ? string.substring(0, length) : string;
        }

        private void buildDensityString(DisplayMetrics displayMetrics) {
            String density;
            switch (displayMetrics.densityDpi) {
                case DisplayMetrics.DENSITY_260:
                case DisplayMetrics.DENSITY_280:
                case DisplayMetrics.DENSITY_300:
                    density = "hdpi/xhdpi";
                    break;
                case DisplayMetrics.DENSITY_340:
                case DisplayMetrics.DENSITY_360:
                case DisplayMetrics.DENSITY_400:
                case DisplayMetrics.DENSITY_420:
                    density = "xhdpi/xxhdpi";
                    break;
                case DisplayMetrics.DENSITY_560:
                    density = "xxhdpi/xxxhdpi";
                    break;
                case DisplayMetrics.DENSITY_LOW:
                    density = "ldpi";
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    density = "mdpi";
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    density = "hdpi";
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    density = "xhdpi";
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    density = "xxhdpi";
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    density = "xxxhdpi";
                    break;
                case DisplayMetrics.DENSITY_TV:
                    density = "tvdpi";
                    break;
                default:
                    density = "unknown";
            }

            setDeviceDensity(displayMetrics.densityDpi + "dpi (" + density + ")");
        }
    }

    public static class Builder {

        private String make;
        private String model;
        private String deviceResolution;
        private String deviceDensity;
        private String release;
        private String api;

        public Builder setMake(String make) {
            this.make = make;

            return this;
        }

        public Builder setModel(String model) {
            this.model = model;

            return this;
        }

        public Builder setDeviceResolution(String deviceResolution) {
            this.deviceResolution = deviceResolution;

            return this;
        }

        public Builder setDeviceDensity(String deviceDensity) {
            this.deviceDensity = deviceDensity;

            return this;
        }

        public Builder setRelease(String release) {
            this.release = release;

            return this;
        }

        public Builder setApi(String api) {
            this.api = api;

            return this;
        }

        public SlackDeviceInfoAttachment build() {
            return new SlackDeviceInfoAttachment(this);
        }
    }
}
