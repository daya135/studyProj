package org.jzz.study.algorithm;

import org.jzz.study.util.Print;

/** 
 * 判断是否回文链表
 * 时间复杂度O(n)
 * 空间复杂度O(1)
 * */
public class LC_IsPalindromeList {
	
	/** 判断回文*/
	public static boolean isPalindrome(ListNode head) {
		if (head == null || head.next == null) return true;
		ListNode half = halfOfList(head);
		ListNode tail = reverseList(half.next);
		ListNode tail_bak = tail;
		half.next = null;
		boolean isPalindrome = true;
		while (head != null && tail != null) {
			if (head.val != tail.val) {
				isPalindrome = false;
				break;
			}
			head = head.next;
			tail = tail.next;
		}
		ListNode halfNext = reverseList(tail_bak);
		half.next = halfNext;
		return isPalindrome;
	}
	
	/** 反转链表,（一学就会一写就废）、*/
	public static ListNode reverseList(ListNode head) {
		if (head == null || head.next == null) return head;
		ListNode node = reverseList(head.next);
		head.next.next = head;
		head.next = null;
		return node;
	}
	
	/** 找到链表的一半位置
	 * 奇数节点则返回中点前一个
	 */
	public static ListNode halfOfList(ListNode head) {
		ListNode first = head;
		ListNode second = head;
		while (first.next != null && first.next.next != null) {
			first = first.next.next;
			second = second.next;
		}
		return second;
	}
	
	public static void main(String[] args) {	
		ListNode node = ListNode.createList(new int[]{1, 2, 3, 2, 1});
		Print.print(isPalindrome(node));
		node.print();
	}
}
