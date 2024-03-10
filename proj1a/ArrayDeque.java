public class ArrayDeque<T> {
    private T[] items;
    private int left;
    private int right;
    private int capacity = 8;
    public ArrayDeque(){
        items = (T[]) new Object[capacity];
        left = right = 0;
    }
    public void resize(int newsize){
        T[] newAlist = (T[]) new Object[newsize];
        int size = size();
        //复制元素到新数组 正常数组顺序
        if(left < right){
            for(int i = left,j = 0; i < right; i++, j++){
                newAlist[j] = items[i];
            }
        }
        //复制元素到新数组 考虑环绕的情况 分两次
        // 首先复制从 left 到数组末尾的元素，然后复制从数组开头到 right 的元素
        else if(left > right){
            int j = 0;
            for(int i = left; i < capacity; i++, j++){
                newAlist[j] = items[i];
            }
            for(int i = 0; j < size; i ++, j++){
                newAlist[j] = items[0];
            }
        }
        left = 0;
        right = size;
        items = newAlist;
        capacity = newsize;
    }
    public boolean isFull(){
        return size() == capacity;
    }
    public void addFirst(T item){
        if(isFull()){
            resize((int) (capacity * 1.5));
        }
        left = (left - 1 + capacity) % capacity;
        items[left] = item;
    }
    public  void addLast(T item){
        if (isFull()){
            resize((int) (capacity * 1.5));
        }
        items[right] = item;
        right = right + 1;
    }
    public  boolean isEmpty(){
        return  left == right;
    }
    public int size(){
        return (right - left + capacity) % capacity;
    }
    public  void printDeque(){
        if (left < right){
            for (int i = 0; i <= right - 1; i++){
                System.out.print(items[i] + " ");
            }
        }
        else if(left > right){
            for (int i = left; i <= capacity - 1; i++){
                System.out.print(items[i] + " ");
            }
            for (int i = 0; i <= right - 1; i++){
                System.out.print(items[i] + " ");
            }
        }
    }
    public T removeFirst(){
        if (isEmpty()){
            return  null;
        }
        T res = items[left];
        left = (left + 1) % capacity;
        if (isLowUsageRate()){
            resize((int) (capacity * 0.5));
        }
        return res;
    }
    public T removeLast(){
        if (isEmpty()){
            return null;
        }
        T res = items[right - 1];
        right = right - 1;
        if (isLowUsageRate()){
            resize((int) (capacity * 0.5));
        }
        return res;
    }
    public T get(int index){
        if (index < 0 || index >= size() || isEmpty()){
            return null;
        }
        if (left < right){
            return items[index];
        }
        else if(left >right){
            if (index + left < capacity){
                return items[index + left];
            }
            else {
                return items[index - left];
            }
        }
        return null;
    }
    public boolean isLowUsageRate(){
        if (capacity >= 16 && size() / capacity < 0.25){
            return true;
        }
        return false;
    }

}
