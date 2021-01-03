package searchwordinfile;

/*
 * @file SearchWordInFile
 * @description Girilen kelimenin verilen dosya yolunda aranarak, hangi dosyada kaç defa olduğunu bulma.
 * @assignment odev2
 * @date 26/05/2020
 * @author Furkan ATAK - furkanatak98@gmail.com
 */
public class Heap<T extends Comparable<T>> {

    private Node<T> heap[];
    private int size;

    public Heap(int capacity) {
        this.heap = new Node[capacity];
    }

    void insert(Node<T> newNode) {
       boolean fileControl=false,control1 = true;
        int current = 0;
        // size büyükse liste uzunluğundan
        if(size > heap.length){
            if(search(newNode.data)){ // aynı türden var ise heapdeki ile sıklık arttırmak için control1 kosullu if e girer
            control1 = true;
            }else{       
                control1=false;
                System.out.println("heap is full");
            }
        }if (control1){
            // Heapi dönüyoruz
            for (int i = 0; i < heap.length; i++) { 
                if (heap[i] == null) {
                    break;
                } 
                // Eger aradigimiz dosyanin ismi heapde varsa
                else if (heap[i].data.equals(newNode.data)) {
                    // heap icindeki dosyanın frekansına ekle ve controlu bildir
                    heap[i].frequency += newNode.frequency;
                    fileControl = true;
                }
            }
            // eger dosyamız heapde yoksa, yeni dosyayı heapin sonuna ekle
            if (!fileControl) {
                heap[size] = newNode;
                current = size++;
            }
            // Kök ve child heaplerin kontrolleri
            while (heap[current].frequency > heap[parent(current)].frequency) {
                swap(current, parent(current));
                current = parent(current);
            }
        }
         
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private void swap(int i, int j) {
        Node<T> temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    void printAllDocuments() {
        for (Node<T> node : heap) {
            if (node != null) {
                System.out.print(node.data + "(" + node.frequency + ")" + " -> ");
            }
        }
        System.out.println("");
    }

    boolean search(T search) {
        for (int i = 0; i < heap.length; i++) {
            if (heap[i].data.equals(search)) {
                return true;
            }
        }
        return false;
    }

}
