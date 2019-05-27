package com.dextercodelab;

import java.util.Arrays;
import java.util.stream.IntStream;

public class WithoutGpuExample {
	public static void main(String[] args) {
		final int size = 100000;
		final int[] a = IntStream.range(2, size + 2).toArray();
		final boolean[] primeNumbers = new boolean[size];

		long startTime = System.currentTimeMillis();
		for (int n = 0; n < size; n++) {
			int num = a[n];
			boolean prime = true;
			for (int i = 2; i < num; i++) {
				if (num % i == 0) {
					prime = false;
					// not using break for a fair comparision
				}
			}
			primeNumbers[n] = prime;
		}
		System.out.printf("time taken: %s ms%n", System.currentTimeMillis() - startTime);
		System.out.println(Arrays.toString(Arrays.copyOf(primeNumbers, 20)));// just print a sub array
	}
}
