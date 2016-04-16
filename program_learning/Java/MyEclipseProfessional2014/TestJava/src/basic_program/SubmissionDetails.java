package basic_program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.swing.tree.TreeNode;

public class SubmissionDetails {

	public static void main(String[] args) {

		List<List<Integer>> list = new ArrayList<>(10);
//		List<Integer> list1 = new ArrayList<>();
		System.out.println(list.get(9)==null);
//		System.out.println(Math.abs(1-Integer.MIN_VALUE));
//		singlyLinkedList list = new singlyLinkedList(new int[]{1});
//		singlyLinkedList list1 = new singlyLinkedList(new int[]{2});
//		list.showList();
//		list1.showList();
//		ListNode node = mergeTwoLists(list.root, list1.root);
//		while (node!=null) {
//			System.out.println(node.val);
//			node = node.next;
//		}
//		list.deleteDuplicates(list.root);
//		list.showList();
	}
	
	public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		ListNode head = new ListNode(0);
		ListNode node = head;
		while(l1!=null && l2 !=null) {
			if(l1.val<l2.val) {
				node.next = l1;
				node =node.next;
				l1=l1.next;
			}
			else {
				node.next = l2;
				node =node.next;
				l2=l2.next;
			}
		}
		node.next = l1==null? l2:l1;
		return head.next;
    }

}

//Definition for singly-linked list.
class ListNode {
	int val;
	ListNode next;
	ListNode(int x) { val = x; }
}

class singlyLinkedList {
	ListNode root = null;
	public singlyLinkedList(int[] num) {
		if (num.length>0) {
			ListNode node = null;
			for (int i = 0; i < num.length; i++) {
				if(i==0){
					root = new ListNode(num[i]);
					root.next = new ListNode(0);
					node = root;
				}
				else {
					node.val = num[i];
					node.next = new ListNode(0);
//					System.out.println(root.next.next.val);
				}
				node = node.next;
			}
			node=null;
		}
	}
	
	public ListNode deleteDuplicates(ListNode head) {
		if(head==null) return null;
		HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
		ListNode node = head;
		map.put(node.val,1);
		while(node.next!=null) {
			if(map.containsKey(node.next.val))
				node.next = node.next.next;
			else {
				map.put(node.next.val,1);
				node = node.next;
			}
		}
		return head;
	}
	
	public void showList()
	{
		ListNode node = root;
		while (node.next!=null) {
			System.out.print(node.val+"\t");
			node = node.next;
		}
		System.out.println();
	}
}