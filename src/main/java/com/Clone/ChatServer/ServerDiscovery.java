/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.Clone.ChatServer;

/**
 *
 * @author Meshack
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ServerDiscovery {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            String serverIp = InetAddress.getLocalHost().getHostAddress();
            String message = "ServerIP:" + serverIp;
            DatagramPacket packet = new DatagramPacket(
                message.getBytes(),
                message.length(),
                InetAddress.getByName("255.255.255.255"), // Broadcast
                8888 // Discovery port
            );
            while (true) {
                socket.send(packet);
                System.out.println("Broadcasting: " + message);
                Thread.sleep(5000); // Broadcast every 5 seconds
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
