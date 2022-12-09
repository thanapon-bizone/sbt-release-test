package etl.velocity.resource


import scala.util.Try


case class NotebookMetaData(id: String, path: String) {def getPath = s"/notebooks/$path"}

object NotebookCatalog {
    private val _catalog = "test"
  
}

 