package it.fi.itismeucci;

import java.io.*;
import java.net.*;
import java.util.*;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Serverthread extends Thread {
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String StringaInviata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    XmlMapper riceve = new XmlMapper();
    XmlMapper invia = new XmlMapper();

    public Serverthread(Socket socket) {
        this.client = socket;
    }

    protected Socket attendi() {
        try {
            System.out.println("1-Il Server è partito in esecuzione. . .");
            server = new ServerSocket(6789);
            client = server.accept();
            server.close();
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server !");
            System.exit(1);

        }
        return client;
    }

    public void comunica() throws IOException {
        Biglietto uno = new Biglietto("Palco 1-a");
        Biglietto due = new Biglietto("tribuna-2c");
        Biglietto tre = new Biglietto("parterre-7a");
        Biglietto quattro = new Biglietto("Maratona 8-5");
        Messaggio oneMessaggio = new Messaggio();
        oneMessaggio.Aggiungi(uno);
        oneMessaggio.Aggiungi(due);
        oneMessaggio.Aggiungi(tre);
        oneMessaggio.Aggiungi(quattro);

        outVersoClient = new DataOutputStream(client.getOutputStream());
        for (;;) {
            // riceve la richiesta del client
            Messaggio value = riceve.readValue(stringaRicevuta, Messaggio.class);
            if (value.getBiglietti().size() == 0) {
                String xml = invia.writeValueAsString(oneMessaggio);
                outVersoClient.writeBytes(xml + "\n");
                System.out.println("ECho sul server in chiusura :" + stringaRicevuta);
            } else {
                Messaggio two = riceve.readValue(stringaRicevuta, Messaggio.class);
                for (int i = 0; i < value.getBiglietti().size(); i++) {
                    if (value.getBiglietti().get(i).getIdentificativo() == two.getBiglietti().get(0)
                            .getIdentificativo()) {
                        value.getBiglietti().remove(value.getBiglietti().get(i));
                        StringaInviata = "Biglietto comprato";
                        outVersoClient.writeBytes(StringaInviata + '\n');
                        break;
                    }
                }
                StringaInviata = "Biglietto non trovato";
                outVersoClient.writeBytes(StringaInviata + '\n');
                break;
            }
        }
        outVersoClient.close();
        inDalClient.close();
        System.out.println("9 Chiusura socket" + client);
        client.close();

    }

    public void run() {
        try {
            comunica();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
