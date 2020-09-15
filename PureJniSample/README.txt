Use javah to generate a JNI C header.

1. JAVA source:
	com/android/defcontainer/MeasurementUtils.java

2. compile JAVA source:
	cd ${TOP}
	javac com/android/defcontainer/MeasurementUtils.java -d classes/

3. generate JNI C header:
	cd ${TOP}
	javah -d jni -jni com.android.defcontainer.MeasurementUtils
