package repo

import dbconfig.DbConfig
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import model.Admin
import org.mongodb.scala.MongoCollection
import org.mongodb.scala.model.Filters.{and, equal}

import scala.concurrent.Future

object AdminRepo extends FailFastCirceSupport{
  private val  adminDoc:MongoCollection[Admin]=DbConfig.admin
  def checkCredential(name:String,password:String): Future[Option[Admin]] = {
    adminDoc.find(
      and(
        equal("name",name),
        equal("password",password)
      )
    ).headOption()
  }
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
