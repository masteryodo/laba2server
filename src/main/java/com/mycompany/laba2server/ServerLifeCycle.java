
package com.mycompany.laba2server;

import java.text.ParseException;
import com.mycompany.laba2server.model.InformationSystemModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static com.mycompany.laba2server.Constants.*;
import javax.xml.parsers.ParserConfigurationException;

public class ServerLifeCycle
{

    public static void main(String[] args) throws ParserConfigurationException, ParseException, IOException
    {   
        InformationSystemModel model = new InformationSystemModel();
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
