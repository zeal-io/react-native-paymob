package com.reactnativepaymob;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Promise;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import com.paymob.acceptsdk.IntentConstants;
import com.paymob.acceptsdk.PayActivity;
import com.paymob.acceptsdk.PayActivityIntentKeys;
import com.paymob.acceptsdk.PayResponseKeys;
import com.paymob.acceptsdk.SaveCardResponseKeys;


import java.util.HashMap;

public class PaymobModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  int REQUEST_CODE = 30767;

  public PaymobModule(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addActivityEventListener(mActivityEventListener);
    this.reactContext = reactContext;
  }

  private void sendEvent(ReactContext reactContext, String eventName, WritableMap params) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(eventName, params);
  }


  private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
      super.onActivityResult(activity, requestCode, resultCode, data);


      if (requestCode == REQUEST_CODE) {
        Bundle extras = data.getExtras();
        WritableMap params = Arguments.createMap();
        if (resultCode == IntentConstants.USER_CANCELED) {
          // User canceled and did no payment request was fired
          // ToastMaker.displayShortToast(this, "User canceled!!");
          params.putString("type", "userDidCancel");
          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.MISSING_ARGUMENT) {
          // You forgot to pass an important key-value pair in the intent's extras
          // ToastMaker.displayShortToast(this, "Missing Argument == " + extras.getString(IntentConstants.MISSING_ARGUMENT_VALUE));
        } else if (resultCode == IntentConstants.TRANSACTION_ERROR) {
          params.putString("type", "paymentAttemptFailed");
          params.putString("detailedDescription", extras.getString(IntentConstants.TRANSACTION_ERROR_REASON));
          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.TRANSACTION_REJECTED) {
          // User attempted to pay but their transaction was rejected


          params.putString("type", "transactionRejected");
          sendEvent(reactContext, "didDismiss", params);
          // Use the static keys declared in PayResponseKeys to extract the fields you want
          // ToastMaker.displayShortToast(this, extras.getString(PayResponseKeys.DATA_MESSAGE));
        } else if (resultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
          // User attempted to pay but their transaction was rejected. An error occured while reading the returned JSON
          // ToastMaker.displayShortToast(this, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
        } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
          // User finished their payment successfully

          // Use the static keys declared in PayResponseKeys to extract the fields you want
          // ToastMaker.displayShortToast(this, extras.getString(PayResponseKeys.DATA_MESSAGE));

          params.putString("type", "transactionAccepted");
          params.putString("token", extras.getString(SaveCardResponseKeys.TOKEN));
          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
          // User finished their payment successfully. An error occured while reading the returned JSON.
          // ToastMaker.displayShortToast(this, "TRANSACTION_SUCCESSFUL - Parsing Issue");

          // ToastMaker.displayShortToast(this, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
        } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
          //

          params.putString("type", "transactionAccepted");

          WritableMap payData = Arguments.createMap();

          payData.putInt("amount_cents", extras.getInt(PayResponseKeys.AMOUNT_CENTS));
          payData.putBoolean("is_refunded", extras.getBoolean(PayResponseKeys.IS_REFUNDED));
          payData.putInt("captured_amount", extras.getInt(PayResponseKeys.CAPTURED_AMOUNT));

          payData.putString("source_data_type", extras.getString(PayResponseKeys.SOURCE_DATA_SUB_TYPE));
          payData.putString("currency", extras.getString(PayResponseKeys.CURRENCY));

          payData.putBoolean("is_void", extras.getBoolean(PayResponseKeys.IS_VOID));
          payData.putBoolean("pending",  extras.getBoolean(PayResponseKeys.PENDING));
          payData.putBoolean("is_3d_secure", extras.getBoolean(PayResponseKeys.IS_3D_SECURE));
          payData.putBoolean("is_auth", extras.getBoolean(PayResponseKeys.IS_AUTH));
          payData.putBoolean("is_refund", extras.getBoolean(PayResponseKeys.IS_REFUND));
          payData.putBoolean("is_voided", extras.getBoolean(PayResponseKeys.IS_VOIDED));
          payData.putBoolean("success", extras.getBoolean(PayResponseKeys.SUCCESS));
          payData.putBoolean("error_occured", extras.getBoolean(PayResponseKeys.ERROR_OCCURED));
          payData.putBoolean("is_standalone_payment", extras.getBoolean(PayResponseKeys.IS_STANDALONE_PAYMENT));

          payData.putInt("id", extras.getInt(PayResponseKeys.AMOUNT_CENTS));
          payData.putInt("owner", extras.getInt(PayResponseKeys.AMOUNT_CENTS));
          payData.putInt("profile_id", extras.getInt(PayResponseKeys.AMOUNT_CENTS));
          payData.putInt("refunded_amount_cents", extras.getInt(PayResponseKeys.AMOUNT_CENTS));
          payData.putInt("integration_id", extras.getInt(PayResponseKeys.AMOUNT_CENTS));
          payData.putInt("order", extras.getInt(PayResponseKeys.AMOUNT_CENTS));


          payData.putString("source_data_pan", "");
          payData.putString("created_at", "");
          payData.putString("source_data_sub_type", "");
          payData.putString("dataMessage", "");


          WritableMap savedCardData = Arguments.createMap();
          savedCardData.putString("token", extras.getString(SaveCardResponseKeys.TOKEN));
          savedCardData.putString("card_subtype", extras.getString(SaveCardResponseKeys.CARD_SUBTYPE));
          savedCardData.putString("id", extras.getString(SaveCardResponseKeys.ID));
          savedCardData.putString("masked_pan", extras.getString(SaveCardResponseKeys.MASKED_PAN));
          savedCardData.putString("merchant_id", extras.getString(SaveCardResponseKeys.MERCHANT_ID));

          params.putMap("payData", payData);
          params.putMap("savedCardData", savedCardData);

          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
          // User finished their payment successfully and card was saved.

          // Use the static keys declared in PayResponseKeys to extract the fields you want
          // Use the static keys declared in SaveCardResponseKeys to extract the fields you want
          // ToastMaker.displayShortToast(this, "Token == " + extras.getString(SaveCardResponseKeys.TOKEN));
        } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION) {
          // ToastMaker.displayShortToast(this, "User canceled 3-d scure verification!!");

          // Note that a payment process was attempted. You can extract the original returned values
          // Use the static keys declared in PayResponseKeys to extract the fields you want
          // ToastMaker.displayShortToast(this, extras.getString(PayResponseKeys.PENDING));
        } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE) {
          // ToastMaker.displayShortToast(this, "User canceled 3-d scure verification - Parsing Issue!!");

          // Note that a payment process was attempted.
          // User finished their payment successfully. An error occured while reading the returned JSON.
          // ToastMaker.displayShortToast(this, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
        }
      }
    }
  };

  @ReactMethod
  public void presentPayVC(ReadableMap params, Promise promise) {
    try {
      Activity currentActivity = getCurrentActivity();
      Intent pay_intent = new Intent(currentActivity, PayActivity.class);

      pay_intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, params.getString("paymentKey"));
      pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, params.getBoolean("saveCardDefault"));
      pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, params.getBoolean("showSaveCard"));

      pay_intent.putExtra("language", params.getBoolean("isEnglish") ? "en" : "ar");
      pay_intent.putExtra("ActionBar", false);

      ReadableMap billingData = params.getMap("billingData");
      pay_intent.putExtra( PayActivityIntentKeys.FIRST_NAME, billingData.getString("first_name"));
      pay_intent.putExtra(PayActivityIntentKeys.LAST_NAME, billingData.getString("last_name"));
      pay_intent.putExtra(PayActivityIntentKeys.BUILDING, billingData.getString("building"));
      pay_intent.putExtra(PayActivityIntentKeys.FLOOR, billingData.getString("floor"));
      pay_intent.putExtra(PayActivityIntentKeys.APARTMENT, billingData.getString("apartment"));
      pay_intent.putExtra(PayActivityIntentKeys.CITY, billingData.getString("city"));
      pay_intent.putExtra(PayActivityIntentKeys.STATE, billingData.getString("state"));
      pay_intent.putExtra(PayActivityIntentKeys.COUNTRY, billingData.getString("country"));
      pay_intent.putExtra(PayActivityIntentKeys.EMAIL, billingData.getString("email"));
      pay_intent.putExtra(PayActivityIntentKeys.PHONE_NUMBER, billingData.getString("phone_number"));
      pay_intent.putExtra(PayActivityIntentKeys.POSTAL_CODE, billingData.getString("postal_code") );

      currentActivity.startActivityForResult(pay_intent, REQUEST_CODE);
      promise.resolve(12345);
    } catch(Exception e) {
      promise.reject("Create Event Error", e);
    }
  }

  @Override
  public String getName() {
    return "Paymob";
  }
}
