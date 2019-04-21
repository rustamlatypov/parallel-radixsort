object radixSort {


  def countPar(a: Array[Int], n: Int, shift: Int, threshold: Int, P: Int): Array[Array[Int]] = {

    def inner(start: Int, n: Int): Array[Array[Int]] = {
      if(n <= threshold) {
        val count = new Array[Int](256)
        var i = start
        while(i < start+n) {
          count((a(i) >> shift) & 255) += 1
          i += 1
        }
        Array(count)
      } else {
        val N = n/2
        var r1 = Array.empty[Array[Int]]
        var r2 = Array.empty[Array[Int]]
        par.parallel(
          r1 = inner(start, N),
          r2 = inner(start+N, n-N)
        )
        r1++r2
      }
    }
    inner(0, n)
  }


  def calcindex(count: Array[Array[Int]], P: Int): Array[Array[Int]] = {

    val index = Array.ofDim[Int](P, 256)
    var m = 0
    while(m < count.length) {
      var n = 0
      while (n < count.head.length) {
        var i = 0
        while (i < n) {
          var j = 0
          while (j < P) {
            index(m)(n) += count(j)(i); j += 1
          }
          i += 1
        }
        var j = 0
        while (j < m) {
          index(m)(n) += count(j)(n); j += 1
        }
        n += 1
      }
      m += 1
    }
    index
  }


  def reorganize(a: Array[Int], n:Int, index: Array[Array[Int]], shift: Int,
                 threshold: Int, P: Int): Array[Int] = {

    val temp = new Array[Int](n)

    def inner(start: Int, n: Int): Unit = {
      if(n <= threshold) {
        val go = ((start+0.0)*P)/a.length
        val partion = index(go.toInt)
        var i = start
        while(i < start+n) {
          val num = (a(i) >> shift) & 255
          temp(partion(num)) = a(i)
          partion(num) += 1
          i += 1
        }
      } else {
        val N = n/2
        par.parallel(
          inner(start, N),
          inner(start+N, n-N)
        )
      }
    }
    inner(0, n)
    temp
  }


  def sortPar(a: Array[Int]): Unit = {
    var arr = a
    val P: Int = par.getParallelism
    val N = arr.length
    if(N <= 1) return
    val threshold = N/P

    for (shift <- 0 to 24 by 8) {
      val count = countPar(arr, N, shift, threshold, P)
      val index = calcindex(count, P)
      if(index(0)(1) == N) return
      val temp = reorganize(arr, N, index, shift, threshold, P)
      arr = temp
    }
    Array.copy(arr, 0, a, 0, N)
  }
}