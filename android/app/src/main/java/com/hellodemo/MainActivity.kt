package com.hellodemo

import android.os.Bundle
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate
import com.netcore.android.Smartech.Companion.getInstance
import io.hansel.hanselsdk.Hansel
import java.lang.ref.WeakReference

class MainActivity : ReactActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val isSmartechHandledDeeplink = getInstance(WeakReference(this)).isDeepLinkFromSmartech(intent)
        if (!isSmartechHandledDeeplink) {
            Hansel.pairTestDevice(intent?.dataString)
        }

    }

    /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  override fun getMainComponentName(): String = "helloDemo"

  /**
   * Returns the instance of the [ReactActivityDelegate]. We use [DefaultReactActivityDelegate]
   * which allows you to enable New Architecture with a single boolean flags [fabricEnabled]
   */
  override fun createReactActivityDelegate(): ReactActivityDelegate =
      DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)



}
