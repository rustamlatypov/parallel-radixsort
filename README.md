# Sequential and parallel LSD radix sorts

Developed in April, 2019.

## Description

Sequential and parallel implementations of LSD radix sort. Sequential radix sort is a rather straightforward algorithm. Parallel radix sort is parallelized both in the counting phase and the reorganizing phase. Multicore processing is achieved using Java's ForkJoinTask framework.

Both algorithms can be tested and benchmarked against Scala's own implementation of quicksort, i.e., `scala.util.Sorting.quickSort` by running `main.scala`. This script checks the validity of the algorithms and times them against quickSort using System.nanoTime. Both algorithms sort 32-bit signed integers by grouping them into 8-bit sized batches.


## Results

macOS Movaje 10.14.6 <br/>
Intel Core i5-7600 Kaby Lake 3.5 GHz <br/>
Runtimes with **n** sized input arrays:

```
n = 20 000 000
quickSort:	1.64
radixSort:	0.30	speedup: 5.47
radixSortPar:	0.14	speedup: 11.7

n = 30 000 000
quickSort:	2.53
radixSort:	0.41	speedup: 6.12
radixSortPar:	0.21	speedup: 12.1

n = 40 000 000
quickSort:	3.37
radixSort:	0.53	speedup: 6.42
radixSortPar:	0.27	speedup: 12.3

n = 50 000 000
quickSort:	4.54
radixSort:	0.62	speedup: 7.36
radixSortPar:	0.35	speedup: 13.1

n = 60 000 000
quickSort:	6.17
radixSort:	0.81	speedup: 7.67
radixSortPar:	0.42	speedup: 14.6
```

<img src="https://github.com/rustamlatypov/parallel-radixsort/blob/master/R/Rplot.png" width="650">


The logarithmic plot shows how the running times develop. The speedup increases with **n** as it should, since on average quicksort runs in **Θ(nlog(n))** and radix sort in **Θ(nk)**, where k is 4 in this case.


## Software prerequisites

Built with Scala 2.12.8 and JDK 1.8.0_202. All required packages are included in `/common`.

## Author

[Rustam Latypov](mailto:rustam.latypov@aalto.fi)
