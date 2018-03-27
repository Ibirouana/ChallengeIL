  import scala.io.Source
  import java.io.{BufferedWriter, File, FileWriter}
  object TopCentVenteMagasin {
     def main(args: Array[String]): Unit=
    {
      val transaction = Source.fromFile("C:/Users/ibirouana/Desktop/phenix-challenge-master/data/transactions/transactions_20170514.data")
      val splittedLines = transaction.getLines.toList.map(x=> x.split('|'))
      val group = splittedLines.map(x => (x(2), x(3), x(4)))

      val  groupedList  = group.groupBy(_._1)
      val groupedProduct = groupedList.map{ case (k,v) => (k,v.map(y=> (y._2,y._3)))}
      val productQuantity = groupedProduct.map{case (k,v)=> (k,v.toMap)}



      productQuantity.take(2).foreach(println)


      val values = groupedList.map{case (k,v) => v.map(x => (x._1,x._2, x._3.toInt))}

      val sortedValues= values.map(x=> x.sortBy(_._3))


      val topCentVente = sortedValues.map(x=> x.takeRight(10))
      val topCentProduct = topCentVente.map(x => x.map(y=> (y._1,y._2 )))
      val groupByMag =topCentProduct.map(x=> x.groupBy(_._1))
     // val myMapList= groupByMag.map(x=> x.map{case (k,v) => (k, v.map(y=> y._2))})
       val myMapList= groupByMag.map(x=> x.map{
         case (k,v) =>
           val product = v.map(y=> y._2).mkString("\n")
           val fileName = new BufferedWriter(new FileWriter(s"top_100_ventes_${k}_20170514.data"))
           val topCentMag = fileName.write(s"Les cents produits qui ont les meilleures ventes du magasin : $k \n\n$product\n")
           fileName.flush()
       })


      val referenceFiles = new File("C:/Users/ibirouana/Desktop/phenix-challenge-master/data/reference_prod")
      val  listofFiles = referenceFiles.listFiles()
     // listofFiles.foreach(println)
      //val referenceProductPaths = listofFiles.map(x=> x.getAbsolutePath)
      //val referenceProductFiles = referenceProductPaths.map( x => Source.fromFile(x))
      val magProd = listofFiles.map{
        case x =>
          val uUID = x.getName.substring(15, 51)
          val referenceProductPath = x.getAbsolutePath
          val referenceProductFile =Source.fromFile(referenceProductPath)
          val referenceLines = referenceProductFile.getLines().toList
          val elements = referenceLines.map(x=> x.split('|'))
          val lineProduct = elements.map(x=> x(1))
          //(uUID,lineProduct)
      }

      //val m = productQuantity.map(x=> ( ma,productQuantity(x._1))) // (s"uUID ${magProd.map(x=> x._1).toList.map}")
      //val n = m.map(x=> x.map(y=> x(y._1)))

     // val l = m.map(x=> m(x))









      //val price = elements.map(x=> x)




    }


  }