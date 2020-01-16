package ch.hearc.fidarc.ui.client.scan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ch.hearc.fidarc.R

import android.graphics.Bitmap
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScanFragmentUser : Fragment() {


    private var bitmap: Bitmap? = null
    private var iv: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // declare a root var for accessed the content of the layout
        val root = inflater.inflate(R.layout.fragment_scan_user, container, false)

        // Get the id oh the connected user using the shared preference
        val sharedPref = activity!!.getSharedPreferences("user", Context.MODE_PRIVATE)
        val userID = sharedPref.getInt("id", -1)


        // give a text to a textview content in the fragment
        val textView: TextView = root.findViewById(R.id.text_scan)
        textView.text = resources.getString(R.string.text_qrCode_display)

        // Generate the bitmap image in a coroutine to not freeze the UI
        GlobalScope.launch(Dispatchers.Default) {
            bitmap = textToImageEncode(userID.toString())
            iv = root.findViewById(R.id.iv)
            withContext(Dispatchers.Main){
                iv!!.setImageBitmap(bitmap)
            }
        }

        return root
    }

    // Convert a string given by parameter to a bitmap who contains the QRCode
    @Throws(WriterException::class)
    private fun textToImageEncode(Value: String): Bitmap? {

        val bitMatrix: BitMatrix

        // specify size dans type of code to generate
        try {
            bitMatrix = MultiFormatWriter().encode(Value, BarcodeFormat.QR_CODE, 800, 800, null)
        } catch (Illegalargumentexception: IllegalArgumentException) {
            return null
        }

        // define indexer according to bitmatrix attribute
        val bitMatrixWidth = bitMatrix.width
        val bitMatrixHeight = bitMatrix.height

        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        // double for, to iterate on every pixel of the gride
        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {

                // draw pixel for the QRCode in two differents colors
                pixels[offset + x] = if (bitMatrix.get(x, y))
                    resources.getColor(R.color.green)
                else
                    resources.getColor(R.color.white)
            }
        }

        // Create the bitmap image
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)

        bitmap.setPixels(pixels, 0, 800, 0, 0, bitMatrixWidth, bitMatrixHeight)

        // return the image of the QRCode
        return bitmap
    }
}