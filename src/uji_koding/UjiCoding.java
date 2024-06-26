package uji_koding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UjiCoding {
    private String Kode;
    private int Jumlah_halaman;
    private String member;
    private double total_bayar;

    // Getter dan Setter untuk Kode
    public String getKode() {
        return Kode;
    }

    public void setKode(String Kode) {
        this.Kode = Kode;
    }

    // Getter dan Setter untuk Jumlah_halaman
    public int getJumlah_halaman() {
        return Jumlah_halaman;
    }

    public void setJumlah_halaman(int Jumlah_halaman) {
        this.Jumlah_halaman = Jumlah_halaman;
    }

    // Getter dan Setter untuk Member
    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    // Getter dan Setter untuk Total_bayar
    public double getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(double total_bayar) {
        this.total_bayar = total_bayar;
    }

    // Metode untuk menghitung total bayar dengan diskon jika member
    public void hitungTotalBayar(double hargaPerHalaman) {
        double total = Jumlah_halaman * hargaPerHalaman;
        if (member.equalsIgnoreCase("Ya")) {
            total *= 0.975; // Mengurangi 2.5% untuk member
        }
        this.total_bayar = total;
    }

    // Metode untuk menyimpan data ke database
    public void simpanKeDatabase() {
        String url = "jdbc:mysql://localhost:3306/uji_coding";
        String user = "root";
        String password = "";

        String query = "INSERT INTO praktikum (Kode, Jumlah_halaman, member, total_bayar) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getKode());
            stmt.setInt(2, getJumlah_halaman());
            stmt.setString(3, getMember());
            stmt.setDouble(4, getTotal_bayar());

            stmt.executeUpdate();
            System.out.println("Data berhasil disimpan ke database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk membaca data dari database
    public void bacaDariDatabase() {
        String url = "jdbc:mysql://localhost:3306/uji_coding";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM praktikum";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (!rs.isBeforeFirst()) { // Memeriksa apakah ResultSet kosong
                System.out.println("'Data Kosong'");
            } else {
                while (rs.next()) {
                    String kode = rs.getString("Kode");
                    int jumlahHalaman = rs.getInt("Jumlah_halaman");
                    String member = rs.getString("member");
                    double totalBayar = rs.getDouble("total_bayar");

                    System.out.println("Kode: " + kode);
                    System.out.println("Jumlah halaman: " + jumlahHalaman);
                    System.out.println("Member: " + member);
                    System.out.println("Total bayar: " + totalBayar);
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk memperbarui data di database
    public void updateDatabase() {
        String url = "jdbc:mysql://localhost:3306/uji_coding";
        String user = "root";
        String password = "";

        String query = "UPDATE praktikum SET Jumlah_halaman = ?, member = ?, total_bayar = ? WHERE Kode = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, getJumlah_halaman());
            stmt.setString(2, getMember());
            stmt.setDouble(3, getTotal_bayar());
            stmt.setString(4, getKode());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Data berhasil diperbarui di database.");
            } else {
                System.out.println("Kode tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk menghapus data dari database
    public void hapusDariDatabase() {
        String url = "jdbc:mysql://localhost:3306/uji_coding";
        String user = "root";
        String password = "";

        String query = "DELETE FROM praktikum WHERE Kode = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getKode());

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data berhasil dihapus dari database.");
            } else {
                System.out.println("Kode tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UjiCoding uji = new UjiCoding();

        while (true) {
            System.out.println("Pilih operasi yang ingin dilakukan:");
            System.out.println("1. Create");
            System.out.println("2. Read");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Exit");
            System.out.print("Pilihan: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Membaca newline character

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan Kode: ");
                    uji.setKode(scanner.nextLine());

                    System.out.print("Masukkan Jumlah Halaman: ");
                    uji.setJumlah_halaman(scanner.nextInt());

                    scanner.nextLine(); // Membaca newline character

                    System.out.print("Apakah Anda member? (Ya/Tidak): ");
                    String memberInput = scanner.nextLine();
                    uji.setMember(memberInput);

                    System.out.print("Masukkan Harga per Halaman: ");
                    double hargaPerHalaman = scanner.nextDouble();

                    uji.hitungTotalBayar(hargaPerHalaman);

                    System.out.println("Kode: " + uji.getKode());
                    System.out.println("Jumlah halaman: " + uji.getJumlah_halaman());
                    System.out.println("Member: " + uji.getMember());
                    System.out.println("Total bayar: " + uji.getTotal_bayar());

                    // Simpan data ke database
                    uji.simpanKeDatabase();
                    break;

                case 2:
                    // Baca data dari database
                    uji.bacaDariDatabase();
                    break;

                case 3:
                    System.out.print("Masukkan Kode untuk memperbarui: ");
                    uji.setKode(scanner.nextLine());

                    System.out.print("Masukkan Jumlah Halaman baru: ");
                    uji.setJumlah_halaman(scanner.nextInt());

                    scanner.nextLine(); // Membaca newline character

                    System.out.print("Apakah Anda member? (Ya/Tidak): ");
                    memberInput = scanner.nextLine();
                    uji.setMember(memberInput);

                    System.out.print("Masukkan Harga per Halaman baru: ");
                    double hargaPerHalamanBaru = scanner.nextDouble();

                    uji.hitungTotalBayar(hargaPerHalamanBaru);

                    System.out.println("Kode: " + uji.getKode());
                    System.out.println("Jumlah halaman: " + uji.getJumlah_halaman());
                    System.out.println("Member: " + uji.getMember());
                    System.out.println("Total bayar: " + uji.getTotal_bayar());

                    // Update data di database
                    uji.updateDatabase();
                    break;

                case 4:
                    System.out.print("Masukkan Kode untuk menghapus: ");
                    uji.setKode(scanner.nextLine());

                    // Hapus data dari database
                    uji.hapusDariDatabase();
                    break;

                case 5:
                    System.out.println("Keluar dari program.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        }
    }
}
