
public class ArrayDeque<dtype> {
    private dtype[] items;
    private int size;
    private int next_first_loc;
    private int next_last_loc;

    private static final int INIT_LEN = 8;
    private static final int RESIZE_FACTOR = 2;

    public ArrayDeque() {
        items = (dtype[]) new Object[INIT_LEN];
        size = 0;
        init_next_first_and_last_loc();
    }

    public ArrayDeque(dtype x) {
        items = (dtype[]) new Object[INIT_LEN];
        items[0] = x;
        size = 1;
        init_next_first_and_last_loc();
    }

    public ArrayDeque(ArrayDeque other) {
        items = arrayCopyOperation(other, (dtype[]) new Object[other.items.length]);
        size = other.size();
        init_next_first_and_last_loc();
    }

    private void init_next_first_and_last_loc() {
        next_first_loc = items.length-1;
        next_last_loc = size;
    }

    private dtype[] arrayCopyOperation(ArrayDeque source, dtype[] target) {
        if (source.next_first_loc == source.items.length - 1) {
            //copy source.items [0:source.next_last_loc] to target
            System.arraycopy(source.items, 0,
                    target, 0, source.next_last_loc);
        }
        else {
            if (source.next_first_loc >= source.next_last_loc) {
                //copy source.items [source.next_first_loc+1:source.items.length] to target
                System.arraycopy(source.items, source.next_first_loc+1,
                        target, 0, source.items.length-1-source.next_first_loc);
                //copy source.items [0:source.next_last_loc] to target
                System.arraycopy(source.items, 0,
                        target, source.items.length-1-source.next_first_loc, source.next_last_loc);
            }
            else {
                //copy source.items [source.next_first_loc+1:source.next_last_loc] to target
                System.arraycopy(source.items, source.next_first_loc+1,
                        target, 0, source.next_last_loc - 1 - source.next_first_loc);
            }
        }
        return target;
    }

    private void resize(int capacity) {
        items = arrayCopyOperation(this, (dtype[]) new Object[capacity]);
        init_next_first_and_last_loc();
    }

    private boolean isArrayFull() {
        return size >= items.length - 1;
    }

    private boolean isArrayTooLong() {
        if (items.length>=16) {
            return Double.valueOf(size) < Double.valueOf(0.25 * items.length);
        }
        return false;
    }

    private void resizeArrayWhenNeeded() {
        if (isArrayFull()) {
            resize(items.length * RESIZE_FACTOR);
        }
        else if (isArrayTooLong()) {
            resize(Integer.valueOf(items.length / RESIZE_FACTOR));
        }
    }

    public void addFirst(dtype x) {
        items[next_first_loc] = x;
        next_first_loc = change_next_first_loc(-1);
        size += 1;
        resizeArrayWhenNeeded();
    }

    public void addLast(dtype x) {
        items[next_last_loc] = x;
        next_last_loc = change_next_last_loc(1);
        size += 1;
        resizeArrayWhenNeeded();
    }

    public void removeFirst() {
        if (!isEmpty()) {
            next_first_loc = change_next_first_loc(1);
            items[next_first_loc] = null;
            size -= 1;
            resizeArrayWhenNeeded();
        }
        else {
            System.out.println("No item in the list");
        }
    }

    public void removeLast() {
        if (!isEmpty()) {
            next_last_loc = change_next_last_loc(-1);
            items[next_last_loc] = null;
            size -= 1;
            resizeArrayWhenNeeded();
        }
        else {
            System.out.println("No item in the list");
        }
    }

    private int change_next_first_loc(int change) {
        /*
        change = -1 means addFirst
        change = 1 means removeFirst
         */

        if (change == 1 && next_first_loc == items.length-1) {
            return 0;
        }
        else if (change == -1 && next_first_loc == 0) {
            return items.length-1;
        }
        else {
            return next_first_loc + change;
        }
    }

    private int change_next_last_loc(int change) {
        /*
        change = 1 means addLast
        change = -1 means removeLast
         */

        if (change == 1 && next_last_loc == items.length-1) {
            return 0;
        }
        else if (change == -1 && next_last_loc == 0) {
            return items.length-1;
        }
        else {
            return next_last_loc + change;
        }
    }

    public int size() {
        return size;
    }

    public int usedMemory() {
        return items.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public dtype get(int index) {
        if (index < 0) {
            index = 0;
        }
        else if (index > size-1) {
            index = size - 1;
        }

        if (!isEmpty()) {
            if (next_first_loc + index >= items.length - 1) {
                return items[index - (items.length - 1 - next_first_loc)];
            } else {
                return items[next_first_loc + 1 + index];
            }
        }
        else {
            return null;
        }
    }

    public dtype[] getAll() {
        return items;
    }

    public String toString() {
        String str;
        if (!isEmpty()) {
            str = "[";
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    str += get(i) + "]";
                } else {
                    str += get(i) + ", ";
                }
            }
        }
        else {
            str = "No item in the list";
        }
        return str;
    }
}

