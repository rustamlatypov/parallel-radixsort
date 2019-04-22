package sorts


object radixSort {


  def sort(a: Array[Int]): Array[Int] = {

    // length of the array to be sorted
    val len = a.length
    // to cover first 8 bits, i.e., ...000011111111
    val mask = 255
    // possible number of different 8-bit sequnces
    val N = 256

    for (shift <- 0 to 24 by 8)
    {

      val count = new Array[Int](N)
      var i = 0
      while (i < len)
      {
        count((a(i) >> shift) & mask) += 1
        i += 1
      }

      if (count(0) == len - 1) return a
      val index = new Array[Int](N)

      var j = 0
      while (j < N-1)
      {
        index(j+1) = index(j) + count(j)
        j += 1
      }

      val temp = new Array[Int](len)

      var k = 0
      while (k < len)
      {
        val num = (a(k) >> shift) & mask
        temp(index(num)) = a(k)
        index(num) += 1
        k += 1
      }

      Array.copy(temp, 0, a, 0, len)
    }
    a
  }
}
