package org.csuf.cpsc411.Dao.claim

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


data class Claim (var id : UUID = UUID.randomUUID(), var title : String?, var date : String?, var isSolved : Boolean? = false)

fun main(){

}
