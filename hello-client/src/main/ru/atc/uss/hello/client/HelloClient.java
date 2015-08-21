package ru.atc.uss.hello.client;

import com.sun.istack.internal.Nullable;
import com.twitter.common.base.Function;
import com.twitter.common.quantity.Amount;
import com.twitter.common.quantity.Time;
import com.twitter.common.thrift.Thrift;
import com.twitter.common.thrift.ThriftFactory;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import ru.atc.uss.hello.Hello;
import ru.atc.uss.hello.Title;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Evgeny Stankevich {@literal <estankevich@at-consulting.ru>}
 */
public class HelloClient {

    public static void main(String [] args) {
        Set<InetSocketAddress> set = new HashSet<InetSocketAddress>();
        set.add(new InetSocketAddress("localhost", 9093));
        set.add(new InetSocketAddress("localhost", 9092));

        Thrift<Hello.Iface> clientBuilder = ThriftFactory.create(Hello.Iface.class)
                .withServiceName("calculator")
                .withDeadConnectionRestoreInterval(Amount.of(3L, Time.SECONDS))
//                    .withSocketTimeout(Amount.of(3L, Time.SECONDS))
                .withClientFactory(
                        new Function<TTransport, Hello.Iface>() {
                            @Nullable
                            @Override
                            public Hello.Iface apply(TTransport transport) {
                                TProtocol protocol = new TBinaryProtocol(transport);
                                return new Hello.Client(protocol);
                            }
                        }
                ).withMaxConnectionsPerEndpoint(10)
                .build(set);

        Hello.Iface client = clientBuilder.create();
        for (int i = 0; i < 1000; i++) {
            try {
                System.out.println(client.sayHello("Eugen" + i, Title.MR));
            } catch (TException e) {
                System.out.println(e.getMessage());
            }
        }
        clientBuilder.close();
    }

}
