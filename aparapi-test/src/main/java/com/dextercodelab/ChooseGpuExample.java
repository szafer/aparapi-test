package com.dextercodelab;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.aparapi.Kernel;
import com.aparapi.Range;
import com.aparapi.device.Device;
import com.aparapi.device.Device.TYPE;
import com.aparapi.internal.kernel.KernelManager;
import com.aparapi.internal.kernel.KernelPreferences;

public class ChooseGpuExample {
	public static void main(String[] args) {

		final int size = 150000;
		final int[] a = IntStream.range(2, size + 2).toArray();
		final boolean[] primeNumbers = new boolean[size];

		Kernel kernel = new Kernel() {
			@Override
			public void run() {
				int gid = getGlobalId();
				int num = a[gid];
				boolean prime = true;
				for (int i = 2; i < num; i++) {
					if (num % i == 0) {
						prime = false;
						// break is not supported
					}
				}
				primeNumbers[gid] = prime;
			}

			@Override
			public boolean isAllowDevice(Device _device) {
				if (_device.getDeviceId() == 441774992L)
					return false;
				else
					return super.isAllowDevice(_device);
			}
		};
		long startTime = System.currentTimeMillis();
		kernel.setExecutionModeWithoutFallback(Kernel.EXECUTION_MODE.GPU);
		KernelPreferences preferences = KernelManager.instance().getPreferences(kernel);
		Device choosen = null;
		Device intelGpu = Device.bestGPU();
		for (Device device : preferences.getPreferredDevices(kernel)) {
			if (device.getDeviceId() != intelGpu.getDeviceId() && device.getType() == TYPE.GPU) {
				choosen = device;
				break;
			}
		}
		System.out.println("----------");
		System.out.println(choosen);
		Range range = Range.create(choosen, size);
		kernel.execute(range);

		System.out.printf("time taken: %s ms%n", System.currentTimeMillis() - startTime);
		System.out.println(Arrays.toString(Arrays.copyOf(primeNumbers, 20)));// just print a sub array
		kernel.dispose();
	}
}
