package hipravin.samples.interview;

import java.util.Optional;
import java.util.OptionalInt;

public class StackMin {
    Node head = null;
    Node min = null;

    public void push(int value) {
        Node newHead = new Node(value, head);
        if (min == null || min.getValue() >= value) {
            newHead.setPrevMin(min);
            min = newHead;
        }
        head = newHead;
    }

    public OptionalInt pop() {
        if (head == null) {
            return OptionalInt.empty();
        } else {
            if (min == head) {
                min = head.getPrevMin();
            }
            var headValue = head.getValue();
            head = head.getPrev();
            return OptionalInt.of(headValue);
        }
    }

    public OptionalInt min() {
        if (min == null) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(min.getValue());
        }
    }

    static class Node {
        private int value;
        private Node prev;
        private Node prevMin;

        public Node(int value, Node prev) {
            this.value = value;
            this.prev = prev;
        }

        public int getValue() {
            return value;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getPrevMin() {
            return prevMin;
        }

        public void setPrevMin(Node prevMin) {
            this.prevMin = prevMin;
        }
    }

    public static void main(String[] args) {
        StackMin sm = new StackMin();

        sm.push(1);
        sm.push(2);
        sm.push(3);
        sm.push(0);
        sm.push(0);

        System.out.println(sm.min() + ", pop: " + sm.pop());
        System.out.println(sm.min() + ", pop: " + sm.pop());
        System.out.println(sm.min() + ", pop: " + sm.pop());
        System.out.println(sm.min() + ", pop: " + sm.pop());
        System.out.println(sm.min() + ", pop: " + sm.pop());
        System.out.println(sm.min() + ", pop: " + sm.pop());
        System.out.println(sm.min() + ", pop: " + sm.pop());

    }

}
