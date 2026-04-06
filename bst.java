import java.util.*;
import java.io.*;

// ==========================================
// BST DENGAN INPUT FILE CSV
// ==========================================
public class bst {

    // ===== NODE =====
    static class Node {
        int id;
        String nama;
        Node left, right;

        Node(int id, String nama) {
            this.id = id;
            this.nama = nama;
        }
    }

    // ===== BST =====
    static class BST {
        Node root;

        // INSERT
        Node insert(Node node, int id, String nama) {
            if (node == null) return new Node(id, nama);

            if (id < node.id)
                node.left = insert(node.left, id, nama);
            else if (id > node.id)
                node.right = insert(node.right, id, nama);

            return node;
        }

        // LOAD CSV
        void loadCSV(String fileName) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;

                br.readLine(); // skip header

                while ((line = br.readLine()) != null) {
                    String[] data;

                    // support ; atau ,
                    if (line.contains(";"))
                        data = line.split(";");
                    else
                        data = line.split(",");

                    int id = Integer.parseInt(data[0].trim());
                    String nama = data[1].trim();

                    root = insert(root, id, nama);
                }

                System.out.println("Semua data berhasil dimasukkan ke BST!");

            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }

        // SEARCH
        Node search(Node node, int id) {
            if (node == null || node.id == id) return node;
            return (id < node.id) ? search(node.left, id) : search(node.right, id);
        }

        // DELETE
        Node min(Node node) {
            while (node.left != null) node = node.left;
            return node;
        }

        Node delete(Node node, int id) {
            if (node == null) return null;

            if (id < node.id)
                node.left = delete(node.left, id);
            else if (id > node.id)
                node.right = delete(node.right, id);
            else {
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;

                Node temp = min(node.right);
                node.id = temp.id;
                node.nama = temp.nama;
                node.right = delete(node.right, temp.id);
            }
            return node;
        }

        // TRAVERSAL (rapi)
        void inorder(Node node, List<String> list) {
            if (node != null) {
                inorder(node.left, list);
                list.add(node.id + " - " + node.nama);
                inorder(node.right, list);
            }
        }

        void preorder(Node node, List<String> list) {
            if (node != null) {
                list.add(node.id + " - " + node.nama);
                preorder(node.left, list);
                preorder(node.right, list);
            }
        }

        void postorder(Node node, List<String> list) {
            if (node != null) {
                postorder(node.left, list);
                postorder(node.right, list);
                list.add(node.id + " - " + node.nama);
            }
        }

        void printGrid(List<String> list) {
            int col = 3;
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%-30s", list.get(i));
                if ((i + 1) % col == 0) System.out.println();
            }
            System.out.println();
        }

        void showTraversal() {
            List<String> data = new ArrayList<>();

            System.out.println("\n=== INORDER ===");
            inorder(root, data);
            printGrid(data);

            System.out.println("\n=== PREORDER ===");
            data.clear();
            preorder(root, data);
            printGrid(data);

            System.out.println("\n=== POSTORDER ===");
            data.clear();
            postorder(root, data);
            printGrid(data);
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BST tree = new BST();

        int pilih;

        do {
            System.out.println("\n=== MENU BST (CSV) ===");
            System.out.println("1. Load Data dari CSV");
            System.out.println("2. Cari Data");
            System.out.println("3. Hapus Data");
            System.out.println("4. Tampilkan Traversal");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            pilih = sc.nextInt();

            switch (pilih) {
                case 1:
                    tree.loadCSV("data100.csv");
                    break;

                case 2:
                    System.out.print("Masukkan ID: ");
                    int cari = sc.nextInt();
                    Node hasil = tree.search(tree.root, cari);

                    if (hasil != null)
                        System.out.println("✔ Ditemukan: " + hasil.id + " - " + hasil.nama);
                    else
                        System.out.println("✖ Tidak ditemukan");
                    break;

                case 3:
                    System.out.print("Masukkan ID: ");
                    int hapus = sc.nextInt();
                    tree.root = tree.delete(tree.root, hapus);
                    System.out.println("✔ Data dihapus");
                    break;

                case 4:
                    tree.showTraversal();
                    break;

                case 0:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan salah!");
            }

        } while (pilih != 0);

        sc.close();
    }
}