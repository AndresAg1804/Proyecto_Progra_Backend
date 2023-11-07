package instrumentos.logic;

import java.io.IOException;
import java.util.List;

public class Worker {
    Server srv;
    ObjectSocket os;
    IService service;

    public Worker(Server srv, ObjectSocket os, IService service) {
        this.srv=srv;
        this.os=os;
        this.service=service;
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
    
    public void listen(){
        int method;
        while (continuar) {
            try {
                method = os.in.readInt();
                System.out.println("Operacion: "+method);
                switch(method){
                    case Protocol.CREATETI:
                    try {
                        service.create((TipoInstrumento)os.in.readObject());
                        os.out.writeInt(Protocol.ERROR_NO_ERROR);
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
                            service.update((TipoInstrumento)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.DELETETI:
                        try {
                            service.delete((TipoInstrumento)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
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
                            service.create((Instrumento)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
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
                            service.update((Instrumento)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.DELETEI:
                        try {
                            service.delete((Instrumento)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
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
                            service.create((Calibraciones)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
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
                            service.update((Calibraciones)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.DELETEC:
                        try{
                            service.delete((Calibraciones)os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
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
                            List<Mediciones> list = service.searchMedicionesByCalibracion(String.valueOf((Calibraciones)os.in.readObject()));
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(list);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.SEARCHCBI:
                        try {
                            List<Calibraciones> list = service.searchCalibracionesByInstrumento(String.valueOf((Instrumento)os.in.readObject()));
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(list);
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
//                    case Protocol.SEARCHMBC:
//                        try {
//                            List<Mediciones> list = service.searchByCalibraciones((Calibraciones)os.in.readObject());
//                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
//                            os.out.writeObject(list);
//                        } catch (Exception ex) {
//                            os.out.writeInt(Protocol.ERROR_ERROR);
//                        }
//                        break;
                }
            } catch (IOException  ex) {
                System.out.println(ex);
                continuar = false;
                srv.remove(this);
            }                        
        }
    }

}
