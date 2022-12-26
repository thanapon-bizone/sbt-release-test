package etl.velocity

import java.io.FileNotFoundException
import java.net.URL

import scala.collection.immutable.SortedMap

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import org.apache.spark.ErrorInfo
import org.apache.spark.util.Utils

/**
 * The helper object for Velocity code base to pick error class template and compile
 * the exception message.
 */
object ThrowableHelper
{
  private lazy val mapper: JsonMapper = JsonMapper.builder().addModule(DefaultScalaModule).build()

  /**
   * Try to find the error class source file and throw exception if it is no found.
   */
  private def safeGetErrorClassesSource(sourceFile: String): URL = {
    val classLoader = Utils.getContextOrSparkClassLoader
    Option(classLoader.getResource(sourceFile)).getOrElse {
      throw new FileNotFoundException(
        s"""Cannot find the error class definition file on path $sourceFile" through the """ +
          s"class loader ${classLoader.toString}")
    }
  }

  def errorClassSource: URL = {
    safeGetErrorClassesSource("error/error-classes.json")
  }

  /** The error classes of Velocity. */
  lazy val errorClassToInfoMap: SortedMap[String, ErrorInfo] = {
    mapper.readValue(errorClassSource, new TypeReference[SortedMap[String, ErrorInfo]]() {})
  }

  def getStrMessage(errorClass: String, messageParameters: Array[String] = Array("")): String = {
    val errorInfo = errorClassToInfoMap.getOrElse(errorClass,
      throw new IllegalArgumentException(s"Cannot find error class '$errorClass'"))

    String.format(errorInfo.messageFormat.replaceAll("<[a-zA-Z0-9_-]+>", "%s"),
      messageParameters: _*)
  }

  def getMessage(errorClass: String, messageParameters: Array[String] = Array("")): Seq[String] = {
    val errorInfo = errorClassToInfoMap.getOrElse(errorClass,
      throw new IllegalArgumentException(s"Cannot find error class '$errorClass'"))

    errorInfo.message.map(msg => {
      String.format(msg.replaceAll("<[a-zA-Z0-9_-]+>", "%s"), messageParameters: _*)
    })
  }


  def getSqlState(errorClass: String): String =
    Option(errorClass).flatMap(errorClassToInfoMap.get).flatMap(_.sqlState).orNull

  def isInternalError(errorClass: String): Boolean = errorClass == "INTERNAL_ERROR"

}