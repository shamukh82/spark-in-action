/**
  * Created by smukherjee on 7/25/17.
  */


import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger

object App {

  def main(args: Array[String]): Unit = {
    val logger = Logger.getLogger(getClass)

    val spark = SparkSession.builder().appName("spark-in-action")
                  .master("local[*]").getOrCreate()

    val sc = spark.sparkContext
    val homeDir = System.getenv("HOME")

    //val jsonText = sc.textFile(homeDir+"/spark_new/github-archive/2015-03-01-0.json")
    val jsonText = spark.read.json(homeDir+"/spark_new/github-archive/2015-03-01-0.json")
    logger.info("***** count: "+jsonText.count)

    val filteredJsonText = jsonText.filter("type = 'PushEvent'")
    logger.info("***** pushEvent count: "+filteredJsonText.count)

    println(jsonText.printSchema())

    println(jsonText.show(5))

    val pushEventsByUserName = filteredJsonText.groupBy("actor.login").count()

    val pushEventsByUserNameSorted = pushEventsByUserName.orderBy(pushEventsByUserName("count").desc)

    logger.info("***** pushEvent count: "+ pushEventsByUserNameSorted.show(5))
    //logger.info("***** pushEvent count: "+ pushEventsByUserName.show(5))

  }
}
