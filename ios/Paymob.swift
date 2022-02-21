import Foundation
import UIKit
import AcceptSDK

@objc(Paymob)
class Paymob: RCTEventEmitter, AcceptSDKDelegate {
    let accept = AcceptSDK()

    override init() {
        super.init()
        accept.delegate = self
    }

    func topMostController() -> UIViewController {
        var topController: UIViewController = UIApplication.shared.keyWindow!.rootViewController!
        while (topController.presentedViewController != nil) {
            topController = topController.presentedViewController!
        }
        return topController
    }

    @objc func presentPayVC(_ data: [String: Any], promiseResolver: @escaping RCTPromiseResolveBlock, promiseRejecter: @escaping RCTPromiseRejectBlock ){

        DispatchQueue.main.async {
            let vc = self.topMostController()
            do {
              try self.accept.presentPayVC(
//                vC: UIApplication.shared.keyWindow!.rootViewController!,
                vC: vc,
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
        sendEvent(withName: "didDismiss", body: [
            "type": "paymentAttemptFailed",
            "detailedDescription": detailedDescription
        ])
    }

    public func transactionRejected(_ payData: PayResponse) {
        sendEvent(withName: "didDismiss", body: [
            "type":  "transactionRejected",
            "payData" : payResponseToDictionary(payData)
        ])
    }

    public func transactionAccepted(_ payData: PayResponse) {
        sendEvent(withName: "didDismiss", body: [
            "type": "transactionAccepted",
            "payData" : payResponseToDictionary(payData)
        ])
    }

    public func transactionAccepted(_ payData: PayResponse, savedCardData: SaveCardResponse) {
        sendEvent(withName: "didDismiss", body: [
            "type": "transactionAccepted",
            "payData": payResponseToDictionary(payData),
            "savedCardData": [
                "card_subtype": savedCardData.card_subtype,
                "id": savedCardData.id,
                "token": savedCardData.token,
                "masked_pan": savedCardData.masked_pan,
                "merchant_id": savedCardData.merchant_id,
            // .  "email": savedCardData.email,
            //    "order_id": savedCardData.order_id
            ]

        ])
    }

    public func userDidCancel3dSecurePayment(_ pendingPayData: PayResponse) {
        sendEvent(withName: "didDismiss", body: [
            "type": "userDidCancel3dSecurePayment",
            "pendingPayData": payResponseToDictionary(pendingPayData)
        ])
    }

    public func userDidCancel() {
        sendEvent(withName: "didDismiss", body: [
            "type": "userDidCancel",
        ])
    }
    override func supportedEvents() -> [String]! {
        return ["didDismiss"]
    }


    func payResponseToDictionary(_ payData: PayResponse) -> [String : Any] {
        print(payData)
        return [
             "amount_cents": payData.amount_cents,
             "is_refunded": payData.is_refunded,
             "is_capture": payData.is_capture,
             "captured_amount": payData.captured_amount,
             "source_data_type": payData.source_data_type,
             "pending": payData.pending,
             "is_3d_secure": payData.is_3d_secure,
             "id": payData.id,
             "is_void": payData.is_void,
             "currency": payData.currency,
             "is_auth": payData.is_auth,
             "is_refund": payData.is_refund,
             "owner": payData.owner,
             "is_voided": payData.is_voided,
             "source_data_pan": payData.source_data_pan,
             "profile_id": payData.profile_id,
             "success": payData.success,
             "dataMessage": payData.dataMessage,
             "source_data_sub_type": payData.source_data_sub_type,
             "error_occured": payData.error_occured,
             "is_standalone_payment": payData.is_standalone_payment,
             "created_at": payData.created_at,
             "refunded_amount_cents": payData.refunded_amount_cents,
             "integration_id": payData.integration_id,
             "order": payData.order
        ] as [String : Any]
    }

    @objc override static func requiresMainQueueSetup() -> Bool {
        return true
    }
}
