package dbconfig

import model.Admin

import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

object DbConfig {


  val codecRegistry = fromRegistries(fromProviders(classOf[Admin]), DEFAULT_CODEC_REGISTRY)
  val client: MongoClient = MongoClient("mongodb+srv://m001-student:1230@sandbox.t2nc4.mongodb.net/test?retryWrites=true&w=majority")
  val database: MongoDatabase = client.getDatabase("test").withCodecRegistry(codecRegistry)
  val admin: MongoCollection[Admin] = database.getCollection("adminDetails")

}
