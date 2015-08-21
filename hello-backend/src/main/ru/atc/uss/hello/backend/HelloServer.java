package ru.atc.uss.hello.backend;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import ru.atc.uss.hello.Hello;

/**
 * @author Evgeny Stankevich {@literal <estankevich@at-consulting.ru>}
 */
public class HelloServer {

    public static HelloHandler handler;

    public static Hello.Processor processor;

    public static void main(String [] args) {
        try {
            handler = new HelloHandler();
            processor = new Hello.Processor(handler);

            Runnable simple = new Runnable() {
                public void run() {
                    simple(processor);
                }
            };

            new Thread(simple).start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void simple(Hello.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9092);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            System.out.println("Hello server is starting to serve requests...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
