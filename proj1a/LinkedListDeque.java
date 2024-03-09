public class LinkedListDeque<T> {
    private class LLDNode {
        public T item;
        public LLDNode next;
        public LLDNode prev;
        public LLDNode(T i) {
            item = i;
        }
    }
    private LLDNode sentinel;
    private int size;
    public LinkedListDeque(){
        sentinel = new LLDNode(null);
        size = 0;
    }
    public void addFirst(T item){
        if (size == 0){
            LLDNode A = new LLDNode(item);
            A.prev = sentinel;
            A.next = sentinel;
            sentinel.next = A;
            sentinel.prev = A;
            size += 1;
        }
        else {
            LLDNode A = new LLDNode(item);
            A.next = sentinel.next.next;
            A.next.prev = A;
            sentinel.next = A;
            size += 1;
        }

    }
    public void addLast(T item){
        if (size == 0){
            LLDNode A = new LLDNode(item);
            A.prev = sentinel;
            A.next = sentinel;
            sentinel.next = A;
            sentinel.prev = A;
            size += 1;
        }
        else {
            LLDNode A = new LLDNode(item);
            A.prev = sentinel.prev;
            A.prev.next = A;
            A.next = sentinel;
            sentinel.prev = A;
            size += 1;
        }

    }

    public boolean isEmpty(){
        if(size == 0)
            return true;
        return false;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        if(size == 0){
            return;
        }
        else {
            for(int i = 0; i <= size - 1; i += 1){
                System.out.print(get(i) + " ");
            }
        }

    }
    public T removeFirst(){
        if(size == 0){
            return null;
        }
        else {
            T item = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return item;
        }

    }
    public T removeLast(){
        if(size == 0){
            return null;
        }
        else {
            T item = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return  item;
        }

    }
    public T get(int index){
        if (index < 0 || index >= size)
            return  null;
        else {
            LLDNode temp = sentinel;
            for(int i = 0; i <= index; i += 1){
                temp = temp.next;
            }
            return temp.item;
        }

    }
    public T getRecursive(int index){
        if(index < 0 || index >= size || sentinel.next == null)
            return null;
        else if(index == 0)
            return sentinel.next.item;
        else
            return getRecursiveHelper(sentinel.next, index);
    }
    private T getRecursiveHelper(LLDNode Node, int index){
        if(index == 0)
            return Node.item;
        else
            return getRecursiveHelper(Node.next, index - 1);
    }

}
