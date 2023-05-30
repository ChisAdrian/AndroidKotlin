package com.example.CalculatorInventory
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
class SqliteDatabase internal constructor(context: Context?) :

    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
    {
        override
        fun onCreate(db: SQLiteDatabase)
        {
            // Raw Strings because I have Objects :))
            val creteStr = "CREATE TABLE COUNTING_TABLE (id INTEGER PRIMARY KEY , list_name TEXT,item TEXT, item_descript TEXT, quan NUMERIC)"
            db.execSQL(creteStr)

            val ins = "INSERT INTO COUNTING_TABLE ( list_name, item, item_descript, quan)  VALUES ( 'l1', 'it0', 'itdec0', 0) ,  ( 'l1', 'it0', 'itdec0', '0.5')"

            db.execSQL(ins)
        }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS COUNTING_TABLE")
        onCreate(db)
    }

        fun execStringSql (sql :String) {
            val db = writableDatabase
            db.execSQL(sql)
            db.close()
        }


    fun listModelMain(): ArrayList<ModelMain> {
        val retarget  =  ArrayList<ModelMain>()
        retarget.add(ModelMain("items data" ,"cnt" ))
        val selectQuery = "SELECT list_name ,count(id) as nrItems from COUNTING_TABLE group by list_name"

        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery(selectQuery, null)
        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    retarget.add(ModelMain (it.getString(0)
                        ,it.getLong(1).toString() ))
                } while (it.moveToNext())
            }
        }
        cursor?.close()
        db.close()
        return retarget
    }

    fun listModelCount( sqlName :String ): ArrayList<ModelCount> {
        val retarget  =  ArrayList<ModelCount>()
        //retarget.add(ModelCount("items data" ))
        val selectQuery =
            "SELECT id,  item, item_descript, quan FROM COUNTING_TABLE WHERE list_name = '$sqlName' ;"

        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery(selectQuery, null)
        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(0)
                    val item = it.getString(1)
                    val descriptive = it.getString(2)
                    val quan = it.getDouble (3)
                    retarget.add(ModelCount (id.toString() , item , descriptive ,quan ))
                } while (it.moveToNext())
            }
        }

        cursor?.close()
        db.close()

        return retarget

        }

        fun allforCSV(): String
        {
            var retarget  = "List_name,Item,Item_description,Qty\r\n"

            val selectQuery =
                "SELECT list_name , item, item_descript, quan FROM COUNTING_TABLE ";
            val db = readableDatabase
            val cursor: Cursor? = db.rawQuery(selectQuery, null)
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        val list_name = it.getString(0)
                        val item = it.getString(1)
                        val descriptive = it.getString(2)
                        val quan = it.getDouble (3)
                        retarget += "$list_name,$item,$descriptive,${quan.toString()}\r\n"

                    } while (it.moveToNext())
                }
            }

            cursor?.close()
            db.close()

            return retarget

        }


    companion object {
        private const val DATABASE_VERSION = 5
        private const val DATABASE_NAME = "APP_DB"

    }
}
