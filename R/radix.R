n = c(1000000, 5000000, 10000000, 30000000, 50000000, 80000000, 100000000)
quick = c(0.07224, 0.38822, 0.76325, 2.49603, 4.24371, 7.05439, 8.89121)
radix = c(0.01250, 0.05962, 0.12054, 0.41451, 0.77241, 1.22147, 1.54018)
radixPar = c(0.00882, 0.03273, 0.06948, 0.23495, 0.35213, 0.54680, 0.68003)

data <- data.frame(n, quick, radix, radixPar)

par(mar = c(5,5,5,5))
plot(data$n, data$quick, type="b", xlab = "n", ylab = "seconds", bty = "n", log = "y",
     lwd = 1.6, cex = 1.2, cex.axis = 1.3, cex.lab = 1.3)
lines(data$n, data$radix, type="b", lwd = 1.6, cex = 1.2)
lines(data$n, data$radixPar, type="b", lwd = 1.6, cex = 1.2)

text(60000000, 6.7, "quickSort", cex = 1.3)
text(60000000, 1.25, "radixSort", cex = 1.3)
text(62200000, 0.59, "radixSortPar", cex = 1.3)
