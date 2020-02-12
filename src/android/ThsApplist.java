package cn.com.ths.applist;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * This class echoes a string called from JavaScript.
 */
public class ThsApplist extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getAppList")) {
            this.getAppList(callbackContext);
            return true;
        }
        return false;
    }

    private void getAppList(CallbackContext callbackContext){
        try {
        List<PackageInfo> packages = cordova.getActivity().getPackageManager().getInstalledPackages(0);
        JSONArray appArr = new JSONArray();
        for (int i = 0; i < packages.size(); i++) {
            JSONObject appObj =new JSONObject();
            PackageInfo packageInfo = packages.get(i);

            String  appName = packageInfo.applicationInfo.loadLabel(cordova.getActivity().getPackageManager()).toString();
            appObj.put("appName",appName);
            String  packageName = packageInfo.packageName;
            appObj.put("packageName",packageName);
            String  versionName = packageInfo.versionName;
            appObj.put("versionName",versionName);
            int versionCode = packageInfo.versionCode;
            appObj.put("versionCode",versionCode);

            //Only display the non-system app info  

            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                appArr.put(appObj);//如果非系统应用，则添加至appList  
            }
        }
            callbackContext.success(appArr);
        }catch (JSONException e){
            callbackContext.error(e.getMessage());
        }

    }
}
