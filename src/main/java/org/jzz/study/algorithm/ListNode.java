package org.jzz.study.algorithm;

/** leetcode 链表节点定义 */
public class ListNode {
     public int val;
     public ListNode next;
     public ListNode(int x) { val = x; }
     
     public static ListNode createList(Integer[] nums) {
    	 ListNode head = new ListNode(0);
    	 ListNode p = head;
    	 for (int num : nums) {
    		 p.next = new ListNode(num);
    		 p = p.next;
    	 }
    	 return head.next;
     }
}
