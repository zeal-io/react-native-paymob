import UIKit
import AcceptSDK


@objc(Paymob)
class Paymob:NSObject {

    let accept = AcceptSDK()
    var topVC: UIViewController!


    func topMostController() -> UIViewController {
        var topController: UIViewController = UIApplication.shared.keyWindow!.rootViewController!
        while (topController.presentedViewController != nil) {
            topController = topController.presentedViewController!
        }
        return topController
    }

    @objc public func payWithNoToken(_ data:NSDictionary, successCallback:  @escaping RCTPromiseResolveBlock, errorCallback:  @escaping RCTPromiseRejectBlock) -> Void {
        DispatchQueue.main.async { [weak self] in
            self?._payWithNoToken(data, successCallback:successCallback , errorCallback:errorCallback)
        }

    }

    public func _payWithNoToken(_ data:NSDictionary, successCallback: @escaping RCTPromiseResolveBlock, errorCallback: @escaping RCTPromiseRejectBlock) {
        if topVC == nil {
            topVC = topMostController()
        }
        
        do {
            
            let mappedData = NSMutableDictionary()
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
            
             successCallback([])
       }catch let error {
            // TODO: proper error handling.
             errorCallback("")
       }
    }

}
