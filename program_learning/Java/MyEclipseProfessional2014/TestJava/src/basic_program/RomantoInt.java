package basic_program;

import java.util.HashMap;

public class RomantoInt {
    public int romanToInt(String s) {
        HashMap<Character,Integer> map = new HashMap<Character,Integer>();
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);
        map.put('L',50);
        map.put('C',100);
        map.put('D',500);
        map.put('M',1000);
        int finalnum = 0;
        char[] romannum = s.toCharArray();
        for(int i=0;i<romannum.length;i++) {
            if(i==romannum.length-1)
                finalnum += map.get(romannum[i]);
            else {
                if(map.get(romannum[i])<map.get(romannum[i+1]))
                    finalnum -= map.get(romannum[i]);
                else
                    finalnum += map.get(romannum[i]);
            }
        }
        return finalnum;
    }
}