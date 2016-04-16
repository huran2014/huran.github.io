package stczwd.collectiontest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapIterator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer, String> map=new HashMap<Integer, String>();
		map.put(1, "1");
		map.put(2, "2");
		map.put(3, "3");
		map.put(4, "4");
		map.put(5, "5");
		map.put(6, "6");
		for(Entry<Integer, String> m : map.entrySet())
		{
			System.out.println(m.getValue().getClass());
		}
	}

}
