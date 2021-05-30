#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTViewManager.h>


@interface RCT_EXTERN_MODULE(Paymob, RCTViewManager)

RCT_EXTERN_METHOD(payWithNoToken:(NSDictionary *)data successCallback:(RCTResponseSenderBlock)successCallback errorCallback:(RCTResponseErrorBlock)errorCallback)

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}
@end
