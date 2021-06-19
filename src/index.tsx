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

type Handler = (data: DidDismissData) => void;

export function onDismiss(handler: Handler) {
  return paymobEventEmitter.addListener('didDismiss', handler);
}

export function removeOnDismiss(handler: Handler) {
  return paymobEventEmitter.removeListener('didDismiss', handler);
}

export const Paymob: PaymobT = NativePaymob;
