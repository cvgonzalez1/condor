/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data_en_crypto.cifras;

/**
 *
 * @author nyx
 */
public class VICTOR implements Cifras{
    private final char[][] TABLA_LLAVE;
    private final byte[] LLAVE;
    private final int[] CHAR_ESPECIAL;
    private final int[] CHAR_NULOS;

    public VICTOR(char[][] TABLA_LLAVE, byte[] LLAVE) {
        this.TABLA_LLAVE = TABLA_LLAVE;
        this.LLAVE = LLAVE;
        this.CHAR_ESPECIAL = this.encontrar_en_tabla('.');
        this.CHAR_NULOS = this.encontrar_nulos();
    }

    @Override
    public String encriptar(String texto) {
        texto = texto.toUpperCase();
        int[] temp = this.texto_a_numeros(texto);
        temp = this.juntar(temp, LLAVE);
        return this.numeros_a_texto(temp);
    }

    @Override
    public String decifrar(String texto) {
        texto = texto.toUpperCase();
        int[] temp = this.texto_a_numeros(texto);
        temp = this.disjuntar(temp, LLAVE);
        return this.numeros_a_texto(temp);
    }

    private int[] texto_a_numeros(String texto) {
        int[] salida = new int[texto.length() + 1];
        char[] texto_arreglo = texto.toCharArray();
        int i = 1;
        for (char c : texto_arreglo) {
            int[] pos = encontrar_en_tabla(c);
            salida[i] = (pos[0] * 10) + pos[1];
            i++;
        }
        return salida;
    }

    private String numeros_a_texto(int[] temp) {
        String salida = "";
        for (int i = 0; i < temp.length; i++) {
            int b = temp[i];
            int[] pos = new int[2];
            pos[0] = -1;
            pos[1] = b;
            if(existe(b, CHAR_NULOS)){
                i++;
                b = temp[i];
                pos[0] = pos[1];
                pos[1] = b;
            }
            for(int n : temp){
                System.out.print(n + " ");
            }
            System.out.println();
            System.out.println("pos[0] = " + pos[0] + " pos[1] = " + pos[1]);
            if(pos[0] == -1){
                if(pos[1] < TABLA_LLAVE[0].length){
                    System.out.println("pos[1] = " + pos[1]);
                    salida += TABLA_LLAVE[0][pos[1]];
                } else {
                    System.out.println("salida = " + salida);
                    if((pos[1] / TABLA_LLAVE[0].length) >= TABLA_LLAVE[0].length){
                        int t = pos[1] / (TABLA_LLAVE[0].length * TABLA_LLAVE[0].length);
                        System.out.println("t = " + t);
                        salida += TABLA_LLAVE[0][t];
                        System.out.println("salida = " + salida);
                        t = pos[1] % (TABLA_LLAVE[0].length * TABLA_LLAVE[0].length);
                        pos[1] = t;
                        t /= TABLA_LLAVE[0].length;
                        System.out.println("t = " + t);
                        salida += TABLA_LLAVE[0][t / TABLA_LLAVE[0].length];
                        salida += TABLA_LLAVE[0][t % TABLA_LLAVE[0].length];
                    } else {
                        System.out.println("pos[1] = " + pos[1]);
                        System.out.println("pos[1] / TABLA_LLAVE[0].length = " + (pos[1] / TABLA_LLAVE[0].length));
                        salida += TABLA_LLAVE[0][pos[1] / TABLA_LLAVE[0].length];
                        salida += TABLA_LLAVE[0][pos[1] % TABLA_LLAVE[0].length];
                    }
                }
            } else {
                salida += TABLA_LLAVE[pos[0]][pos[1]];
            }
        }
        return salida;
    }

    private int[] juntar(int[] temp, byte[] LLAVE) {
        int j = 0;
        for (int i = 0; i < temp.length; i++) {
            int c = temp[i];
            short k = Short.parseShort(""+LLAVE[j]);
            temp[i] = c + k;
        }
        return temp;
    }
    
    private int[] disjuntar(int[] temp, byte[] LLAVE) {
        int j = 0;
        for (int i = 0; i < temp.length; i++) {
            int c = temp[i];
            short k = Short.parseShort(""+LLAVE[j]);
            temp[i] = c - k;
            temp[i] %= Character.MAX_VALUE;
        }
        return temp;
    }

    private int[] encontrar_en_tabla(char c) {
        int[] pos = new int[2];
        for (int i = 0; i < TABLA_LLAVE.length; i++) {
            for (int j = 0; j < TABLA_LLAVE[i].length; j++) {
                if(c == TABLA_LLAVE[i][j]){
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return CHAR_ESPECIAL;
    }

    private int[] encontrar_nulos() {
        byte nulos = 0;
        for(char c : TABLA_LLAVE[0]){
            if(c == '\u0000'){
                nulos++;
            }
        }
        int[] char_nulos = new int[nulos];
        int j = 0;
        for (int i = 0; i < TABLA_LLAVE[0].length; i++) {
            char c = TABLA_LLAVE[0][i];
            if(c == '\u0000'){
                char_nulos[j] = i;
                j++;
            }
        }
        return char_nulos;
    }

    private boolean existe(int b, int[] arreglo) {
        for(int i : arreglo){
            if(b == i){
                return true;
            }
        }
        return false;
    }
    
}
