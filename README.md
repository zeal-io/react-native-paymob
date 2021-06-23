# react-native-paymob

react native paymob sdk

## Installation

```sh
yarn add https://github.com/a-eid/react-native-paymob
```

###  IOS
IOS installation is almost automatic, the one thing you need to do is to create an empty `Swift` file in your project.

![Screen Shot 2021-06-23 at 4 55 15 PM](https://user-images.githubusercontent.com/19273413/123120137-5e4e8180-d444-11eb-9c80-6446dba180e4.png)

![Screen Shot 2021-06-23 at 4 55 27 PM](https://user-images.githubusercontent.com/19273413/123120152-61497200-d444-11eb-8145-55677320ebec.png)

file name does not matter ( I think )

![Screen Shot 2021-06-23 at 4 55 34 PM](https://user-images.githubusercontent.com/19273413/123120160-63133580-d444-11eb-8f9f-bd29308cde59.png)

choose create bridging header.

![Screen Shot 2021-06-23 at 4 55 38 PM](https://user-images.githubusercontent.com/19273413/123120214-6ad2da00-d444-11eb-9ca8-83a275e6db08.png)

there should be a swift file and a bridging header in your project.

![Screen Shot 2021-06-23 at 4 56 33 PM](https://user-images.githubusercontent.com/19273413/123120217-6c9c9d80-d444-11eb-8204-87b6921ebb31.png)



### Android 

1- in `AndroidManifest.xml` 
  - add `xmlns:tools="http://schemas.android.com/tools"` to `manifest` tag 
  - make sure to have these 2 attributes in the `application` tag 
      `android:supportsRtl="false"`
      `tools:replace="android:supportsRtl, android:allowBackup"` 
  - make sure to have the following values in `android/app/res/values/colors.xml` ( create the file if you don't have it already )
   ```xml 
     <resources>
        <color name="white">#FFF</color>
        <color name="colorPrimary">#6200EE</color>
        <color name="colorPrimaryDark">#03DAC5</color>
        <color name="colorAccent">#03DAC5</color>
        <color name="ThemeColor"> 	#FF0000 </color>
      </resources>
    ```


## Usage

```js
import { Paymob, useDidDismissPaymob } from 'react-native-paymob';

useDidDismissPaymob(
	// make sure to not use destruction here  to get proper typing.
	// & use React.useCallback
  React.useCallback((data) => {
    console.log(data);
  }, [])
);


// some event handler
   Paymob.presentPayVC({
      billingData: {
        apartment: 'NA',
        email: 'NA',
        floor: 'NA',
        first_namae: 'NA',
        street: 'NA',
        building: 'NA',
        phone_number: 'NA',
        shipping_method: 'NA',
        postal_code: 'NA',
        city: 'NA',
        country: 'NA',
        last_name: 'NA',
        state: 'NA',
      },
      paymentKey: "your payment key",
      saveCardDefault: false,
      showSaveCard: false,
      showAlerts: false,
      isEnglish: true,
      showScanCardButton: false,
    });
```

## Note 

the library was created to cover our use case only, but we are open to adding more features if we have the time.


## Contributing

All Pull Requests are welcome See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
