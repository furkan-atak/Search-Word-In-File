package searchwordinfile;

/*
 * @file SearchWordInFile
 * @description Girilen kelimenin verilen dosya yolunda aranarak, hangi dosyada kaç defa olduğunu bulma.
 * @assignment odev2
 * @date 26/05/2020
 * @author Furkan ATAK - furkanatak98@gmail.com
 */
public class TreeNode<T extends Comparable<T>> {

    T data;
    TreeNode<T> leftChild;
    TreeNode<T> rightChild;
    LinkedList<T> list;
    
    public TreeNode(T data) {
        this.data = data;
    }   
}
