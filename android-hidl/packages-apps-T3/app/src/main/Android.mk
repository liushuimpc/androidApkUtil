LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-java-files-under, java)

LOCAL_PACKAGE_NAME := T3
LOCAL_PRIVILEGED_MODULE := true
LOCAL_PRIVATE_PLATFORM_APIS := true
LOCAL_DEX_PREOPT := false

LOCAL_STATIC_ANDROID_LIBRARIES := \
    android-support-compat \
    android-support-media-compat \
    android-support-core-utils \
    android-support-core-ui \
    android-support-fragment \
    android-support-v7-appcompat \
    android-support-v7-palette \
    android-support-v7-recyclerview

include $(BUILD_PACKAGE)
