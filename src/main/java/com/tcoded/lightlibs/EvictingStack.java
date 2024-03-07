package com.tcoded.lightlibs;

import java.util.Iterator;

public class EvictingStack<T> implements Iterable<T> {

    private final T[] stack;
    private int next;
    private int bottom;

    public EvictingStack(int size) {
        // noinspection unchecked
        stack = (T[]) new Object[size];
        next = 0;
        bottom = 0;
    }

    public void push(T item) {
        if (next == bottom) {
            bottom = (bottom + 1) % stack.length;
        }

        stack[next] = item;

        next = (next + 1) % stack.length;
    }

    public T pop() {
        if (next == bottom) {
            return null;
        }

        next = (next - 1) % stack.length;
        return stack[next];
    }

    public T peek() {
        if (next == bottom) {
            return null;
        }

        return stack[(next - 1) % stack.length];
    }


    /**
     * Iterates starting from the top of the stack and ending at the bottom
     * @return An iterator that iterates over the stack (top to bottom)
     */
    @Override
    public Iterator<T> iterator() {
        return new StackIterator(stack.length, next);
    }

    private class StackIterator implements Iterator<T> {

        private final int end;
        private final int size;
        private int index;

        public StackIterator(int size, int endIndex) {
            this.end = endIndex;
            this.size = size;
            this.index = (endIndex - 1) % this.size;
        }

        @Override
        public boolean hasNext() {
            return index != end;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IllegalStateException("There are no elements left in the stack");
            }

            T item = stack[index];
            index = (index - 1) % size;
            return item;
        }
    }

}