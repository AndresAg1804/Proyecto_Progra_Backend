package instrumentos.logic;

import java.io.IOException;
import java.util.List;

public class Worker {
    Server srv;
    ObjectSocket os; // Syncronous Socket
    ObjectSocket as; // Asynchronous Socket
    IService service;


    public Worker(Server srv, ObjectSocket os, IService service) {
        this.srv=srv;
        this.os=os;
        this.service=service;
    }
    public void setAs(ObjectSocket as) {
        this.as = as;
    }
    boolean continuar;    
    public void start(){
        try {
            System.out.println("Worker atendiendo peticiones...");
            Thread t = new Thread(new Runnable(){
                public void run(){
                    listen();
                }
            });
            continuar = true;
            t.start();
        } catch (Exception ex) {  
        }
    }
    
    public void stop(){
        continuar=false;
        System.out.println("Conexion cerrada...");
    }
    public void deliver(Message message){
        if(as != null) {
            try {
                as.out.writeInt(Protocol.DELIVER);
                as.out.writeObject(message);
                as.out.flush();
            } catch (IOException ex) {
            }
        }
    }
    
    public void listen(){
        int method;
        while (continuar) {
            try {
                method = os.in.readInt();
                System.out.println("Operacion: "+method);
                switch(method){
                    case Protocol.DISCONNECT:
                        try{
                            this.stop();
                            srv.remove(this);
                        } catch (Exception ex) {}
                        break;
                    case Protocol.CREATETI:
                    try {
                        TipoInstrumento e = (TipoInstrumento)os.in.readObject();
                        service.create(e);
                        os.out.writeInt(Protocol.ERROR_NO_ERROR);
                        srv.deliver(new Message(Message.CREATE, "TI", e.getNombre()));
                    } catch (Exception ex) {
                        os.out.writeInt(Protocol.ERROR_ERROR);
                    }
                    break;                 
                    case Protocol.READTI:
                    try {
                        TipoInstrumento e = service.read((TipoInstrumento)os.in.readObject());
                        os.out.writeInt(Protocol.ERROR_NO_ERROR);
                        os.out.writeObject(e);
                    } catch (Exception ex) {
                        os.out.writeInt(Protocol.ERROR_ERROR);
                    }
                    break;
                    case Protocol.UPDATETI:
                        try {
                            TipoInstrumento e = (TipoInstrumento)os.in.readObject();
                            service.update(e);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver(new Message(Message.UPDATE, "TI", e.getNombre()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.DELETETI:
                        try {
                            TipoInstrumento e = (TipoInstrumento)os.in.readObject();
                            service.delete(e);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver(new Message(Message.DELETE, "TI", e.getNombre()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.SEARCHTI:
                        try {
                            List<TipoInstrumento> list = service.search((TipoInstrumento)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(list);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.CREATEI:
                        try {
                            Instrumento e = (Instrumento)os.in.readObject();
                            service.create(e);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver(new Message(Message.CREATE, "I", e.getSerie()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.READI:
                        try {
                            Instrumento e = service.read((Instrumento)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(e);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.UPDATEI:
                        try {
                            Instrumento e = (Instrumento)os.in.readObject();
                            service.update(e);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver(new Message(Message.UPDATE, "I", e.getSerie()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.DELETEI:
                        try {
                            Instrumento e = (Instrumento)os.in.readObject();
                            service.delete(e);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver(new Message(Message.DELETE, "I", e.getSerie()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.SEARCHI:
                        try {
                            List<Instrumento> list = service.search((Instrumento)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(list);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.CREATEC:
                        try {
                            Calibraciones e = (Calibraciones)os.in.readObject();
                            service.create(e);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver(new Message(Message.CREATE, "C", e.getNumero()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.READC:
                        try {
                            Calibraciones e = service.read((Calibraciones)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(e);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.UPDATEC:
                        try{
                            Calibraciones e = (Calibraciones)os.in.readObject();
                            service.update(e);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver(new Message(Message.UPDATE, "C", e.getNumero()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.DELETEC:
                        try{
                            Calibraciones e = (Calibraciones)os.in.readObject();
                            service.delete(e);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver(new Message(Message.DELETE, "C", e.getNumero()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.SEARCHC:
                        try {
                            List<Calibraciones> list = service.search((Calibraciones)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(list);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.CREATEM:
                        try {
                            service.create((Mediciones)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.READM:
                        try {
                            Mediciones e = service.read((Mediciones)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(e);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.UPDATEM:
                        try {
                            service.update((Mediciones)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.DELETEM:
                        try {
                            service.delete((Mediciones)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.SEARCHM:
                        try {
                            List<Mediciones> list = service.search((Mediciones)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(list);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.SEARCHMBC:
                        try {
                            List<Mediciones> list = service.searchMedicionesByCalibracion((String)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(list);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.SEARCHCBI:
                        try {
                            List<Calibraciones> list = service.searchCalibracionesByInstrumento((String)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(list);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                }
                os.out.flush();
            } catch (IOException  ex) {
                System.out.println(ex);
                continuar = false;
                srv.remove(this);
            }                        
        }
    }

}
