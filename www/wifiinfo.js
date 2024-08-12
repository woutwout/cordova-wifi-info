/* global cordova:false */
/* globals window */

var exec = cordova.require('cordova/exec'),
    utils = cordova.require('cordova/utils');

var WifiInfo = {
    getConnectedSSID: function(successCallback, errorCallback, options) {
        exec(successCallback, errorCallback, 'WifiInfoIOS', 'getConnectedSSID', [options]);
    }
};

module.exports = WifiInfoIOS;
