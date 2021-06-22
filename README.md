# react-native-paymob

react native paymob sdk

## Installation

```sh
yarn add https://github.com/a-eid/react-native-paymob#eventEmitter
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

Paymob.presentPayVC(...)
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
