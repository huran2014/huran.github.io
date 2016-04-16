package stczwd.collectiontest;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LinkedList_Collection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer,Collection<Integer>> map = new HashMap<>();
		LinkedList<Integer> list = new LinkedList<Integer>(Arrays.asList(1,2,3,4,5));
		map.put(0, list);
	}

}
