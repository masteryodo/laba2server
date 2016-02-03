
package com.mycompany.laba2server;

import com.mycompany.laba2server.controller.ServerController;
import com.mycompany.laba2server.model.InformationSystemModel;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

class ServerProcess implements Runnable {

    private final Socket socket;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private final ServerController controller ;
    private final String transactionFile;
    private File file;
    

    public ServerProcess(Socket socket, InformationSystemModel model) throws IOException 
    {
        this.socket = socket;
        bis = new BufferedInputStream(socket.getInputStream());
        bos = new BufferedOutputStream(socket.getOutputStream());
        this.controller = new ServerController(model, bis, bos);
        this.transactionFile = "trans"+socket.getPort()+".xml";
        this.file = new File(transactionFile);
    }
    @Override
    public void run() {

            System.out.println("Connection accepted: " + socket);
            controller.sendUpdatesToClient();
            while (true) {
            try {
                // принимаем файл и пытаемся его разобрать
                controller.getCommand(file);

                // Если пришло -1 закрываем сокет (закрывается со стороны клиента)
                if(bis.read() == -1)
                {
                        System.out.println("closing " + socket + "...");
                        try {
                            System.out.println("Все операции выполнены закрываем сокет и входящий поток");
                            bis.close();
                            socket.close();
                            file.delete();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        } 
                }        
            }
            catch(ParseException | IOException e) 
            {
                e.printStackTrace();
            }   catch (ParserConfigurationException ex) {
                    Logger.getLogger(ServerProcess.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(ServerProcess.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
}

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
    

