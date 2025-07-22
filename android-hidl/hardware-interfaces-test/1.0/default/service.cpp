#define LOG_TAG "android.hardware.test@1.0-service"
#include <android-base/logging.h>
#include <hidl/HidlTransportSupport.h>
#include <android/hardware/test/1.0/ITest.h>

#include <hidl/LegacySupport.h>
#include "Test.h"
using android::hardware::configureRpcThreadpool;
using android::hardware::joinRpcThreadpool;
using android::hardware::test::V1_0::implementation::Test;
//using android::hardware::defaultPassthroughServiceImplementation;
//passthrough mode

int main() {
	configureRpcThreadpool(4, true);

    Test test;
    LOG(WARNING) << "marcooo--service.cpp-main()";
    auto status = test.registerAsService();
    CHECK_EQ(status, android::OK) << "marcoo--Failed to register test HAL implementation";

    joinRpcThreadpool();
    return 0;  // joinRpcThreadpool shouldn't exit
 //   return defaultPassthroughServiceImplementation<ITest>();
 //passthrough mode
}
