import Foundation
import UIKit
import AcceptSDK

@objc(Paymob)
class Paymob: RCTEventEmitter, AcceptSDKDelegate {
    let accept: AcceptSDK = AcceptSDK()

    override init() {
        super.init()
        accept.delegate = self
    }

    @objc func presentPayVC(_ data: [String: Any],
                            promiseResolver: @escaping RCTPromiseResolveBlock,
                            promiseRejecter: @escaping RCTPromiseRejectBlock ){
        DispatchQueue.main.async {
            do {
            try self.accept.presentPayVC(
                vC: UIApplication.shared.keyWindow!.rootViewController!,
                billingData: data["billingData"] as! [String: String],
                paymentKey: data["paymentKey"] as! String,
                saveCardDefault: data["saveCardDefault"] as! Bool,
                showSaveCard: data["showSaveCard"] as! Bool,
                showAlerts: data["showAlerts"] as! Bool,
                isEnglish: data["isEnglish"] as! Bool,
                showScanCardButton: data["showScanCardButton"] as! Bool
              )
              promiseResolver(true)
            } catch AcceptSDKError.MissingArgumentError(let errorMessage) {
              promiseRejecter("AcceptSDKError", errorMessage, nil)
            }  catch let error {
                promiseRejecter("error", error.localizedDescription, nil)
            }
        }
    }

    public func paymentAttemptFailed(_ error: AcceptSDKError, detailedDescription: String) {
        sendEvent(withName: "didDismiss", body: "paymentAttemptFailed")
    }

    public func transactionRejected(_ payData: PayResponse) {
        sendEvent(withName: "didDismiss", body: "transactionRejected")
    }

    public func transactionAccepted(_ payData: PayResponse) {
        sendEvent(withName: "didDismiss", body: "transactionAccepted")
    }

    public func transactionAccepted(_ payData: PayResponse, savedCardData: SaveCardResponse) {
        sendEvent(withName: "didDismiss", body: "transactionAccepted")
    }


    public func userDidCancel3dSecurePayment(_ pendingPayData: PayResponse) {
        sendEvent(withName: "didDismiss", body: "userDidCancel3dSecurePayment")
    }

    public func userDidCancel() {
        sendEvent(withName: "didDismiss", body: "userDidCancel")
    }
    override func supportedEvents() -> [String]! {
        return ["didDismiss"]
    }
}
