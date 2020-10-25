package org.csuf.cpsc411.Dao.claim

import org.csuf.cpsc411.Dao.Dao
import org.csuf.cpsc411.Dao.Database
import java.util.*


fun toUUID(uuid : String?) : UUID {
    return UUID.fromString(uuid)
}

fun fromUUID(uuid: UUID?): String?{
    return uuid?.toString()
}




class ClaimDao {
    //function used to store cObj typed in postman into Database
    fun addClaim(cObj : Claim){
        //1. Get db Connection
        val conn = Database.getInstance()?.getDbConnection()
        //2. prepare the sql statement
        print(cObj.isSolved)
        var sqlStmt = "insert into claim (uuid, title, date, is_solved) values ('${fromUUID(cObj.id)}', '${cObj.title}', '${cObj.date}', '${cObj.isSolved}')"
        //3. submit the sql statement
        conn?.exec(sqlStmt)
    }

    //get the List of Claims
    fun getAll() : List<Claim> {
        //1. Get db Connection
        val conn = Database.getInstance()?.getDbConnection()
        //2. prepare the sql statement
        var sqlStmt = "select uuid, title, date, is_solved from claim"

        //3. submit the sql statement
        var claimList : MutableList<Claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)

        //4. Convert into Kotlin Object format
        while(st?.step()!!){
            val id = st.columnString(0)
            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnNull(3)

            val convertID = toUUID(id)
            //add a Claim object to the list
            claimList.add(Claim(convertID,title,date, isSolved))

        }

        return claimList

    }

}