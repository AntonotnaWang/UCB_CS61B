public class AD_test {
    public static void main(String[] args) {
        ArrayDeque a = new ArrayDeque();
        a.addFirst(1);
        a.addFirst(2);
        a.addLast(3);
        a.addLast(4);
        a.addLast(5);
        a.addFirst(7);

        System.out.println(a.toString());

        a.removeFirst();
        System.out.println(a.toString());

        a.removeLast();
        System.out.println(a.toString());

        a.removeLast();
        System.out.println(a.toString());

        ArrayDeque b = new ArrayDeque(a);
        System.out.println(b.toString());

        for (int i = 0; i < b.size(); i++) {
            System.out.println(b.get(i) + " " + b.size());
        }

        for (int i = 0; i < 100; i++) {
            if (i%2==0) {
                b.addFirst(i);
            }
            else {
                b.addLast(i);
            }
            System.out.println(b.toString() + " " + b.usedMemory());
        }

        for (int i = 0; i < 100; i++) {
            if (i%2==0) {
                b.removeFirst();
            }
            else {
                b.removeLast();
            }
            System.out.println(b.toString() + " " + b.usedMemory());
        }

        System.out.println(b.toString()+" "+a.toString());
    }
}
