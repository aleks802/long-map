package de.comparus.opensource.longmap;

import java.util.Objects;
import java.util.StringJoiner;

public class Entry<Long, V> {

    final Long key;
    V value;
    Entry<Long, V> next;

    public Entry(Long key, V value, Entry<Long, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public Long getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Entry<Long, V> getNext() {
        return next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry<?, ?> entry = (Entry<?, ?>) o;
        return Objects.equals(key, entry.key) &&
                Objects.equals(value, entry.value) &&
                Objects.equals(next, entry.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, next);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Entry.class.getSimpleName() + "[", "]")
                .add("key=" + key)
                .add("value=" + value)
                .add("next=" + next)
                .toString();
    }
}
