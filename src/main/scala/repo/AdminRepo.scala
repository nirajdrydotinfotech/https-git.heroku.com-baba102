package repo

import dbconfig.DbConfig
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import model.Admin
import org.mongodb.scala.MongoCollection
import utils.JsonUtils

object AdminRepo extends FailFastCirceSupport{
  private val  adminDoc:MongoCollection[Admin]=DbConfig.admin

  def createCollection()={
    DbConfig.database.createCollection("admin").subscribe(
      (results)=>println(s"$results"),
      e=>println(e.getLocalizedMessage),
      ()=>println("complete")
    )
  }
  def insertData(adm:Admin)={

    adminDoc.insertOne(adm).toFuture()
  }

  def findAll=adminDoc.find().toFuture()


}
