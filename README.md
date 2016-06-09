# react-native-portavailable [![npm version](https://badge.fury.io/js/react-native-portavailable.svg)](https://badge.fury.io/js/react-native-portavailable)
> A React Native Package for checking tcp and udp port availability on native android.

This is an [implementation](http://svn.apache.org/viewvc/camel/trunk/components/camel-test/src/main/java/org/apache/camel/test/AvailablePortFinder.java?view=markup#l130) coming from the Apache [camel](http://camel.apache.org/) project.

###### Support
This package is only supported on **android** devices, since i'm not much of an iOS Developer. The source code is pretty much straightforward so feel free to submit a PR of an iOS port i'll be happy to merge ! :)

### Install
    npm i -S react-native-portavailable

#### Android


 - Add the following line to the bottom of your project's `settings.gradle` file.

    `project(':react-native-portavailable').projectDir = new File(settingsDir, '../node_modules/react-native-portavailable/android')`

 - Change the `include` line of your project's `settings.gradle` to include the `:react-native-portavailable` project.

    `include ':react-native-portavailable', ':app'`

 - Open your app's `build.gradle` file and add the following line to the `dependencies` block.

    `compile project(":react-native-portavailable")`

 - In your app's `MainActivity.java` file, add `new PortAvailableReactModule()` to the return statement of the `getPackages()` function.

```
...
    new MainReactPackage(),
    new PortAvailableReactModule()
...
```

 - Then in the same file add the import statement :
 `import com.odinvt.portavailable.PortAvailableReactModule;`


### Usage

    import PortAvailable from 'react-native-portavailable'

#### API

The calls to `PortAvailable` methods are static so you can go ahead and use it directly.

##### Methods

###### `PortAvailable.check(port)` Checks if the port 'port' is available


 - This will return a `Promise` instance that resolves to a `boolean` of the availability of the port. We're handling sockets opened and closed on the native side so we do need to expect an async execution. There is no need to use a `catch` method on the Promise object since all the exceptions are caught on the native side and the Promise just resolves to false.

Example :

```javascript

PortAvailable.check(48500).then(available => {
  // 'available' is true if the port 48500 is available
})

```

###### `PortAvailable.checkRange(min_port, max_port, stop = 0)` Checks which of the ports between 'min_port' and 'max_port' (included) are available

 - This will return a `Promise` instance that resolves to an `array` of the available ports.
 - The `stop` parameter is optional and tells the function at how much available ports it should stop checking and resolve. `stop = 0` is the default value and it tells the function to just check all the ports until `max_port`.

Example :

```javascript

PortAvailable.checkRange(48500, 48600, 5).then(ports => {
  // 'ports' is an array of the available ports
  // for example : ports = [48500, 48501, 48502, 48503, 48505] if 48504 is not available
})

```

###### `PortAvailable.getPorts()` Returns the ports discovered by the last `checkRange` method call

 - This will return an `array` of the available ports.
 - The preferred way of getting the discovered ports is inside the `PortAvailable.checkRange` `then` callback. This method is provided so you can access the discovered ports anywhere on your application **but only if** you're sure that the `PortAvailable.getPorts()` call is made after the `PortAvailable.checkRange` Promise resolves .

Example :

```javascript

let open_ports = PortAvailable.getPorts();
// open_ports = [48500, 48501, 48502, 48503, 48505]

```

### Babel

This component uses ES6. So if you're using `Webpack` you should launch `babel` on `main.js` and output to `main-tf.js` if for some reason the `npm postinstall` script didn't execute.

    "postinstall": "babel main.js --out-file main-tf.js"
