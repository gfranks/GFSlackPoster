GFSlackPoster 1.0
===========

GFSlackPoster was created as a way to easily track user's device information when crashes happen, i.e. app usage, memory information, device information, etc and easily post it to a slack channel via a webhook.

If you would like to contribute or have any issues, please use the issue tracker or email me directly at lgfz71@gmail.com

How To Use It:
-------------

### Basic Example

Add permissions your manifest
```java
<uses-permission android:name="android.permission.READ_LOGS" />
<uses-permission android:name="android.permission.INTERNET"
        android:required="true" />
```

```java
// Your basic webhook url will look something like `https://hooks.slack.com/1234/abcd/1a2b3c4d` with 3 path parameters.
// These path parameters will be used to construct our slack poster.
GFSlackPoster slackPoster = new GFSlackPoster(webhookPath1, webhookPath2, webhookPath3);

// Create a List of GFSlackAttachments
List<GFSlackAttachment> attachments = new ArrayList<>();
// see below for attachment options

// Make the call to post to your slack channel with your app name, any custom message, and your attachments
slackPoster.postToSlack(appName, customMessage, attachments);
```

### Attachment Options

- Basic Slack Attachment
```java
GFSlackAttachment attachment = new GFSlackAttachment();
attachment.setTitle(String); // title for attachment
attachment.setText(String); // text for attachment
attachment.setColor(String); // color of attachment bar
attachment.setIncludeFooter(boolean); // footer to be shown at the bottom of the attachment, i.e. app name and timestamp, timestamp is already set to current time
attachment.setFooter(String); // Title to be displayed with footer, no footer will be displayed if footer and footer icon are not set
attachment.setFooterIcon(String); // url for the image to use as your footer's icon, no footer will be displayed if footer and footer icon are not set
attachment.setFields(List<GFSlackAttachment.SlackField>); // fields to use for attachment (defaults to 1 when not set)

// SlackField
GFSlackAttachment.SlackField field = new GFSlackAttachment.SlackField();
field.setTitle(String); // title for field
field.setValue(String); // value for field
field.setShort(boolean); // if attachment should be truncated if too long, defaults to true
```

- App Usage Attachment (Child of GFSlackAttachment)
```java
GFSlackAppUsageInfoAttachment attachment = new GFSlackAppUsageInfoAttachment();
attachment.setUsedMemoryInMB(String); // used memory in megabytes
attachment.setMaxHeapSizeInMB(String); // max heap size in megabytes
attachment.setAvailableHeapSizeInMB(String); // total available heap size in megabytes

// or use the simple builder to obtain this information for you
GFSlackAppUsageInfoAttachment attachment = new GFSlackAppUsageInfoAttachment.SimpleBuilder().build();
```

- Build Info Attachment (Child of GFSlackAttachment)
```java
GFSlackBuildInfoAttachment attachment = new GFSlackBuildInfoAttachment();
attachment.setApplicationId(String); // application id to be displayed
attachment.setVersionName(String); // version name
attachment.setVersionCode(String); // version code
attachment.setSha(String); // git sha for this particular build
attachment.setBuildDate(String); // date of build
```

- Device Info Attachment (Child of GFSlackAttachment)
```java
GFSlackDeviceInfoAttachment attachment = new GFSlackDeviceInfoAttachment();
attachment.setMake(String); // make of device
attachment.setModel(String); // model of device
attachment.setDeviceResolution(String); // device resolution
attachment.setDeviceDensity(String); // device density
attachment.setRelease(String); // release version of the device
attachment.setApi(String); // api version of the device

// or use the simple builder to obtain this information for you
GFSlackDeviceInfoAttachment attachment = new GFSlackDeviceInfoAttachment.SimpleBuilder(Context).build();
```

- Logcat Attachment (Child of GFSlackAttachment)
```java
GFSlackLogcatAttachment.Builder attachmentBuilder = new GFSlackLogcatAttachment.Builder();
attachmentBuilder.setProcessId(int); // your application's process id to monitor -- REQUIRED
attachmentBuilder.setLineCount(int); // set the number of lines to capture from the log, defaults to 125
attachmentBuilder.build(Map<String, Object> extras, new OnSlackLogcatAttachmentAvailableListener() {
    @Override
    public void onSlackLogcatAttachmentAvailable(GFSlackLogcatAttachment attachment, Map<String, Object> extras) {
        // called when your logcat attachment is available
    }
});
```

- Network Info Attachment (Child of GFSlackAttachment)
```java
GFSlackNetworkInfoAttachment attachment = new GFSlackNetworkInfoAttachment();
attachment.setApiVersion(String); // the version of your api
attachment.setEnvironment(String); // the environment (i.e. QA, Production, DEV, etc)
```

- User Info Attachment (Child of GFSlackAttachment)
```java
GFSlackUserInfoAttachment attachment = new GFSlackUserInfoAttachment();
attachment.setName(String); // name of the current user
attachment.setId(String); // id of the current user
attachment.setEmail(String); // email of the current user
attachment.setPhone(String); // phone of the current user
```

Installation:
------------

### Gradle/JitPack

- Add JitPack to your top-level build.gradle file
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
- Add GFSlackPoster to your module's build.gradle file
```
dependencies {
     api 'com.github.gfranks:GFSlackPoster:<version>'
}
```

License
-------
Copyright (c) 2018 Garrett Franks. All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
