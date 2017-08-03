import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * Created by yurii on 03.08.17.
 */
public class Test {

    public static void main(String[] args) {
        MyHashMap<Integer, Long> myHashMap = new MyHashMap<>();
        Random random = new Random();
        ArrayList<Integer> keys = new ArrayList<>();

        for (int i = 0; i < 19; i++) {
            keys.add(i, random.nextInt());
            long v = random.nextLong();

            myHashMap.put(keys.get(i), v);
            System.out.println("put " + keys.get(i) + " " + v);
        }

        System.out.println("\nsize " + myHashMap.size() + "\n");

        for (int i = 0; i < keys.size(); i++) {
            System.out.println("get " + keys.get(i) + " result " + myHashMap.get(keys.get(i)));
        }
    }
}
