#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>


@interface RCT_EXTERN_MODULE(Paymob, NSObject)

RCT_EXTERN_METHOD(pay:
  (NSDictionary) options
  Resolver:(RCTPromiseResolveBlock)resolve
  Rejector:(RCTPromiseRejectBlock)reject
);
//RCT_EXTERN_METHOD(pay: resolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject);

@end
