package org.csuf.cpsc411.Dao.claim

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


data class Claim (var id : UUID = UUID.randomUUID(), var title : String?, var date : String?, var isSolved : Boolean? = false)

fun main(){
    //JSON Serialization
    val cObj = Claim(UUID.randomUUID(),"Heaven", "2020-05-15",false)
    val jsonStr = Gson().toJson(cObj)
    println("The converted JSON string : ${jsonStr}")

    //Serialization of List <Claim>
    var cList : MutableList<Claim> = mutableListOf()
    cList.add(cObj)
    cList.add(Claim(UUID.randomUUID(), "HappyTime", "2021-01-01", false))
    val listJsonString = Gson().toJson(cList)
    println("${listJsonString}")

    //List <Claim> object deserialization
    val claimListType : Type = object: TypeToken<Claim>(){}.type

    //JSON Deserialization
    var cObj1 : Claim
    cObj1 = Gson().fromJson(jsonStr, Claim::class.java)
    println("${cObj1.toString()}")
}
