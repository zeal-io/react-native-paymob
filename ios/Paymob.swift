import Foundation
import UIKit
import AcceptSDK




@objc(Paymob)
class Paymob: NSObject, AcceptSDKDelegate{
    
    let accept: AcceptSDK = AcceptSDK()
    
    override init() {
        super.init()
        accept.delegate = self
    }

    
    func userDidCancel() {
        print("userDidCancel")
    }

    func paymentAttemptFailed(_ error: AcceptSDKError, detailedDescription: String) {
        print("paymentAttemptFailed")
    }
    
    func transactionRejected(_ payData: PayResponse) {
        print("transactionRejected")
    }
    
    func transactionAccepted(_ payData: PayResponse) {
        print("transactionAccepted")

    }
    
    func transactionAccepted(_ payData: PayResponse, savedCardData: SaveCardResponse) {
        print("transactionAccepted")
    }
    
    func userDidCancel3dSecurePayment(_ pendingPayData: PayResponse) {
        print("userDidCancel3dSecurePayment")
    }
    

    
    @objc func pay(_ options: [String: Int], Resolver resolve: @escaping RCTPromiseResolveBlock,  Rejector reject: @escaping RCTPromiseRejectBlock) -> Void {
        DispatchQueue.main.async {

            let bData = [
                "apartment": "",
                "email": "",
                "floor": "",
                "first_name": "",
                "street": "",
                "building": "",
                "phone_number": "",
                "shipping_method": "",
                "postal_code": "",
                "city": "",
                "country": "",
                "last_name": "",
                "state": ""
            ]

            do {
                try self.accept.presentPayVC(
                        vC: UIApplication.shared.windows.first!.rootViewController!,
                        billingData: bData,
                        paymentKey: "",
                        saveCardDefault: true,
                        showSaveCard: false,
                        showAlerts: true,
                        token: nil,
                        maskedPanNumber: nil,
                        buttonsColor: nil,
                        isEnglish: true,
                        showScanCardButton: false,
                        backgroundColor: nil,
                        navBarColor: nil,
                        navBarTextColor: nil,
                        textFieldBackgroundColor: nil,
                        textFieldTextColor: nil,
                        titleLabelTextColor: nil,
                        inputLabelTextColor: nil,
                        buttonText: "buttonText",
                        cardNameLabelText: "cardNameLabelText",
                        cardNumberLabelText: "cardNumberLabelText",
                        expirationLabelText: "expirationLabelText",
                        cvvLabelText: "cvLabelText"
                )
                resolve("success")

        } catch AcceptSDKError.MissingArgumentError(let errorMessage) {
            print(errorMessage)
            reject("", "", nil)
        }  catch let error {
            print(error.localizedDescription)
            reject("", "", nil)
        }

        }
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
    }
    @objc func presentPayVC(vC: UIViewController, billingData: [String : String], paymentKey: String, saveCardDefault: Bool, showSaveCard: Bool, showAlerts: Bool, token: String? = nil, maskedPanNumber: String? = nil, buttonsColor: UIColor? = nil, isEnglish: Bool = true, backgroundColor: UIColor? = nil, navBarColor: UIColor? = nil, navBarTextColor: UIColor? = nil, textFieldBackgroundColor: UIColor? = nil, textFieldTextColor: UIColor? = nil, titleLabelTextColor: UIColor? = nil, inputLabelTextColor: UIColor? = nil, buttonText: String = "", cardNameLabelText: String = "", cardNumberLabelText: String = "", expirationLabelText: String = "", cvvLabelText: String = "") {

        do {
            try accept.presentPayVC(vC: vC,
                                    billingData: billingData,
                                    paymentKey: paymentKey,
                                    saveCardDefault: saveCardDefault,
                                    showSaveCard: showSaveCard,
                                    showAlerts: showAlerts,
                                    token: token,
                                    maskedPanNumber: maskedPanNumber,
                                    buttonsColor: buttonsColor,
                                    isEnglish: isEnglish,
                                    backgroundColor: backgroundColor,
                                    navBarColor: navBarColor,
                                    navBarTextColor: navBarTextColor,
                                    textFieldBackgroundColor: textFieldBackgroundColor,
                                    textFieldTextColor: textFieldTextColor,
                                    titleLabelTextColor: titleLabelTextColor,
                                    inputLabelTextColor: inputLabelTextColor)
        } catch let error as NSError {
                print("\(error.userInfo)")
        }

    }


    func saveCardResponseToDictionary(savedCardData: SaveCardResponse) -> [String : Any] {
        return [
            "card_subtype" : savedCardData.card_subtype,
            "id" : savedCardData.id,
            "token" : savedCardData.token,
            "created_at" : savedCardData.created_at,
            "masked_pan" : savedCardData.masked_pan,
            "merchant_id" : savedCardData.merchant_id,
            "order_id" : savedCardData.order_id as Any,
            "email" : savedCardData.email as Any
        ] as [String : Any]
    }

    func payResponseToDictionary(payData: PayResponse) -> [String : Any] {

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
}
