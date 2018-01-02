package com.github.gfranks.slack.poster.model;

public class SlackBuildInfoAttachment extends SlackAttachment {

    private String applicationId;
    private String versionName;
    private String versionCode;
    private String sha;
    private String buildDate;

    public SlackBuildInfoAttachment() {
        super();
        setTitle("Build Info");
        setColor("#FEC418");
        getFields().get(0).setShort(false);
    }

    public SlackBuildInfoAttachment(Builder builder) {
        this();
        applicationId = builder.applicationId;
        versionName = builder.versionName;
        versionCode = builder.versionCode;
        sha = builder.sha;
        buildDate = builder.buildDate;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    @Override
    public String getText() {
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
        sb.append("\n");
        return sb.toString();
    }

    public static class Builder {

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

        public SlackBuildInfoAttachment build() {
            return new SlackBuildInfoAttachment(this);
        }
    }
}
