package com.example.neweco.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.neweco.MainActivity
import me.dm7.barcodescanner.zxing.ZXingScannerView

class BarcodeScannerFragment : Fragment(), ZXingScannerView.ResultHandler {

    private var scannerView: ZXingScannerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        scannerView = ZXingScannerView(activity)
        return scannerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startCamera()
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            }
        } else {
            startCamera()
        }
    }

    override fun onResume() {
        super.onResume()
        scannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView?.stopCamera()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                // Пользователь отказал в предоставлении разрешения на использование камеры
                // Обработайте ситуацию соответствующим образом
            }
        }
    }

    override fun handleResult(result: com.google.zxing.Result?) {
        val barcodeText = result?.text
        Toast.makeText(getActivity(), barcodeText, Toast.LENGTH_LONG).show()
        (activity as MainActivity).makecamgone()
        // Обработка результата сканирования (вызывается при успешном сканировании)
        // Ваш код для обработки данных штрихкода
    }

    private fun startCamera() {
        scannerView?.setResultHandler(this)
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 123
    }
}
