# android-hidl

This is an example for adding a new HIDL service to Android P.
For more information, it' recommanded to see https://blog.csdn.net/sinat_18179367/article/details/95940030


NOTE:

// Remember to update current.txt for VTS

hardware/interfaces$ hidl-gen -L hash -r android.hardware:hardware/interfaces -r android.hidl:system/libhidl/transport android.hardware.test@1.0 >> current.txt



### Original commits (Migrate from GitLab to GitHub)

commit e04758b2b48853c5707014ac5d0c6be58b59882f (HEAD -> master, origin/master, origin/HEAD)
Author: Marco Li <lizidong.marco@qq.com>
Date:   Tue Nov 17 12:14:14 2020 +0800

    Update current.txt for VTS



commit 51a4e72f87b6d4658cb1b94a8bb4d476c6ceacdc
Author: Marco Li <lizidong.marco@qq.com>
Date:   Fri Nov 13 14:04:08 2020 +0800

    Update README.md



commit b1ecc9bef9de46d3e0f3e666e7170a1f16f3957a
Author: Marco Li <lizidong.marco@qq.com>
Date:   Thu Nov 12 15:28:23 2020 +0800

    how to add a new HIDL service on Android P



commit 7ee191f9975161950349a67aa7f0beab1d34481f
Author: Marco Li <lizidong.marco@qq.com>
Date:   Thu Nov 12 07:15:07 2020 +0000

    Initial commit
