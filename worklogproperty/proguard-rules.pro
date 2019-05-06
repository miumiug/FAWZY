# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/apple/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#--------------------------1.实体类---------------------------------

-keep class hqxh.worklogproperty.animation.**{*;}
-keep class hqxh.worklogproperty.constant.GlobalConfig.**{*;}
-keep class hqxh.worklogproperty.base.**{*;}
-keep class hqxh.worklogproperty.calendar.utils.**{*;}
-keep class hqxh.worklogproperty.constant.**{*;}
-keep class hqxh.worklogproperty.dialog.**{*;}
-keep class hqxh.worklogproperty.model.**{*;}
-keep class hqxh.worklogproperty.ui.activity.**{*;}
-keep class hqxh.worklogproperty.ui.adapter.**{*;}
-keep class hqxh.worklogproperty.ui.fragment.**{*;}
-keep class hqxh.worklogproperty.until.**{*;}
-keep class hqxh.worklogproperty.widget.**{*;}
#-keep class com.hqxh.fiamproperty.db.**{*;}
#-keep class com.hqxh.fiamproperty.listener.**{*;}
#-keep class com.hqxh.fiamproperty.model.**{*;}
#-keep class com.hqxh.fiamproperty.ui.activity.**{*;}
#-keep class com.hqxh.fiamproperty.ui.adapter.**{*;}
#-keep class com.hqxh.fiamproperty.ui.fragment.**{*;}
#-keep class com.hqxh.fiamproperty.ui.view.**{*;}
#-keep class com.hqxh.fiamproperty.ui.**{*;}
#-keep class com.hqxh.fiamproperty.unit.**{*;}
#--------------------------2.第三方包-------------------------------

#-------------------------3.与js互相调用的类------------------------

#-------------------------4.反射相关的类和方法----------------------

#-------------------------5.基本不用动区域--------------------------

#1.基本指令区
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-ignorewarning
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

#2.默认保留区
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}

#3.webview
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}