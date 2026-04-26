package com.reactnativepaymob;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Color;
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

import androidx.core.content.ContextCompat;
import android.content.res.Resources;

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

        WritableMap savedCardData = null;

        WritableMap payData = Arguments.createMap();

          if (extras != null) {
            for (String key : extras.keySet()) {
              Object value = extras.get(key);
                if (value instanceof Float || value instanceof Double) {
                    payData.putDouble(key, extras.getDouble(key));
                } else if (value instanceof Number) {
                    payData.putInt(key, extras.getInt(key));
                } else if (value instanceof String) {
                    payData.putString(key, extras.getString(key));
                } else if (value instanceof Boolean) {
                    payData.putBoolean(key, extras.getBoolean(key));
                }

                if(payData.hasKey("token") && savedCardData == null){
                    savedCardData = Arguments.createMap();
                    savedCardData.putString("id",extras.getString(SaveCardResponseKeys.ID));
                    savedCardData.putString("token", extras.getString(SaveCardResponseKeys.TOKEN));
                    savedCardData.putString("card_subtype", extras.getString(SaveCardResponseKeys.CARD_SUBTYPE));
                    savedCardData.putString("masked_pan", extras.getString(SaveCardResponseKeys.MASKED_PAN));
                    savedCardData.putString("merchant_id", extras.getString(SaveCardResponseKeys.MERCHANT_ID));
                }
            }
          }

        if (resultCode == IntentConstants.USER_CANCELED) {
          params.putString("type", "userDidCancel");
          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.MISSING_ARGUMENT) {
          params.putString("type", "missingArgument");
          params.putString("missingKey", extras.getString(IntentConstants.MISSING_ARGUMENT_VALUE));
          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.TRANSACTION_ERROR) {
          params.putString("type", "paymentAttemptFailed");
          params.putString("detailedDescription", extras.getString(IntentConstants.TRANSACTION_ERROR_REASON));
          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.TRANSACTION_REJECTED) {
          params.putString("type", "transactionRejected");
          params.putMap("payData", payData);
          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
          params.putString("type", "transactionRejected");
          params.putMap("payData", payData);
          params.putString("rawResponse", extras.getString(IntentConstants.RAW_PAY_RESPONSE));
          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
          params.putString("type", "transactionAccepted");
          params.putMap("payData", payData);
          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
          params.putString("type", "transactionAccepted");
          params.putMap("payData", payData);
          params.putString("rawResponse", extras.getString(IntentConstants.RAW_PAY_RESPONSE));
          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
          params.putString("type", "transactionAcceptedWithCard");
          params.putMap("payData", payData);
          if (savedCardData != null) {
            params.putMap("savedCardData", savedCardData);
          }
          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION) {
          params.putString("type", "userDidCancel3dSecureVerification");
          String pendingJson = extras.getString(PayResponseKeys.PENDING);
          if (pendingJson != null && !pendingJson.isEmpty()) {
            params.putString("pendingPayData", pendingJson);
          }
          sendEvent(reactContext, "didDismiss", params);
        } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE) {
          params.putString("type", "userDidCancel3dSecureVerification");
          params.putString("rawResponse", extras.getString(IntentConstants.RAW_PAY_RESPONSE));
          sendEvent(reactContext, "didDismiss", params);
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
      if(params.getString("buttonText") != null) {
        pay_intent.putExtra("PAY_BUTTON_TEXT", params.getString("buttonText"));
      }

    try {
        int colorValue = ContextCompat.getColor(currentActivity, com.google.android.material.R.color.cardview_dark_background);
        pay_intent.putExtra(PayActivityIntentKeys.THEME_COLOR, colorValue);
      } catch (Resources.NotFoundException e) {
      }

      if(params.getString("buttonBg") != null) {
        int colorValue = Color.parseColor(params.getString("buttonBg"));
        pay_intent.putExtra(PayActivityIntentKeys.THEME_COLOR, colorValue);
      }

      pay_intent.putExtra("language", params.getBoolean("isEnglish") ? "en" : "ar");
      pay_intent.putExtra("ActionBar", false);

      ReadableMap billingData = params.getMap("billingData");
      pay_intent.putExtra(PayActivityIntentKeys.FIRST_NAME, billingData.getString("first_name"));
      pay_intent.putExtra(PayActivityIntentKeys.LAST_NAME, billingData.getString("last_name"));
      pay_intent.putExtra(PayActivityIntentKeys.BUILDING, billingData.getString("building"));
      pay_intent.putExtra(PayActivityIntentKeys.FLOOR, billingData.getString("floor"));
      pay_intent.putExtra(PayActivityIntentKeys.APARTMENT, billingData.getString("apartment"));
      pay_intent.putExtra(PayActivityIntentKeys.CITY, billingData.getString("city"));
      pay_intent.putExtra(PayActivityIntentKeys.STATE, billingData.getString("state"));
      pay_intent.putExtra(PayActivityIntentKeys.COUNTRY, billingData.getString("country"));
      pay_intent.putExtra(PayActivityIntentKeys.EMAIL, billingData.getString("email"));
      pay_intent.putExtra(PayActivityIntentKeys.PHONE_NUMBER, billingData.getString("phone_number"));
      pay_intent.putExtra(PayActivityIntentKeys.POSTAL_CODE, billingData.getString("postal_code"));

      if(params.getString("cardToken") != null && !params.getString("cardToken").isEmpty()){
        pay_intent.putExtra(PayActivityIntentKeys.TOKEN, params.getString("cardToken"));
      }

      if(params.getString("maskedCardNumber") != null && !params.getString("maskedCardNumber").isEmpty()){
        pay_intent.putExtra(PayActivityIntentKeys.MASKED_PAN_NUMBER, params.getString("maskedCardNumber"));
      }

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
