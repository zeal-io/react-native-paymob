package com.reactnativepaymob;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;


import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReadableMap;

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

  static final int ACCEPT_PAYMENT_REQUEST = 10;

  private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
  private Callback mSuccessCallback;
  private Callback mErrorCallback;
  private ReadableMap mParams;



  private void startPayActivityNoToken(Activity currentActivity, HashMap data) {
    Intent pay_intent = new Intent(currentActivity, PayActivity.class);
    Boolean showSaveCard = (Boolean) data.get("showSaveCard");
    Boolean saveCardDefault = (Boolean) data.get("saveCardDefault");
    Boolean isEnglish = (Boolean) data.get("isEnglish");
    Boolean hideActionBar = (Boolean) data.get("hideActionBar");
    String language = "en";
    if(isEnglish == false){
      language = "ar";
    }
    putNormalExtras(pay_intent, data);
    // Boolean to save the entered card details by default
    pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, saveCardDefault);
    // Boolean to display the save card checkbox
    pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, showSaveCard);
    pay_intent.putExtra(PayActivityIntentKeys.THEME_COLOR,0x80970073);
    Intent secure_intent = new Intent(currentActivity, ThreeDSecureWebViewActivty.class);
    secure_intent.putExtra("ActionBar",hideActionBar);
    pay_intent.putExtra("language",language);
    // String themeColor = (String) data.get("themeColor");
    // if(themeColor != null){
    // pay_intent.putExtra(PayActivityIntentKeys.THEME_COLOR, Integer.parseInt(themeColor, 16));

    // }

    currentActivity.startActivityForResult(pay_intent, ACCEPT_PAYMENT_REQUEST);
}

private void startPayActivityToken(Activity currentActivity, HashMap data) {
    Intent pay_intent = new Intent(currentActivity, PayActivity.class);

    putNormalExtras(pay_intent, data);
    // replace this with your actual card token
    pay_intent.putExtra(PayActivityIntentKeys.TOKEN, (String) data.get("token"));
    pay_intent.putExtra(PayActivityIntentKeys.MASKED_PAN_NUMBER, (String) data.get("maskedPanNumber"));
    currentActivity.startActivityForResult(pay_intent, ACCEPT_PAYMENT_REQUEST);
}

private void putNormalExtras(Intent intent, HashMap data) {
    // Pass the correct values for the billing data keys
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

private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
      // super.onActivityResult(requestCode, resultCode, data);
      super.onActivityResult(activity, requestCode, resultCode, data);
      try{
        Bundle extras = data.getExtras();
        boolean successStatus = false;
        String token = "";
        // Activity activity = getCurrentActivity();

        // mErrorCallback.invoke(E_ACTIVITY_DOES_NOT_EXIST);
        if (activity == null) {
          mErrorCallback.invoke(E_ACTIVITY_DOES_NOT_EXIST);
          return;
        }
        // mSuccessCallback.invoke(requestCode);
        if (requestCode == ACCEPT_PAYMENT_REQUEST) {
          String responseMessage = "";
          if (resultCode == IntentConstants.USER_CANCELED) {
            // User canceled and did no payment request was fired
            responseMessage = "User canceled!!";
          } else if (resultCode == IntentConstants.MISSING_ARGUMENT) {
            // You forgot to pass an important key-value pair in the intent's extras
            responseMessage = "Missing Argument == " + extras.getString(IntentConstants.MISSING_ARGUMENT_VALUE);
          } else if (resultCode == IntentConstants.TRANSACTION_ERROR) {
            // An error occurred while handling an API's response
            responseMessage = "Reason == " + extras.getString(IntentConstants.TRANSACTION_ERROR_REASON);
          } else if (resultCode == IntentConstants.TRANSACTION_REJECTED) {
            // User attempted to pay but their transaction was rejected
            // Use the static keys declared in PayResponseKeys to extract the fields you want
            responseMessage = extras.getString(PayResponseKeys.DATA_MESSAGE);
          } else if (resultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
            // User attempted to pay but their transaction was rejected. An error occured while reading the returned JSON
            responseMessage = extras.getString(IntentConstants.RAW_PAY_RESPONSE);
          } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
            // User finished their payment successfully
            // Use the static keys declared in PayResponseKeys to extract the fields you want
            responseMessage = extras.getString(PayResponseKeys.DATA_MESSAGE);
            successStatus = true;
          } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
            // User finished their payment successfully. An error occured while reading the returned JSON.
            responseMessage = "TRANSACTION_SUCCESSFUL - Parsing Issue";
            successStatus = true;
            // responseMessage = extras.getString(IntentConstants.RAW_PAY_RESPONSE);
          } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
          // User finished their payment successfully and card was saved.
          // Use the static keys declared in PayResponseKeys to extract the fields you want
          // Use the static keys declared in SaveCardResponseKeys to extract the fields you want
            responseMessage = "Token == " + extras.getString(SaveCardResponseKeys.TOKEN);
            successStatus = true;
            token = extras.getString(SaveCardResponseKeys.TOKEN);
          } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION) {
            responseMessage = "User canceled 3-d secure verification!!";
            // Note that a payment process was attempted. You can extract the original returned values
            // Use the static keys declared in PayResponseKeys to extract the fields you want
            responseMessage = extras.getString(PayResponseKeys.PENDING);
          } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE) {
            responseMessage = "User canceled 3-d scure verification - Parsing Issue!!";
            // Note that a payment process was attempted.
            // User finished their payment successfully. An error occured while reading the returned JSON.
            responseMessage = extras.getString(IntentConstants.RAW_PAY_RESPONSE);
          }
          // ToastMaker.displayShortToast(activity, responseMessage);
          // HashMap resultMap  = new HashMap();
          // resultMap.put("code", resultCode);
          // resultMap.put("message", responseMessage);
          mSuccessCallback.invoke(successStatus, resultCode, responseMessage, token);
        }

      }catch (Exception e){
        mErrorCallback.invoke(e);
      }
  }
};

  public PaymobModule(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addActivityEventListener(mActivityEventListener);
    this.reactContext = reactContext;
  }


  //********************* React Methods ******************************/

  @ReactMethod
  public void payWithToken(ReadableMap params, Callback successCallback, Callback errorCallback) {
    Activity currentActivity = getCurrentActivity();
    mSuccessCallback = successCallback;
    mErrorCallback = errorCallback;
    mParams = params;

    if (currentActivity == null) {
      mErrorCallback.invoke(E_ACTIVITY_DOES_NOT_EXIST);
      return;
    }

    HashMap paramsMap = params.toHashMap();
    try{
      this.startPayActivityToken(currentActivity, paramsMap);
    } catch(Exception e){
      mErrorCallback.invoke(e);
    }
  }

  @ReactMethod
  public void payWithNoToken(ReadableMap params, Callback successCallback, Callback errorCallback) {
    Activity currentActivity = getCurrentActivity();
    mSuccessCallback = successCallback;
    mErrorCallback = errorCallback;
    mParams = params;

    if (currentActivity == null) {
      mErrorCallback.invoke(E_ACTIVITY_DOES_NOT_EXIST);
      return;
    }
    HashMap paramsMap = params.toHashMap();
    this.startPayActivityNoToken(currentActivity, paramsMap);
  }

  @Override
  public String getName() {
    return "Paymob";
  }
}