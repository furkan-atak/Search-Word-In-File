package searchwordinfile;

/*
 * @file SearchWordInFile
 * @description Girilen kelimenin verilen dosya yolunda aranarak, hangi dosyada kaç defa olduğunu bulma.
 * @assignment odev2
 * @date 26/05/2020
 * @author Furkan ATAK - furkanatak98@gmail.com
 */
public class LinkedList<T extends Comparable<T>> {

    private Node<T> head;   
    
    Node<T> head(){
    return this.head;
    }
    
    void addFirst(Node<T> newNode) {
        if (isEmpty()) {
            head = newNode;
        } else {
            newNode.nextNode = head;
            head = newNode;
        }
    }

    void addFirst(T newData) {
        addFirst(new Node<T>(newData));
    }

    void addLast(Node<T> newNode) {
        if (isEmpty()) {
            head = newNode;
        } else {
            Node<T> temp = head;

            while (temp.nextNode != null) {
                temp = temp.nextNode;
            }

            temp.nextNode = newNode;
        }
    }

    void addLast(T newData) {
        addLast(new Node<>(newData));
    }

    boolean addAfter(T search, T newData) {
        if (isEmpty()) {
            System.out.println("Empty list !");
        } else {
            Node<T> temp = head;

            while (temp != null && !temp.data.equals(search)) {
                temp = temp.nextNode;
            }

            if (temp != null) {
                Node<T> newNode = new Node<>(newData);
                newNode.nextNode = temp.nextNode;
                temp.nextNode = newNode;
                return true;
            }
        }
        return false;
    }

    boolean searchData(T data) {
        if (head.data.equals(data)) {
            return true;
        } else {
            Node temp = head.nextNode;
            while (temp != null && !temp.data.equals(data)) {
                temp = temp.nextNode;
            }
            if (temp != null) {
                return true;
            }
        }

        return false;
    }
    
    Node<T> foundNode(T data) {
        if (head.data.equals(data)) {
            return head;
        } else {
            Node<T> temp = head.nextNode;
            while (temp != null && !temp.data.equals(data)) {
                temp = temp.nextNode;
            }
            if (temp != null) {
                return temp;
            }
        }

        return null;
    }

    Node<T> remove(T data) {
        Node<T> removedNode = null;

        if (isEmpty()) {
            System.out.println("Empty list !");
        } else {

            if (head.data.equals(data)) {
                removedNode = head;
                head = head.nextNode;
            } else {
                Node<T> temp = head;

                while (temp.nextNode != null && !temp.nextNode.data.equals(data)) {
                    temp = temp.nextNode;
                }

                if (temp.nextNode != null) {
                    removedNode = temp.nextNode;
                    temp.nextNode = temp.nextNode.nextNode;
                }
            }
        }

        return removedNode;
    }

    void print() {
        Node<T> temp = head;

        while (temp != null) {
            System.out.print(temp.data + " -> " + "frequency -> " + temp.frequency+ " ");
            temp = temp.nextNode;
        }

        System.out.println("null");
    }

    boolean isEmpty() {
        return head == null;
    }

    int size() {
        Node<T> temp = head;
        int count = 0;

        while (temp != null) {
            count++;
            temp = temp.nextNode;
        }

        return count;
    }
}
