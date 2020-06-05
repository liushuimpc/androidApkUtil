1. Just a demo, may includes bugs, such as KeyguardLock balance, wakelock balance in onDestroy().
2. uses-permission "DISABLE_KEYGUARD" & "RECEIVE_BOOT_COMPLETED".
3. android:exported="true", for accepting broadcast from this app outside.
4. android:directBootAware="true", for monitoring "LOCKED_BOOT_COMPLETED".
5. start a Activity in BroadcastReceiver, requires FLAG_ACTIVITY_NEW_TASK.


## For Android 8.1/9 monitor BOOT_COMPLETED statically, you need to push into /system/app, OR add android.sharedUserId="android.uid.system" + signed with platform key.
