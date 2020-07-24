package de.comparus.opensource.longmap;

import java.util.ArrayList;

public class LongMapImpl<V> implements LongMap<V> {

    private Entry<Long, V>[] buckets;

    private int capacity;
    private int size = 0;
    private double lf = 0.75;


    public LongMapImpl() {
        this(10);
    }

    public LongMapImpl(int capacity) {
        this.capacity = capacity;
        this.buckets = new Entry[capacity];
    }

    public V put(long key, V value) {

        if (size == lf * capacity) {
            Entry<Long, V>[] old = buckets;

            capacity *= 2;
            size = 0;
            buckets = new Entry[capacity];

            for (Entry<Long, V> e : old) {
                while (e != null) {
                    put(e.key, e.value);
                    e = e.next;
                }
            }
        }
        Entry<Long, V> entry = new Entry<>(key, value, null);

        int bucket = getHash(key) % getBucketSize();
        Entry<Long, V> existingBucket = buckets[bucket];
        if (existingBucket == null) {
            buckets[bucket] = entry;
            size++;
        } else {
            while (existingBucket.next != null) {
                if (existingBucket.key.equals(key)) {
                    existingBucket.value = value;
                }
                existingBucket = existingBucket.next;
            }
            if (existingBucket.key.equals(key)) {
                existingBucket.value = value;
            } else {
                existingBucket.next = entry;
                size++;
            }
        }
        return value;
    }

    public V get(long key) {
        Entry<Long, V> bucket = buckets[getHash(key) % getBucketSize()];
        while (bucket != null) {
            if (bucket.key.equals(key)) {
                return bucket.value;
            }
            bucket = bucket.next;
        }
        return null;
    }

    public V remove(long key) {
        int bucket = getHash(key) % getBucketSize();
        V value = null;

        if (buckets[bucket] == null) {
            return null;
        } else {
            Entry<Long, V> previousBucket = null;
            Entry<Long, V> currentBucket = buckets[bucket];

            while (currentBucket != null) {
                if (currentBucket.key.equals(key)) {
                    if (previousBucket == null) {
                        buckets[bucket] = buckets[bucket].next;
                        value = currentBucket.getValue();
                    } else {
                        previousBucket.next = currentBucket.next;
                    }
                }
                previousBucket = currentBucket;
                currentBucket = currentBucket.next;
            }
            return value;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        if (key == 0) {
            return buckets[0].getKey() != null;
        }
        int entry = getHash(key) % getBucketSize();
        Entry<Long, V> existingBucket = buckets[entry];

        if (existingBucket.getNext() != null) {
            do {
                if (existingBucket.getKey() == key) {
                    return true;
                }
            } while (existingBucket.getNext() != null);
        }
        return existingBucket.getKey() != null;
    }

    public boolean containsValue(V value) {
        for (Entry<Long, V> bucket : buckets) {
            for (Entry<Long, V> entry = bucket; entry != null; entry = entry.next) {
                return true;
            }
        }
        return false;
    }

    public long[] keys() {
        long[] keys = new long[size];
        ArrayList<Long> list = new ArrayList<>();

        for (Entry<Long, V> bucket : buckets) {
            Entry<Long, V> entry = bucket;
            if (entry != null) {

                if (entry.next != null) {
                    for (; entry != null; entry = entry.next)
                        list.add(entry.key);
                } else {
                    list.add(entry.key);
                }
            }
        }
        for (int i = 0; i != list.size(); i++) {
            keys[i] = list.get(i);
        }
        return keys;
    }

    public V[] values() {
        String[] values = new String[size];
        ArrayList<String> list = new ArrayList<>();

        for (Entry<Long, V> bucket : buckets) {
            for (Entry<Long, V> entry = bucket; entry != null; entry = entry.next)
                list.add((String) entry.value);
        }
        for (int i = 0; i != list.size(); i++) {
            values[i] = list.get(i);
        }
        return (V[]) values;
    }

    public long size() {
        return size;
    }

    public void clear() {
        Entry<Long, V>[] entry;
        if ((entry = buckets) != null && size > 0) {
            size = 0;
            for (int i = 0; i < buckets.length; ++i)
                entry[i] = null;
        }
    }

    private int getBucketSize() {
        return buckets.length;
    }

    private int getHash(Long key) {
        return key == null ? 0 : Math.abs(key.hashCode());
    }
}
