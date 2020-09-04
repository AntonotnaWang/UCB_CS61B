public class LLD_test {
    public static void main(String[] args) {
        LinkedListDeque<String> L = new LinkedListDeque<String>();
        L.addFirst("B");
        L.removeLast();
        L.removeLast();
        L.removeFirst();
        L.addFirst("C");
        L.addFirst("D");
        L.addFirst("E");
        L.printList();
        System.out.println(L.size());
        System.out.println(L.get(0));

        LinkedListDeque<Integer> L2 = new LinkedListDeque<Integer>(L);
        L2.addLast(23);
        L2.printList();
        System.out.println(L2.getRecursive(45));
    }
}
