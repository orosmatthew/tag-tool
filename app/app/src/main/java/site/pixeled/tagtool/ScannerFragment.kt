package site.pixeled.tagtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.ScanMode


class ScannerFragment : Fragment() {

    private lateinit var mCodeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scanner, container, false)
        val activity = requireActivity()
        val scannerView = view.findViewById<CodeScannerView>(R.id.scannerView)
        mCodeScanner = CodeScanner(activity, scannerView)

        mCodeScanner.camera = CodeScanner.CAMERA_BACK
        mCodeScanner.formats = CodeScanner.ALL_FORMATS
        mCodeScanner.autoFocusMode = AutoFocusMode.SAFE
        mCodeScanner.scanMode = ScanMode.SINGLE
        mCodeScanner.isAutoFocusEnabled = true
        mCodeScanner.isFlashEnabled = false

        mCodeScanner.setDecodeCallback {
            activity.runOnUiThread {
                Toast.makeText(activity, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
                mCodeScanner.startPreview()
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        mCodeScanner.startPreview()
    }

    override fun onPause() {
        mCodeScanner.releaseResources()
        super.onPause()
    }
}