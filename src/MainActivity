package com.example.CalculatorInventory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

import android.widget.Toast


import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream

import java.io.InputStreamReader


class MainActivity : AppCompatActivity() ,
    MainActivityAdapter.OnItemClickListener {
    private lateinit var databaseHelper: SqliteDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainActivityAdapter

    private lateinit var data :  ArrayList<ModelMain>
    private lateinit var toolbar : Toolbar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseHelper = SqliteDatabase(this)
        data = databaseHelper.listModelMain()
        recyclerView = findViewById(R.id.rvCounting)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainActivityAdapter(data,this)
        recyclerView.adapter = adapter

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

         findViewById<Button>(R.id.button_goback).setOnClickListener()
         {
             finish()
         }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_loadCSV -> {
                // Handle search action
                csvFileReadLauncher.launch("text/csv")
                true
            }
            R.id.action_sendEmail -> {
                // Handle settings action
                // CSV data (as string)
                val csvData = databaseHelper.allforCSV()
                // Create a temporary file to store the CSV data
                val csvFile = File(cacheDir, "attachment.csv")
                val fileOutputStream = FileOutputStream(csvFile)
                fileOutputStream.write(csvData.toByteArray())
                fileOutputStream.flush()
                fileOutputStream.close()

                // Generate content URI using FileProvider
                val contentUri = FileProvider.getUriForFile(this, "com.example.CalculatorInventory.fileprovider", csvFile)

                // Create the intent
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/csv"
                intent.putExtra(Intent.EXTRA_SUBJECT, "Email with CSV Attachment")
                //emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("chis.adrian@gmail.com"))
                intent.putExtra(Intent.EXTRA_TEXT, "Please find attached the CSV file.")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant read permission to the receiving app
                intent.putExtra(Intent.EXTRA_STREAM, contentUri)

                // Check if there's an email app available to handle the intent
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(Intent.createChooser(intent, "Send Email"))
                } else {
                    Toast.makeText(this, "No email app found.", Toast.LENGTH_SHORT).show()
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(position: Int) {
        val i = Intent(this, CountItemsActivity::class.java)
      //  i.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        i.putExtra("EXTRA_NAME", data[position].Name )
        startActivity(i)
    }


    private val csvFileReadLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val inputStream = contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))

            var insetSqlStr  = "INSERT INTO COUNTING_TABLE ( list_name, item, item_descript, quan)  VALUES "
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    var colStr = ""
                    // Process each line here
                    line?.let { it1 -> Log.d("CSV", it1) }

                    colStr +="("

                    line?.split(",")?.forEach {
                        colStr += "'$it' ,"
                    }
                    insetSqlStr  += colStr.substringBeforeLast(",") +  ") ,"

                }

            } catch (e: Exception) {
                Toast.makeText(this, "Error reading CSV file: $e", Toast.LENGTH_SHORT).show()

            } finally {
                databaseHelper.execStringSql("DELETE FROM COUNTING_TABLE ")
                databaseHelper.execStringSql(insetSqlStr.substringBeforeLast(","))

                reader.close()
                data = databaseHelper.listModelMain()
                adapter = MainActivityAdapter(data,this)
                // adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter
            }
        }
    }

}



