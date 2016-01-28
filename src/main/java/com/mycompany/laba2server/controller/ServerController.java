package com.mycompany.laba2server.controller;
import com.mycompany.laba2server.dto.Client;
import com.mycompany.laba2server.dto.Order;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.mycompany.laba2server.model.InformationSystemModel;
import com.mycompany.laba2server.view.View;
import com.mycompany.laba2server.utils.XmlReaderWriter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

//--------------------------------------------------------------------------------
public class ServerController
{
    private final InformationSystemModel model;
    private final XmlReaderWriter xml = new XmlReaderWriter();
    private final View view;
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//-------------------------------------------------------------------------------
    
    public ServerController(InformationSystemModel model)
    {
        this.model = model;
        view = new View(model);
    }
    
    //-----------------------------------------------------------------------------------------
    
    public void getCommand(BufferedInputStream bis, File file) throws ParseException, IOException, ParserConfigurationException, SAXException
    {   
        FileOutputStream fos = new FileOutputStream(file);
        int in;
        byte[] buffer = new byte[1024];
        
        while ((in = bis.read()) > 0){
            fos.write(in);
        }
        fos.close();
        // как только получили файл парсим и выполняем событие
        String action = xml.getActionFromXml(file);
        System.out.println(action);
        
            Client c = xml.getClientFromXml(file);
            Order o = xml.getOrderFromXml(file);
            if(c != null) 
            {
                System.out.println(c);
            }
            if(o != null) 
            {
                System.out.println(o);
            }
        
    }
    //-----------------------------------------------------------------------------
    
    public void add() throws ParseException
    {
        //model.addClient(client);
        //model.addOrder(order);
    }

    private void remove() {
        //model.removeClient(id);
        //model.removeOrder(id);
    }
    
    private void modify() throws ParseException {
        //model.commitOrders();
        // здесь будем тупо перезаписывать клиента или ордер полностью
    }
    
//-------------------------------------------------------------------------------
    
    private void eventFromXml() {
        String str ;

        //model.importElementsFromXml(str);
        try {
            
            System.out.println("Импорт успешно завершен");
        } catch (Exception e) {
            System.out.println("Импорт завершился ошибкой "+e);
        }
        
    }

}
