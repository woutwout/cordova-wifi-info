
package com.wifiinfo.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.ConnectivityManager;

import android.annotation.SuppressLint;
import android.util.Base64;

/**
* This class exposes methods in Cordova that can be called from JavaScript.
*/
public class WifiInfoPlugin extends CordovaPlugin {


    private WifiManager wifiManager;
    private CallbackContext callbackContext;

     /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback context from which we were invoked.
     */
    @SuppressLint("NewApi")
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("echo")) {
          this.getConnectedSSID(callbackContext);
        } else {
            return false;
        }
        return true;
    }

    /**
     * This method retrieves the SSID for the currently connected network
     *
     * @param callbackContext A Cordova callback context
     * @return true if SSID found, false if not.
     */
    private boolean getConnectedSSID(CallbackContext callbackContext) {
      return getWifiServiceInfo(callbackContext, false);
    }

      /**
   * This method retrieves the WifiInformation for the (SSID or BSSID) currently connected network.
   *
   * @param callbackContext A Cordova callback context
   * @param basicIdentifier A flag to get BSSID if true or SSID if false.
   * @return true if SSID found, false if not.
   */
  private boolean getWifiServiceInfo(CallbackContext callbackContext, boolean basicIdentifier) {

    WifiInfo info = wifiManager.getConnectionInfo();

    if (info == null) {
      callbackContext.error("UNABLE_TO_READ_WIFI_INFO");
      return false;
    }

    // Only return SSID or BSSID when actually connected to a network
    SupplicantState state = info.getSupplicantState();
    if (!state.equals(SupplicantState.COMPLETED)) {
      callbackContext.error("CONNECTION_NOT_COMPLETED");
      return false;
    }

    String serviceInfo;
    if (basicIdentifier) {
      serviceInfo = info.getBSSID();
    } else {
      serviceInfo = info.getSSID();
    }

    if (serviceInfo == null || serviceInfo.isEmpty() || serviceInfo == "0x") {
      callbackContext.error("WIFI_INFORMATION_EMPTY");
      return false;
    }

    // http://developer.android.com/reference/android/net/wifi/WifiInfo.html#getSSID()
    if (serviceInfo.startsWith("\"") && serviceInfo.endsWith("\"")) {
      serviceInfo = serviceInfo.substring(1, serviceInfo.length() - 1);
    }

    callbackContext.success(serviceInfo);
    return true;
  }
}
