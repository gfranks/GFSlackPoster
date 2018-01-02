package com.github.gfranks.slack.poster.model;

public class SlackNetworkInfoAttachment extends SlackAttachment {

    private String apiVersion;
    private String environment;

    public SlackNetworkInfoAttachment() {
        super();
        setTitle("Networking Info");
        setColor("#3DB766");
        getFields().get(0).setShort(false);
    }

    public SlackNetworkInfoAttachment(Builder builder) {
        this();
        apiVersion = builder.apiVersion;
        environment = builder.environment;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Api Version: ");
        sb.append(apiVersion);
        sb.append("\n");
        sb.append("Environment: ");
        sb.append(environment);
        return sb.toString();
    }

    public static class Builder {

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

        public SlackNetworkInfoAttachment build() {
            return new SlackNetworkInfoAttachment(this);
        }
    }
}
