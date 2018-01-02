package com.github.gfranks.slack.poster.model;

public class GFSlackAppUsageInfoAttachment extends GFSlackAttachment {

    private String usedMemoryInMB;
    private String maxHeapSizeInMB;
    private String availableHeapSizeInMB;

    public GFSlackAppUsageInfoAttachment() {
        super();
        setTitle("App Usage");
        setColor("#FFA500");
        getFields().get(0).setShort(false);
    }

    public GFSlackAppUsageInfoAttachment(Builder builder) {
        super(builder);
        setTitle("App Usage");
        setColor("#FFA500");
        getFields().get(0).setShort(false);
        usedMemoryInMB = builder.usedMemoryInMB;
        maxHeapSizeInMB = builder.maxHeapSizeInMB;
        availableHeapSizeInMB = builder.availableHeapSizeInMB;
        setValues();
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
