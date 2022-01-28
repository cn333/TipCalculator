package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 10

class MainActivity : AppCompatActivity() {
    private lateinit var etAmount: EditText
    private lateinit var tvPercentLabel: TextView
    private lateinit var sbPercent: SeekBar
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etAmount = findViewById(R.id.etAmount)
        tvPercentLabel = findViewById(R.id.tvPercentLabel)
        sbPercent = findViewById(R.id.sbPercent)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)

        sbPercent.progress = INITIAL_TIP_PERCENT
        tvPercentLabel.text = "$INITIAL_TIP_PERCENT%"

        sbPercent.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvPercentLabel.text = "$progress%"
                computeTipandTotal()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.i(TAG, "onStartTrackingTouch")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.i(TAG, "onStopTrackingTouch")
            }

        } )

        etAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTipandTotal()
            }

        })
    }

    private fun computeTipandTotal() {
        // 1. Get amount and percent values
        val amount = if (etAmount.text.isEmpty()) 0.0 else etAmount.text.toString().toDouble()
        val tipPercent = sbPercent.progress

        // 2. Compute tip and total
        val tip = amount * tipPercent / 100
        val total = amount + tip

        // 3. Update UI
        tvTipAmount.text = "%.2f".format(tip)
        tvTotalAmount.text = "%.2f".format(total)
    }
}