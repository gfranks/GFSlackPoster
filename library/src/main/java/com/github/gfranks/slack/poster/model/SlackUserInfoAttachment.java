package com.github.gfranks.slack.poster.model;

public class SlackUserInfoAttachment extends SlackAttachment {

    private String name;
    private String id;
    private String email;
    private String phone;

    public SlackUserInfoAttachment() {
        super();
        setTitle("User Info");
        setColor("#000000");
        getFields().get(0).setShort(false);
    }

    public SlackUserInfoAttachment(Builder builder) {
        this();
        name = builder.name;
        id = builder.id;
        email = builder.email;
        phone = builder.phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getText() {
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
        return sb.toString();
    }

    public static class Builder {

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

        public SlackUserInfoAttachment build() {
            return new SlackUserInfoAttachment(this);
        }
    }
}
