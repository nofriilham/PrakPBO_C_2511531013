package Pertemuan3;

public class Transaksi {
	//1.Menguabh semua atribut menjadi private
	private String idTransaksi;
	private String jenis;
	private double nominal;
	
	//Construktor
	public Transaksi(String id,String Jenis, double nominal )
	{
		this.idTransaksi = id;
		this.jenis=Jenis;
		this.nominal =nominal;
	}
	//2. Hanya menyediakan Getter (Read-Only) 
	public String getIdTransaksi() {return idTransaksi;}
	public String getJenis() {return jenis;}
	public double getNominal() { return nominal;}
	public void cetakDetail() {
		System.out.println("ID: "+ idTransaksi +" | Jenis: "+ jenis + " | Nominal: Rp"+ nominal);
	}
		
	
}
