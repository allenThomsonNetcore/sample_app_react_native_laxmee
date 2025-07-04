package com.hellodemo


import com.hellodemo.R
import android.app.Application
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactHost
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.load
import com.facebook.react.defaults.DefaultReactHost.getDefaultReactHost
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.react.uimanager.util.ReactFindViewUtil
import com.facebook.soloader.SoLoader
import com.netcore.android.Smartech
import com.smartechbasereactnative.SmartechBasePlugin
import java.lang.ref.WeakReference
import io.hansel.core.logger.HSLLogLevel
import android.view.View
import io.hansel.react.HanselRn
import io.hansel.hanselsdk.Hansel


class MainApplication : Application(), ReactApplication {

  override val reactNativeHost: ReactNativeHost =
      object : DefaultReactNativeHost(this) {
        override fun getPackages(): List<ReactPackage> =
            PackageList(this).packages.apply {
              // Packages that cannot be autolinked yet can be added manually here, for example:
              // add(MyReactNativePackage())
            }

        override fun getJSMainModuleName(): String = "index"

        override fun getUseDeveloperSupport(): Boolean = BuildConfig.DEBUG

        override val isNewArchEnabled: Boolean = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
        override val isHermesEnabled: Boolean = BuildConfig.IS_HERMES_ENABLED
      }

  override val reactHost: ReactHost
    get() = getDefaultReactHost(applicationContext, reactNativeHost)

  override fun onCreate() {
    super.onCreate()
    SoLoader.init(this, false)
    Smartech.getInstance(WeakReference(this)).initializeSdk(this)
    val smartechBasePlugin = SmartechBasePlugin.getInstance()
    smartechBasePlugin.init(this)



      val nativeIdSet = setOf("hansel_ignore_view_overlay", "hansel_ignore_view")

      ReactFindViewUtil.addViewsListener(
          object : ReactFindViewUtil.OnMultipleViewsFoundListener {
              override fun onViewFound(view: View, nativeID: String) {
                  if (nativeID == "hansel_ignore_view_overlay") {
                      val tag = view.tag?.toString() ?: return
                      val values = tag.split("#")
                      val parentsLayerCount = values[0].toIntOrNull() ?: return
                      val childLayerIndex = if (values.size < 2 || values[1].isEmpty()) {
                          0
                      } else {
                          values[1].toIntOrNull() ?: 0
                      }
                      HanselRn.setHanselIgnoreViewTag(view, parentsLayerCount, childLayerIndex)
                  } else {
                      view.setTag(io.hansel.react.R.id.hansel_ignore_view, true)
                  }
              }
          },
          nativeIdSet
      )



//    Smartech.setDebugLevel(SMTDebugLevel.Level.VERBOSE);
 HSLLogLevel.all.setEnabled(true);
  HSLLogLevel.mid.setEnabled(true);
  HSLLogLevel.debug.setEnabled(true);
    Smartech.getInstance(WeakReference(applicationContext)).setDebugLevel(9)



    if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
      // If you opted-in for the New Architecture, we load the native entry point for this app.
      load()
    }
  }
}
