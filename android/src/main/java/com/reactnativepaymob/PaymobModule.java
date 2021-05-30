package com.reactnativepaymob;

import androidx.annotation.NonNull;
import android.content.Intent;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import android.app.Activity;

 import com.paymob.acceptsdk.PayActivity;
 import com.paymob.acceptsdk.PayActivityIntentKeys;
 import com.paymob.acceptsdk.PayResponseKeys;
 import com.paymob.acceptsdk.SaveCardResponseKeys;
 import com.paymob.acceptsdk.ToastMaker;

@ReactModule(name = PaymobModule.NAME)
public class PaymobModule extends ReactContextBaseJavaModule {
    public static final String NAME = "Paymob";
    static final int ACCEPT_PAYMENT_REQUEST = 10;


    public PaymobModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod
    public void payWithNoToken(ReadableMap params, Callback successCallback, Callback errorCallback) {
      Activity currentActivity = getCurrentActivity();
      mParams = params;

      if (currentActivity == null) {
        mErrorCallback.invoke(E_ACTIVITY_DOES_NOT_EXIST);
        return;
      }
      HashMap paramsMap = params.toHashMap();

      Intent pay_intent = new Intent(currentActivity, PayActivity.class);
      Boolean showSaveCard = (Boolean) paramsMap.get("showSaveCard");
      Boolean saveCardDefault = (Boolean) datadata.get("saveCardDefault");
      Boolean isEnglish = (Boolean) datadata.get("isEnglish");
      Boolean hideActionBar = (Boolean) datadata.get("hideActionBar");
      String language = "en";

      if(isEnglish == false){
        language = "ar";
      }

      putNormalExtras(pay_intent, paramsMap);
      pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, saveCardDefault);
      pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, showSaveCard);
      pay_intent.putExtra(PayActivityIntentKeys.THEME_COLOR,0x80970073);
      Intent secure_intent = new Intent(currentActivity, ThreeDSecureWebViewActivty.class);
      secure_intent.putExtra("ActionBar",hideActionBar);
      pay_intent.putExtra("language",language);
      currentActivity.startActivityForResult(pay_intent, ACCEPT_PAYMENT_REQUEST);
    }

    private void putNormalExtras(Intent intent, HashMap data) {
      String firstName = (String) data.get("firstName");
      if(firstName != null){
        intent.putExtra(PayActivityIntentKeys.FIRST_NAME, firstName);
      }
      String lastName = (String) data.get("lastName");
      if(lastName != null){
        intent.putExtra(PayActivityIntentKeys.LAST_NAME, lastName);
      }
      String building = (String) data.get("building");
      if(building != null){
        intent.putExtra(PayActivityIntentKeys.BUILDING, building);
      }
      String floor = (String) data.get("floor");
      if(floor != null){
        intent.putExtra(PayActivityIntentKeys.FLOOR, floor);
      }
      String apartment = (String) data.get("apartment");
      if(apartment != null){
        intent.putExtra(PayActivityIntentKeys.APARTMENT, apartment);
      }
      String city = (String) data.get("city");
      if(city != null){
        intent.putExtra(PayActivityIntentKeys.CITY, city);
      }
      String state = (String) data.get("state");
      if(state != null){
        intent.putExtra(PayActivityIntentKeys.STATE, state);
      }
      String country = (String) data.get("country");
      if(country != null){
        intent.putExtra(PayActivityIntentKeys.COUNTRY, country);
      }
      String email = (String) data.get("email");
      if(email != null){
        intent.putExtra(PayActivityIntentKeys.EMAIL, email);
      }
      String phoneNumber = (String) data.get("phoneNumber");
      if(phoneNumber != null){
        intent.putExtra(PayActivityIntentKeys.PHONE_NUMBER, phoneNumber);
      }
      String postalCode = (String) data.get("postalCode");
      if(postalCode != null){
        intent.putExtra(PayActivityIntentKeys.POSTAL_CODE, postalCode);
      }
      String paymentKey = (String) data.get("paymentKey");
      if(paymentKey != null){
        intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, paymentKey);
      }
      String activityTitle = (String) data.get("activityTitle");
      if(activityTitle!=null){
        intent.putExtra(PayActivityIntentKeys.THREE_D_SECURE_ACTIVITY_TITLE, activityTitle);
      }
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }
}
