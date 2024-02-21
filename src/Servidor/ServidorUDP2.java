/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Vista.Interfaz;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DAM
 */
public class ServidorUDP2 {

    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] buffer = new byte[4096];

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(receivePacket);

            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");

            if (receivedMessage.equalsIgnoreCase("REGISTER")) {
                String registrationAck = "Registration successful";
                buffer = registrationAck.getBytes();
                DatagramPacket registrationAckPacket = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
                serverSocket.send(registrationAckPacket);
            } else {
                MulticastSocket ms = new MulticastSocket();
// Se escoge un puerto para el server
                int puerto = 12345;
// Se escoge una direcciÃ³n para el grupo
                InetAddress grupoMulticast = InetAddress.getByName("225.0.0.1");
                String cadena = "";
                cadena = receivedMessage;
                System.out.println(cadena);
// Enviamos el mensaje a todos los clientes que se hayan unido al grupo
                DatagramPacket paquete = new DatagramPacket(cadena.getBytes(), cadena.length(), grupoMulticast, puerto);
                ms.send(paquete);
                //  }
            }
        }

    }

}
