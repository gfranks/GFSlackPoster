package com.github.gfranks.slack.poster.model;

public class GFSlackNetworkInfoAttachment extends GFSlackAttachment {

    private String apiVersion;
    private String environment;

    public GFSlackNetworkInfoAttachment() {
        super();
        setTitle("Networking Info");
        setColor("#3DB766");
        getFields().get(0).setShort(false);
    }

    public GFSlackNetworkInfoAttachment(Builder builder) {
        super(builder);
        setTitle("Networking Info");
        setColor("#3DB766");
        getFields().get(0).setShort(false);
        apiVersion = builder.apiVersion;
        environment = builder.environment;
        setValues();
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
