//
//import org.apache.spark.sql.delta._
//
//object Main extends App {
//    try {
//        // throw new Exception("Something.. doesn't exist ...")
//        // throw new Exception("Something.. is not a Delta table ...")
//        // throw new Exception("Something..")
//        println("test")
//        val test = new test("eiei")
//        println(test.name)
//
//        val errorClass = "DELTA_TABLE_NOT_FOUND"
//        val messageParameters = Array("path")
//        val result = DeltaThrowableHelper.getMessage(errorClass, messageParameters)
//        val resultSql = DeltaThrowableHelper.getSqlState(errorClass)
//
//        // println(resultSql)
//        // println(result contains "Something.. doesn't exist ..." )
//
//        val a = "some random test message"
//        val keys = List("hi","random","test")
//        println(keys.exists(a.contains))
//
//
//    } catch {
//        case ex: Exception if ex.getMessage.contains("doesn't exist") => {
//            println("==============doesn't exist")
//            throw ex
//        }
//        case ex: Exception if ex.getMessage.contains("is not a Delta table") => {
//            println("==============is not a Delta table")
//            throw ex
//        }
//        case ex: Throwable => {
//            println("==============etc..")
//            throw ex
//        }
//    }
//
//}