# Toolbox Library Consumer ProGuard Rules
# These rules are automatically applied to apps that use this library

# Keep all Toolbox classes and members (extension functions need this)
-keep class com.elytelabs.toolbox.** { *; }
-keepclassmembers class com.elytelabs.toolbox.** { *; }

# Keep ColorGenerator singleton
-keep class com.elytelabs.toolbox.ColorGenerator { *; }
