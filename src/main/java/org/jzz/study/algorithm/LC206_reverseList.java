package org.jzz.study.algorithm;

import java.util.Stack;

import org.jzz.study.util.Print;

public class LC206_reverseList {
	/** 自己写的2b版本，递归 */
	public static ListNode reverseList(ListNode head) {
        if (head== null || head.next == null) {
            return head;
        }
        ListNode node = reverseList(head.next);
        ListNode p = node;
        while (p.next != null) {	//笨方法找到尾节点
        	p = p.next;
        }
        p.next = head;
        head.next = null;
        return node;
    }
	
	/** 正常版本，递归， 这个很难理解，建议查询知乎https://zhuanlan.zhihu.com/p/86745433*/
	public static ListNode reverseList_2(ListNode head) {
        if (head== null || head.next == null) {
            return head;
        }
        ListNode node = reverseList_2(head.next);
        head.next.next = head;	//让head后的节点（即反转后的链表尾节点）指向head自身
        head.next = null;	//将当前节点next置空，为尾节点
        return node;
    }
	
	/** 使用迭代反转链表 */
	public static ListNode reverseList_3(ListNode head) {
		if (head == null || head.next == null) return head;
		ListNode cur = head;
		ListNode pre = null;
		ListNode temp = null;
		while (cur.next != null) {
			temp = cur.next;
			cur.next = pre;
			pre = cur;
			cur = temp;
		}
		cur.next = pre;
		return cur;
	}
	
	/** 堆栈法*/
	public static ListNode reverseList_stack(ListNode head) {
		Stack<ListNode> stack = new Stack<ListNode>();
		while (head != null) {
			stack.push(head);
			head = head.next;
		}
		if (stack.isEmpty()) return null;
		ListNode node = stack.pop();
		ListNode p = node;
		while (!stack.isEmpty()) {
			p.next = stack.pop();
			p = p.next;
		}
		p.next = null;
		return node;
	}
	
	public static void main(String[] args) {
		ListNode root = ListNode.createList(new Integer[] {1,2,3,4,5});
		ListNode tail = reverseList_2(root);
		while(tail != null) {
			Print.print(tail.val);
			tail = tail.next;
		}
	}
}
