package com.mycompany.laba2server.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.mycompany.laba2server.model.InformationSystemModel;
import com.mycompany.laba2server.view.View;
import java.util.Scanner;
import com.mycompany.laba2server.utils.XmlReaderWriter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ServerController
{
    private final InformationSystemModel model;
    private final XmlReaderWriter xml = new XmlReaderWriter();
    private final View view;
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    public ServerController(InformationSystemModel model)
    {
        this.model = model;
        view = new View(model);
    }
    
    public void getCommand(BufferedInputStream bis, String filename) throws ParseException, IOException
    {   
        File file = new File(filename + ".xml");
        FileOutputStream fos = new FileOutputStream(file);
        int in;
        byte[] buffer = new byte[1024];
        
        while ((in = bis.read()) > 0){
            fos.write(in);
            System.out.println(in);
        }
        
        fos.close();
        System.out.println("Файл принят");
        String action = null;
        
    }

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

    private void find() {
        String str;
        System.out.println("Выберите тип элемента");
        System.out.println("1 Клиент");
        System.out.println("2 Заказ");
        System.out.print("spr1.0: ");
        Scanner scanner = new Scanner(System.in);
        str = scanner.nextLine();
        String mask;
        switch( str )
        {   
            case "1" :
                System.out.println("Введите маску для поиска клиента (возможны символы '*' и '?')");
                System.out.print("spr1.0: ");
                mask = scanner.nextLine();
                model.findClientsByMask(mask);
                view.showLastSearchClients();
                break;
            case "2" :
                System.out.println("Введите маску для поиска заказа (возможны символы '*' и '?')");
                System.out.print("spr1.0: ");
                mask = scanner.nextLine();
                model.findOrdersByMask(mask);
                view.showLastSearchOrders();
                break;
            default :
                System.out.println("Вы ввели неверное значение " + str + "попробуйте заново");
        }
    }
    private void eventFromXml() {
        String str ;
        System.out.println("Введите имя файла (можно использовать подготовленный для теста data2import.xml)");
        System.out.print("spr1.0: ");
        Scanner scanner = new Scanner(System.in);
        str = scanner.nextLine();
        model.importElementsFromXml(str);
        try {
            
            System.out.println("Импорт успешно завершен");
        } catch (Exception e) {
            System.out.println("Импорт завершился ошибкой "+e);
        }
        
    }
}
