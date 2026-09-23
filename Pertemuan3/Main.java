package Pertemuan3;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;
public class Main {
	public static void main(String[]args) {
		Scanner input =new Scanner(System.in);
		ArrayList<Rekening>daftarRekening =new ArrayList<>();
		Rekening akunAktif=null;//Objek belum diinisialisasi(null)
		boolean isRunning=true;
		DecimalFormat df=new DecimalFormat("#,###,0");
		
		System.out.println("=== SISTEM PERBANKAN MINI===");
		while(isRunning) {
			System.out.println("\nMenu Utama: ");
			System.out.println("1. Buka Rekening Baru");
			System.out.println("2. Setor Tunai");
			System.out.println("3. Tarik Tunai");
			System.out.println("4. Cek Informasi Rekening");
			System.out.println("5. Ganti Akun");
			System.out.println("6.Cetak Mutasi");
			System.out.println("7.Cek Transaksi");
			System.out.println("0. Keluar");
			System.out.println("Pilih Menu: ");
			
			int pilihan=input.nextInt();
			input.nextLine(); //Membersihkan buffer enter
			switch ( pilihan){
				case 1:
					 System.out.println("Masukkan No Rekening: ");
					 String no = input.nextLine();
					 System.out.println("Masukkan Nama Pemilik: ");
					 String nama = input.nextLine();
					 System.out.println("Masukkan PIN(Harus 6 Digit)");
					 String pin= input.nextLine();
					 while (!pin.matches("\\d{6}")) {
						 System.out.println("Pin harus terdiri dari 6 angka! ");
						 System.out.println("Masukkan PIN(Harus 6 Digit)");
						 pin=input.nextLine();
					 }
					 System.out.println("Masukkan Saldo Awal: ");
					 double saldo = input.nextDouble();
					 Rekening rekeningBaru = new Rekening(no, nama, saldo,pin);
					 daftarRekening.add(rekeningBaru);
					 akunAktif = rekeningBaru;

					 System.out.println("Rekening berhasil ditambahkan.");
					 break;
					
				case 2:
					if(akunAktif==null) {
						System.out.println("Error: Mohon maaf,anda belum memiliki nomor rekening!");
					}else {
						System.out.println("Masukkan nominal setor: ");
						double setor=input.nextDouble();
						akunAktif.setorTunai(setor);//Memanggil Behavior/ Method
					}
					break;
				
				case 3:
				    if (akunAktif == null) {
				        System.out.println("Error: Anda belum membuka rekening!");
				    } else {
				    	System.out.println("Masukkan PIN");
				    	String pinTarik=input.nextLine();
				    	if(akunAktif.otentikasi(pinTarik)) {
				    		System.out.println("Masukkan Nominal Tarik tunai");
					        double tarik = input.nextDouble();
					        akunAktif.tariktunai(tarik);
				    	}else {
				    		System.out.println("Akses Ditolak: PIN yang anda masukkan salah!");
				    	}
				    		
				        
				    }
				    break;
					
				case 4:
					if(akunAktif==null) {
						System.out.println("Error: Anda belum membuka rekening!");
					}
					else {
						akunAktif.cekInformasi();
					}
					break;
				case 5:
				    System.out.println("Masukkan No Rekening: ");
				    String cariNo = input.nextLine();

				    boolean ditemukan = false;

				    for (Rekening rekening : daftarRekening) {
				        if (rekening.getNomorRekening().equals(cariNo)) {
				            akunAktif = rekening;
				            ditemukan = true;

				            System.out.println("Berhasil mengganti akun.");
				            System.out.println("Akun aktif: " + akunAktif.getNamaPemilik());
				            break;
				        }
				    }

				    if (!ditemukan) {
				        System.out.println("Rekening tidak ditemukan.");
				    }

				    break;
				case 6:
					if(akunAktif==null) {
						System.out.println("Error: Anda belum membuka rekening!");
					}else {
						System.out.println("Masukkan PIN");
				    	String pinTarik=input.nextLine();
				    	if(akunAktif.otentikasi(pinTarik)) {
						akunAktif.cetakMutasi();
						}else {
							System.out.println("Akses Ditolak: PIN yang anda masukkan salah!");
						}
					}
					break;
				case 7:
					if(akunAktif==null) {
						System.out.println("Error: Anda belum membuka rekening!");
					}else {
					akunAktif.cektransaksi();
					}
					break;
					
				default:
						System.out.println("Pilihan tidak valid!");
					
			}
		}
		input.close();
	}

}
