# systemui-service

This is a demo to show how to add a new service in **SystemUI** of AOSP, it is based on Android 13, Mediatek platform.

**Note1:** Apply the patch by below command, due to there is a picture.png.

(In Mediatek platform, the path is **vendor/mediatek/proprietary/packages/apps/SystemUI**)

```cmd
git apply --binary systemui-new-service.patch
```

**Note2:** You can use below command to communicate with service, and SystemUI service will show a picture on the screen.

```cmd
adb shell settings put global wifi_display_on 10
```

