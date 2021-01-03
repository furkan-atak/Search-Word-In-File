package searchwordinfile;


/*
 * @file SearchWordInFile
 * @description Girilen kelimenin verilen dosya yolunda aranarak, hangi dosyada kaç defa olduğunu bulma.
 * @assignment odev2
 * @date 26/05/2020
 * @author Furkan ATAK - furkanatak98@gmail.com
 */
public class Test {

    public static void main(String[] args){
        BinarySearchTree<String> bst = new BinarySearchTree<>();
        bst.createTree();
        bst.searchedWords("data structures");

    }
}

