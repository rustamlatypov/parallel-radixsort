package tests
import org.scalatest._
import sorts._

class ParallelRadixSort extends FlatSpec with Matchers {
  val rand = new scala.util.Random(1000)

  def randArrayInt(n: Int): Array[Int] = {
    require(n > 0)
    Array.tabulate[Int](n)(j => rand.nextInt(Int.MaxValue))
  }


  def measureTime[T](f: => T): Double = {
    val start = System.nanoTime
    f
    val end = System.nanoTime
    val t = 1e-9 max (end - start) / 1000000000.0
    t
  }


  "The radixSort.sort method" should "work correctly" in {
    val nofTests = 10
    val N = 100
    for(t <- 1 to nofTests) {
      val a = randArrayInt(N)
      val sorted = a.sorted
      radixSort.sort(a)
      for(i <- 0 until N) {a(i) should be (sorted(i))}
    }
  }


  "The radixSortPar.sort method" should "work correctly" in {
    val nofTests = 10
    val N = 100
    for(t <- 1 to nofTests) {
      val a = randArrayInt(N)
      val sorted = a.sorted
      radixSortPar.sort(a)
      for(i <- 0 until N) {a(i) should be (sorted(i))}
    }
  }


  "Both methods" should "outperform scala.util.Sorting.quickSort" in {
    val nofTests = 5
    val N = 1000000
    println("Evaluating efficiency\n")
    var scalaSortTime_sum = 0d
    var radixTime_sum = 0d
    var radixparTime_sum = 0d

    for(t <- 1 to nofTests) {
      println(f"Test $t")
      val a = randArrayInt(N)
      val b = a.clone
      val c = a.clone

      val scalaSortTime = measureTime { scala.util.Sorting.quickSort(a) }
      val radixTime = measureTime { radixSort.sort(b) }
      val radixParTime = measureTime { radixSortPar.sort(c) }

      scalaSortTime_sum += scalaSortTime
      radixTime_sum += radixTime
      radixparTime_sum += radixParTime

      for(i <- 0 until N) {a(i) should be (b(i))}
      for(i <- 0 until N) {a(i) should be (c(i))}
      radixTime should be <= scalaSortTime
      radixParTime should be <= scalaSortTime

      println(f"  quickSort:     $scalaSortTime%1.5f \n" +
              f"  radixSort:     $radixTime%1.5f \n" +
              f"  radixSortPar:  $radixParTime%1.5f\n")
    }

    val scalaSort_avg = scalaSortTime_sum/nofTests
    val radix_avg = radixTime_sum/nofTests
    val radixPar_avg = radixparTime_sum/nofTests


    println("On average")
    println(f"  quickSort:     $scalaSort_avg%1.5f \n" +
            f"  radixSort:     $radix_avg%1.5f \n" +
            f"  radixSortPar:  $radixPar_avg%1.5f")
  }
}

