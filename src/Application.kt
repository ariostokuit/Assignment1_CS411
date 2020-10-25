package org.csuf.cpsc411

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.utils.io.*
import com.almworks.sqlite4java.SQLiteConnection
import com.google.gson.Gson
import io.ktor.http.*
import org.csuf.cpsc411.Dao.Database
import org.csuf.cpsc411.Dao.claim.Claim
import org.csuf.cpsc411.Dao.claim.ClaimDao
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads



fun Application.module(testing: Boolean = false) {

    routing{
        this.get("/ClaimService/add"){
            println("HTTP message is using GET method with /get")
            val uuid = UUID.randomUUID() //generate a random UUID
            //val uuid : String? = call.request.queryParameters["uuid"]
            val title : String? = call.request.queryParameters["title"]
            val date : String? = call.request.queryParameters["date"]
            //val isSolved : String? = call.request.queryParameters["is_solved"]
            val response = String.format("UUID %s and Title %s and Date %s and isSolved %b",uuid, title, date, false)

            val cObj = Claim(uuid, title, date, false)

            val dao = ClaimDao().addClaim(cObj) //add the cObj to the database

            call.respondText(response, status = HttpStatusCode.OK, contentType = ContentType.Text.Plain )
        }
        get("/ClaimService/getAll"){
            val cList = ClaimDao().getAll()
            println("HTTP message is using GET method /get")
            println("The number of titles : ${cList.size}")

            //JSON Serialization/Deserialization convert Kotlin Object into a String as a JSON format
            val respJsonStr = Gson().toJson(cList)

            call.respondText(respJsonStr, status = HttpStatusCode.OK, contentType = ContentType.Application.Json )
        }

        post("/ClaimService/add"){
            val contType = call.request.contentType()
            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead
            var output = ByteArray(dataLength)
            data.readAvailable(output)
            var str = String(output)

            //JSON serialization/deserialization
            val gsonString = Gson().fromJson(str, Claim::class.java)
            val cObj = Claim(UUID.randomUUID(), gsonString.title, gsonString.date, isSolved = false)
            val dao = ClaimDao().addClaim(cObj)

            println("HTTP message is using POST method with /post for ${contType} ${str}")
            call.respondText("The POST request was successfully processed. ", status = HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }
    }
}

