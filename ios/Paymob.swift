import Foundation
import UIKit
import AcceptSDK


@objc(Paymob)
class Paymob:NSObject, AcceptSDKDelegate {

    let accept = AcceptSDK()
    var successCallback: RCTPromiseResolveBlock!
    var errorCallback: RCTPromiseRejectBlock!
    var topVC: UIViewController!

    @objc
    static func requiresMainQueueSetup() -> Bool {
      return true
    }

    func topMostController() -> UIViewController {
        var topController: UIViewController = UIApplication.shared.keyWindow!.rootViewController!
        while (topController.presentedViewController != nil) {
            topController = topController.presentedViewController!
        }
        return topController
    }

    @objc public func payWithNoToken(_ data:NSDictionary, successCallback:  @escaping RCTPromiseResolveBlock, errorCallback:  @escaping RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            self?._payWithNoToken(data, successCallback:successCallback , errorCallback:errorCallback)
        }

    }

    public func _payWithNoToken(_ data:NSDictionary, successCallback: @escaping RCTPromiseResolveBlock, errorCallback: @escaping RCTPromiseRejectBlock) {
        accept.delegate = self
        self.successCallback = successCallback
        self.errorCallback = errorCallback
        
        if topVC == nil {
            topVC = topMostController()
        }
        
        do {
            let mappedData:NSMutableDictionary = NSMutableDictionary()
            mappedData["paymentKey"] = data["paymentKey"] ?? ""
            mappedData["saveCardDefault"] = data["saveCardDefault"] ?? false
            mappedData["showSaveCard"] = data["showSaveCard"] ?? true
            mappedData["isEnglish"] = data["isEnglish"] ?? true
            mappedData["showAlerts"] = data["showAlerts"] ?? true

            mappedData["firstName"] = data["firstName"] ?? "NA"
            mappedData["lastName"] = data["lastName"] ?? "NA"
            mappedData["building"] = data["building"] ?? "NA"
            mappedData["floor"] = data["floor"] ?? "NA"
            mappedData["apartment"] = data["apartment"] ?? "NA"
            mappedData["city"] = data["city"] ?? "NA"
            mappedData["state"] = data["state"] ?? "NA"
            mappedData["city"] = data["city"] ?? "NA"
            mappedData["country"] = data["country"] ?? "NA"
            mappedData["email"] = data["email"] ?? "NA"
            mappedData["phoneNumber"] = data["phoneNumber"] ?? "NA"
            mappedData["postalCode"] = data["postalCode"] ?? "NA"


            let bData = [  "apartment": mappedData["apartment"],
                           "email": mappedData["email"],
                           "floor": mappedData["floor"],
                           "first_name": mappedData["firstName"],
                           "street": "NA",
                           "building":  mappedData["building"],
                           "phone_number": mappedData["phoneNumber"],
                           "shipping_method": "NA",
                           "postal_code": mappedData["postalCode"],
                           "city": mappedData["city"],
                           "country": mappedData["country"],
                           "last_name": mappedData["lastName"],
                           "state": mappedData["state"]
            ]
            try accept.presentPayVC(vC: topVC,
                                    billingData: bData as! [String : String],
                                    paymentKey:mappedData["paymentKey"] as! String,
                                    saveCardDefault: mappedData["saveCardDefault"] as! Bool,
                                    showSaveCard: mappedData["showSaveCard"] as! Bool,
                                    showAlerts: mappedData["showAlerts"] as! Bool,
                                    isEnglish: mappedData["isEnglish"] as! Bool
            )
            
            successCallback("")

        } catch AcceptSDKError.MissingArgumentError(let errorMessage) {
            self.errorCallback("-1",errorMessage, nil)
            errorCallback(errorMessage)
       }  catch let error {
        self.errorCallback("-1", error.localizedDescription, error)
            errorCallback(error.localizedDescription)
       }


    }
    

    public func paymentAttemptFailed(_ error: AcceptSDKError, detailedDescription: String) {
        self.successCallback([false, detailedDescription, error.localizedDescription])
    }

    public func transactionRejected(_ payData: PayResponse) {
        self.successCallback([false, 400, "Transaction Rejected!!"])
    }

    public func transactionAccepted(_ payData: PayResponse) {
        self.successCallback([true, 0, "", ""])
    }

    public func transactionAccepted(_ payData: PayResponse, savedCardData: SaveCardResponse) {
        self.successCallback([true, 0, "", savedCardData.token])
    }

    public func userDidCancel3dSecurePayment(_ pendingPayData: PayResponse) {
        self.successCallback([false, 0, "User canceled 3-d secure verification!!"])
    }

    public func userDidCancel() {
        self.successCallback([false, 0, "User canceled!!"])
    }

}
