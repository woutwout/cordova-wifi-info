
cordova-wifi-info
------------------------

Simple function for iOS only that allows you to get the SSID information from a the connected device.

`cordova plugin add https://github.com/gregavola/cordova-wifi-info`


```
WifiInfo.getConnectedSSID(
  result => {
    console.log(result);
  },
  err => {
    console.error(err);
  }
);
```

Note: This will not work on a simulator - it will throw an error, but will actual allow to build.