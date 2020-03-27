package org.ore.framework.common.math;

import java.math.BigDecimal;

public class DoubleCompare {
	public int compare(BigDecimal val1, BigDecimal val2) {
		int result = 0;
		if (val1.compareTo(val2) < 0) {
			// result = "第二位数大！";
			result = -1;
		}
		if (val1.compareTo(val2) == 0) {
			// result = "两位数一样大！";
			result = 0;
		}
		if (val1.compareTo(val2) > 0) {
			// result = "第一位数大！";
			result = 1;
		}
		return result;
	}

	public int compare(Double data1, Double data2) {

		// 普通比较
		// Double obj1 = new Double("0.001");
		// Double obj2 = new Double("0.0011");
		// obj1.compareTo(obj2);

		// 精度准确比较
		BigDecimal val1 = new BigDecimal(data1);
		BigDecimal val2 = new BigDecimal(data2);

		int result = 0;
		if (val1.compareTo(val2) < 0) {
			// result = "第二位数大！";
			result = -1;
		}
		if (val1.compareTo(val2) == 0) {
			// result = "两位数一样大！";
			result = 0;
		}
		if (val1.compareTo(val2) > 0) {
			// result = "第一位数大！";
			result = 1;
		}
		
		return result;
	}

	public static void main(String[] args) {
		double a = 0.01;
		double b = 0.001;
		BigDecimal data1 = new BigDecimal(a);
		BigDecimal data2 = new BigDecimal(b);
		System.out.print(new DoubleCompare().compare(data1, data2));
	}
}