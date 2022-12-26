package etl.velocity


import etl.velocity.ThrowableHelper
import org.apache.spark.sql.{ Column, DataFrame}
import org.apache.spark.sql.AnalysisException

object ErrorSample extends App{

    def persistOutput(df: DataFrame, sink: String) {
        try {
            df.write
                .format("delta")
                .mode("append")
                .save(sink)
        } catch {
           case ex: AnalysisException if  ThrowableHelper.getMessage("INVALID_DELTA_TABLE").exists(ex.message.contains) => {
                println("*******INVALID_DELTA_TABLE*******")
                throw ex
            }
            case ex: Throwable => throw ex
        } 
    }

    def customErrorMessage(errorMsg: String, errorClass: String) {
        try {
            throw new Exception(errorMsg)         
        } catch {
            case ex: Exception if  ThrowableHelper.getMessage(errorClass).exists(ex.getMessage.contains) => {
                println("Caught the exception!")
                println(s"Error Class: ${errorClass}")
                println("Without table " + ThrowableHelper.getMessage(errorClass))
                println("With table " + ThrowableHelper.getMessage(errorClass, Array("test")))
                throw ex
            }
            case ex: Throwable => {
                println("==============etc..")
                throw ex
            }
        } 
    }

    // val errorMsg = "Something.. doesn't exist ..."
    // val errorClass = "DELTA_TABLE_NOT_FOUND"

    val errorMsg = "Something.. does not exist ..."
    val errorClass = "DELTA_TABLE_NOT_FOUND"
    
    // val errorMsg = "Something....."
    // val errorClass = "DELTA_TABLE_NOT_FOUND"

    // val errorMsg = "Something..is not a Delta table..."
    // val errorClass = "INVALID_DELTA_TABLE"

    customErrorMessage(errorMsg, errorClass)

}