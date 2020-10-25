package org.csuf.cpsc411.Dao

import com.almworks.sqlite4java.SQLite
import com.almworks.sqlite4java.SQLiteConnection
import java.io.File


class Database constructor (var dbName : String = ""){
    init{
        //Create the database, create tables and keep the db connection
        dbName = "C:\\Users\\Ariosto\\Desktop\\claim_test_DB.db"
        val dbConn = SQLiteConnection(File(dbName))

        dbConn.open()
        val sqlStmt = "create table if not exists claim(uuid text, title text, date text, is_solved int)" //create a table if table Claim doesnt exist

        dbConn.exec(sqlStmt)
    }

    fun getDbConnection() : SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        return dbConn
    }

    //single object pattern
    companion object{
        private var dbObj : Database? = null

        fun getInstance() : Database? {
            if(dbObj == null){
                dbObj = Database()
            }
            return dbObj
        }
    }

}