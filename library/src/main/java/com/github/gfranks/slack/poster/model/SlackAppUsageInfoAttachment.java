package com.github.gfranks.slack.poster.model;

public class SlackAppUsageInfoAttachment extends SlackAttachment {

    private String usedMemoryInMB;
    private String maxHeapSizeInMB;
    private String availableHeapSizeInMB;

    public SlackAppUsageInfoAttachment() {
        super();
        setTitle("App Usage");
        setColor("#FFA500");
        getFields().get(0).setShort(false);
    }

    public SlackAppUsageInfoAttachment(Builder builder) {
        this();
        usedMemoryInMB = builder.usedMemoryInMB;
        maxHeapSizeInMB = builder.maxHeapSizeInMB;
        availableHeapSizeInMB = builder.availableHeapSizeInMB;
    }

    @Override
    public String getText() {
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
        return sb.toString();
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
    }

    public static class Builder {

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

        public SlackAppUsageInfoAttachment build() {
            return new SlackAppUsageInfoAttachment(this);
        }
    }
}
