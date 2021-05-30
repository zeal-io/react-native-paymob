#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>


@interface RCT_EXTERN_MODULE(Paymob, NSObject)

RCT_EXTERN_METHOD(payWithNoToken:(NSDictionary *)data successCallback:(RCTResponseSenderBlock)successCallback errorCallback:(RCTResponseErrorBlock)errorCallback)

@end
