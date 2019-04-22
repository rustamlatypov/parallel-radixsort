# LSD radix sort and parallel radix sort

Developed in April, 2019.

## Description

Implementations for LSD radix sort and parallel radix sort. LSD radix sort is rather straightforward algorithm. 
LSD parallel radix sort is parallelized both in the counting phase and the reorganizing phase. Parellelization is 
achieved using Java's ForkJoinTask framework. 

Both algorithms are benchmarked against Scala's own implementation of quicksort, i.e., scala.util.Sorting.quickSort, 
using the tests in /tests. After the validity of the algorithms is tested, System.nanoTime is utilized to time the 
algoritms and compare them to Scala's quicksort.



## Software prerequisites

Built on Scala 2.12.8 and JDK 1.8.0_202. All required packages are included in /lib.


## Authors

[Rustam Latypov](mailto:rustam.latypov@aalto.fi)