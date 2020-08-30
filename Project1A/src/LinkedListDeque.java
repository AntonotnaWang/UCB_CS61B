public class LinkedListDeque<dtype> {
    private class Node {
        public dtype item;
        public Node next;
        public Node before;

        public Node(dtype i, Node f, Node n) {
            item = i;
            before = f;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.before = sentinel;
        sentinel.next = sentinel.before;
        size = 0;
    }

    public LinkedListDeque(dtype x) {
        sentinel = new Node(null, null, null);
        sentinel.next = new Node(x, sentinel, sentinel);
        sentinel.before = sentinel.next;
        size = 1;
    }

    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new Node(null, null, null);
        sentinel.before = sentinel;
        sentinel.next = sentinel.before;
        size = 0;

        for (int i=0; i<other.size(); i++) {
            addLast((dtype) other.get(i));
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(dtype x) {
        size++;
        sentinel.next = new Node(x, sentinel, sentinel.next);
        sentinel.next.next.before = sentinel.next;
    }

    public void addLast(dtype x) {
        size++;
        sentinel.before = new Node(x, sentinel.before, sentinel);
        sentinel.before.before.next = sentinel.before;
    }

    public void removeFirst() {
        if (!isEmpty()) {
            size--;
            sentinel.next.next.before = sentinel;
            sentinel.next = sentinel.next.next;
        }
    }

    public void removeLast() {
        if (!isEmpty()) {
            size--;
            sentinel.before.before.next = sentinel;
            sentinel.before = sentinel.before.before;
        }
    }

    public dtype get(int loc) {
        int count = -1;
        Node p = sentinel;
        while (count<loc && p.next != null && p.next != sentinel) {
            count++;
            p = p.next;
        }
        return p.item;
    }

    public dtype getRecursive(int loc) {
        return getRecursiveHelper(sentinel, loc).item;
    }

    public Node getRecursiveHelper(Node start, int loc) {
        if (loc<=0 || start.next.next==sentinel) {
            return start.next;
        }
        else {
            loc--;
            return getRecursiveHelper(start.next, loc);
        }
    }

    public void printList() {
        Node p = sentinel;
        while (p.next != null && p.next != sentinel) {
            System.out.print(p.next.item);
            p = p.next;
        }
        System.out.println();
    }

    public int size() {
        return size;
    }

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
