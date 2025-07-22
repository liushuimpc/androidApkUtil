// ITestEventListener.aidl
package android.os.test;
import android.os.test.TestListenerEvent;
// Declare any non-default types here with import statements

interface ITestEventListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onEvent (inout TestListenerEvent event);
}
