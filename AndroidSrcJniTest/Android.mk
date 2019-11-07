LOCAL_PATH:= $(call my-dir)

# jni library for libMarcoJniTest
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES:=      jni/test.c

# Header files path
LOCAL_C_INCLUDES :=     \
    ./jni/  \
    $(JNI_H_INCLUDE)    \
    $(call include-path-for, system-core)/cutils

LOCAL_SHARED_LIBRARIES	+= libdl \
                           libutils   \
                           libcutils

LOCAL_PRELINK_MODULE := false

LOCAL_CFLAGS += -DLOG_TAG=\"JNI_TEST\"

ifeq ($(TARGET_SIMULATOR),true)
LOCAL_CFLAGS += -DSINGLE_PROCESS
endif

LOCAL_MODULE:= libMarcoJniTest

include $(BUILD_SHARED_LIBRARY)

# module for CIT
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_PACKAGE_NAME := MarcoJniHelloWorld
LOCAL_CERTIFICATE := platform

LOCAL_JNI_SHARED_LIBRARIES := libMarcoJniTest

include $(BUILD_PACKAGE)
