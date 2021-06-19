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

const paymobEventEmitter = new NativeEventEmitter(NativePaymob);

type OnEvent = (name: 'onDismiss', data: Data) => void;
type RemoveEvent = (name: 'onDismiss') => void;

// @ts-ignore
export const onEvent: OnEvent = paymobEventEmitter.addListener;
// @ts-ignore
export const removeEvent: RemoveEvent = paymobEventEmitter.removeListener;

export const Paymob = NativePaymob as PaymobT;
