package searchwordinfile;

/*
 * @file SearchWordInFile
 * @description Girilen kelimenin verilen dosya yolunda aranarak, hangi dosyada kaç defa olduğunu bulma.
 * @assignment odev2
 * @date 26/05/2020
 * @author Furkan ATAK - furkanatak98@gmail.com
 */
public class Node<T extends Comparable<T>> {

    T data;
    int frequency=1;
    Node<T> nextNode;

    public Node(T data) {
        this.data = data;
        this.nextNode = null;
    }
}
