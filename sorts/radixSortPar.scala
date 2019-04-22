package sorts

import java.util.concurrent.{ForkJoinTask,RecursiveTask,ForkJoinPool,ForkJoinWorkerThread}

object radixSortPar {

  private val forkJoinPool = new ForkJoinPool()

  // framework for parallel processes
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
    val partionlen = a.length/P

    def count(partionedCount: Array[Array[Int]], point: Int, partionlen: Int): Unit =
    {
      val aux = new Array[Int](N)
      val start = point*partionlen
      var i = start
      while(i < start+partionlen)
      {
        aux((a(i) >> shift) & mask) += 1
        i += 1
      }
      partionedCount(point) = aux
    }

    val partionedCount = new Array[Array[Int]](P)

    // initializing P number of independent function calls to compute counts
    val startingPoints = (0 until P).toArray
    val tasks = startingPoints.map({point => task {  count(partionedCount, point, partionlen)  }})
    tasks.foreach(t => t.join())

    partionedCount
  }



  def calcIndex(partionedCount: Array[Array[Int]], P: Int): Array[Array[Int]] = {

    // possible number of different 8-bit sequnces
    val N = 256

    // computing starting indices for each partionedCount array element
    // for parallelization to be possible in the organize-function,
    // while-loop jungle for efficiency
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

    // mask to cover first 8 bits, i.e., ...000011111111
    val mask = 255
    // size of the partions
    val partion = len/P

    def organizePartion(point: Int, partionlen: Int): Unit =
    {
      val aux = partionedIndex(point)
      val start = point*partionlen
      var i = start
      while(i < start+partionlen)
      {
        val num = (a(i) >> shift) & mask
        temp(aux(num)) = a(i)
        aux(num) += 1
        i += 1
      }
    }

    // initializing P number of independent function calls to reorganize array a
    val startingPairs = (0 until P).toArray
    val tasks = startingPairs.map({point => task {  organizePartion(point, partion)  }})
    tasks.foreach(t => t.join())

    temp
  }


  def sort(a: Array[Int]): Unit = {

    var aux: Array[Int] = a
    val len: Int = aux.length
    if (len <= 1) return

    val P: Int = forkJoinPool.getParallelism

    // sorting type Int with 32 bits, partioned in four groups
    for (shift <- 0 to 24 by 8)
    {
      val partionedCount = calcCount(aux, shift, P)
      val partionedIndex = calcIndex(partionedCount, P)
      val temp = organize(aux, partionedIndex, shift, P)
      aux = temp
    }

    Array.copy(aux, 0, a, 0, len)
  }
}