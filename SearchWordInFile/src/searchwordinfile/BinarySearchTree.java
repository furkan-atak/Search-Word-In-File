package searchwordinfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/*
 * @file SearchWordInFile
 * @description Girilen kelimenin verilen dosya yolunda aranarak, hangi dosyada kaç defa olduğunu bulma.
 * @assignment odev2
 * @date 26/05/2020
 * @author Furkan ATAK - furkanatak98@gmail.com
 */
public class BinarySearchTree<T extends Comparable<T>> {

    private TreeNode<T> root;
    public int fileCount;

    // ağaç yapısını oluşturma metodu
    void createTree() {
        // yolu alının folder in dosyalarında gezinme
        File file = new File(chooseFile());
        fileCount = file.listFiles().length;
        for (int i = 0; i < fileCount; i++) {
            try {
                Scanner input = new Scanner(file.listFiles()[i]); // kelime kelime dosya okuma
                while (input.hasNext()) {
                    String word = input.next();
                    // tırnak vs gibi kelimeleri veya sonunda virgül nokta olanları ayırt etme
                    if (!isStringOnlyAlphabet(word) && word.length() > 1) { 
                        if (!Character.isAlphabetic(word.charAt(0)) && Character.isAlphabetic(word.charAt(1))) {
                            word = word.substring(1);
                        }
                        if (!Character.isAlphabetic(word.charAt(word.length() - 1))
                                && Character.isAlphabetic(word.charAt(word.length() - 2))) {
                            word = word.substring(0, word.length() - 1);
                        }
                    }
                    if (!isStringOnlyAlphabet(word)) { // kelime değilse input next e devam
                        continue;
                    } else {
                        word = word.toLowerCase();
                        // Treenode var mı kontrolü
                        if (search((T) word)) {
                            // Treenode un listesinde varsa dosya adı frequency arttırma
                            if (foundNode((T) word).list.searchData((T) file.listFiles()[i].getName())) {
                                foundNode((T) word).list.foundNode((T) file.listFiles()[i].getName()).frequency++;
                            } else {
                                // yoksa oluşturma ve listeye ekleme
                                Node<T> newNode = new Node<>((T) file.listFiles()[i].getName());
                                foundNode((T) word).list.addLast(newNode);
                            }
                        } else {
                            // Treenode yoksa oluşturma ve ona ait node listesi oluşturma
                            Node<T> newNode = new Node<T>((T) file.listFiles()[i].getName());
                            LinkedList<T> newList = new LinkedList();
                            TreeNode<T> treeNode = new TreeNode(word);
                            newList.addFirst(newNode);
                            treeNode.list = newList;
                            insert(treeNode);
                        }
                    }

                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BinarySearchTree.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
    // input olarak alınan kelimenin aranması
    void searchedWords(String words) {
        Heap heap = new Heap(fileCount);
        // çoklu kelime girilirse ona göre bir ekleme işlemi heap yapısına (kelime kelime listeleyerek)
        if (words.contains(" ")) {
            String[] wordList = words.toString().split(" ");
            for (int i = 0; i < wordList.length; i++) {
                if (search((T) wordList[i])) { // girdi olarak alınan kelime tree de mi kontrolü
                    Node<T> temp = foundNode((T) wordList[i]).list.head(); // treenode un listesine baştan başlama while da listeyi dolaşma
                    while (temp != null) {
                        heap.insert(temp);
                        temp = temp.nextNode;
                    }
                }
            }
        } else {
            // tek kelime girildiyse ona göre treede olan kelimenin listesi heap e ekleniyor
            if (search((T) words)) {
                Node<T> temp = foundNode((T) words).list.head();
                while (temp != null) {
                    heap.insert(temp);
                    temp = temp.nextNode;
                }

            }      
        }
         heap.printAllDocuments();
    }
   

     // JFileChooser ile userdan içindeki dosyalara ulaşmak için klasör seçmesini istiyoruz
    String chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(fileChooser);
        String folderPath = "";
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            folderPath = selectedFile.getAbsolutePath();
        }
        return folderPath;
    }
    // alınan string değeri kelimelerden mi oluşuyor metodu
    boolean isStringOnlyAlphabet(String str) {
        return ((str != null)
                && (!str.equals(""))
                && (str.matches("^[a-zA-Z]*$")));
    }
    // TreeNode bulma.

    private TreeNode<T> foundNode(T searchData) {
        if (root == null) {
            return null;
        } else {
            TreeNode<T> temp = root;

            while (temp != null) {
                if (searchData.compareTo(temp.data) > 0) {
                    temp = temp.rightChild;
                } else if (searchData.compareTo(temp.data) < 0) {
                    temp = temp.leftChild;
                } else {
                    return temp;
                }
            }
        }
        return null;
    }

    // iterative insert method
    void insert(T newData) {
        TreeNode<T> newTreeNode = new TreeNode<>(newData);

        if (isEmpty()) {
            root = newTreeNode;
        } else {
            TreeNode<T> temp = root;

            while (temp != null) {
                if (newData.compareTo(temp.data) > 0) {
                    if (temp.rightChild == null) {
                        temp.rightChild = newTreeNode;
                        return;
                    }

                    temp = temp.rightChild;
                } else if (newData.compareTo(temp.data) < 0) {
                    if (temp.leftChild == null) {
                        temp.leftChild = newTreeNode;
                        return;
                    }

                    temp = temp.leftChild;
                } else {
                    return;
                }
            }
        }
    }

    void insert(TreeNode newTreeNode) {

        if (isEmpty()) {
            root = newTreeNode;
        } else {
            TreeNode<T> temp = root;

            while (temp != null) {
                if (newTreeNode.data.compareTo(temp.data) > 0) {
                    if (temp.rightChild == null) {
                        temp.rightChild = newTreeNode;
                        return;
                    }

                    temp = temp.rightChild;
                } else if (newTreeNode.data.compareTo(temp.data) < 0) {
                    if (temp.leftChild == null) {
                        temp.leftChild = newTreeNode;
                        return;
                    }

                    temp = temp.leftChild;
                } else {
                    return;
                }
            }
        }
    }

    void insertRecursive(T newData) {
        root = insertRecursive(root, newData);
    }

    // recursive insert method
    private TreeNode<T> insertRecursive(TreeNode<T> node, T newData) {
        if (node == null) {
            return new TreeNode<>(newData);
        } else {
            if (newData.compareTo(node.data) > 0) {
                node.rightChild = insertRecursive(node.rightChild, newData);
            } else if (newData.compareTo(node.data) < 0) {
                node.leftChild = insertRecursive(node.leftChild, newData);
            }

            return node;
        }
    }

    // iterative search method
    boolean search(T searchData) {
        if (isEmpty()) {
            return false;
        } else {
            TreeNode<T> temp = root;

            while (temp != null) {
                if (searchData.compareTo(temp.data) > 0) {
                    temp = temp.rightChild;
                } else if (searchData.compareTo(temp.data) < 0) {
                    temp = temp.leftChild;
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    void preorder() {
        System.out.print("preorder : ");
        preorder(root);
        System.out.println("\n" + " " + "\n");
    }

    private void preorder(TreeNode node) {
        if (node != null) {
            if (node.list != null) {
                System.out.println(node.data + " Listesi: ");
                node.list.print();
            }
            System.out.print(node.data + " ");
            preorder(node.leftChild);
            preorder(node.rightChild);

        }
    }

    void inorder() {
        System.out.print("inorder : ");
        inorder(root);
        System.out.println();
    }

    private void inorder(TreeNode<T> node) {
        if (node != null) {
            inorder(node.leftChild);
            System.out.print(node.data + " ");
            inorder(node.rightChild);
        }
    }

    void postorder() {
        System.out.print("postorder : ");
        postorder(root);
        System.out.println();
    }

    private void postorder(TreeNode<T> node) {
        if (node != null) {
            postorder(node.leftChild);
            postorder(node.rightChild);
            System.out.print(node.data + " ");
        }
    }

    private boolean isEmpty() {
        return root == null;
    }

    // TODO: int sumRecursive
    int sumRecursive() {
        return sumRecursive(root);
    }

    private int sumRecursive(TreeNode<T> node) {
        // implement recursive method to find sum
        if (node != null) {
            if (node.data instanceof Number) {
                return (Integer) node.data + sumRecursive(node.leftChild) + sumRecursive(node.rightChild);
            }
        }

        return 0;
    }

    // TODO: int sizeRecursive
    int sizeRecursive() {
        return sizeRecursive(root);
    }

    private int sizeRecursive(TreeNode<T> node) {
        // implement recursive method to find size
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeRecursive(node.leftChild) + sizeRecursive(node.rightChild);
        }
    }

    // TODO: int fullTreeNodeCountRecursive (node with both left and right child)
    int fullTreeNodeCountRecursive() {
        return fullTreeNodeCountRecursive(root);
    }

    private int fullTreeNodeCountRecursive(TreeNode<T> node) {
        // implement recursive method to find full node count
        if (node == null) {
            return 0;
        } else {
            if (node.leftChild != null && node.rightChild != null) {
                return 1 + fullTreeNodeCountRecursive(node.leftChild) + fullTreeNodeCountRecursive(node.rightChild);
            } else {
                return 0 + fullTreeNodeCountRecursive(node.leftChild) + fullTreeNodeCountRecursive(node.rightChild);
            }
        }
    }

    T findMaxRecursive() {
        return findMaxRecursive(root);
    }

    // BST, max element located at right most side of the tree
    private T findMaxRecursive(TreeNode<T> node) {
        if (node.rightChild != null) {
            return findMaxRecursive(node.rightChild);
        }

        return node.data;
    }

    int height() {
        return height(root);
    }

    // find height of the tree, recursive
    private int height(TreeNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return 1 + Math.max(height(node.leftChild), height(node.rightChild));
        }
    }
}
