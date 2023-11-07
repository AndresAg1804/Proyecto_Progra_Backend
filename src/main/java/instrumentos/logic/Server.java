
package instrumentos.logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    ServerSocket srv;
    List<Worker> workers;

    public Server() {
        try {
            srv = new ServerSocket(Protocol.PORT);
            workers = Collections.synchronizedList(new ArrayList<Worker>());
            System.out.println("Servidor iniciado...");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void run() {
        IService service = new Service();

        boolean continuar = true;
        ObjectSocket os = null;
        Socket skt = null;
        while (continuar) {
            try {
                skt = srv.accept();
                os = new ObjectSocket(skt);
                System.out.println("Conexion Establecida...");
                Worker worker = new Worker(this, os, service);
                workers.add(worker);
                System.out.println("Quedan: " + workers.size());
                worker.start();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void remove(Worker w) {
        workers.remove(w);
        System.out.println("Quedan: " + workers.size());
    }

}