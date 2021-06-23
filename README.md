# react-native-paymob

react native paymob sdk

## Installation

```sh
yarn add https://github.com/a-eid/react-native-paymob#eventEmitter
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
