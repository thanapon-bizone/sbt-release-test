//package org.apache.spark.sql.delta
//
//// scalastyle:off import.ordering.noEmptyLine
//import java.io.FileNotFoundException
//import java.net.URL
//
//import scala.collection.immutable.SortedMap
//
//import com.fasterxml.jackson.core.`type`.TypeReference
//import com.fasterxml.jackson.databind.json.JsonMapper
//import com.fasterxml.jackson.module.scala.DefaultScalaModule
//
//import org.apache.spark.ErrorInfo
//import org.apache.spark.util.Utils
//
//case class test(name:String)
//
///**
// * The helper object for Delta code base to pick error class template and compile
// * the exception message.
// */
//object DeltaThrowableHelper
//{
//  private lazy val mapper: JsonMapper = JsonMapper.builder().addModule(DefaultScalaModule).build()
//
//  /**
//   * Try to find the error class source file and throw exception if it is no found.
//   */
//  private def safeGetErrorClassesSource(sourceFile: String): URL = {
//    val classLoader = Utils.getContextOrSparkClassLoader
//    Option(classLoader.getResource(sourceFile)).getOrElse {
//      throw new FileNotFoundException(
//        s"""Cannot find the error class definition file on path $sourceFile" through the """ +
//          s"class loader ${classLoader.toString}")
//    }
//  }
//
//  lazy val sparkErrorClassSource: URL = {
//    safeGetErrorClassesSource("error/error-classes.json")
//  }
//
//  def deltaErrorClassSource: URL = {
//    safeGetErrorClassesSource("error/delta-error-classes.json")
//  }
//
//  /** The error classes of spark. */
//  lazy val sparkErrorClassesMap: SortedMap[String, ErrorInfo] = {
//    mapper.readValue(sparkErrorClassSource, new TypeReference[SortedMap[String, ErrorInfo]]() {})
//  }
//
//  /** The error classes of delta. */
//  lazy val deltaErrorClassToInfoMap: SortedMap[String, ErrorInfo] = {
//    mapper.readValue(deltaErrorClassSource, new TypeReference[SortedMap[String, ErrorInfo]]() {})
//  }
//  /**
//   * Combined error classes from delta and spark. There should not be same error classes between
//   * deltaErrorClassesMap and sparkErrorClassesMap.
//   */
//  private lazy val errorClassToInfoMap: SortedMap[String, ErrorInfo] = {
//    deltaErrorClassToInfoMap ++ sparkErrorClassesMap
//  }
//
//  def getMessage(errorClass: String, messageParameters: Array[String]): String = {
//    val errorInfo = errorClassToInfoMap.getOrElse(errorClass,
//      throw new IllegalArgumentException(s"Cannot find error class '$errorClass'"))
//
//
//    println(errorInfo)
//    println("=========")
//    val test = String.format(errorInfo.messageFormat.replaceAll("<[a-zA-Z0-9_-]+>", "%s"),
//      messageParameters: _*)
//    print(test)
//    test
//  }
//
//
//  def getSqlState(errorClass: String): String =
//    Option(errorClass).flatMap(errorClassToInfoMap.get).flatMap(_.sqlState).orNull
//
//  def isInternalError(errorClass: String): Boolean = errorClass == "INTERNAL_ERROR"
//
//}