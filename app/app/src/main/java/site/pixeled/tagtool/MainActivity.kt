package site.pixeled.tagtool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.CodeScanner


class MainActivity : AppCompatActivity() {
    private lateinit var mCodeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val scannerView = findViewById<CodeScannerView>(R.id.scannerView)
//        mCodeScanner = CodeScanner(this, scannerView)
//
//        mCodeScanner.camera = CodeScanner.CAMERA_BACK
//        mCodeScanner.formats = CodeScanner.ALL_FORMATS
//        mCodeScanner.autoFocusMode = AutoFocusMode.SAFE
//        mCodeScanner.scanMode = ScanMode.SINGLE
//        mCodeScanner.isAutoFocusEnabled = true
//        mCodeScanner.isFlashEnabled = false
//
//        mCodeScanner.setDecodeCallback {
//            runOnUiThread {
//                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
//                mCodeScanner.startPreview();
//            }
//        }
    }

//    override fun onResume() {
//        super.onResume()
//        mCodeScanner.startPreview()
//    }
//
//    override fun onPause() {
//        mCodeScanner.releaseResources()
//        super.onPause()
//    }
}