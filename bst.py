import csv

# ===== NODE =====
class Node:
    def __init__(self, id, nama):
        self.id = id
        self.nama = nama
        self.left = None
        self.right = None

# ===== BST =====
class BST:
    def __init__(self):
        self.root = None

    # INSERT
    def insert(self, root, id, nama):
        if root is None:
            return Node(id, nama)

        if id < root.id:
            root.left = self.insert(root.left, id, nama)
        elif id > root.id:
            root.right = self.insert(root.right, id, nama)

        return root

    # LOAD CSV
    def load_csv(self, filename):
        try:
            with open(filename, newline='', encoding='utf-8') as file:
                reader = csv.reader(file)

                next(reader)  # skip header

                for row in reader:
                    if len(row) < 2:
                        continue

                    # support ; atau ,
                    if ";" in row[0]:
                        row = row[0].split(";")

                    id = int(row[0].strip())
                    nama = row[1].strip()

                    self.root = self.insert(self.root, id, nama)

            print("✅ Data berhasil dimuat ke BST!")

        except Exception as e:
            print("❌ Error:", e)

    # SEARCH
    def search(self, root, id):
        if root is None or root.id == id:
            return root
        if id < root.id:
            return self.search(root.left, id)
        return self.search(root.right, id)

    # DELETE
    def min_value(self, root):
        while root.left:
            root = root.left
        return root

    def delete(self, root, id):
        if root is None:
            return root

        if id < root.id:
            root.left = self.delete(root.left, id)
        elif id > root.id:
            root.right = self.delete(root.right, id)
        else:
            if root.left is None:
                return root.right
            elif root.right is None:
                return root.left

            temp = self.min_value(root.right)
            root.id = temp.id
            root.nama = temp.nama
            root.right = self.delete(root.right, temp.id)

        return root

    # TRAVERSAL
    def inorder(self, root, result):
        if root:
            self.inorder(root.left, result)
            result.append(f"{root.id} - {root.nama}")
            self.inorder(root.right, result)

    def preorder(self, root, result):
        if root:
            result.append(f"{root.id} - {root.nama}")
            self.preorder(root.left, result)
            self.preorder(root.right, result)

    def postorder(self, root, result):
        if root:
            self.postorder(root.left, result)
            self.postorder(root.right, result)
            result.append(f"{root.id} - {root.nama}")

    # PRINT GRID
    def print_grid(self, data):
        col = 3
        for i, val in enumerate(data):
            print(f"{val:<30}", end="")
            if (i + 1) % col == 0:
                print()
        print()

    def show_traversal(self):
        data = []

        print("\n=== INORDER ===")
        self.inorder(self.root, data)
        self.print_grid(data)

        print("\n=== PREORDER ===")
        data.clear()
        self.preorder(self.root, data)
        self.print_grid(data)

        print("\n=== POSTORDER ===")
        data.clear()
        self.postorder(self.root, data)
        self.print_grid(data)


# ===== MAIN =====
bst = BST()

while True:
    print("\n=== MENU BST (PYTHON) ===")
    print("1. Load Data dari CSV")
    print("2. Cari Data")
    print("3. Hapus Data")
    print("4. Tampilkan Traversal")
    print("0. Keluar")

    pilih = input("Pilih: ")

    if pilih == "1":
        bst.load_csv("D:/Kuliah/Semester 4/Struktur Data/Praktikum/#6 BST/data100.csv")

    elif pilih == "2":
        id = int(input("Masukkan ID: "))
        hasil = bst.search(bst.root, id)

        if hasil:
            print(f"✔ Ditemukan: {hasil.id} - {hasil.nama}")
        else:
            print("✖ Tidak ditemukan")

    elif pilih == "3":
        id = int(input("Masukkan ID: "))
        bst.root = bst.delete(bst.root, id)
        print("✔ Data dihapus")

    elif pilih == "4":
        bst.show_traversal()

    elif pilih == "0":
        print("Program selesai.")
        break

    else:
        print("Pilihan tidak valid!")