import * as React from 'react';
import { NativeEventEmitter, NativeModules } from 'react-native';
import type { PaymobT, PayResponse, SaveCardResponse } from './types';
const { Paymob: NativePaymob } = NativeModules;

export type DidDismissData =
  | { type: 'userDidCancel' }
  | { type: 'userDidCancel3dSecurePayment'; data: PayResponse }
  | { type: 'transactionAccepted'; data: SaveCardResponse }
  | { type: 'transactionAccepted'; data: PayResponse }
  | { type: 'transactionRejected'; data: PayResponse }
  | { type: 'paymentAttemptFailed'; data: string };

export const paymobEventEmitter = new NativeEventEmitter(NativePaymob);

type Handler = (data: DidDismissData) => Promise<void>;

export function useDidDismissPaymob(cb: Handler) {
  React.useEffect(() => {
    const subscription = paymobEventEmitter.addListener('didDismiss', cb);
    return () => subscription.remove();
  }, [cb]);
}

export const Paymob: PaymobT = NativePaymob;
