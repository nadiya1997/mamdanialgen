 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programta.Controller;

import java.util.ArrayList;
import programta.Entity.ECuaca;

/**
 *
 * @author Nadiya
 */
public class CInterfaceManager {
    ArrayList<ECuaca> data_cuaca;
    ArrayList<ArrayList> excel_dokumen;
    ArrayList<ArrayList> daftar_rules;
    CDokumenManager excel_manager;
    String[][] rule;
    CHybridAlgen_Mamdani AM;
    CFuzzyMamdani fuzzy;
    String[] hasil_prediksi, hasil_prediksi_hybrid;
    
    public CInterfaceManager() {
    }
    
    public ArrayList<ECuaca> load_file_excel(String path){
        int baris_excel, kolom_excel;
        ECuaca cuaca;
        
        excel_manager = new CDokumenManager();
        excel_dokumen = excel_manager.get_dataExcel(path);
        
        baris_excel = excel_dokumen.size()-1;
        kolom_excel = excel_dokumen.get(0).size();
        
        data_cuaca = new ArrayList();
        
        System.out.println("baris : "+baris_excel);
        System.out.println("kolom : "+kolom_excel);
        
        for (int i = 0; i < baris_excel; i++) {
            cuaca = new ECuaca();   
            cuaca.setNo(Double.parseDouble(excel_dokumen.get(i+1).get(0).toString()));
            cuaca.setTanggal(excel_dokumen.get(i+1).get(1).toString());
            cuaca.setSuhu(Double.parseDouble(excel_dokumen.get(i+1).get(2).toString()));
            cuaca.setKelembaban(Double.parseDouble(excel_dokumen.get(i+1).get(3).toString()));
            cuaca.setTekananUdara(Double.parseDouble(excel_dokumen.get(i+1).get(4).toString()));
            cuaca.setKecepatanAngin(Double.parseDouble(excel_dokumen.get(i+1).get(5).toString()));
            cuaca.setResult(excel_dokumen.get(i+1).get(6).toString());

            data_cuaca.add(cuaca);
        }
        return data_cuaca;
    }
    
    public ArrayList<ArrayList> load_rules(){
        ArrayList<ArrayList> dokumen;
        ArrayList<String> kolom_list;
        
        int baris_excel, kolom_excel;
        String path = "D:\\Rule Cuaca.xlsx"; 
        
        excel_manager = new CDokumenManager();
        dokumen = excel_manager.get_dataExcel(path);
        daftar_rules = new ArrayList<>();
        
        baris_excel = dokumen.size();
        kolom_excel = dokumen.get(0).size();
        
        for (int i = 0; i < baris_excel; i++) {
            kolom_list = new ArrayList<>();
            
            for(int j = 0; j < kolom_excel; j++){
                kolom_list.add(dokumen.get(i).get(j).toString());
            }
            
            daftar_rules.add(kolom_list);
        }
        
        return daftar_rules;   
    }
  
    public void do_fuzzy_mamdani(){
        load_rules();
        fuzzy = new CFuzzyMamdani();
        fuzzy.init_cuaca(data_cuaca);
        fuzzy.do_fuzzifikasi();
        fuzzy.init_rule(daftar_rules);
        fuzzy.fuzzifikasi_output_cuaca();
        fuzzy.do_inferensi();
        fuzzy.do_deffuzifikasi();
        fuzzy.do_prediksi();
        fuzzy.hitung_akurasi();
        
        hasil_prediksi = fuzzy.gethasil_prediksi();
    }
    
    public void do_algenfuzzy(int jum_iterasi, int jum_populasi,double cr, double mr){
        double[][] f_anggota_populasi_mutasi;
        load_rules();
        AM = new CHybridAlgen_Mamdani(jum_populasi);
        AM.BangkitPopulasi(jum_populasi);
        AM.init_cuaca(data_cuaca);
        
        for (int i = 0; i < jum_iterasi; i++) {
            AM.do_fuzzyfikasi();
            AM.init_rule(daftar_rules);
            AM.fuzzyfikasi_output_cuaca();
            AM.do_hitung_fuzzy_Mamdani();
            AM.hitung_fitness();
            AM.do_crossover(cr);
            AM.do_mutasi(mr);
            f_anggota_populasi_mutasi = AM.getHasilMutasi();
            AM.BangkitPopulasi(f_anggota_populasi_mutasi);
        }
        
        AM.hitung_akurasi();
        hasil_prediksi_hybrid = AM.gethasil_prediksi();
        
//        
//        System.out.println("Populasi" +  AM.BangkitPopulasi(4));        
    }   

    public String[] getHasilPrediksiHybrid(){
        return hasil_prediksi_hybrid;
    }
    
    public String[] getHasilPrediksi(){
        return hasil_prediksi;
    }
}
