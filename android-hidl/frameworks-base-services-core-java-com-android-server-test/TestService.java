package com.android.server.test;

import android.hardware.test.V1_0.ITest;
import android.hardware.test.V1_0.ITestCallback;
import android.hardware.test.V1_0.TestEvent;
import android.hardware.test.V1_0.TestID;
import android.os.RemoteException;
import android.util.Log;

import android.os.test.ITestEventListener;
import android.os.test.ITestService;
import android.os.test.TestListenerEvent;

import java.util.ArrayList;


/**
 * com.android.server.test.TestService
 */
public class TestService extends ITestService.Stub {
    private String TAG = "TestService";
	private ITest halService ;
    public TestService(){
        try {
            Log.d(TAG, "marcooo--TestService(), try getService()");
            halService = ITest.getService(); //Get service
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void init(int id, String name) throws RemoteException {
        Log.d(TAG, "init: ");
        TestID testID = new TestID();
        testID.id = id;
        testID.name = name;
        halService.init(testID);
    }

    @Override
    public String helloWorld(String str) throws RemoteException {
        Log.d(TAG, "helloWorld: ");
        return halService.helloWorld(str);
    }

    @Override
    public boolean setTestEventListener(ITestEventListener listener) throws RemoteException {
        Log.d(TAG, "setTestEventListener: ");
        TestCallback testCallback = new TestCallback(listener);
        return halService.setCallback(testCallback);
    }

    @Override
    public void release() throws RemoteException {
        Log.d(TAG, "release: ");
        halService.release();
    }

    class TestCallback extends ITestCallback.Stub{
        ITestEventListener mITestEventListener;
        TestCallback (ITestEventListener listener){
            mITestEventListener = listener;
        }
        @Override
        public void onTestEvent(TestEvent testEvent) throws RemoteException {
            Log.d(TAG, "onTestEvent: " + testEvent.what + ", " + testEvent.msg);
            TestListenerEvent testListenerEvent = new TestListenerEvent(testEvent.what,testEvent.msg);
            mITestEventListener.onEvent(testListenerEvent);
        }
    }

}
