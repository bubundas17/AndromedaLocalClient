package com.bubundas.andromedalocalclient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.jrummyapps.android.shell.Shell
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this, getString(R.string.admob_pub_id))
        val adRequest = AdRequest.Builder().build()
        mainAd.loadAd(adRequest)
        checkStatus()
        startMeda.setOnClickListener {
            enableSubstratum()
        }
    }

    private fun checkStatus() {
        substratumBox.isEnabled = false
        andromedaBox.isEnabled = false
        rootBox.isEnabled = false
        if (isInstalled(getString(R.string.substratum_pkg_name))) {
            substratumBox.isChecked = true
        }
        if (isInstalled(getString(R.string.andromeda_pkg_name))) {
            andromedaBox.isChecked = true
        }
        if (Shell.SU.available()) {
            rootBox.isChecked = true
        }
    }

    private fun isInstalled(packageMane: String): Boolean {
        return try {
            this.packageManager.getPackageInfo(packageMane, 0)
            true
        } catch (e: Throwable) {
            false
        }
    }

    private fun enableSubstratum() {
        if (!isInstalled(getString(R.string.substratum_pkg_name))) {
            Toast.makeText(this, getString(R.string.install_substratum_toast), Toast.LENGTH_LONG).show()
            return
        }
        if (!isInstalled(getString(R.string.andromeda_pkg_name))) {
            Toast.makeText(this, getString(R.string.install_andromeda_toast), Toast.LENGTH_LONG).show()
            return
        }
        if (!Shell.SU.available()) {
            Toast.makeText(this, getString(R.string.grant_root_toast), Toast.LENGTH_LONG).show()
            return
        }
        try {
            runOnUiThread {
                val shell = Shell.SU.run(
                        "andromeda=$(/system/bin/pm path projekt.andromeda)",
                        "pkg=\$andromeda", "pkg=\$(/system/bin/echo \"\$pkg\" | cut -d : -f 2 | sed s/\\\\r//g)",
                        "export CLASSPATH=\$pkg",
                        "nohup app_process /system/bin --nice-name=andromeda projekt.andromeda.Andromeda >/dev/null 2>&1 &")
                if (shell.isSuccessful){
                }
            }
        } catch (e: Throwable) {
            Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
            println(e)
        }
    }
}
