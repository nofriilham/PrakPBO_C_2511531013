package Pertemuan5;
import java.util.ArrayList;

public class Rekening {
	//Bagian atas dari class Rekening.java
	private String nomorRekening;
	private String namaPemilik;
	private String pin;// Data Sensitif
	
	//Gunakan protected agar Subclass bisa mengaksesnya langsung
	protected double saldo;
	protected ArrayList<Transaksi>riwayatTransaksi;
	
	//2. Modifikasi Constructor untuk menerima PIN awal
	public Rekening (String nomor, String nama, double saldoAwal,String pinAwal){
		this.nomorRekening =nomor;
		this.namaPemilik =nama;
		this.saldo= saldoAwal;
		
		
		//Validasi PIN di dalam Constructor 
		if(pinAwal.length()==6) {
			this.pin=pinAwal;
		}else {
			System.out.println("Peringatan: PIN harus 6 digit! Menggunakan PIN default 123456");
			this.pin="123456";
		}
		
		//Wajib meninisialiasi ArrayList di dalam construktor agar tidak NullPointerException
		this.riwayatTransaksi=new ArrayList<>();
		System.out.println(" Rekening atas nama " + namaPemilik + " Berhasil dibuat  ");
	}
	//3. Getter untuk atribut yang diizinkan dibaca publik
	public String getNomorRekening() { return nomorRekening;}
	public String getNamaPemilik() { return namaPemilik;}
	
	//4. Method Otentikasi Internal (Validasi enkapsulasi)
	public boolean otentikasi(String inputPin) {
		return this.pin.equals(inputPin);
	}
	public void setorTunai(double nominal) {
		if(nominal>0 ) {
			saldo += nominal;
			//Merekam riwayat (Pembuatan objek Transkasi di dalam method)
			String idTrx="TRX-S-"+ System.currentTimeMillis();
			Transaksi trxBaru =new Transaksi(idTrx,"Kredit",nominal);
			riwayatTransaksi.add(trxBaru);
			System.out.println(" Setor tunai Rp "+ nominal + " Berhasil. Saldo saat ini: Rp "+ saldo);
		} else {
			System.out.println(" Gagal: Nominal setor harus lebih dari 0! ");
		}
	}
	
	public void cekInformasi () {
		System.out.println("---INFO REKENING ---");
		System.out.println(" No. Rekening : " + nomorRekening);
		System.out.println(" Nama Pemilik : "+ namaPemilik);
		System.out.println(" Saldo Akhir : Rp"+ saldo);
		System.out.println("----------------------");
	}
	// Mengubah void menjadi boolean
	public boolean tarikTunai(double nominal) {
	    if (nominal <= 0) {
	        System.out.println("Gagal: Nominal tarik harus lebih dari 0!");
	        return false;
	    } else if (nominal > saldo) {
	        System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
	        return false;
	    } else {
	        saldo -= nominal;

	        String idTrx = "TRX-T-" + System.currentTimeMillis();
	        riwayatTransaksi.add(new Transaksi(idTrx, "Tarik Tunai", nominal));

	        System.out.println("Tarik tunai Rp" + nominal + " berhasil. Sisa saldo: Rp" + saldo);
	        return true; // Transaksi sukses
	    }
	}
	public void cetakMutasi() {
		if (riwayatTransaksi.isEmpty()) {
			System.out.println("Belum Ada Transaksi pada rekening ini");
		}else {
			for(Transaksi transaksi :riwayatTransaksi ) {
				transaksi.cetakDetail();
			}
			}
	}
	
	public void cektransaksi() {
		if (riwayatTransaksi.size()>3) {
			for (Transaksi transaksi : riwayatTransaksi) {
		
		transaksi.cetakDetail();
			}
	}else {
		System.out.println("Transakasi kurang dari 3");
	
	}
		
	}
	public void transfer(Rekening tujuan, double nominal, String pinInput) {
	    // 1. Autentikasi PIN
	    if (!otentikasi(pinInput)) {
	        System.out.println("Transfer gagal: PIN salah!");
	        return;
	    }

	    // 2. Tarik saldo dari rekening pengirim
	    boolean berhasil = this.tarikTunai(nominal);

	    // 3. Jika penarikan berhasil, setor ke rekening tujuan
	    if (berhasil) {
	        tujuan.setorTunai(nominal);
	        System.out.println("Transfer berhasil!");
	    } else {
	        System.out.println("Transfer gagal!");
	    }
	}
	}
	

	



