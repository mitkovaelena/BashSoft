package dataStructures;

import contracts.SimpleOrderedBag;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class SimpleSortedList<T extends Comparable<T>> implements SimpleOrderedBag<T> {

    private static final int DEFAULT_SIZE = 16;

    private T[] innerCollection;
    private int size;
    private Comparator<T> comparator;

    public SimpleSortedList(Class<T> type, Comparator<T> comparator, int capacity) {
        this.initializeInnerCollection(type, capacity);
        this.comparator = comparator;
    }

    public SimpleSortedList(Class<T> type, int capacity) {
        this(type, Comparable::compareTo, capacity);
    }

    public SimpleSortedList(Class<T> type, Comparator<T> comparator) {
        this(type, comparator , DEFAULT_SIZE);
    }

    public SimpleSortedList(Class<T> type) {
        this(type, Comparable::compareTo, DEFAULT_SIZE);
    }

    @SuppressWarnings("unchecked")
    private void initializeInnerCollection(Class<T> type, int capacity) {
        if(capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        this.innerCollection = (T[]) Array.newInstance(type, capacity);
    }

    @Override
    public boolean remove(T element) {

        if(element == null) {
            throw new IllegalArgumentException();
        }

        boolean hasBeenRemoved = false;
        int indexOfRemovedElement = 0;

        for (int i = 0; i < this.size(); i++) {
            if(this.innerCollection[i].compareTo(element) == 0) {
                indexOfRemovedElement = i;
                this.innerCollection[i] = null;
                hasBeenRemoved = true;
                break;
            }
        }

        if(hasBeenRemoved) {
            for (int i = indexOfRemovedElement; i < this.size() - 1; i++) {
                this.innerCollection[i] = this.innerCollection[i + 1];
            }
            this.innerCollection[this.size()-1] = null;
            this.size--;
        }

        return hasBeenRemoved;
    }

    @Override
    public int capacity() {
        return this.innerCollection.length;
    }

    @Override
    public void add(T element) {

        if(element == null) {
            throw new IllegalArgumentException();
        }

        if (size >= this.innerCollection.length) {
            this.resize();
        }
        this.innerCollection[this.size()] = element;
        this.size++;
        Arrays.sort(this.innerCollection, 0, this.size(), this.comparator);
    }

    @Override
    public void addAll(Collection<T> elements) {

        if(elements == null) {
            throw new IllegalArgumentException();
        }

        if(this.size() + elements.size() >= this.innerCollection.length) {
            this.multiResize(elements);
        }

        for (T element : elements) {
            this.innerCollection[this.size()] = element;
            this.size++;
        }
        Arrays.sort(this.innerCollection, 0, this.size(), this.comparator);
    }

    private void multiResize(Collection<T> elements) {
        int newSize = this.innerCollection.length * 2;

        while (this.size() + elements.size() >= newSize) {
            newSize *= 2;
        }

        T[] newCollection = Arrays.copyOf(
                this.innerCollection,
                newSize
        );
        this.innerCollection = newCollection;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String joinWith(String joiner) {

        if(joiner == null) {
            throw new IllegalArgumentException();
        }

        StringBuilder output = new StringBuilder();

        for (T t : this) {
            output.append(t);
            output.append(joiner);
        }
        output.setLength(output.length() - joiner.length());
        return output.toString();
    }

    @Override
    public Iterator<T> iterator() {

        Iterator<T> iterator = new Iterator<T>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public T next() {
                return innerCollection[this.index++];
            }
        };

        return iterator;
    }

    private void resize() {
        T[] newCollection = Arrays.copyOf(
                this.innerCollection,
                this.innerCollection.length*2
        );

        this.innerCollection = newCollection;
    }

//    public static void main(String[] args) {
//        String[] TEST_STRING_NAMES =  new String[] {"Pesho", "Georgi", "Balkan"};
//        SimpleOrderedBag<String> strings = new SimpleSortedList<>(String.class);
//        strings.addAll(Arrays.asList(TEST_STRING_NAMES));
//        strings.remove("Pesho");
//        strings.remove("Pesho");
//    }
}
