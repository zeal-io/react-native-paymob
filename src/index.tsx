import { NativeModules } from 'react-native';

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
