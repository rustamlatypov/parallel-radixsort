n = c(20000000, 30000000, 40000000, 50000000, 60000000)
quick = c(1.64, 2.53, 3.37, 4.54, 6.17)
radix = c(0.3, 0.41, 0.53, 0.62, 0.81)
radixPar = c(0.14, 0.21, 0.27, 0.35, 0.42)

data <- data.frame(n, quick, radix, radixPar)

par(mar = c(5,5,5,5))
plot(data$n, data$quick, type="b", xlab = "n", ylab = "seconds", bty = "n", log = "y",
     lwd = 1.6, cex = 1.2, cex.axis = 1.3, cex.lab = 1.3, ylim=c(0.1,7))
lines(data$n, data$radix, type="b", lwd = 1.6, cex = 1.2)
lines(data$n, data$radixPar, type="b", lwd = 1.6, cex = 1.2)

text(40200000, 4.4, "quickSort", cex = 1.3)
text(40000000, 0.7, "radixSort", cex = 1.3)
text(41000000, 0.36, "radixSortPar", cex = 1.3)

