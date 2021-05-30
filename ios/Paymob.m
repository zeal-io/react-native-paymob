#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTViewManager.h>


@interface RCT_EXTERN_MODULE(Paymob, RCTViewManager)
RCT_EXTERN_METHOD(playSound:(NSString *)url)

RCT_EXTERN_METHOD(payWithNoToken:(NSDictionary *)data successCallback:(RCTResponseSenderBlock)successCallback errorCallback:(RCTResponseErrorBlock)errorCallback)

RCT_EXTERN_METHOD(presentPayVC:(UIViewController)vc
                 billingData:(NSDictionary<NSString,NSString>)billingData
                 paymentKey:(NSString) paymentKey
                 saveCardDefault: (BOOL)saveCardDefault
                 showSaveCard: (BOOL)showSaveCard
                 showAlerts: (BOOL)showAlerts
                 token(NSString): [token
                                   maskedPanNumber:(NSString)maskedPanNumber
                                   buttonsColor:(UIColor)buttonsColor
                 isEnglish: (BOOL)isEnglish
                 backgroundColor:(UIColor)backgroundColor
                 navBarColor:(UIColor)navBarColor
                 textFieldBackgroundColor:(UIColor)textFieldBackgroundColor
                                   textFieldTextColor:(UIColor)textFieldTextColor
                                   titleLabelTextColor:(UIColor)titleLabelTextColor
                                   inputLabelTextColor:(UIColor)inputLabelTextColor
                                   buttonText:(NSString) buttonText
                                   cardNameLabelText:(NSString) cardNameLabelText
                                   cardNumberLabelText:(NSString) cardNumberLabelText
                                   expirationLabelText:(NSString) expirationLabelText
                                   cvvLabelText:(NSString) cvvLabelText
                 )
+ (BOOL)requiresMainQueueSetup
{
    return YES;
}
@end
