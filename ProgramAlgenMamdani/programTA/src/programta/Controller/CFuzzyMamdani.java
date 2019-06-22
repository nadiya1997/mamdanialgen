/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programta.Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import programta.Entity.ECuaca;

/**
 *
 * @author Nadiya
 */
public class CFuzzyMamdani {
    double [] f_anggota_pakar;
    ArrayList<double[][]> f_anggota_baru_output_list;
    ArrayList<ECuaca> data_cuaca;
    ArrayList<ArrayList> daftar_rule;
    double[][] f_anggota_output;
    double[][] f_keanggotaan_cuaca;
    String[] kondisi_cuaca;
    double[] hasil_deffuzifikasi;
    double akurasi;
   
    
    public CFuzzyMamdani() {
        f_anggota_pakar = new double[12];
        
        f_anggota_pakar[0] = 26;
        f_anggota_pakar[1] = 27.5;
        f_anggota_pakar[2] = 29;
        f_anggota_pakar[3] = 65;
        f_anggota_pakar[4] = 75;
        f_anggota_pakar[5] = 85;
        f_anggota_pakar[6] = 1008.5;
        f_anggota_pakar[7] = 1011;
        f_anggota_pakar[8] = 1013.5;
        f_anggota_pakar[9] = 2;
        f_anggota_pakar[10] = 4.5;
        f_anggota_pakar[11] = 7;
        
    }
    
    public void init_cuaca(ArrayList<ECuaca> data_cuaca){
        this.data_cuaca = data_cuaca;
    }
    
    public void init_rule(ArrayList<ArrayList> daftar_rule){
        this.daftar_rule = daftar_rule;
    }
    
    public void do_fuzzifikasi(){
        DecimalFormat format = new DecimalFormat("#.00");
     
        f_keanggotaan_cuaca = new double[data_cuaca.size()][12];
    
        for (int i = 0; i < data_cuaca.size(); i++) {
            if (data_cuaca.get(i).getSuhu()<=f_anggota_pakar[0]) {
                f_keanggotaan_cuaca[i][0] = 1;
            }
            else if(data_cuaca.get(i).getSuhu()>f_anggota_pakar[0] && data_cuaca.get(i).getSuhu()<f_anggota_pakar[1]){
                f_keanggotaan_cuaca[i][0] = Double.valueOf(format.format((f_anggota_pakar[1]-data_cuaca.get(i).getSuhu())/(f_anggota_pakar[1]-f_anggota_pakar[0])));
            }
            else{
                f_keanggotaan_cuaca[i][0] = 0;
            }
            
//      +++++++++++++ Suhu Hangat ++++++++++++++++++++++++++++++++++++++++++++++                    
            if(data_cuaca.get(i).getSuhu()> f_anggota_pakar[0] && data_cuaca.get(i).getSuhu() < f_anggota_pakar[1]){
                f_keanggotaan_cuaca[i][1] = Double.valueOf(format.format((data_cuaca.get(i).getSuhu()-f_anggota_pakar[0])/(f_anggota_pakar[1]-f_anggota_pakar[0])));
            }
            else if(data_cuaca.get(i).getSuhu()>f_anggota_pakar[1] && data_cuaca.get(i).getSuhu()<f_anggota_pakar[2]){
                f_keanggotaan_cuaca[i][1] = Double.valueOf(format.format((f_anggota_pakar[2]-data_cuaca.get(i).getSuhu())/(f_anggota_pakar[2]-f_anggota_pakar[1])));
            }
            else{
                f_keanggotaan_cuaca[i][1] = 0;
            }

//      +++++++++++++ Suhu Panas ++++++++++++++++++++++++++++++++++++++++++++++                 
            if(data_cuaca.get(i).getSuhu()<=f_anggota_pakar[1]){
                f_keanggotaan_cuaca[i][2] = 0;
            }
            else if(data_cuaca.get(i).getSuhu()>f_anggota_pakar[1] && data_cuaca.get(i).getSuhu()<f_anggota_pakar[2]){
                f_keanggotaan_cuaca[i][2] = Double.valueOf(format.format((data_cuaca.get(i).getSuhu()-f_anggota_pakar[1])/(f_anggota_pakar[2]-f_anggota_pakar[1])));
            }
            else{
                f_keanggotaan_cuaca[i][2] = 1;
            }

//      +++++++++++++ Kelembaban Dry ++++++++++++++++++++++++++++++++++++++++++++++ 
            if(data_cuaca.get(i).getKelembaban() <= f_anggota_pakar[3]){
                f_keanggotaan_cuaca[i][3] = 1;
            }
            else if(data_cuaca.get(i).getKelembaban()>f_anggota_pakar[3] && data_cuaca.get(i).getKelembaban()<f_anggota_pakar[4]){
                f_keanggotaan_cuaca[i][3] = Double.valueOf(format.format(f_anggota_pakar[4]-data_cuaca.get(i).getSuhu()/(f_anggota_pakar[4]-f_anggota_pakar[3])));
            }
            else{
                f_keanggotaan_cuaca[i][3] = 0;
            }

//      +++++++++++++ Kelembaban Wet ++++++++++++++++++++++++++++++++++++++++++++++ 
            if(data_cuaca.get(i).getKelembaban()> f_anggota_pakar[3] && data_cuaca.get(i).getKelembaban() < f_anggota_pakar[4]){
                f_keanggotaan_cuaca[i][4] = Double.valueOf(format.format((data_cuaca.get(i).getKelembaban()-f_anggota_pakar[3])/(f_anggota_pakar[4]-f_anggota_pakar[3])));
            }
            else if(data_cuaca.get(i).getKelembaban()>f_anggota_pakar[4] && data_cuaca.get(i).getKelembaban()<f_anggota_pakar[5]){
                f_keanggotaan_cuaca[i][4] = Double.valueOf(format.format((f_anggota_pakar[5]-data_cuaca.get(i).getKelembaban())/(f_anggota_pakar[5]-f_anggota_pakar[4])));
            }
            else{
                f_keanggotaan_cuaca[i][4] = 0;
            }

//      +++++++++++++ Kelembaban Moist ++++++++++++++++++++++++++++++++++++++++++++++ 
            if(data_cuaca.get(i).getKelembaban()<=f_anggota_pakar[4]){
                f_keanggotaan_cuaca[i][5] = 0;
            }
            else if(data_cuaca.get(i).getKelembaban()>f_anggota_pakar[4] && data_cuaca.get(i).getKelembaban()<f_anggota_pakar[5]){
                f_keanggotaan_cuaca[i][5] = Double.valueOf(format.format((data_cuaca.get(i).getKelembaban()-f_anggota_pakar[4])/(f_anggota_pakar[5]-f_anggota_pakar[4])));
            }
            else{
                f_keanggotaan_cuaca[i][5] = 1;
            }

//      +++++++++++++ Tekanan Udara Rendah ++++++++++++++++++++++++++++++++++++++++++++++ 
            if(data_cuaca.get(i).getTekananUdara() <= f_anggota_pakar[6]){
                f_keanggotaan_cuaca[i][6] = 1;
            }
            else if(data_cuaca.get(i).getTekananUdara()>f_anggota_pakar[6] && data_cuaca.get(i).getTekananUdara()<f_anggota_pakar[7]){
                f_keanggotaan_cuaca[i][6] = Double.valueOf(format.format((f_anggota_pakar[7]-data_cuaca.get(i).getTekananUdara())/(f_anggota_pakar[7]-f_anggota_pakar[6])));
            }
            else{
                f_keanggotaan_cuaca[i][6] = 0;
            }

//      +++++++++++++ Tekanan Udara Sedang ++++++++++++++++++++++++++++++++++++++++++++++ 
            if(data_cuaca.get(i).getTekananUdara()> f_anggota_pakar[6] && data_cuaca.get(i).getTekananUdara() < f_anggota_pakar[7]){
                f_keanggotaan_cuaca[i][7] = Double.valueOf(format.format((data_cuaca.get(i).getTekananUdara()-f_anggota_pakar[6])/(f_anggota_pakar[7]-f_anggota_pakar[6])));
            }
            else if(data_cuaca.get(i).getTekananUdara()>f_anggota_pakar[7] && data_cuaca.get(i).getTekananUdara()<f_anggota_pakar[8]){
                f_keanggotaan_cuaca[i][7] = Double.valueOf(format.format((f_anggota_pakar[8]-data_cuaca.get(i).getTekananUdara())/(f_anggota_pakar[8]-f_anggota_pakar[7])));
            }
            else{
                f_keanggotaan_cuaca[i][7] = 0;
            }

//      +++++++++++++ Tekanan Udara Tinggi ++++++++++++++++++++++++++++++++++++++++++++++ 
            if(data_cuaca.get(i).getTekananUdara()<=f_anggota_pakar[7]){
                f_keanggotaan_cuaca[i][8] = 0;
            }
            else if(data_cuaca.get(i).getTekananUdara()>f_anggota_pakar[7] && data_cuaca.get(i).getTekananUdara()<f_anggota_pakar[8]){
                f_keanggotaan_cuaca[i][8] = Double.valueOf(format.format((data_cuaca.get(i).getTekananUdara()-f_anggota_pakar[7])/(f_anggota_pakar[8]-f_anggota_pakar[7])));
            }
            else{
                f_keanggotaan_cuaca[i][8] = 1;
            }

//      +++++++++++++ Kecapatan Angin Sedang ++++++++++++++++++++++++++++++++++++++++++++++ 
            if(data_cuaca.get(i).getKecepatanAngin()<= f_anggota_pakar[9]){
                f_keanggotaan_cuaca[i][9] = 1;
            }
            else if(data_cuaca.get(i).getKecepatanAngin()>f_anggota_pakar[9] && data_cuaca.get(i).getKecepatanAngin()<f_anggota_pakar[10]){
                f_keanggotaan_cuaca[i][9] = Double.valueOf(format.format((f_anggota_pakar[10]-data_cuaca.get(i).getKecepatanAngin())/(f_anggota_pakar[10]-f_anggota_pakar[9])));
            }
            else{
                f_keanggotaan_cuaca[i][9] = 0;
            }

//      +++++++++++++ Kecepatan Angin Kencang ++++++++++++++++++++++++++++++++++++++++++++++ 
            if(data_cuaca.get(i).getKecepatanAngin()> f_anggota_pakar[9] && data_cuaca.get(i).getKecepatanAngin()< f_anggota_pakar[10]){
                f_keanggotaan_cuaca[i][10] = Double.valueOf(format.format((data_cuaca.get(i).getKecepatanAngin()-f_anggota_pakar[9])/(f_anggota_pakar[10]-f_anggota_pakar[9])));
            }
            else if(data_cuaca.get(i).getKecepatanAngin()>f_anggota_pakar[10] && data_cuaca.get(i).getKecepatanAngin()<f_anggota_pakar[11]){
                f_keanggotaan_cuaca[i][10] = Double.valueOf(format.format((f_anggota_pakar[11]-data_cuaca.get(i).getKecepatanAngin())/(f_anggota_pakar[11]-f_anggota_pakar[10])));
            }
            else{
                f_keanggotaan_cuaca[i][10] = 0;
            }

//      +++++++++++++ Kecepatan Angin Sangat Kencang ++++++++++++++++++++++++++++++++++++++++++++++ 
            if(data_cuaca.get(i).getKecepatanAngin()<=f_anggota_pakar[10]){
                f_keanggotaan_cuaca[i][11] = 0;
            }
            else if(data_cuaca.get(i).getKecepatanAngin()>f_anggota_pakar[10] && data_cuaca.get(i).getKecepatanAngin()<f_anggota_pakar[11]){
                f_keanggotaan_cuaca[i][11] = Double.valueOf(format.format((data_cuaca.get(i).getKecepatanAngin()-f_anggota_pakar[10])/(f_anggota_pakar[11]-f_anggota_pakar[10])));
            }
            else{
                f_keanggotaan_cuaca[i][11] = 1;
            }
        }
        
//        for (int i = 0; i < data_cuaca.size(); i++) {
//            System.out.println("Data ke-"+(i+1)+"");
//            for (int j = 0; j < 12; j++) {
//                System.out.println(f_keanggotaan_cuaca[i][j]);
//            }
//        }
    }
    
    public void fuzzifikasi_output_cuaca(){
        
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
    
    public void do_inferensi(){
        DecimalFormat format = new DecimalFormat("#.0000");
        double min;
        double[] max_keanggotaan;
        double[][] max;
        double[][] m_keanggotaan, f_anggota_cuaca, f_anggota_output_baru;
        
        ArrayList<double[][]> listm_keanggotaan;
       
        f_anggota_cuaca = new double [f_keanggotaan_cuaca.length][f_keanggotaan_cuaca[0].length];
        f_anggota_baru_output_list = new ArrayList<>();
        
        for (int i = 0; i < data_cuaca.size(); i++) {
            for (int j = 0; j < 12; j++) {
                f_anggota_cuaca[i][j] = f_keanggotaan_cuaca[i][j];
            }
        }
        
//        for (int i = 0; i < data_cuaca.size(); i++) {
//            System.out.println("Data ke-"+(i+1)+"");
//            for (int j = 0; j < 12; j++) {
//                System.out.print(f_anggota_cuaca[i][j]+" ");
//            }
//            System.out.println();
//        }
//        System.out.println(" ");
        
//        System.out.println("f lengt : "+f_anggota_cuaca.length);
//++++++++++++ Perulangan untuk setiap data pada 1 populasi++++++++++++++++++++++++ 
        max = new double[f_anggota_cuaca.length][4];
        
        for (int j = 0; j < max.length; j++) {
            for (int k = 0; k < max[j].length; k++) {
                max[j][k] = 0;
            }
        }
                    
        for (int j = 0; j < f_anggota_cuaca.length; j++) {
            m_keanggotaan = new double[daftar_rule.size()][daftar_rule.get(0).size()];

//+++++++++++++++++ Perulangan untuk mencari nilai max setiap kategori output pada setiap data +++++++++++++                
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

//            System.out.println("Data ke-"+(j+1)+" : ");
//            for (int m = 0; m < daftar_rule.size(); m++) {
//                System.out.println("Rule ke"+(m+1)+": ");
//                    for (int l = 0; l < daftar_rule.get(m).size(); l++) {
//                        System.out.print(m_keanggotaan[m][l]+" ");
//                    }
//                    System.out.println();
//            }
            
//=======================================================================================                            
            f_anggota_output_baru = new double[f_anggota_output.length][f_anggota_output[0].length];
            for (int k = 0; k < f_anggota_output.length; k++) {
                for (int l = 0; l < f_anggota_output[k].length; l++) {
                    if (f_anggota_output[k][l] > max[j][l]) {
                        f_anggota_output_baru[k][l] = max[j][l];
                    }
                    else{
                        f_anggota_output_baru[k][l] = f_anggota_output[k][l];
                    }
                }
            } 
            
            f_anggota_baru_output_list.add(f_anggota_output_baru);
        }
        
        System.out.println(" ");
        for (int i = 0; i < f_anggota_cuaca.length; i++) {
            System.out.print("Data ke-"+(i+1)+" ");
            for (int k = 0; k < 4; k++) {
                System.out.print(max[i][k]+" ");
            }
            System.out.println();
        }
        System.out.println(" ");
    }
    
    public void do_deffuzifikasi(){
        double[][] f_anggota_output_baru;
        double[] max_keanggotaan;
        DecimalFormat format = new DecimalFormat("#.0000");
        double sum = 0;
        double x = 0;
        
       
        hasil_deffuzifikasi = new double[data_cuaca.size()];
        for (int i = 0; i < data_cuaca.size(); i++) {
            f_anggota_output_baru = f_anggota_baru_output_list.get(i);
            hasil_deffuzifikasi[i] = 0;
            for (int k = 0; k < f_anggota_output_baru.length; k++) {
                max_keanggotaan = new double[f_anggota_output_baru.length];
                max_keanggotaan[k] = f_anggota_output_baru[k][0];

                for(int l = 0; l < f_anggota_output_baru[k].length; l++){
                    if(max_keanggotaan[k] < f_anggota_output_baru[k][l]){
                        max_keanggotaan[k] = f_anggota_output_baru[k][l];
                    }
                }
                sum = Double.valueOf(format.format(sum + max_keanggotaan[k]));
                hasil_deffuzifikasi[i] = hasil_deffuzifikasi[i] + (x*max_keanggotaan[k]);
                x = x+0.5;
            }
            System.out.println("Sum : "+sum);
            
            hasil_deffuzifikasi[i] = Double.valueOf(format.format(hasil_deffuzifikasi[i]/sum));
        }   
    }
    
    public void do_prediksi(){
        double miu1, miu2;

        kondisi_cuaca = new String[data_cuaca.size()];
        
        for (int i = 0; i < data_cuaca.size(); i++) {
            if(hasil_deffuzifikasi[i]<=2){
                kondisi_cuaca[i] = "Sunny";
            }
            else if(hasil_deffuzifikasi[i]>2 && hasil_deffuzifikasi[i]<=4){
                miu1 = (4-hasil_deffuzifikasi[i])/2;
                miu2 = (hasil_deffuzifikasi[i]-2)/6;
                if(miu1>miu2){
                    kondisi_cuaca[i] = "Sunny";
                }
                else {
                    kondisi_cuaca[i] = "Cloudy";
               }
            }
            else if(hasil_deffuzifikasi[i]>4 && hasil_deffuzifikasi[i]<=8){
                miu1 = (hasil_deffuzifikasi[i]-2)/6;
                miu2 = (hasil_deffuzifikasi[i]-6)/8;
               
                if(miu1>miu2){
                    kondisi_cuaca[i] = "Cloudy";
                }
                else {
                    kondisi_cuaca[i] = "Light Rain";
                }
            }
            else if(hasil_deffuzifikasi[i]>8 && hasil_deffuzifikasi[i]<=14){
                miu1 = (14-hasil_deffuzifikasi[i])/6;
                miu2 = (hasil_deffuzifikasi[i]-6)/8;

                if(miu1>miu2){
                    kondisi_cuaca[i] = "Cloudy";
                }
                else {
                    kondisi_cuaca[i] = "Light Rain";
                }
            }

            else if(hasil_deffuzifikasi[i]>14 && hasil_deffuzifikasi[i]<=22){
                miu1 = (22-hasil_deffuzifikasi[i])/8;
                miu2 = (hasil_deffuzifikasi[i]-14)/8;

                if(miu1>miu2){
                    kondisi_cuaca[i] = "Light Rain";
                }
                else {
                    kondisi_cuaca[i] = "Rain";
                }
            }
            else {
                kondisi_cuaca[i] = "Rain";
            }   
        }
        
//        for (int i = 0; i < data_cuaca.size(); i++) {
//            System.out.println("Data ke"+(i+1)+" : "+kondisi_cuaca[i]);
//        }
    }
    
    public void hitung_akurasi(){
        double count, max;
        DecimalFormat format = new DecimalFormat("#.0000");
        
        max = -1;
        count = 0;
            
        for (int i = 0; i < data_cuaca.size(); i++) {
            if (kondisi_cuaca[i].equals(data_cuaca.get(i).getResult())) {
                count++;
            }
        }
        akurasi = Double.valueOf(format.format(count/(data_cuaca.size())*100));
    }
    
    public String[] gethasil_prediksi(){
        return kondisi_cuaca;
    }
    
    public double gethasil_akurasi(){
        return akurasi;
    }
}

