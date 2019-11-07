#include <stdio.h>
#include <jni.h>
#include <android/log.h>

#ifdef __cplusplus
#extern "C" {
#endif

#define PI_F 3.141592653f


JNIEXPORT float JNICALL Java_com_example_marcotest_HelloWorld_process(JNIEnv *env, jobject obj, jint i)
{
	__android_log_print(4, LOG_TAG, "======Hello, get i = %d\n", i);
	return PI_F;
}

#ifdef __cplusplus
}
#endif
