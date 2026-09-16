package Pertemuan2;

public class Transaksi {
	String idTransaksi;
	String jenis;
	double nominal;
	
	//Construktor
	public Transaksi(String id,String Jenis, double nominal )
	{
		this.idTransaksi = id;
		this.jenis=Jenis;
		this.nominal =nominal;
	}
	public void cetakDetail() {
		System.out.println("ID: "+ idTransaksi +" | Jenis: "+ jenis + " | Nominal: Rp"+ nominal);
	}
		
	
}
