#define LOG_TAG "Test_cpp"

#include "Test.h"

#include <log/log.h>

namespace android {
namespace hardware {
namespace test {
namespace V1_0 {
namespace implementation {

pthread_t pthread;
sp<ITestCallback> mCallback = nullptr;
std::string mName;
int32_t mID;
bool mExit;
sp<Thread> mThread;

// Methods from ::android::hardware::test::V1_0::ITest follow.
Return<void> Test::init(const ::android::hardware::test::V1_0::TestID& id) {
    int status;
    mExit = false;
    mName = id.name;
    mID = id.id;
    ALOGD("initttttttt:");
    // run("test_thread");  // comment by Marco.Li
    mThread = new ProcessThread();
    status = mThread->run("marco--thread");
    if (status != 0) {
        ALOGD("marcoo--run thread failed");
    }

    return Void();
}

Return<void> Test::helloWorld(const hidl_string& name, helloWorld_cb _hidl_cb) {
    ALOGD("helloWorld:");
    char buf[100];
    ::memset(buf,0x00,100);
    ::snprintf(buf,100,"Hello World,%s",name.c_str());
    hidl_string result(buf);
    
    _hidl_cb(result);
    return Void();
}

Return<bool> Test::setCallback(const sp<::android::hardware::test::V1_0::ITestCallback>& callback) {
    mCallback = callback;
    bool res = false;
    if(mCallback != nullptr) {
        ALOGD("setCallback: done");
        res = true;
    }
    return res;
}

Return<void> Test::release() {
    mExit = true;
    ALOGD("release:");

    // TODO implement
    return Void();
}

bool ProcessThread::threadLoop(){
     static int32_t count = 0;
     TestEvent event;

     ALOGD("marcoo--threadLoop start running");
     while(!mExit) {
         ::sleep(1);
         ALOGD("marcoo--run thread count=%d", count);
         event.msg = mName;
         event.what = count ++;
         if(mCallback != nullptr) {
             mCallback->onTestEvent(event);
         }
     }
     ALOGD("ProcessThread::threadLoop(): exit");
     return false;
}


// Methods from ::android::hidl::base::V1_0::IBase follow.

//ITest* HIDL_FETCH_ITest(const char* /* name */) {
    //return new Test();
//}
//
}  // namespace implementation
}  // namespace V1_0
}  // namespace test
}  // namespace hardware
}  // namespace android
