import static com.sun.xml.internal.fastinfoset.util.KeyIntMap.indexFor;


public class MyHashMap<K, V> {

    private int threshold = 15;
    private Entry<K, V>[] table;
    private int size;

    public MyHashMap() {
        table = new Entry[threshold];
    }

    public V put(K key, V value) {
        if (key == null)
            return putForNullKey(value);
        int hash = hash(key.hashCode());
        int position = indexFor(hash, table.length);
        for (Entry<K, V> entry = table[position]; entry != null; entry = entry.next) {
            Object k;
            if (entry.hash == hash && ((k = entry.key) == key || key.equals(k))) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        size++;
        addEntry(hash, key, value, position);
        return value;
    }

    public V get(Object key) {
        if (key == null)
            return getForNullKey();
        int hash = hash(key.hashCode());
        for (Entry<K, V> entry = table[indexFor(hash, table.length)]; entry != null; entry = entry.next) {
            Object k;
            if (entry.hash == hash && ((k = entry.key) == key || key.equals(k)))
                return entry.value;
        }
        return null;
    }

    public int size() {
        return size;
    }


    private V putForNullKey(V value) {
        for (Entry<K, V> entry = table[0]; entry != null; entry = entry.next) {
            if (entry.key == null) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        size++;
        addEntry(0, null, value, 0);
        return value;
    }

    private V getForNullKey() {
        if (size == 0) {
            return null;
        }
        for (Entry<K, V> e = table[0]; e != null; e = e.next) {
            if (e.key == null)
                return e.value;
        }
        return null;
    }

    private void addEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K, V> entry = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, entry);
        if (size >= threshold)
            resize(2 * table.length);
    }

    private void resize(int newSize) {
        threshold = newSize;
        Entry<K, V>[] oldTable = this.table;
        table = new Entry[newSize];
        size = 0;

        for (int i = 0; i < oldTable.length; i++) {
            for (Entry<K, V> entry = oldTable[i]; entry != null; entry = entry.next) {
                put(entry.key, entry.value);
            }
        }
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;
        int hash;

        public Entry(int hash, K key, V value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.hash = hash;
        }
    }
}
