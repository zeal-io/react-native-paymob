#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>


@interface RCT_EXTERN_MODULE(Paymob, RCTEventEmitter)
RCT_EXTERN_METHOD(
  presentPayVC:
  (NSDictionary*) data
  promiseResolver:(RCTPromiseResolveBlock *)promiseResolver
  promiseRejecter:(RCTPromiseRejectBlock *)promiseRejecter
)
@end
