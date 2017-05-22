package sample.activemq.vtdparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Sender implements CommandLineRunner {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Override
    public void run(String... args) throws Exception {

        send(getFile("test.xml"));
        Thread.sleep(1000);
        send(getFile("test1.xml"));

        System.out.println("Message was sent to the Queue");
    }

    public Map getFile(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        FileInputStream fis = new FileInputStream(file);
        byte[] fileByteArray = new byte[(int) file.length()];
        fis.read(fileByteArray);
        Map map = new HashMap<String, Object>();
        map.put("file", fileByteArray);
        map.put("fileName", fileName);
        return map;
    }

    public void send(Map map) {
        this.jmsMessagingTemplate.convertAndSend(this.queue, map);
    }

}
