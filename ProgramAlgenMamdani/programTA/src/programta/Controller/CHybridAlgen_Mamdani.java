/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programta.Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import programta.Entity.ECuaca;
import programta.Entity.EParameterAlgen;

import java.util.Collections;

/**
 *
 * @author Nadiya
 */
public class CHybridAlgen_Mamdani {
    double [] f_anggota_lama;
    double[][] f_anggota_populasi_mutasi;
    double [][] f_anggota_populasi, f_anggota_populasi_terurut;
    double [][] f_anggota_output, f_anggota_populai_terurut_cr;
    int xminSuhu, xmaxSuhu, xminKelembaban, xmaxKelembaban, xminTekananU, xmaxTekananU, xminKecepatanA, xmaxKecepatanA;
    ArrayList<ECuaca> data_cuaca;
    private double cuaca;
    ArrayList<ArrayList> daftar_rule;
    ArrayList<ArrayList> excel_dokumen;
    EParameterAlgen PA;
    int jum_populasi;
    double fitness[], akurasi[];
    int[][] posisi_terpilih;

    double hasil_deffuzifikasi;
    double jumlah_output_max;
    String[] kondisi_cuaca;
    ArrayList<String[]> kondisi_cuaca_list;

    ArrayList<double[][]> f_anggota_cuaca_populasi;
        
        
    public CHybridAlgen_Mamdani(int jum_populasi) {
        PA = new EParameterAlgen();
        
        f_anggota_lama = new double[12];
        
        f_anggota_lama[0] = 26;
        f_anggota_lama[1] = 27.5;
        f_anggota_lama[2] = 29;
        f_anggota_lama[3] = 63;
        f_anggota_lama[4] = 75;
        f_anggota_lama[5] = 85;
        f_anggota_lama[6] = 1008.5;
        f_anggota_lama[7] = 1011;
        f_anggota_lama[8] = 1013;
        f_anggota_lama[9] = 2;
        f_anggota_lama[10] = 4;
        f_anggota_lama[11] = 6.5;
        
        xminSuhu = 24;
        xmaxSuhu = 32;
        xminKelembaban = 63;
        xmaxKelembaban = 100;
        xminTekananU = 1007;
        xmaxTekananU = 1016;
        xminKecepatanA = 0;
        xmaxKecepatanA = 15;
        this.jum_populasi = jum_populasi; 
    }
     
    public void init_cuaca(ArrayList<ECuaca> data_cuaca){
        this.data_cuaca = data_cuaca;
    }
    
    public void init_rule(ArrayList<ArrayList> daftar_rule){
        this.daftar_rule = daftar_rule;
    }
    
    public void BangkitPopulasi(double[][] f_anggota_populasi){
        for (int i = 0; i < f_anggota_populasi.length; i++) {
            for (int j = 0; j < f_anggota_populasi[0].length; j++) {
                this.f_anggota_populasi[i][j] = f_anggota_populasi[i][j];
            }
        }
    }
    
    public double[][] BangkitPopulasi(int populasi){
        double[] temp, temp1;
        DecimalFormat format = new DecimalFormat("#.00");
        Random r = new Random();
        double wk = r.nextDouble();
        int pilih;
        
        f_anggota_populasi = new double[populasi][12];
//++++++++++++++++ Perulangan sebanyak populasi yang dibangkitkan +++++++++++++++++++++++++++++++    
        for (int i = 0; i < populasi; i++) {
//        +++++++++ Perulangan untuk panjang gen dalam setiap populasi yang dibangkitkan ++++++++++++
            for (int j = 0; j < 12; j++) {
                pilih = r.nextInt(2);
                if(j<3){
                    if(pilih==0){
                        f_anggota_populasi[i][j] = Double.valueOf(format.format(f_anggota_lama[j]+(xmaxSuhu-f_anggota_lama[j])*wk));
                    }
                    else{
                        f_anggota_populasi[i][j] = Double.valueOf(format.format(f_anggota_lama[j]-(f_anggota_lama[j]-xminSuhu)*wk));
                    }
                    
                   
                    if(j == 2){
                        temp = new double[3];
                        temp1 = new double[3];
                        
                        for(int k=0;k<temp.length; k++){
                            temp[k] = f_anggota_populasi[i][k];
                        }
                        
                        temp1 = bubblesort(temp);
                        
                        for(int k=0;k<temp1.length; k++){
                            f_anggota_populasi[i][k] = temp1[k];
                        }
                    }
                }
                else if(j>=3 && j<6){
                    if(pilih==0){
                        f_anggota_populasi[i][j] = Double.valueOf(format.format(f_anggota_lama[j]+(xmaxKelembaban-f_anggota_lama[j])*wk));
                    }
                    else{
                        f_anggota_populasi[i][j] = Double.valueOf(format.format(f_anggota_lama[j]-(f_anggota_lama[j]-xminKelembaban)*wk));
                    }
                    
                    if(j == 5){
                        temp = new double[3];
                        temp1 = new double[3];
                        
                        for(int k=0;k<temp.length; k++){
                            temp[k] = f_anggota_populasi[i][k+3];
                        }
                        
                        temp1 = bubblesort(temp);
                        
                        for(int k=0;k<temp1.length; k++){
                            f_anggota_populasi[i][k+3] = temp1[k];
                        }
                    }
                }
                else if(j>=6 && j<9){
                    if(pilih==0){
                        f_anggota_populasi[i][j] = Double.valueOf(format.format(f_anggota_lama[j]+(xmaxTekananU-f_anggota_lama[j])*wk));
                    }
                    else{
                        f_anggota_populasi[i][j] = Double.valueOf(format.format(f_anggota_lama[j]-(f_anggota_lama[j]-xminTekananU)*wk));
                    }
                    
                    if(j == 8){
                        temp = new double[3];
                        temp1 = new double[3];
                        
                        for(int k=0;k<temp.length; k++){
                            temp[k] = f_anggota_populasi[i][k+6];
                        }
                        
                        temp1 = bubblesort(temp);
                        
                        for(int k=0;k<temp1.length; k++){
                            f_anggota_populasi[i][k+6] = temp1[k];
                        }
                    }
                }
                else {
                    if(pilih==0){
                        f_anggota_populasi[i][j] = Double.valueOf(format.format(f_anggota_lama[j]+(xmaxKecepatanA-f_anggota_lama[j])*wk));
                    }
                    else{
                        f_anggota_populasi[i][j] = Double.valueOf(format.format(f_anggota_lama[j]-(f_anggota_lama[j]-xminKecepatanA)*wk));
                    }
                    
                    if(j == 11){
                        temp = new double[3];
                        temp1 = new double[3];
                        
                        for(int k=0;k<temp.length; k++){
                            temp[k] = f_anggota_populasi[i][k+9];
                        }
                        
                        temp1 = bubblesort(temp);
                        
                        for(int k=0;k<temp1.length; k++){
                            f_anggota_populasi[i][k+9] = temp1[k];
                        }
                    }
                }
            }
        }
        
        for (int i = 0; i < populasi; i++) {
            System.out.print("Populasi ke-"+(i+1)+" : ");
            for (int j = 0; j < 12; j++) {
                System.out.print(f_anggota_populasi[i][j]+" ");
            }
            System.out.println();
        }
        
        return f_anggota_populasi;  
        
    }
    
   
    
//=====================================Fuzzyfikasi====================================================================
    
    public void do_fuzzyfikasi(){
        double[][] f_keanggotaan_cuaca;
        DecimalFormat format = new DecimalFormat("#.00");
        ECuaca cuaca;
        
        f_anggota_cuaca_populasi = new ArrayList<>();
//+++++++++++++++ Perulangan untuk setiap populasi pada setiap data ++++++++++++++++            
        for (int i = 0; i < jum_populasi; i++) {
            f_keanggotaan_cuaca = new double[data_cuaca.size()][12];
            
            for (int j = 0; j < data_cuaca.size(); j++) {
                
//========================Fuzzyfikasi Suhu==========================================  
//      ++++++++++++++ Suhu Dingin ++++++++++++++++++++++++++++++++++++++++++++  
                if(data_cuaca.get(j).getSuhu() <= f_anggota_populasi[i][0]){
                    f_keanggotaan_cuaca[j][0] = 1;
                }
                else if(data_cuaca.get(j).getSuhu()>f_anggota_populasi[i][0] && data_cuaca.get(j).getSuhu()<f_anggota_populasi[i][1]){
                    f_keanggotaan_cuaca[j][0] = Double.valueOf(format.format((f_anggota_populasi[i][1]-data_cuaca.get(j).getSuhu())/(f_anggota_populasi[i][1]-f_anggota_populasi[i][0])));
                }
                else{
                    f_keanggotaan_cuaca[j][0] = 0;
                }
                
//      +++++++++++++ Suhu Hangat ++++++++++++++++++++++++++++++++++++++++++++++                    
                if(data_cuaca.get(j).getSuhu()> f_anggota_populasi[i][0] && data_cuaca.get(j).getSuhu() < f_anggota_populasi[i][1]){
                    f_keanggotaan_cuaca[j][1] = Double.valueOf(format.format((data_cuaca.get(j).getSuhu()-f_anggota_populasi[i][0])/(f_anggota_populasi[i][1]-f_anggota_populasi[i][0])));
                }
                else if(data_cuaca.get(j).getSuhu()>f_anggota_populasi[i][1] && data_cuaca.get(j).getSuhu()<f_anggota_populasi[i][2]){
                    f_keanggotaan_cuaca[j][1] = Double.valueOf(format.format((f_anggota_populasi[i][2]-data_cuaca.get(j).getSuhu())/(f_anggota_populasi[i][2]-f_anggota_populasi[i][1])));
                }
                else{
                    f_keanggotaan_cuaca[j][1] = 0;
                }
                
//      +++++++++++++ Suhu Panas ++++++++++++++++++++++++++++++++++++++++++++++                 
                if(data_cuaca.get(j).getSuhu()<=f_anggota_populasi[i][1]){
                    f_keanggotaan_cuaca[j][2] = 0;
                }
                else if(data_cuaca.get(j).getSuhu()>f_anggota_populasi[i][1] && data_cuaca.get(j).getSuhu()<f_anggota_populasi[i][2]){
                    f_keanggotaan_cuaca[j][2] = Double.valueOf(format.format((data_cuaca.get(j).getSuhu()-f_anggota_populasi[i][1])/(f_anggota_populasi[i][2]-f_anggota_populasi[i][1])));
                }
                else{
                    f_keanggotaan_cuaca[j][2] = 1;
                }
                
//      +++++++++++++ Kelembaban Dry ++++++++++++++++++++++++++++++++++++++++++++++ 
                if(data_cuaca.get(j).getKelembaban() <= f_anggota_populasi[i][3]){
                    f_keanggotaan_cuaca[j][3] = 1;
                }
                else if(data_cuaca.get(j).getKelembaban()>f_anggota_populasi[i][3] && data_cuaca.get(j).getKelembaban()<f_anggota_populasi[i][4]){
                    f_keanggotaan_cuaca[j][3] = Double.valueOf(format.format((f_anggota_populasi[i][4]-data_cuaca.get(j).getKelembaban())/(f_anggota_populasi[i][4]-f_anggota_populasi[i][3])));
                }
                else{
                    f_keanggotaan_cuaca[j][3] = 0;
                }
                
//      +++++++++++++ Kelembaban Wet ++++++++++++++++++++++++++++++++++++++++++++++ 
                if(data_cuaca.get(j).getKelembaban()> f_anggota_populasi[i][3] && data_cuaca.get(j).getKelembaban() < f_anggota_populasi[i][4]){
                    f_keanggotaan_cuaca[j][4] = Double.valueOf(format.format((data_cuaca.get(j).getKelembaban()-f_anggota_populasi[i][3])/(f_anggota_populasi[i][4]-f_anggota_populasi[i][3])));
                }
                else if(data_cuaca.get(j).getKelembaban()>f_anggota_populasi[i][4] && data_cuaca.get(j).getKelembaban()<f_anggota_populasi[i][5]){
                    f_keanggotaan_cuaca[j][4] = Double.valueOf(format.format((f_anggota_populasi[i][5]-data_cuaca.get(j).getKelembaban())/(f_anggota_populasi[i][5]-f_anggota_populasi[i][4])));
                }
                else{
                    f_keanggotaan_cuaca[j][4] = 0;
                }
                
//      +++++++++++++ Kelembaban Moist ++++++++++++++++++++++++++++++++++++++++++++++ 
                if(data_cuaca.get(j).getKelembaban()<=f_anggota_populasi[i][4]){
                    f_keanggotaan_cuaca[j][5] = 0;
                }
                else if(data_cuaca.get(j).getKelembaban()>f_anggota_populasi[i][4] && data_cuaca.get(j).getKelembaban()<f_anggota_populasi[i][5]){
                    f_keanggotaan_cuaca[j][5] = Double.valueOf(format.format((data_cuaca.get(j).getKelembaban()-f_anggota_populasi[i][4])/(f_anggota_populasi[i][5]-f_anggota_populasi[i][4])));
                }
                else{
                    f_keanggotaan_cuaca[j][5] = 1;
                }

//      +++++++++++++ Tekanan Udara Rendah ++++++++++++++++++++++++++++++++++++++++++++++ 
                if(data_cuaca.get(j).getTekananUdara() <= f_anggota_populasi[i][6]){
                    f_keanggotaan_cuaca[j][6] = 1;
                }
                else if(data_cuaca.get(j).getTekananUdara()> f_anggota_populasi[i][6] && data_cuaca.get(j).getTekananUdara() < f_anggota_populasi[i][7]){
                    f_keanggotaan_cuaca[j][6] = Double.valueOf(format.format((f_anggota_populasi[i][7]-data_cuaca.get(j).getTekananUdara())/(f_anggota_populasi[i][7]-f_anggota_populasi[i][6])));
                }
                else{
                    f_keanggotaan_cuaca[j][6] = 0;
                }
                
//      +++++++++++++ Tekanan Udara Sedang ++++++++++++++++++++++++++++++++++++++++++++++ 
                if(data_cuaca.get(j).getTekananUdara()> f_anggota_populasi[i][6] && data_cuaca.get(j).getTekananUdara() < f_anggota_populasi[i][7]){
                    f_keanggotaan_cuaca[j][7] = Double.valueOf(format.format((data_cuaca.get(j).getTekananUdara()-f_anggota_populasi[i][6])/(f_anggota_populasi[i][7]-f_anggota_populasi[i][6])));
                }
                else if(data_cuaca.get(j).getTekananUdara()>f_anggota_populasi[i][7] && data_cuaca.get(j).getTekananUdara()<f_anggota_populasi[i][8]){
                    f_keanggotaan_cuaca[j][7] = Double.valueOf(format.format((f_anggota_populasi[i][8]-data_cuaca.get(j).getTekananUdara())/(f_anggota_populasi[i][8]-f_anggota_populasi[i][7])));
                }
                else{
                    f_keanggotaan_cuaca[j][7] = 0;
                }
                
//      +++++++++++++ Tekanan Udara Tinggi ++++++++++++++++++++++++++++++++++++++++++++++ 
                if(data_cuaca.get(j).getTekananUdara()<=f_anggota_populasi[i][7]){
                    f_keanggotaan_cuaca[j][8] = 0;
                }
                else if(data_cuaca.get(j).getTekananUdara()>f_anggota_populasi[i][7] && data_cuaca.get(j).getTekananUdara()<f_anggota_populasi[i][8]){
                    f_keanggotaan_cuaca[j][8] = Double.valueOf(format.format((data_cuaca.get(j).getTekananUdara()-f_anggota_populasi[i][7])/(f_anggota_populasi[i][8]-f_anggota_populasi[i][7])));
                }
                else{
                    f_keanggotaan_cuaca[j][8] = 1;
                }

//      +++++++++++++ Kecapatan Angin Sedang ++++++++++++++++++++++++++++++++++++++++++++++ 
                if(data_cuaca.get(j).getKecepatanAngin() <= f_anggota_populasi[i][9]){
                    f_keanggotaan_cuaca[j][9] = 1;
                }
                else if(data_cuaca.get(j).getKecepatanAngin()>f_anggota_populasi[i][9] && data_cuaca.get(j).getKecepatanAngin()<f_anggota_populasi[i][10]){
                    f_keanggotaan_cuaca[j][9] = Double.valueOf(format.format(((f_anggota_populasi[i][10]-data_cuaca.get(j).getKecepatanAngin())/(f_anggota_populasi[i][10]-f_anggota_populasi[i][9]))));  
                }
                else{
                    f_keanggotaan_cuaca[j][9] = 0;
                }
                
//      +++++++++++++ Kecepatan Angin Kencang ++++++++++++++++++++++++++++++++++++++++++++++ 
                if(data_cuaca.get(j).getKecepatanAngin()> f_anggota_populasi[i][9] && data_cuaca.get(j).getKecepatanAngin() < f_anggota_populasi[i][10]){
                    f_keanggotaan_cuaca[j][10] = Double.valueOf(format.format((data_cuaca.get(j).getKecepatanAngin()-f_anggota_populasi[i][9])/(f_anggota_populasi[i][10]-f_anggota_populasi[i][9])));
                }
                else if(data_cuaca.get(j).getKecepatanAngin()>f_anggota_populasi[i][10] && data_cuaca.get(j).getKecepatanAngin()<f_anggota_populasi[i][11]){
                    f_keanggotaan_cuaca[j][10] = Double.valueOf(format.format((f_anggota_populasi[i][11]-data_cuaca.get(j).getKecepatanAngin())/(f_anggota_populasi[i][11]-f_anggota_populasi[i][10])));
                }
                else{
                    f_keanggotaan_cuaca[j][10] = 0;
                }
                
//      +++++++++++++ Kecepatan Angin Sangat Kencang ++++++++++++++++++++++++++++++++++++++++++++++ 
                if(data_cuaca.get(j).getKecepatanAngin()<=f_anggota_populasi[i][10]){
                    f_keanggotaan_cuaca[j][11] = 0;
                }
                else if(data_cuaca.get(j).getKecepatanAngin()>f_anggota_populasi[i][10] && data_cuaca.get(j).getKecepatanAngin()<f_anggota_populasi[i][11]){
                    f_keanggotaan_cuaca[j][11] = Double.valueOf(format.format((data_cuaca.get(j).getKecepatanAngin()-f_anggota_populasi[i][10])/(f_anggota_populasi[i][11]-f_anggota_populasi[i][10])));
                }
                else{
                    f_keanggotaan_cuaca[j][11] = 1;
                }
                System.out.println();
            }
        
            f_anggota_cuaca_populasi.add(f_keanggotaan_cuaca);
        }   
    }
    
    public void fuzzyfikasi_output_cuaca(){
        
        f_anggota_output = new double[51][4];
        
        int count = 0;
        for (double x = 0; x <= 25; x = x + 0.5) {
            if(x<=2){
                f_anggota_output[count][0] = 1;
            }
            else if(x>2 && x<4){
                f_anggota_output[count][0] = (4-x)/(4-2);
            }
            else {
                f_anggota_output[count][0] = 0;
            }

            if(x>2 && x<=8){
                f_anggota_output[count][1] = (x-2)/(8-2);
            }
            else if(x>8 && x<=14){
                f_anggota_output[count][1] = (14-x)/(14-8);
            }
            else{
                f_anggota_output[count][1] = 0;
            }

            if(x>6 && x<=14){
                f_anggota_output[count][2] = (x-6)/(14-6);
            }
            else if(x>14 && x<=22){
                f_anggota_output[count][2] = (22-x)/(22-14);
            }
            else{
                f_anggota_output[count][2] = 0;
            }

            if(x<=14){
                f_anggota_output[count][3] = 0;
            }
            else if(x>14 && x<=22){
                f_anggota_output[count][3] = (x-14)/(22-14);
            }
            else{
                f_anggota_output[count][3] = 1;
            }
            
            count++;
        }
    }
    
    
    
    public void do_hitung_fuzzy_Mamdani(){
        DecimalFormat format = new DecimalFormat("#.0000");
        double min;
        double[] max_keanggotaan;
        double[][] max;
        double[][] m_keanggotaan, f_anggota_cuaca;
        double[][] f_keanggotaan_cuaca, f_anggota_output_baru;
        ArrayList<double[][]> listm_keanggotaan;
        ArrayList<ArrayList> listm_keanggotaan_populasi;
        
        listm_keanggotaan_populasi = new ArrayList<>();
        
//++++++++++++ Perulangan sebanyak jumlah populasi ++++++++++     
        kondisi_cuaca_list = new ArrayList<>();
        for (int i = 0; i < f_anggota_cuaca_populasi.size(); i++) {
            f_anggota_cuaca = f_anggota_cuaca_populasi.get(i);
            
            System.out.println("matriks kenaggotaan data populasi ke - "+(i+1)+" : ");
            for (int h = 0; h < f_anggota_cuaca.length; h++) {
                for (int m = 0; m < f_anggota_cuaca[h].length; m++) {
                    System.out.print(f_anggota_cuaca[h][m]+" ");
                }
                System.out.println();
            }
            
            listm_keanggotaan = new ArrayList<>();
            
            kondisi_cuaca = new String[f_anggota_cuaca.length];
//++++++++++++ Perulangan untuk setiap data pada 1 populasi++++++++++++++++++++++++            
            for (int j = 0; j < f_anggota_cuaca.length; j++) {
                m_keanggotaan = new double[daftar_rule.size()][daftar_rule.get(0).size()];
                
                max = new double[f_anggota_cuaca.length][4];
                
//+++++++++++++++++ Perulangan untuk mencari nilai max setiap kategori output pada setiap data +++++++++++++                
                for(int k = 0; k < max[j].length; k++){
                    max[j][k] = 0;
                }
                
//++++++++++++++++++++++++++++ Perulangan untuk mendapatkan nilai m_keanggotaan setiap rule ++++++++++++++++++                
                for (int k = 0; k < daftar_rule.size(); k++) {
                    min = 1000;
                    for (int l = 0; l < daftar_rule.get(k).size(); l++) {
                        if(daftar_rule.get(k).get(l).equals("Cold")){
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][0];
                        }
                        else if(daftar_rule.get(k).get(l).equals("Warm")){
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][1]; 
                        }
                        else if(daftar_rule.get(k).get(l).equals("Hot")){
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][2];
                        } 

                        if(daftar_rule.get(k).get(l).equals("Dry")){
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][3];
                        }
                        else if(daftar_rule.get(k).get(l).equals("Wet")){
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][4];
                        }
                        else if(daftar_rule.get(k).get(l).equals("Moist")){
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][5];
                        }

                        if(daftar_rule.get(k).get(l).equals("Low")){
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][6];
                        }
                        else if(daftar_rule.get(k).get(l).equals("Medium")){
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][7];
                        }
                        else if(daftar_rule.get(k).get(l).equals("High")){
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][8];
                        }

                        if(daftar_rule.get(k).get(l).equals("Sedang")){
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][9];
                        }
                        else if(daftar_rule.get(k).get(l).equals("Kencang")){
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][10];
                        }
                        else if(daftar_rule.get(k).get(l).equals("Skencang")){
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][11];
                        }
                        
                       
//                    Untuk memperoleh nilai max pada setiap kategori         
                        if(l == 4){
                            m_keanggotaan[k][l] = min;
                            if(daftar_rule.get(k).get(l).equals("Sunny")){
                                if(max[j][0] < m_keanggotaan[k][l]){
                                    max[j][0] = m_keanggotaan[k][l];
                                } 
                            }
                            else if(daftar_rule.get(k).get(l).equals("Cloudy")){
                                if(max[j][1] < m_keanggotaan[k][l]){
                                    max[j][1] = m_keanggotaan[k][l];
                                } 
                            }
                            else if(daftar_rule.get(k).get(l).equals("Light Rain")){
                                if(max[j][2] < m_keanggotaan[k][l]){
                                    max[j][2] = m_keanggotaan[k][l];
                                } 
                            }
                            else if(daftar_rule.get(k).get(l).equals("Rain")){
                                if(max[j][3] < m_keanggotaan[k][l]){
                                    max[j][3] = m_keanggotaan[k][l];
                                } 
                            }
                        }
                        else{
                           if(min > m_keanggotaan[k][l]){
                               min = m_keanggotaan[k][l];
                           } 
                        }
                        
                    }        
                    
                }
//=======================================================================================                
                
                f_anggota_output_baru = new double[f_anggota_output.length][f_anggota_output[0].length];
                
                for (int k = 0; k < f_anggota_output.length; k++) {
                    for (int l = 0; l < f_anggota_output[k].length; l++) {
                        if (f_anggota_output[k][l] > max[j][l]) {
                            f_anggota_output_baru[k][l] = max[j][l];
                        }
//                        if(f_anggota_output_baru[k][l] > max[j][l]){
//                            f_anggota_output_baru[k][l] = max[j][l];
//                        }
                        else{
                            f_anggota_output_baru[k][l] = f_anggota_output[k][l];
                        }
                    
                    }
                }
                
//++++++++++++++++++++ Mencari nilai maksimum dari semua fungsi keanggotaan output baru +++++++++++++++++++++++++++     
                max_keanggotaan = new double[f_anggota_output_baru.length];
                double sum = 0;
                
                double x = 0;
                hasil_deffuzifikasi = 0;
                for (int k = 0; k < f_anggota_output_baru.length; k++) {
                    max_keanggotaan[k] = f_anggota_output_baru[k][0];
                    
                    for(int l = 0; l < f_anggota_output_baru[k].length; l++){
                        if(max_keanggotaan[k] < f_anggota_output_baru[k][l]){
                            max_keanggotaan[k] = f_anggota_output_baru[k][l];
                        }
                    }
                    
                    sum = sum + max_keanggotaan[k];  
                    hasil_deffuzifikasi = hasil_deffuzifikasi + (x*max_keanggotaan[k]);
                    
                    x = x+0.5;
                }             
                
                hasil_deffuzifikasi = Double.valueOf(format.format(hasil_deffuzifikasi/sum));
                
               
                
                double miu1, miu2;
                
//          +++++++++ Mengecek Kondisi Cuaca Berdasarkan Hasil Deffuzifikasi ++++++++++++++      
                if(hasil_deffuzifikasi<=2){
                    kondisi_cuaca[j] = "Sunny";
                }
                else if(hasil_deffuzifikasi>2 && hasil_deffuzifikasi<=4){
                    miu1 = (4-hasil_deffuzifikasi)/2;
                    miu2 = (hasil_deffuzifikasi-2)/6;

                    if(miu1>miu2){
                        kondisi_cuaca[j] = "Sunny";
                    }
                    else {
                        kondisi_cuaca[j] = "Cloudy";
                    }
                }
                else if(hasil_deffuzifikasi>4 && hasil_deffuzifikasi<=8){
                    miu1 = (hasil_deffuzifikasi-2)/6;
                    miu2 = (hasil_deffuzifikasi-6)/8;
                    
                    if(miu1>miu2){
                        kondisi_cuaca[j] = "Cloudy";
                    }
                    else {
                        kondisi_cuaca[j] = "Light Rain";
                    }
                }
                else if(hasil_deffuzifikasi>8 && hasil_deffuzifikasi<=14){
                    miu1 = (14-hasil_deffuzifikasi)/6;
                    miu2 = (hasil_deffuzifikasi-6)/8;
                    
                    if(miu1>miu2){
                        kondisi_cuaca[j] = "Cloudy";
                    }
                    else {
                        kondisi_cuaca[j] = "Light Rain";
                    }
                }
                
                else if(hasil_deffuzifikasi>14 && hasil_deffuzifikasi<=22){
                    miu1 = (22-hasil_deffuzifikasi)/8;
                    miu2 = (hasil_deffuzifikasi-14)/8;
                    
                    if(miu1>miu2){
                        kondisi_cuaca[j] = "Light Rain";
                    }
                    else {
                        kondisi_cuaca[j] = "Rain";
                    }
                }
                else{
                    kondisi_cuaca[j] = "Rain";
                }
                
                System.out.println(hasil_deffuzifikasi);
                listm_keanggotaan.add(m_keanggotaan); 
            }
            kondisi_cuaca_list.add(kondisi_cuaca);
            listm_keanggotaan_populasi.add(listm_keanggotaan);
        }  
        
    }
    
    public void hitung_fitness(){
        double count;
        
        for (int i = 0; i < jum_populasi; i++) {
            fitness = new double[jum_populasi];
            count = 0;
            for (int j = 0; j < data_cuaca.size(); j++) {
                if (kondisi_cuaca_list.get(i)[j].equals(data_cuaca.get(j).getResult())) {
                    count++;
                }
            }
            fitness[i]= count/data_cuaca.size();
            System.out.println();
            System.out.println("Nilai fitnes populasi "+(i+1)+" : "+fitness[i]);
        }
        
        f_anggota_populasi_terurut = bubblesort_fitness(fitness, f_anggota_populasi);
        
    }
    
    public void do_crossover(double cr){
        double[][] f_anggota_populai_temp;
        DecimalFormat format = new DecimalFormat("#.0000");
        ArrayList<Integer> idx_k_list, idx_k_list_temp;
        Random r = new Random();
        double R[] = new double[jum_populasi];
        int posisi_cross = r.nextInt(12);
        double hasil_crossover[][];
        double kromosom_crossover[][];
        ArrayList<Boolean> perbandingan_rk;
        
        kromosom_crossover = new double[f_anggota_populasi.length][f_anggota_populasi[0].length];
        hasil_crossover = new double[f_anggota_populasi.length][12];
        idx_k_list = new ArrayList<>();
        idx_k_list_temp = new ArrayList<>();
        perbandingan_rk = new ArrayList<Boolean>();
        
        int jum_cross = (int) (jum_populasi*cr);
        System.out.println("Jumlah Crossover : "+jum_cross);
        System.out.println("Posisi Crossover : "+posisi_cross);
      
        for (int i = 0; i < jum_populasi; i++) {
            R[i] = Double.valueOf(format.format(r.nextDouble()));
            System.out.println("R"+(i+1)+" : "+R[i]);
        }
        
        for (int i = 0; i < jum_populasi; i++) {
            if(R[i] < cr){
                idx_k_list_temp.add(i);
                perbandingan_rk.add(Boolean.TRUE);
            }
        }
        
//        for (int i = 0; i < idx_k_list_temp.size(); i++) {
//            System.out.print(idx_k_list_temp.get(i)+" ");
//        }
        
        if(jum_cross < idx_k_list_temp.size()){
            for (int i = 0; i < jum_cross; i++) {
                idx_k_list.add(idx_k_list_temp.get(i));
            }
        }
        else{
            for (int i = 0; i < idx_k_list_temp.size(); i++) {
                idx_k_list.add(idx_k_list_temp.get(i));
            }
        }
        
        f_anggota_populai_terurut_cr = new double[f_anggota_populasi_terurut.length][f_anggota_populasi_terurut[0].length];
        f_anggota_populai_temp = new double[idx_k_list.size()][f_anggota_populasi_terurut[0].length-posisi_cross];
        
        for (int i = 0; i < f_anggota_populai_terurut_cr.length; i++) {
            for (int j = 0; j < f_anggota_populai_terurut_cr[i].length; j++) {
                f_anggota_populai_terurut_cr[i][j] = f_anggota_populasi_terurut[i][j];
            }
        }
        
        System.out.println("Sebelum Crossover :");
        for (int i = 0; i < f_anggota_populai_terurut_cr.length; i++) {
            System.out.print("pupulasi ke-"+(i+1)+" : ");
            for (int j = 0; j < f_anggota_populai_terurut_cr[i].length; j++) {
                System.out.print(f_anggota_populai_terurut_cr[i][j]+" ");;
            }
            System.out.println();
        }
//++++++++++++++++++++++++++++++++++++++++ Bingung Proses Crossovernya++++++++++++++++++++++++++++++++++++++++++++++++++++        
        if(idx_k_list.size() > 1){
            for (int i = 0; i < idx_k_list.size(); i++) {
                for (int j = 0; j < f_anggota_populasi_terurut[0].length; j++) {
                    if(j > posisi_cross-1){
                        f_anggota_populai_temp[i][j-posisi_cross] = f_anggota_populasi_terurut[i][j];
                    }
                }
            }
            
            
            for (int i = 0; i < idx_k_list.size(); i++) {
                for (int j = 0; j < f_anggota_populai_temp[0].length; j++) {
                    if(i == idx_k_list.size()-1){
                        f_anggota_populai_terurut_cr[idx_k_list.get(i)][j+posisi_cross] = f_anggota_populai_temp[0][j];
                        System.out.println("1 : "+f_anggota_populai_terurut_cr[idx_k_list.get(i)][j+posisi_cross]);
                    }
                    else{
                        f_anggota_populai_terurut_cr[idx_k_list.get(i)][j+posisi_cross] = f_anggota_populai_temp[i+1][j];
                        System.out.println("2 : "+f_anggota_populai_terurut_cr[idx_k_list.get(i)][j+posisi_cross]);
                    }
                }
            }
        }
    }
    
    public void do_mutasi(double mr){
        ArrayList<Integer> randPosisi;
        int total_gen, jumgen_perkromosom, jum_mutasi, count_moving;
        DecimalFormat format = new DecimalFormat("#.0000");
        jumgen_perkromosom = 12;
        total_gen = jum_populasi*jumgen_perkromosom;
        jum_mutasi = (int) (total_gen*mr);
        Random r = new Random();
        int[] Rm = new int[jum_mutasi];
        double[] rm = new double[jum_mutasi];
        
        randPosisi = new ArrayList<>();
        
        for (int i = 1; i <= total_gen; i++) {
            randPosisi.add(i);
        }
        
        Collections.shuffle(randPosisi);
        
        for (int i = 0; i < jum_mutasi; i++) {
            Rm[i] = randPosisi.get(i);
            rm[i] = Double.valueOf(format.format(-0.01 + (Math.random() * 0.02)));
            System.out.println("Posisi mutasi : "+Rm[i]);
            System.out.println("Bilangan acak : "+rm[i]);
        }
        
        
        f_anggota_populasi_mutasi = new double[f_anggota_populai_terurut_cr.length][f_anggota_populai_terurut_cr[0].length];
        
        for (int i = 0; i < f_anggota_populasi_mutasi.length; i++) {
            for (int j = 0; j < f_anggota_populasi_mutasi[0].length; j++) {
                f_anggota_populasi_mutasi[i][j] = f_anggota_populai_terurut_cr[i][j];
            }
        }
        System.out.println();
        System.out.println("Sebelum Mutasi : ");
        for (int i = 0; i < f_anggota_populasi_mutasi.length; i++) {
            System.out.print("Populasi ke-"+(i+1)+" ");
            for (int j = 0; j < f_anggota_populasi_mutasi[0].length; j++) {
                System.out.print(f_anggota_populasi_mutasi[i][j]+" ");;
            }
            System.out.println();
        }
        
        for (int i = 0; i < Rm.length; i++) {
            count_moving = 1;
            for (int j = 0; j < f_anggota_populasi_mutasi.length; j++) {
                for (int k = 0; k < f_anggota_populasi_mutasi[j].length; k++) {
                    if(count_moving == Rm[i]){
                        if(k < 3){
                            f_anggota_populasi_mutasi[j][k] += (rm[i]*(xmaxSuhu-xminSuhu)); 
                        }
                        else if(k >= 3 && k < 6){
                            f_anggota_populasi_mutasi[j][k] += (rm[i]*(xmaxKelembaban-xminKelembaban));
                        }
                        else if(k >= 6 && k < 9){
                            f_anggota_populasi_mutasi[j][k] += (rm[i]*(xmaxTekananU-xminTekananU));
                        }
                        else{
                            f_anggota_populasi_mutasi[j][k] += (rm[i]*(xmaxKecepatanA-xminKecepatanA));
                        }
                    }
                    
                    count_moving++;
                }
            }
        }
        
        System.out.println();
        System.out.println("Setelah Mutasi : ");
        for (int i = 0; i < f_anggota_populasi_mutasi.length; i++) {
            System.out.println("Populasi ke-"+(i+1)+" : ");
            for (int j = 0; j < f_anggota_populasi_mutasi[i].length; j++) {
                System.out.print(f_anggota_populasi_mutasi[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    public double[] bubblesort(double arr[]){
        int i,j;
        int n = arr.length;
        
        for(i=0;i<n-1;i++){
            for(j=0;j<n-i-1;j++){
                if(arr[j]>arr[j+1]){
                    double temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
                    
            }
        }
      return arr;
    }
    
    public double[][] bubblesort_fitness(double arr[], double[][] f_keanggotaan){
        int i,j;
        int n = arr.length;
        double[][] f_keanggotaan_terurut_populasi;
        double[] f_keanggotaan_temp;
        
        f_keanggotaan_terurut_populasi = new double[f_keanggotaan.length][f_keanggotaan[0].length];
        
        for (int k = 0; k < f_keanggotaan_terurut_populasi.length; k++) {
            for (int l = 0; l < f_keanggotaan_terurut_populasi[k].length; l++) {
                f_keanggotaan_terurut_populasi[k][l] = f_keanggotaan[k][l];
            }
        }
        
        for(i=0;i<n-1;i++){
            for(j=0;j<n-i-1;j++){
                if(arr[j]>arr[j+1]){
                    double temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    
                    f_keanggotaan_temp = f_keanggotaan_terurut_populasi[j]; 
                    f_keanggotaan_terurut_populasi[j] = f_keanggotaan_terurut_populasi[j+1];
                    f_keanggotaan_terurut_populasi[j+1] = f_keanggotaan_temp;
                }
                    
            }
        }
        
        return f_keanggotaan_terurut_populasi;
    }
    
    public double[][] getHasilMutasi(){
        return f_anggota_populasi_mutasi;
    }
    
    public void hitung_akurasi(){
        double count, max;
        
        max = -1;
        for (int i = 0; i < jum_populasi; i++) {
            akurasi = new double[jum_populasi];
            count = 0;
            
            for (int j = 0; j < data_cuaca.size(); j++) {
                if (kondisi_cuaca_list.get(i)[j].equals(data_cuaca.get(j).getResult())) {
                    count++;
                }
            }
            
            akurasi[i] = (count/data_cuaca.size())*100;
            if (max < akurasi[i] ) {
                max = akurasi[i];
                
                kondisi_cuaca = kondisi_cuaca_list.get(i);
            }
            System.out.println("populasi ke-"+(i+1)+" : "+akurasi[i]);
        }
        
        for (int i = 0; i < jum_populasi; i++) {
            System.out.println("populasi ke-"+(i+1)+" : "+akurasi[i]);
        }
    }
    
    public String[] gethasil_prediksi(){
        return kondisi_cuaca;
    }
    
    
//    public void proses_crossover(double pc){
//        PA = new EParameterAlgen();
//        int osCrossover = (int) (PA.getPopulasi()*PA.getCr());
//        
//        if (  ) {
//            
//        }
//        
//    }
}
