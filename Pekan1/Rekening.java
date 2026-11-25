package Pekan1;

public class Rekening {
	String nomorRekening;
	String namaPemilik;
	double saldo;
	public Rekening (String nomor, String nama, double saldoAwal){
		nomorRekening =nomor;
		namaPemilik =nama;
		saldo= saldoAwal;
		System.out.println(" Rekening atas nama " + namaPemilik + " Berhasil dibuat dengan saldo Rp " +saldo);
	}
	public void setorTunai(double nominal) {
		if(nominal>0 ) {
			saldo += nominal;
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
			System.out.println("Tranasaksi Berhasil: Saldo Anda: Rp "+saldo);
		}
	}

	}



