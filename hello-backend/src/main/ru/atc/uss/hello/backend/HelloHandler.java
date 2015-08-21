package ru.atc.uss.hello.backend;

import org.apache.thrift.TException;
import ru.atc.uss.hello.Hello;
import ru.atc.uss.hello.Title;

/**
 * @author Evgeny Stankevich {@literal <estankevich@at-consulting.ru>}
 */
public class HelloHandler implements Hello.Iface {

    @Override
    public String sayHello(String name, Title title) throws TException {
        return "Hello, " + titleToString(title) + " " + name + "!";
    }

    private String titleToString(Title title) {
        switch (title) {
            case MISS:
                return "Miss.";
            case MR:
                return "Mr.";
            case MRS:
                return "Mrs.";
            case MS:
                return "Ms.";
            default:
                return "";
        }
    }
}
