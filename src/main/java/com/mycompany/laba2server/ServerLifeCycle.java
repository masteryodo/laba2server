
package com.mycompany.laba2server;

import java.text.ParseException;
import com.mycompany.laba2server.model.InformationSystemModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;

public class ServerLifeCycle
{
    public static final int PORT = 8181;
    private static InformationSystemModel model;
    
    public ServerLifeCycle() throws ParserConfigurationException {
        this.model = new InformationSystemModel();
    }
    
    public static void main(String[] args) throws ParserConfigurationException, ParseException, IOException
    {
        //ServerController controller = new ServerController(model);
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Started: " + s);
        try {
            while(true) {
                Socket socket = s.accept();
                new Thread(new ServerProcess(socket, model)).start();
            }
        } finally {
            s.close();
        }

    }


    
}
