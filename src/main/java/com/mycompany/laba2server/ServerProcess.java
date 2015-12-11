
package com.mycompany.laba2server;

import com.mycompany.laba2server.controller.ServerController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;


class ServerProcess implements Runnable {

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private ServerController controller;

    public ServerProcess(Socket socket, ServerController controller) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
        this.controller = controller;
    }

    @Override
    public void run() {
        String command = null;
        String args = null;
        String str;
        try {
            System.out.println("Connection accepted: " + socket);
            while (true) {
                try {
                    str = in.readLine();
                    try {
                        command = str.split(";;")[0];
                        args = str.split(";;")[1];
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    
                    if (str.equals("disconnect")) { break; } // убиваем сокет
                    controller.getCommand(command, args);
                    System.out.println("Echo: " + command + " "+ args );
                    out.println(str);
                }
                catch(IOException e) {
                    e.printStackTrace();
                } 
                catch (ParseException ex) {
                    Logger.getLogger(ServerProcess.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            System.out.println("closing " + socket + "...");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
}
