package com.example.CalculatorInventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import kotlin.math.roundToInt


class CalculatorActivityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_activity)
        val extraINFOARR = intent.getStringExtra("EXTRA_INFO")?.split(",")
        val txttvItem: TextView = findViewById(R.id.tvItem)
        val txttvItemDescription: TextView = findViewById(R.id.tvItemDescription)
        val txttinputs :TextView = findViewById(R.id.txtinputs)

        var sid = ""

        var cLname  = ""

        if(!extraINFOARR.isNullOrEmpty())
        {
            sid =  extraINFOARR[0]

            txttvItem.text = extraINFOARR[1]
            txttvItemDescription.text = extraINFOARR[2]
            // Saved Quantity
            if( extraINFOARR[3] != "0.0")
                txttinputs.text = extraINFOARR[3]

            cLname =  extraINFOARR[4]

        }

        val buttonClear: Button = findViewById(R.id.buttonClear)
        val buttonBackspace: Button = findViewById(R.id.buttonBackspace)

        val buttonDivide: Button = findViewById(R.id.buttonDivide)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val buttonMultiply: Button = findViewById(R.id.buttonMultiply)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val buttonMinus: Button = findViewById(R.id.buttonMinus)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val buttonPlus: Button = findViewById(R.id.buttonPlus)
        val button0: Button = findViewById(R.id.button0)
        val buttonDecimal: Button = findViewById(R.id.buttonDecimal)
        val buttonEquals: Button = findViewById(R.id.buttonEquals)

        buttonDivide.setOnClickListener { txttinputs.append(buttonDivide.text) }
        button7.setOnClickListener { txttinputs.append(button7.text) }
        button8.setOnClickListener { txttinputs.append(button8.text) }
        button9.setOnClickListener { txttinputs.append(button9.text) }
        buttonMultiply.setOnClickListener { txttinputs.append(buttonMultiply.text) }
        button4.setOnClickListener { txttinputs.append(button4.text) }
        button5.setOnClickListener { txttinputs.append(button5.text) }
        button6.setOnClickListener { txttinputs.append(button6.text) }
        buttonMinus.setOnClickListener { txttinputs.append(buttonMinus.text) }
        button1.setOnClickListener { txttinputs.append(button1.text) }
        button2.setOnClickListener { txttinputs.append(button2.text) }
        button3.setOnClickListener { txttinputs.append(button3.text) }
        buttonPlus.setOnClickListener { txttinputs.append(buttonPlus.text) }
        button0.setOnClickListener { txttinputs.append(button0.text) }
        buttonDecimal.setOnClickListener { txttinputs.append(buttonDecimal.text) }

        buttonClear.setOnClickListener {
            txttinputs.text = ""
        }

        buttonBackspace.setOnClickListener {
            val init :String = txttinputs.text.toString()
            if(init.isNotEmpty())
                txttinputs.text =  init.substring(0, init.length-1)
        }
        val databaseHelper = SqliteDatabase(this)


        findViewById<Button>(R.id.buttonPercent)
            .setOnClickListener{
                val expression = txttinputs.text.toString() + "/100"
                val result = EvalMath().doCalculation (expression)

                txttinputs.text = result.toString()
            }

        findViewById<Button>(R.id.buttonMRplus)
            .setOnClickListener {
                val iniVal =  txttinputs.text.toString()
                if(iniVal.isNotEmpty())
                {
                    /* Because SqliteDatabase ...  quan NUMERIC
                     * SQLITE Doing
                     *  Arithmetic operators understood by SQLite are multiplication, division,
                     * addition, subtraction, and modulo.
                     */

                    val sqlUp = "UPDATE COUNTING_TABLE SET quan= $iniVal WHERE id=$sid"
                    databaseHelper.execStringSql(sqlUp)
                    val intent = Intent(this, CountItemsActivity::class.java)
                    intent.putExtra("EXTRA_NAME" , cLname)
                    startActivity(intent)
                    finish()
                }
        }

        buttonEquals.setOnClickListener {
                if(txttinputs.text.toString().isNotEmpty())
                {
                    val dVal = EvalMath().doCalculation (txttinputs.text.toString())
                    if(dVal>0.0)
                    {
                        txttinputs.text =  roundTo2Decs(dVal).toString()
                    }

                }
            }
        }
    }


//  by me
private fun roundTo2Decs(inDounble :Double): Double {
    return (inDounble * 100.0).roundToInt() / 100.0
}
