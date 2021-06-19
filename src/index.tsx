import * as React from 'react';
import { NativeEventEmitter, NativeModules } from 'react-native';
import type { PaymobT, PayResponse, SaveCardResponse } from './types';
const { Paymob: NativePaymob } = NativeModules;

export type DidDismissData =
  | { type: 'userDidCancel' }
  | { type: 'userDidCancel3dSecurePayment'; pendingPayData: PayResponse }
  | {
      type: 'transactionAccepted';
      payData: PayResponse;
      savedCardData: SaveCardResponse;
    }
  | { type: 'paymentAttemptFailed'; detailedDescription: PayResponse }
  | { type: 'transactionRejected'; payData: PayResponse }
  | { type: 'paymentAttemptFailed'; detailedDescription: string };

export const paymobEventEmitter = new NativeEventEmitter(NativePaymob);

type Handler = (data: DidDismissData) => Promise<void>;

export function useDidDismissPaymob(cb: Handler) {
  React.useEffect(() => {
    const subscription = paymobEventEmitter.addListener('didDismiss', cb);
    return () => subscription.remove();
  }, [cb]);
}

export const Paymob: PaymobT = NativePaymob;
