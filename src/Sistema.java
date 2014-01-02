/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.acolita.deberes.semestre.cuatro.BaseDeDatos;
import com.acolita.deberes.semestre.dos.*;
import com.acolita.deberes.semestre.dos.troncales.EstructuraDeDatos;
import com.acolita.deberes.semestre.uno.Matematicas;

/**
 *
 * @author nyx
 */
public class Sistema {
    private static Matematicas mat1;
    private static Matematicas mat2;
    
    public static void main(String[] args) {
        mat1 = new Matematicas();
        System.out.println("mat1 = " + mat1.getNota());
    }
}
