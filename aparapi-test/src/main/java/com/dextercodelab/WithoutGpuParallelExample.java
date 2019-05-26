package com.dextercodelab;

import java.util.Arrays;
import java.util.stream.IntStream;

public class WithoutGpuParallelExample {
	public static void main(String[] args) {
		final int size = 100000;
		final int[] a = IntStream.range(2, size + 2).toArray();

		long startTime = System.currentTimeMillis();
		Object[] primeNumbers = Arrays.stream(a).parallel().mapToObj(WithoutGpuParallelExample::isPrime).toArray();
		System.out.printf("time taken: %s ms%n", System.currentTimeMillis() - startTime);
		System.out.println(Arrays.toString(Arrays.copyOf(primeNumbers, 20)));// just print a sub array
	}

	private static boolean isPrime(int num) {
		boolean prime = true;
		for (int i = 2; i < num; i++) {
			if (num % i == 0) {
				prime = false;
				// not using break for a fair comparision
			}
		}
		return prime;
	}
}
