import Foundation
import UIKit
import AcceptSDK

@objc(Paymob)
class Paymob: RCTEventEmitter, AcceptSDKDelegate {
    let accept: AcceptSDK = AcceptSDK()


    @objc
    static func requiresMainQueueSetup() -> Bool {
      return true
    }



    public func paymentAttemptFailed(_ error: AcceptSDKError, detailedDescription: String) {
        sendEvent("")
    }

    public func transactionRejected(_ payData: PayResponse) {
    }

    public func transactionAccepted(_ payData: PayResponse) {
    }

    public func transactionAccepted(_ payData: PayResponse, savedCardData: SaveCardResponse) {
    }

    
    public func userDidCancel3dSecurePayment(_ pendingPayData: PayResponse) {
    }

    
    public func userDidCancel() {
    }



    override func supportedEvents() -> [String]! {
        return ["didShow", "didDismiss"]
    }

}
