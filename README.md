# LSD radix sort and parallel radix sort

Developed in April, 2019.

## Description

Implementations for LSD radix sort and parallel radix sort. LSD radix sort is rather straightforward algorithm. 
LSD parallel radix sort is parallelized both in the counting phase and the reorganizing phase. Parellelization is 
achieved using Java's ForkJoinTask framework. 

Both algorithms are benchmarked against Scala's own implementation of quicksort, i.e., scala.util.Sorting.quickSort, 
using the tests included in /tests. After the validity of the algorithms is tested, System.nanoTime is utilized to time the 
algoritms and to compare them to Scala's quicksort.

## Results

Average scores for 10 runs with different ``n`` sized input arrays:

```markdown

n = 1 000 000
quickSort:     0.07224
radixSort:     0.01250 
radixSortPar:  0.00882

n = 5 000 000
quickSort:     0.38822 
radixSort:     0.05962 
radixSortPar:  0.03273
  
n = 10 000 000
quickSort:     0.76325 
radixSort:     0.12054 
radixSortPar:  0.06948
  
n = 30 000 000
quickSort:     2.49603 
radixSort:     0.41451 
radixSortPar:  0.23495

n = 50 000 000
quickSort:     4.24371 
radixSort:     0.77241 
radixSortPar:  0.35213

n = 80 000 000
quickSort:     7.05439 
radixSort:     1.22147 
radixSortPar:  0.54680
  
n = 100 000 000
quickSort:     8.89121 
radixSort:     1.54018 
radixSortPar:  0.68003  

```

## Software prerequisites

Built on Scala 2.12.8 and JDK 1.8.0_202. All required packages are included in /lib.


## Author

[Rustam Latypov](mailto:rustam.latypov@aalto.fi)