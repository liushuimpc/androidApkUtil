package android.os.test;

/**
 * android.os.test.TestManager;
 */

import android.os.RemoteException;
import android.util.Log;

public class TestManager {
    private ITestService mService;
    public static final String TAG = "TestManager";

    public TestManager(ITestService server) {
        Log.d(TAG, "TestManager: ");
        mService = server;
    }

    public void init(int id,String name){
        Log.d(TAG, "init: "+id+" "+name);
        try {
            if (mService != null) {
                mService.init(id,name);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String helloWorld(String str) {
        Log.d(TAG, "helloWorld: "+str);
        try {
            if (mService == null) {
                return null;
            }
            return mService.helloWorld(str);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "service connect failed";
    }

    public boolean setTestListener(TestEventListener listener){
        Log.d(TAG, "setTestListener: ");
        try {
            if (mService == null) {
                return false;
            }
            return  mService.setTestEventListener(listener);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void release(){
        Log.d(TAG, "release: ");
        try {
            if(mService != null) {
                mService.release();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
