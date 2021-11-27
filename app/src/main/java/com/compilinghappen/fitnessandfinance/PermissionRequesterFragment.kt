package com.compilinghappen.fitnessandfinance

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

open class PermissionRequesterFragment : Fragment() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var permissionResultCallback: ((PermReqResult) -> Unit)? = null

    enum class PermReqResult {
        GRANTED, DENIED, DENIED_FOREVER
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionResultCallback?.let { handler ->
                if (isGranted) {
                    handler(PermReqResult.GRANTED)
                } else {
                    handler(PermReqResult.DENIED)
                }
            }
        }
    }

    fun requestPermission(permission: String, callback: (PermReqResult) -> Unit) {
        when {
            ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED -> {
                callback(PermReqResult.GRANTED)
            }
            shouldShowRequestPermissionRationale(permission) -> {
                callback(PermReqResult.DENIED)
            }
            else -> {
                permissionResultCallback = callback
                requestPermissionLauncher.launch(permission)
            }
        }
    }
}