package com.example.CalculatorInventory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CountItemsActivity : AppCompatActivity() {

    private lateinit var databaseHelper: SqliteDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CountItemsActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_items)

        databaseHelper = SqliteDatabase(this)
        val name = intent.getStringExtra("EXTRA_NAME").toString()
        val data = databaseHelper.listModelCount(name)

        findViewById<TextView>(R.id.textView_Count).text = name

        recyclerView = findViewById(R.id.rvCountItems)
        recyclerView.layoutManager = LinearLayoutManager(this)


        findViewById<Button>(R.id.button_goback2).setOnClickListener()
        {
            finish()
        }

        adapter = CountItemsActivityAdapter(data) 
        // On Click Item Show Calculator see CountItemsActivityAdapter for 
        { item ->
            val i = Intent(this, CalculatorActivityActivity::class.java)
            i.putExtra("EXTRA_INFO", "$item,$name")
            startActivity(i)
            global_stVisibleP =
                (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            finish()
        }
        recyclerView.adapter = adapter

        (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(global_stVisibleP)
    }
}



