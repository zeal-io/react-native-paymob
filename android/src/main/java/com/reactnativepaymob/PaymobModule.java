package com.reactnativepaymob;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;


import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
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
import com.paymob.acceptsdk.ThreeDSecureWebViewActivty;
import com.paymob.acceptsdk.ToastMaker;


import java.util.HashMap;

public class PaymobModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

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
        WritableMap params = Arguments.createMap();
        params.putString("eventProperty", "someValue");
        System.out.println("didDismiss");
        sendEvent(reactContext, "didDismiss", params);
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
        pay_intent.putExtra("ActionBar", true);

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

        currentActivity.startActivityForResult(pay_intent, 10);
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