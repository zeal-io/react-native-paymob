import { NativeModules } from 'react-native';

type PaymobType = {
  multiply(a: number, b: number): Promise<number>;
};

const { Paymob } = NativeModules;

export default Paymob as PaymobType;
