package Pertemuan2;
import java.util.ArrayList;
public class Rekening {
	String nomorRekening;
	String namaPemilik;
	double saldo;
	
	//Implementasi Asosiasi (1-to-many)
	ArrayList<Transaksi>riwayatTransaksi;
	public Rekening (String nomor, String nama, double saldoAwal){
		nomorRekening =nomor;
		namaPemilik =nama;
		saldo= saldoAwal;
		
		//Wajib meninisialiasi ArrayList di dalam construktor agar tidak NullPointerException
		this.riwayatTransaksi=new ArrayList<>();
		System.out.println(" Rekening atas nama " + namaPemilik + " Berhasil dibuat dengan saldo Rp " +saldo);
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
	public void tariktunai(double nominal) {
		if(nominal<10000) {
			System.out.println("Transaksi Gagal : Minimal tarik 10000");
		}else if(nominal>saldo){
			System.out.println("Transaksi Gagal! Saldo tidak mencukupi. Saldo Anda: Rp"+ saldo);
		}
		else {
			saldo-=nominal;
			String idTrx="TRX-T-"+ System.currentTimeMillis();
			Transaksi trxBaru =new Transaksi(idTrx,"Debit",nominal);
			riwayatTransaksi.add(trxBaru);
			System.out.println("Tranasaksi Berhasil: Saldo Anda: Rp "+saldo);
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
	}

	



