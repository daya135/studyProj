package org.jzz.study.algorithm;

import org.jzz.study.util.Print;

/** leetcode 链表节点定义 */
public class ListNode {
     public int val;
     public ListNode next;
     public ListNode(int x) { val = x; }
     public ListNode(int x, ListNode next) {
    	 this.val = x;
    	 this.next = next;
     }
     
     public static ListNode createList(Integer[] nums) {
    	 ListNode head = new ListNode(0);
    	 ListNode p = head;
    	 for (int num : nums) {
    		 p.next = new ListNode(num);
    		 p = p.next;
    	 }
    	 return head.next;
     }
     
     public static ListNode createList(int[] nums) {
    	 ListNode head = new ListNode(0);
    	 ListNode p = head;
    	 for (int num : nums) {
    		 p.next = new ListNode(num);
    		 p = p.next;
    	 }
    	 return head.next;
     }
     
     public void print() {
    	 ListNode node = this;
    	 while (node != null) {
    		 System.out.print(node.val + " ");
    		 node = node.next;
    	 }
    	 System.out.println();
     }
}
