package sorts

import java.util.concurrent.{ForkJoinTask,RecursiveTask,ForkJoinPool,ForkJoinWorkerThread}

object radixSortPar {

  private val forkJoinPool = new ForkJoinPool()

  def task[T](func: => T): ForkJoinTask[T] = {
    val task = new RecursiveTask[T] {
      def compute: T = {
        func
      }
    }
    Thread.currentThread match {
      case hy: ForkJoinWorkerThread =>
        task.fork()
      case _ =>
        forkJoinPool.execute(task)
    }
    task
  }


  def calcCount(a: Array[Int], shift: Int, P: Int): Array[Array[Int]] = {

    // mask to cover first 8 bits, i.e., ...000011111111
    val mask = 255
    // possible number of different 8-bit sequnces
    val N = 256
    // size of the partions
    val partion = a.length/P

    def count(partionedCount: Array[Array[Int]], t: Int, n: Int): Unit =
    {
      val start = t*n
      val c = new Array[Int](N)
      var i = start
      while(i < start+n)
      {
        c((a(i) >> shift) & mask) += 1
        i += 1
      }
      partionedCount(t) = c
    }

    val partionedCount = new Array[Array[Int]](P)

    val startingPairs = (0 to P).toArray zip Array.fill(P){partion}
    val tasks = startingPairs.map({case(t, n) => task {  count(partionedCount, t, n)  }})
    tasks.foreach(t => t.join())

    partionedCount
  }



  def calcIndex(partionedCount: Array[Array[Int]], P: Int): Array[Array[Int]] = {

    // possible number of different 8-bit sequnces
    val N = 256

    val partionedIndex = Array.ofDim[Int](P, N)
    var m = 0
    while(m < partionedCount.length) {
      var n = 0
      while (n < partionedCount.head.length) {
        var i = 0
        while (i < n) {
          var j = 0
          while (j < P) {
            partionedIndex(m)(n) += partionedCount(j)(i); j += 1
          }
          i += 1
        }
        var j = 0
        while (j < m) {
          partionedIndex(m)(n) += partionedCount(j)(n); j += 1
        }
        n += 1
      }
      m += 1
    }
    partionedIndex
  }


  def organize(a: Array[Int], partionedIndex: Array[Array[Int]], shift: Int, P: Int): Array[Int] = {

    val len = a.length
    val temp = new Array[Int](len)
    val partion = len/P

    def organizePartion(start: Int, n: Int): Unit =
    {
      val go = ((start+0.0)*P)/len
      val partion = partionedIndex(go.toInt)
      var i = start
      while(i < start+n)
      {
        val num = (a(i) >> shift) & 255
        temp(partion(num)) = a(i)
        partion(num) += 1
        i += 1
      }
    }

    val startingPairs = (0 to P).toArray zip Array.fill(P){partion}
    val tasks = startingPairs.map({case(t, n) => task {  organizePartion(t*partion, n)  }})
    tasks.foreach(t => t.join())

    temp
  }


  def sort(a: Array[Int]): Unit = {

    var aux: Array[Int] = a
    val len: Int = aux.length
    val P: Int = forkJoinPool.getParallelism


    for (shift <- 0 to 24 by 8)
    {
      val partionedCount = calcCount(aux, shift, P)
      val partionedIndex = calcIndex(partionedCount, P)
      if(partionedIndex(0)(1) == len) return
      val temp = organize(aux, partionedIndex, shift, P)
      aux = temp
    }
    Array.copy(aux, 0, a, 0, len)
  }
}