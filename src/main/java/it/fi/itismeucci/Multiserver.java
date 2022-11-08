package it.fi.itismeucci;

import java.io.*;
import java.net.*;
import java.util.*;


public class Multiserver{
    
public Multiserver() {
    }

public void avvio()
    {
        try{
        ServerSocket ServerSocket=new ServerSocket(6789);
        for(;;)
        {
            System.out.println("1 Server in attesa . . .");
            Socket socket=ServerSocket.accept();
            System.out.println("3 Server socket"+socket);
            Serverthread serverThread=new Serverthread(socket);
            serverThread.start();

        }
    }
    catch(Exception e)
    {
        System.out.println(e.getMessage());
        System.out.println("Errore durantre l'instanza del server !");
        System.exit(1);
    }
}
}
