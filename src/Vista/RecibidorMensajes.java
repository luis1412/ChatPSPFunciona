/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luish
 */
public class RecibidorMensajes extends Thread {

    Interfaz i;

    public RecibidorMensajes(Interfaz i) {
        this.i = i;
    }

    @Override
    public void run() {
        try {
            recibirMensaje();
        } catch (IOException ex) {
            Logger.getLogger(RecibidorMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String recibirMensaje() throws IOException {

        int puerto = 12345;//Puerto multicast
        MulticastSocket ms = new MulticastSocket(puerto);
        //Nos unimos al grupo multicast
        InetAddress grupo = InetAddress.getByName("225.0.0.1");
        ms.joinGroup(grupo);
        //ms.joinGroup(mcastaddr, netIf);
        String msg = "";
        while (msg != null) {
            // El buffer se crea dentro del bucle para que se sobrescriba
            // con cada nuevo mensaje
            byte[] buf = new byte[1000];
            DatagramPacket paquete = new DatagramPacket(buf, buf.length);
            //Recibe el paquete del servidor multicast
            ms.receive(paquete);
            msg = new String(paquete.getData(), 0, paquete.getLength());
            System.out.println("Recibo: " + msg.trim());

            int indiceSegundoCorchete = 0;
            for (int j = 0; j < msg.length(); j++) {
                if ((msg.charAt(j) + "").equals("]")) {
                    indiceSegundoCorchete = j;
                }
            }
            String cadenaCorchetes = msg.substring(0, indiceSegundoCorchete);
            if (!cadenaCorchetes.contains(i.getNombreUsuario())) {
                i.escribirText("", msg, false);
            }
        }
        // Abandonamos grupo
        ms.leaveGroup(grupo);
        //ms.leaveGroup(mcastaddr, netIf);
        // Cerramos recursos
        ms.close();
        //  System.out.println("Socket cerrado...");
        return "";
    }

}
