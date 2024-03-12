public class demo {
    public static void main(String[] args) {
        Deque<Integer> List = new LinkedListDeque<>();
        List.addFirst(1);
        List.addLast(2);
        List.addLast(3);
        //List.printDeque();
        if(!List.isEmpty()) {
            List.printDeque();
        };
    }
}
