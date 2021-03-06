package com.mycompany.laba2server.controller;
import static com.mycompany.laba2server.Constants.*;
import com.mycompany.laba2server.dto.Client;
import com.mycompany.laba2server.dto.Order;
import java.text.ParseException;
import com.mycompany.laba2server.model.InformationSystemModel;
import com.mycompany.laba2server.utils.XmlReaderWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class ServerController
{
    private final InformationSystemModel model;
    private final XmlReaderWriter xml = new XmlReaderWriter();
    private BufferedOutputStream bos;
    private BufferedInputStream bis;
    private Socket socket;

    public ServerController(InformationSystemModel model, BufferedInputStream bis, BufferedOutputStream bos, Socket socket)
    {
        this.model = model;
        this.bos = bos;
        this.bis = bis;
        this.socket = socket;
    }
  
    public void getCommand(File file) throws ParseException, IOException, ParserConfigurationException, SAXException
    {   
        
        FileOutputStream fos = new FileOutputStream(file);
        int in = 0;

        while ((in = bis.read()) != 0){
            if (in == -1) {
                fos.close();
                file.delete();
                bis.close();
                socket.close();
                System.out.println("Socket is closed");
                break;
            }
            fos.write(in);
        }
        fos.close();
        if (file.exists()) {

        String action = xml.getActionFromXml(file);

            Client client = xml.getClientFromXml(file);
            Order order = xml.getOrderFromXml(file);
            if(client != null) {
                switch (action){
                    case "add": 
                        model.addClient(client); 
                        System.out.println("Client: "+client.getName()+" added");
                        break;
                        
                    case "modify": 
                        System.out.println("modify client " + client);
                        model.removeClient(client.getId());
                        model.addClient(client);
                        System.out.println("Client: "+client.getName()+" modifed");
                        break;
                        
                    case "remove": 
                        model.removeClient(client.getId());
                        System.out.println("Client: "+client.getName()+" removed");
                        break;
                } 
            }
            
            if(order != null) {
                switch (action){
                    case "add": 
                        model.addOrder(order); 
                        System.out.println("order: "+order.getOrderId()+" added");
                        break;
                    case "modify": 
                        model.removeOrder(order.getOrderId());
                        model.addOrder(order); 
                        System.out.println("order: "+order.getOrderId()+" modifed");
                        break;
                    case "remove": 
                        System.out.println("Удаляется ордер " + order.getOrderId() );
                        System.out.println(model);
                        try {
                            model.removeOrder(order.getOrderId()); 
                        } catch (Exception e) {
                            System.out.println("case remove " + e);
                        }

                        System.out.println("order: "+order.getOrderId()+" removed");
                        break;
                } 
            }
       }
    sendUpdatesToClient();
        System.out.println("getcommand конец итерации");
    }

    public void sendUpdatesToClient(){
        try {
            FileInputStream fis = new FileInputStream(new File(CLIENTS_FILE));
            int in = 0;

            while ((in = fis.read()) != -1 ){
                bos.write(in);
            }
            bos.write(0);
            bos.flush();
            fis.close();
            
            fis = new FileInputStream(new File(ORDERS_FILE));
            in = 0;
            while ((in = fis.read()) != -1 ){
                bos.write(in);
            }
            bos.write(0);
            bos.flush();
            fis.close();
            
            
        } catch (Exception e) {
            System.out.println("sendUpdatesToClient: "+e);
        }
    }

}
