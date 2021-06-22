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
import com.facebook.react.bridge.Promise;

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
    this.reactContext = reactContext;
  }


  @ReactMethod
  public void present(ReadableMap params, Promise promise) {
     try {
        Activity currentActivity = getCurrentActivity();
        Intent pay_intent = new Intent(currentActivity, PayActivity.class);

        pay_intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, "ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6VXhNaUo5LmV5SjFjMlZ5WDJsa0lqb3pOVFUxTENKdmNtUmxjbDlwWkNJNk5qRXpNams0TUN3aVkzVnljbVZ1WTNraU9pSkZSMUFpTENKcGJuUmxaM0poZEdsdmJsOXBaQ0k2TkRnMU9Dd2liRzlqYTE5dmNtUmxjbDkzYUdWdVgzQmhhV1FpT21aaGJITmxMQ0ppYVd4c2FXNW5YMlJoZEdFaU9uc2labWx5YzNSZmJtRnRaU0k2SWtOc2FXWm1iM0prSWl3aWJHRnpkRjl1WVcxbElqb2lUbWxqYjJ4aGN5SXNJbk4wY21WbGRDSTZJa1YwYUdGdUlFeGhibVFpTENKaWRXbHNaR2x1WnlJNklqZ3dNamdpTENKbWJHOXZjaUk2SWpReUlpd2lZWEJoY25SdFpXNTBJam9pT0RBeklpd2lZMmwwZVNJNklrcGhjMnR2YkhOcmFXSjFjbWRvSWl3aWMzUmhkR1VpT2lKVmRHRm9JaXdpWTI5MWJuUnllU0k2SWtOU0lpd2laVzFoYVd3aU9pSmpiR0YxWkdWMGRHVXdPVUJsZUdFdVkyOXRJaXdpY0dodmJtVmZiblZ0WW1WeUlqb2lLemcyS0RncE9URXpOVEl4TURRNE55SXNJbkJ2YzNSaGJGOWpiMlJsSWpvaU1ERTRPVGdpTENKbGVIUnlZVjlrWlhOamNtbHdkR2x2YmlJNklrNUJJbjBzSW1WNGNDSTZNell3TURBd01EQXdNREF3TVRZd01ESTJNakF3T1N3aVlXMXZkVzUwWDJObGJuUnpJam8xTURBd01EQXNJbkJ0YTE5cGNDSTZJalF4TGpJek5pNHhOREF1TWpFMEluMC5yTXdsRXhiWF8xbFZsVHBIanhwaFNscDlLd3V1YzVnRVBfRy1xTFgtdHRnWW83SWJQNXpsTDFfS2RlYkxUeGlXMHl6WEM2Z29LbnVoQVAxMjV5RWExUQ==");
        pay_intent.putExtra(PayActivityIntentKeys.THREE_D_SECURE_ACTIVITY_TITLE, "Verification");
        pay_intent.putExtra("language","ar");
        pay_intent.putExtra(PayActivityIntentKeys.TOKEN, "6088c38c19705a495f1727561d4f4814b2ed7e45e9cd80c72f233253");
        pay_intent.putExtra(PayActivityIntentKeys.MASKED_PAN_NUMBER, "xxxx-xxxx-xxxx-1234");
        pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false);
        pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, false);
        pay_intent.putExtra("ActionBar", true);

        pay_intent.putExtra( PayActivityIntentKeys.FIRST_NAME, "Cliffo");
        pay_intent.putExtra(PayActivityIntentKeys.LAST_NAME, "Nicol");
        pay_intent.putExtra(PayActivityIntentKeys.BUILDING,"8028");
        pay_intent.putExtra(PayActivityIntentKeys.FLOOR, "42");
        pay_intent.putExtra(PayActivityIntentKeys.APARTMENT, "803");
        pay_intent.putExtra(PayActivityIntentKeys.CITY, "Jask");
        pay_intent.putExtra(PayActivityIntentKeys.STATE, "Uta");
        pay_intent.putExtra(PayActivityIntentKeys.COUNTRY,"CR" );
        pay_intent.putExtra(PayActivityIntentKeys.EMAIL, "claudette09@exa.com");
        pay_intent.putExtra(PayActivityIntentKeys.PHONE_NUMBER, "+86(8)9135210487");
        pay_intent.putExtra(PayActivityIntentKeys.POSTAL_CODE,  "01898");

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