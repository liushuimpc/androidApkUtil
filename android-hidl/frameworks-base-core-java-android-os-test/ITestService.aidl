// ITestManager.aidl
package android.os.test;

import android.os.test.ITestEventListener;
// Declare any non-default types here with import statements

interface ITestService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void init(int id,String name);
     String helloWorld(String str);
     boolean setTestEventListener(ITestEventListener listener);
     void release();

}
