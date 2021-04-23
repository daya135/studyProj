package org.jzz.study.algorithm;

import org.jzz.study.util.Print;

/** 
 * 动态规划
 * 找到字符串S中最长的回文子串。
 * 首先循环找到S中的所有字串，有两种办法：
 * 1、固定左指针不动，逐渐增加右指针位置（增加长度），循环一圈后左指针右移一位
 * 2、固定长度不动，右指针=左指针+长度，逐渐右移左指针，循环一圈后长度+1
 * 显然第二种方更适合动态规划
 * P(i,j)=true/false (下标i,j之间的子串是否为回文串)
 * 状态转移方程：P(i,j)=P(i+1,j−1)∧(Si==Sj) 首尾各增加一个字符，确定更长子串是否为回文
 * 边界条件：P(i,i)=true;	P(i,i+1)=(Si==Si+1)
 * */
public class LongestPalindrome {
	static String findLongestPalindrome(String str) {
		String ans = "";
		int length = str.length();
		boolean[][] p = new boolean[length][length];	//保存子串是否为回文串，两个坐标分别为子串左右下标
		
		for (int l = 0; l < length; l++) {
			for (int i = 0; i + l < length; i++) {
				int j = i + l; //右指针
				if (l == 0) {
					p[i][j] = true; //长度为1，一定为回文串
				}else if (l == 1) {
					p[i][j] = str.charAt(i) == str.charAt(j);	
				} else {
					p[i][j] = p[i + 1][j - 1] && (str.charAt(i) == str.charAt(j));
				}
				if (p[i][j]) {
					ans = ans.length() < l + 1 ? str.substring(i, i + l + 1) : ans;
				}
			} 
		}
		
		return ans;
	}
	
	public static void main(String[] args) {
		String[] strs = {"cbbd","abad","aadcdab"};
		for (String s : strs) {
			Print.print(findLongestPalindrome(s));
		}
	}
}
