/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programta.Controller;

import java.util.ArrayList;
import programta.Entity.EDokumenModel;

/**
 *
 * @author Nadiya
 */
public class CDokumenManager {
    EDokumenModel dokumen_excel;
    
    public CDokumenManager(){
        dokumen_excel = new EDokumenModel();
    }
    
    public ArrayList<ArrayList> get_dataExcel(String path) {
        ArrayList<ArrayList> data_excel;
        data_excel = dokumen_excel.load_excel(path);
        return data_excel;
    }
}
