public class ArrayDeque<T> {
    private T[] items;
    private int left;
    private int right;
    private int capacity = 8;
    public ArrayDeque() {
        items = (T[]) new Object[capacity];
        left = right = 0;
    }
    private void resize(int newsize) {
        T[] newAlist = (T[]) new Object[newsize];
        int size = size();
        //复制元素到新数组 正常数组顺序
        if (left < right) {
            for (int i = left, j = 0; i < right && j < size; i++, j++) {
                newAlist[j] = items[i];
            }
        } else if (left > right) {
            int j = 0;
            for (int i = left; j < capacity - left; i++, j++) {
                newAlist[j] = items[i];
            }
            for (int i = 0; j < size; i++, j++) {
                newAlist[j] = items[i];
            }
        }
        left = 0;
        right = size;
        items = newAlist;
        capacity = newsize;
    }
    private boolean isFull() {
        return size() == capacity - 1;
    }
    public void addFirst(T item) {
        if (isFull()) {
            resize((int) (capacity * 1.5));
        }
        left = (left - 1 + capacity) % capacity;
        items[left] = item;
    }
    public  void addLast(T item) {
        if (isFull()) {
            resize((int) (capacity * 1.5));
        }
        items[right] = item;
        right = (right + 1 + capacity) % capacity;
    }
    public  boolean isEmpty() {
        return  left == right;
    }
    public int size() {
        return (right - left + capacity) % capacity;
    }
    public  void printDeque() {
        if (left < right) {
            for (int i = left; i < right; i++) {
                if (i == right - 1) {
                    System.out.println(items[i]);
                    break;
                }
                System.out.print(items[i] + " ");
            }
        } else if (left > right) {
            for (int i = left; i < capacity; i++) {
                System.out.print(items[i] + " ");
            }
            for (int i = 0; i < right; i++) {
                if (i == right - 1) {
                    System.out.println(items[i]);
                    break;
                }
                System.out.print(items[i] + " ");
            }
        }
    }
    public T removeFirst() {
        if (isEmpty()) {
            return  null;
        }
        T res = items[left];
        left = (left + 1) % capacity;
        if (isLowUsageRate()) {
            resize((int) (capacity * 0.5));
        }
        return res;
    }
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        right = (right - 1 + capacity) % capacity;
        T res = items[right];
        if (isLowUsageRate()) {
            resize((int) (capacity * 0.5));
        }
        return res;
    }
    public T get(int index) {
        if (index < 0 || index >= size() || isEmpty()) {
            return null;
        }
        if (left < right) {
            return items[index + left];
        } else if (left > right) {
            if (index + left < capacity) {
                return items[index + left];
            } else {
                return items[(index + left) % capacity];
            }
        }
        return null;
    }
    private boolean isLowUsageRate() {
        return capacity >= 16 && size() / (double) capacity < 0.25;
    }

}
