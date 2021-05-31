import * as React from 'react';
import { Alert, TouchableOpacity, View } from 'react-native';
import paymob from 'react-native-paymob';

export default () => {
  return (
    <TouchableOpacity
      style={{ flex: 1 }}
      onPress={() => {
        paymob.payWithNoToken(
          {
            token: '12345',
            maskedPanNumber: 'XXXXXXXXXXXXXX1234',
            firstName: 'first_name',
            lastName: 'last_name',
            building: 'building',
            floor: 'floor',
            apartment: 'apartment',
            city: 'city',
            state: 'state',
            country: 'country',
            email: 'email',
            phoneNumber: 'phoneNumber',
            postalCode: 'postalCode',
          },
          (...success) => {
            Alert.alert(JSON.stringify(success));
          },
          (...error) => {
            Alert.alert(JSON.stringify(error));
          },
        );
      }}>
      {/*  */}
      {/*  */}
    </TouchableOpacity>
  );
};
