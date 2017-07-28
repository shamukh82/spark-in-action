/**
  * Created by smukherjee on 7/25/17.
  */

package learn.spark.in.action.ch3
import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger
import scala.io.Source.fromFile

object App {

  def main(args: Array[String]): Unit = {
    val logger = Logger.getLogger(getClass)
    val spark = SparkSession.builder().appName("ch_3").master("local[*]").getOrCreate()
    val home = System.getenv("HOME")
    val fileLocation = home + "/spark_new/github-archive/2015-03-01-0.json"
    val jsonFile = spark.read.json(fileLocation).filter("type='PushEvent'")

    val grouped = jsonFile.groupBy("actor.login").count()
    val ordered = grouped.orderBy(grouped("count").desc)

    val employees = Set() ++ ( for {
      line <- fromFile(home + "/spark_new/github-archive/ghEmployees.txt").getLines()
    } yield line.trim)

    val employeeBroadcast = spark.sparkContext.broadcast(employees)
    val isEmployee = user => employeeBroadcast.value.contains(user)
    val hasEmployee = spark.sqlContext.udf.register("hasEmployee",isEmployee)

    import spark.sqlContext.implicits._
    val filtered = ordered.filter(hasEmployee($"login"))
    filtered.show()
    //println("filtered count"+filtered.count)
  }
}