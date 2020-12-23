package org.jzz.study.algorithm;

import org.jzz.study.util.Print;

/** 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。 */
public class LC148_sortList {

	 static ListNode createList(int[] nums) {
		 if(nums == null) return null;
		 ListNode head = new ListNode(0);	//果然，为了方便循环逻辑，还是得先建一个虚拟头节点，这样后面就能直接使用head.next当作返回头节点
		 ListNode p = head;
		 for (int i : nums) {
			p.next = new ListNode(i);
			p = p.next;
		 }
		 return head.next;
	 }
	 
	 static ListNode merge(ListNode head) {
		 if (head == null || head.next == null) return head;
		 ListNode fast = head.next;
		 ListNode slow = head;
		 //使用快慢指针法分割链表，奇数个节点找到中点，偶数个节点找到中心左边的节点。
		 while (fast != null && fast.next != null) {
			 slow = slow.next;
			 fast = fast.next.next;
		 }
		 ListNode temp = slow.next;
		 slow.next = null;	//断开前半截
		 ListNode left = merge(head);	//这个递归太骚了，直接获取归并完成的前半截
		 ListNode right = merge(temp);	//获取归并完成的后半截
		 ListNode h = new ListNode(0); 
		 ListNode p = h;
		 //归并操作
		 while (left != null && right != null) {
			 if (left.val <= right.val) {
				 p.next = left;
				 left = left.next;
			 } else {
				p.next = right;
				right = right.next;
			}
			 p = p.next;
		 }
		 p.next = left != null ? left : right;
		 return h.next;	
	 }
	 
	 
	 public static void main(String[] args) {
		ListNode root = createList(new int[]{1,2,4,5,3});
		merge(root);
		while (root != null) {
			Print.print(root.val);
			root = root.next;
		}
	 }
}
