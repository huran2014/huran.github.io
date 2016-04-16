package stczwd.collectiontest;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class CollectionIterator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<Integer> list = new LinkedList<Integer>(Arrays.asList(1,2,3,4,5));
		viewList(list);
//		list.removeFirst();
		list.removeLast();
		viewList(list);
	}
	public static void viewList(LinkedList list)
	{
		Iterator it= list.iterator();
		while(it.hasNext())
		{
			System.out.print(it.next()+"\t");
		}
		System.out.println();
	}
}
