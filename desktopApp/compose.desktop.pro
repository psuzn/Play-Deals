-ignorewarnings


-keep class org.slf4j.**{ *; }
-keep class com.sun.jna.* { *; }
-keep class * implements com.sun.jna.* { *; }
-keep class * implements org.mozilla.javascript.* { *; }
