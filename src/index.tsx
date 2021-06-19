import { NativeModules, NativeEventEmitter } from 'react-native';

interface PaymobT {
  payWithNoToken: (
    data: any,
    successCallback: (data: any) => void,
    errorCallback: (error: any) => void
  ) => void;
  presentPayVC: () => void;
}

const { Paymob } = NativeModules;
export default Paymob as PaymobT;

new NativeEventEmitter(Paymob).addListener('didDismiss', (...stuff) => {
  console.log(stuff);
});
