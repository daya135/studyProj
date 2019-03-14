package org.jzz.study.algorithm;

import java.util.Arrays;

import org.jzz.study.util.Print;

/* 皇后问题 */
public class queenproblem {
	static final int SIZE = 8; //棋盘大小
	static final int valid = 0; //当前格子值为0时可以设置皇后
	static final int invalid = -1; //放置皇后，将攻击范围内的所有格子值减1，撤销则加1
	static final int hasQueen = 1; //当前格子值为1时表示有1个皇后
	
	/** 皇后问题矩阵解法 */
	public static void MatrixSolution() {
		int[][] table = new int[SIZE][SIZE]; // 棋盘
		int[] queen = new int[SIZE]; //皇后位置
		int lastQueen = -1;	//记录上一次某行的皇后放在哪一列，回溯用
		int solution = 0;	//解法
		//初始化
		for (int[] t : table) {
			Arrays.fill(t, valid);
		}
		Arrays.fill(queen, -1);
		
		int i = 0;
		while (i < SIZE) {
			int j = searchLine(table, i, lastQueen + 1);
			if(j == -1) {
				i -= 1;
				if(i < 0)
					break;
				lastQueen = queen[i];	//记录本层在第几列放置过皇后，回溯时以此为起始向后查找
				setTable(table, queen, i, queen[i], valid);	//撤销皇后，回溯
//				printTable(table);
			} else {
				lastQueen = -1;
				setTable(table, queen, i, j, invalid);	//放置皇后
//				printTable(table);
				if (i == SIZE - 1) {
					printTable(table);
					solution ++;
					setTable(table, queen, i, j, valid);	//找出一种解法后，回退寻找其他可行方案
					lastQueen = j;
					i--;
				}
				i++;
			}
		}
		Print.print(solution);
	}
	//搜寻皇后位置，以当前行上一次的皇后位置为起始向后查找
	private static int searchLine(int[][] table, int i, int j) {
		for(; j < SIZE; j++) {
			if(table[i][j] == valid) {
				return j;
			}
		}
		return -1;
	}
	
	/** 设置/撤销皇后，并修改攻击范围内的状态 
	 *  增加皇后, 则攻击范围所有格子值减1
	 *  撤销皇后, 则攻击范围所有格子值加1
	 *  皇后位置的格子值固定为1
	 *  可以放置皇后的格子值为0
	 */
	private static void setTable(int[][] table, int queen[], int row, int col, int value) {
		value = value == invalid ? value : -invalid; //判断是撤销还是放置皇后
		
		//行列设置值
		for (int i = 0; i < SIZE; i++) {
			table[i][col] += value;
			table[row][i] += value;
		}
		
		//皇后位置设置值
		if (value == invalid) {
			table[row][col] = hasQueen;
			queen[row] = col;
		} else {
			table[row][col] = valid;
		}
		
		//对角线设置值
		int i = row;
		int j = col;
		while (--i >= 0 && --j >= 0) {
			table[i][j] += value;
		}
		i = row;
		j = col;
		while (++i < SIZE && ++j < SIZE) {
			table[i][j] += value;
		}
		i = row;
		j = col;
		while (--i >= 0 && ++j < SIZE) {
			table[i][j] += value;
		}
		i = row;
		j = col;
		while (++i < SIZE && --j >= 0) {
			table[i][j] += value;
		}
	}
	
	static int count = 0;
	public static void recursionSolution() {
		int queen[] = new int[SIZE]; //index:第几行的皇后，value：位于第几列
		Arrays.fill(queen, -1);
		findSpace(queen, 0);
	}
	
	//判断冲突
	private static boolean available(int[] queen, int row, int col) {
		for(int i = 0; i < row; i++){ //遍历已经放好的皇后
	        if(col == queen[i]) return false; //同一列拒绝
	        if((row - i) == (col - queen[i])) return false; //同一主对角线拒绝(左上到右下)
	        if((row - i) == (queen[i] - col)) return false; //同一副对角线拒绝
	    }
		return true;
	}
	
	//queenIdx ：列
	private static void findSpace(int[] queen, int queenIdx) {
		for (int i = 0; i < SIZE; i++) {	//扫描1～8列
			if (available(queen, queenIdx, i)) {	//查找当前行的合法位置
				queen[queenIdx] = i;  //如果本行有合法位置则记录下第queenIdx个皇后的位置
				if(queenIdx == SIZE - 1) {	//皇后放满了
					count++;
					Print.PrintIntArr(queen);
					return;
				}
				int nextIdx = queenIdx + 1; //皇后位置
				findSpace(queen, nextIdx);  //查找下一个皇后位置
			}
		}
		if(queenIdx == 0) {
			Print.print("解法数量:" + count);
		} else {
//			queen[--queenIdx] = -1; //撤销上一个皇后，实际上这句话不影响运行结果！！因为available函数没用这个值作判断
		}
	}
	
	static void printTable(int[][] table) {
		Print.PrintIntArr(table);
		System.out.println("************************");
	}
	
	public static void main(String[] args) {
//		MatrixSolution();
		recursionSolution();
	}
	
}
