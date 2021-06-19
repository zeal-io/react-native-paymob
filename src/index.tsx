import { NativeEventEmitter, NativeModules } from 'react-native';
import type { PaymobT, PayResponse, SaveCardResponse } from './types';
const { Paymob: NativePaymob } = NativeModules;

type Data =
  | { type: 'userDidCancel' }
  | { type: 'userDidCancel3dSecurePayment'; data: PayResponse }
  | { type: 'transactionAccepted'; data: SaveCardResponse }
  | { type: 'transactionAccepted'; data: PayResponse }
  | { type: 'transactionRejected'; data: PayResponse }
  | { type: 'paymentAttemptFailed'; data: string };

export const paymobEventEmitter = new NativeEventEmitter(NativePaymob);

export function onDismiss(handler: (data: Data) => void) {
  return paymobEventEmitter.addListener('onDismiss', handler);
}

export function removeOnDismiss(handler: (data: Data) => void) {
  return paymobEventEmitter.removeListener('onDismiss', handler);
}

export const Paymob: PaymobT = NativePaymob;
