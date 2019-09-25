# Sequential and parallel LSD radix sorts

Developed in April, 2019.

## Description

Implementations of the sequential and parallel LSD radix sorts. LSD radix sort is a rather straightforward and sequential algorithm. LSD parallel radix sort is parallelized both in the counting phase and the reorganizing phase. Multicore processing is achieved using Java's ForkJoinTask framework. 

Both algorithms are benchmarked against Scala's own implementation of quicksort, i.e., scala.util.Sorting.quickSort, using the tests included in /tests. After the validity of the algorithms is tested, System.nanoTime is used to time the algorithms and to compare them to Scala's quicksort.

## Results

OSX, Intel Core i5-7600 Kaby Lake 3.5 GHz. Average scores for 10 runs with ``n`` sized input arrays:

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


<img src="https://raw.githubusercontent.com/rustamlatypov/parallel-radixsort/master/R/Rplot.png" width="650">


From the logarithmic plot it can be infered that both radixSort and radixSortPar are proportional to quickSort and each other. Roughly, the runtime of quickSort is proportinal to radixSort with constant 6, and to radixSortPar with constant 13.

## Software prerequisites

Built on Scala 2.12.8 and JDK 1.8.0_202. All required packages are included in `/common`.


## Author

[Rustam Latypov](mailto:rustam.latypov@aalto.fi)
