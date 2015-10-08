package EJ4AD;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author oracle
 */
public class EJ4AD2 {

    String fichero = "/home/oracle/Desktop/Pruebas/texto4.txt";
    DataInputStream dis;
    DataOutputStream dos;
    int cantidad;

    public void proceder(String str) throws IOException {
        try {
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(fichero)));
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fichero)));
            dos.writeUTF(str); //Sin esto, no se vuelca el contenido del buffer al fichero.
            dos.flush();
            /**Con .available() guardamos la cantidad disponible para leer.*/
            cantidad = dis.available();
            System.out.println("writeUTF ha escrito: " + dis.readUTF());
            System.out.println("Cantidad escrita: " + cantidad + " bytes.\n\n");
            dos.writeUTF(str);
            dos.flush();
            cantidad = dis.available(); //Sobreescribimos la cantidad que vamos a leer y que ocupa la linea.
            System.out.println("writeUTF ha escrito: " + dis.readUTF());
            System.out.println("Cantidad escrita: " + cantidad + " bytes.\n\n");
            System.out.println("Bytes totales escritos: " + dos.size() + "\n"); //Size nos da el tamaño TOTAL de las dos lineas leidas.

            /**Necesitamos reiniciar el streaming, porque ya lo hemos recorrido
             * de inicio a fin, y no podemos volver al principio. Reabriendo el
             * streaming volveremos a tener los 40 bytes disponibles para volver
             * a ser recorridos linea a linea con readUTF.*/
            this.reiniciarDIS();
            /**Breve explicación:
             * Total: 40 bytes. Leido: una vez hecho el primer readUTF va a leer
             * 20 byes. Restante: diferencia entre total disponible (40) entre
             * los leidos (20)*/
            int total, leido, restante;
            total = dis.available();
            System.out.println("Comprobacion de todos los bytes por leer --> " + total + "\n");
            System.out.println("Primera linea leida en UTF: " + dis.readUTF());
            leido = dis.available();
            restante = total - leido;
            System.out.println("Cantidad de bytes leidos: " + leido);
            System.out.println("Segunda linea leida en UTF: " + dis.readUTF());
            System.out.println("Cantidad de bytes restantes: " + restante);
            System.out.println("\n Comprobacion de que se ha leido todo: Bytes restantes --> " + dis.available());
            //System.out.println("writeUTF ha escrito: "+ textoL);
        } catch (FileNotFoundException ex) {
            System.err.println("Error encontrando el archivo.");
        } catch (IOException ex) {
            System.err.println("Error de I/O.");
        } finally {
            dis.close();
            dos.close();
        }
    }
    public void reiniciarDIS() {
        try {
            dis.close();
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(fichero)));
        } catch (IOException ex) {
            System.err.println("Error en el reinicio del DataInputStream.");
        }
    }
    public static void main(String[] args) {
        try {
            EJ4AD2 obj = new EJ4AD2();
            obj.proceder("Esto es una cadena");

        } catch (IOException ex) {
            System.err.println("Error en el main.");
        }
    }
}
