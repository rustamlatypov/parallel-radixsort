# Sequential and parallel LSD radix sorts

Developed in April, 2019.

## Description

Sequential and parallel implementations of LSD radix sort. Sequential radix sort is a rather straightforward algorithm. Parallel radix sort is parallelized both in the counting phase and the reorganizing phase. Multicore processing is achieved using Java's ForkJoinTask framework.

Both algorithms can be tested and benchmarked against Scala's own implementation of quicksort, i.e., `scala.util.Sorting.quickSort` by running `main.scala`. This script checks the validity of the algorithms and times them algorithms against quicksort using System.nanoTime. The algorithms sort 32-bit signed integers by grouping them into 8-bit sized batches.


## Software prerequisites

Built with Scala 2.12.8 and JDK 1.8.0_202. All required packages are included in `/common`.


## Results

macOS Movaje 10.14.6 <br/>
Intel Core i5-7600 Kaby Lake 3.5 GHz <br/>
Average scores for 10 runs with **n** sized input arrays:

```
n = 10 000 000
quickSort:	0.763 
radixSort:      0.121	speedup: 6.33
radixSortPar:   0.070   speedup: 11.0
  
n = 30 000 000
quickSort:      2.496    
radixSort:      0.415	speedup: 6.02
radixSortPar:   0.235   speedup: 10.6

n = 50 000 000
quickSort:      4.244	
radixSort:      0.773	speedup: 5.49
radixSortPar:   0.352   speedup: 12.1

n = 80 000 000
quickSort:      7.054 
radixSort:      1.221	speedup: 5.78
radixSortPar:   0.547	speedup: 12.9
  
n = 100 000 000
quickSort:      8.891		 
radixSort:      1.540	speedup: 5.77
radixSortPar:   0.680	speedup: 13.1

```

<img src="https://github.com/rustamlatypov/parallel-radixsort/blob/master/R/Rplot.png" width="650">


The logarithmic plot shows how the running times develop. The speedup increases with **n** as it should, since on average quicksort runs in **Θ(nlog(n))** and radix sort in **Θ(nk)**, where k is 4 in this case.


## Author

[Rustam Latypov](mailto:rustam.latypov@aalto.fi)
