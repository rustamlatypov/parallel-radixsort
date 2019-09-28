package tests
import org.scalatest._
import sorts._

class ParallelRadixSort extends FlatSpec with Matchers {

  val rand = new scala.util.Random(999)

  def randArrayInt(n: Int): Array[Int] = Array.tabulate[Int](n)(j => rand.nextInt(Int.MaxValue))


  def measureTime[T](f: => T): Double = {
    val start = System.nanoTime
    f
    val end = System.nanoTime
    val time = (end - start) / 1e+9
    time
  }


  "The radixSort.sort method" should "work correctly" in {
    val nofTests = 100
    val N = 1000
    for(t <- 1 to nofTests) {
      val a = randArrayInt(N)
      val sorted = a.sorted
      radixSort.sort(a)
      for(i <- 0 until N) {a(i) should be (sorted(i))}
    }
  }


  "The radixSortPar.sort method" should "work correctly" in {
    val nofTests = 100
    val N = 1000
    for(t <- 1 to nofTests) {
      val a = randArrayInt(N)
      val sorted = a.sorted
      radixSortPar.sort(a)
      for(i <- 0 until N) {a(i) should be (sorted(i))}
    }
  }


  "Both methods" should "outperform scala.util.Sorting.quickSort" in {
    val iter = 20
    val N = 40000000
    var scalaSortTime_sum = 0d
    var radixTime_sum = 0d
    var radixparTime_sum = 0d

    val qS = scala.util.Sorting
    val rS = radixSort
    val rSP = radixSortPar

    val a = randArrayInt(N)


    var t1 = 0d
    for(_ <- 1 to iter) {
      val temp = a.clone
      val t = measureTime { qS.quickSort(temp) }
      t1 += t
    }

    var t2 = 0d
    for(_ <- 1 to iter) {
      val temp = a.clone
      val t = measureTime { rS.sort(temp) }
      t2 += t
    }

    var t3 = 0d
    for(_ <- 1 to iter) {
      val temp = a.clone
      val t = measureTime { rSP.sort(temp) }
      t3 += t
    }

    t1 = t1/iter
    t2 = t2/iter
    t3 = t3/iter

    val a1 = t1/t2
    val a2 = t1/t3

    println("On average")
    println(f"  quickSort:     $t1%1.3f \n" +
            f"  radixSort:     $t2%1.3f \n" +
            f"  radixSortPar:  $t3%1.3f \n" +
            f"  $a1%1.3f  $a2%1.3f")

    /*
    for(t <- 1 to nofTests) {
      println(f"Test $t")
      val a = randArrayInt(N)
      val b = a.clone
      val c = a.clone

      val scalaSortTime = measureTime { qS.quickSort(a) }
      val radixTime = measureTime { rS.sort(b) }
      val radixParTime = measureTime { rSP.sort(c) }

      scalaSortTime_sum += scalaSortTime
      radixTime_sum += radixTime
      radixparTime_sum += radixParTime

      for(i <- 0 until N) {a(i) should be (b(i))}
      for(i <- 0 until N) {a(i) should be (c(i))}

      val s1 = scalaSortTime/radixTime
      val s2 = scalaSortTime/radixParTime

      println(f"  quickSort:     $scalaSortTime%1.3f \n" +
              f"  radixSort:     $radixTime%1.3f \n" +
              f"  radixSortPar:  $radixParTime%1.3f\n" +
              f" $s1%1.3f  $s2%1.3f")
    }


    val scalaSort_avg = scalaSortTime_sum / nofTests
    val radix_avg = radixTime_sum / nofTests
    val radixPar_avg = radixparTime_sum / nofTests

    val s1 = scalaSort_avg/radix_avg
    val s2 = scalaSort_avg/radixPar_avg

    radix_avg should be <= scalaSort_avg
    radixPar_avg should be <= scalaSort_avg
    radixPar_avg should be <= radix_avg

    println("On average")
    println(f"  quickSort:     $scalaSort_avg%1.3f \n" +
            f"  radixSort:     $radix_avg%1.3f \n" +
            f"  radixSortPar:  $radixPar_avg%1.3f\n" +
            f" $s1%1.3f  $s2%1.3f")
    */


  }

}