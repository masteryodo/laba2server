
package com.mycompany.laba2server;

import com.mycompany.laba2server.controller.ServerController;

import java.io.*;
import java.net.Socket;

class ServerProcess implements Runnable {

    private final Socket socket;
    private BufferedInputStream fileIn;
    private BufferedOutputStream fileOut;
    private final ServerController controller;

    public ServerProcess(Socket socket, ServerController controller) throws IOException 
    {
        this.socket = socket;
        fileIn = new BufferedInputStream(socket.getInputStream());
        //fileOut = new BufferedOutputStream(socket.getOutputStream());
        this.controller = controller;
    }

    @Override
    public void run() {

            System.out.println("Connection accepted: " + socket);
            while (true) {
            try {
                // принимаем файл и пытаемся его разобрать
                controller.getCommand(fileIn);
                    }
            catch(IOException e) {
                e.printStackTrace();    
            }

            finally {
            System.out.println("closing " + socket + "...");
            try {
                fileIn.close();
                socket.close();
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
            }}

 /*   public void readInputFile(BufferedInputStream bis) throws IOException {
        
        File file = new File("test1.txt");
        FileOutputStream fos = new FileOutputStream(file);
        int in;
        byte[] buffer = new byte[1024];
        
        while ((in = bis.read()) != -1){
            fos.write(in);
            System.out.println(in);
        }
        fos.close();
    }*/
    
}
