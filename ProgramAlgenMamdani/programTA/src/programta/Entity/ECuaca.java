/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programta.Entity;

/**
 *
 * @author Nadiya
 */
public class ECuaca {
    double no;
    String tanggal;
    double suhu;
    double kelembaban;
    double tekananUdara;
    double kecepatanAngin;
    String result;
//    double []derajatsuhu;
//    double []derajatkelembaban;
//    double []
    
    public ECuaca() {
    }

    public double getNo() {
        return no;
    }

    public void setNo(double no) {
        this.no = no;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public double getSuhu() {
        return suhu;
    }

    public void setSuhu(double suhu) {
        this.suhu = suhu;
    }

    public double getKelembaban() {
        return kelembaban;
    }

    public void setKelembaban(double kelembaban) {
        this.kelembaban = kelembaban;
    }

    public double getTekananUdara() {
        return tekananUdara;
    }

    public void setTekananUdara(double tekananUdara) {
        this.tekananUdara = tekananUdara;
    }

    public double getKecepatanAngin() {
        return kecepatanAngin;
    }

    public void setKecepatanAngin(double kecepatanAngin) {
        this.kecepatanAngin = kecepatanAngin;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
}
