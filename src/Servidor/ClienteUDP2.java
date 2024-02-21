/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author DAM
 */
public class ClienteUDP2 {

  public static void main(String args[]) throws Exception {
    DatagramSocket clientSocket = new DatagramSocket();
    byte[] buffer = new byte[1000];

    InetAddress serverAddress = InetAddress.getLocalHost();
    int serverPort = 9876;


      // Receive and display response from the server
      DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
      clientSocket.receive(receivePacket);
      String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
      System.out.println("Server response: " + receivedMessage);
    }


  public void enviarMensaje(String message) throws SocketException, IOException {

    DatagramSocket clientSocket = new DatagramSocket();
    byte[] buffer;
    byte[] buffer2 = new byte[4096];

    InetAddress serverAddress = InetAddress.getByName("25.40.47.143");
    int serverPort = 9876;
    // Registro del cliente al servidor
    String registrationMessage = "REGISTER";
    buffer2 = registrationMessage.getBytes();
    DatagramPacket registrationPacket = new DatagramPacket(buffer2, buffer2.length, serverAddress, serverPort);
    clientSocket.send(registrationPacket);

    buffer = new byte[message.getBytes().length];
    buffer = message.getBytes();
    DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
    clientSocket.send(sendPacket);

    // Receive and display response from the server
    DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
    clientSocket.receive(receivePacket);
    String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
    System.out.println("Server response: " + receivedMessage);
    
    clientSocket.close();
    
    
  }

}
