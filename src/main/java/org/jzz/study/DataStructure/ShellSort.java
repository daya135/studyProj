package org.jzz.study.DataStructure;

import org.jzz.study.util.Print;

//分类 -------------- 内部比较排序
//数据结构 ---------- 数组
//最差时间复杂度 ---- 根据步长序列的不同而不同。已知最好的为O(n(logn)^2)
//最优时间复杂度 ---- O(n)
//平均时间复杂度 ---- 根据步长序列的不同而不同。
//所需辅助空间 ------ O(1)
//稳定性 ------------ 不稳定
public class ShellSort {
	
	public static void shellSort(int A[], int step){
		//当step = 1时退化为直接插入排序
        for (int i = step; i < A.length; i++) {	//分组，请注意当i/step > 1时会重叠为一组
            int j = i - step;
            int temp = A[i];
          //这段循环即为同一组内的直接插入排序（每组为已经排好序的序列）
            while (j >=0 && A[j] > temp){	//每组为已经排好序的序列
                A[j + step] = A[j];	//元素后移
                j = j - step;
            }
            A[j + step] = temp;	//找到插入位置，插入元素
        }
        System.out.println("step=" + step);
        Print.PrintIntArr(A);
	}

	static void sort(int[] a, int[] steps) {
		for (int step : steps) {
			shellSort(a, step);
		}
	}

	public static void main(String[] args){
	    int A[] = { 10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2}; // 从小到大希尔排序
	    int[] steps = {7,3,1};
		sort(A, steps);
	}
}

/* 总结：这段代码跟直观上的逻辑有所区别，不是分完组后分别将每组整个排好序
 * 分析一次for循环：
 * 将序列分为了step个组，每组为n/step个元素
 * 当i < step * 2 时，完成所有组的前两个元素排序
 * 当 step*2 <= i < step*3 时，完成所有组的前3个元素排序
 * ...
 * i = n时完成一次排序，调整step大小进行下一次循环
 */