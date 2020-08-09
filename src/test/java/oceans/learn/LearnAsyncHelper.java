package oceans.learn;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LearnAsyncHelper {

    @Async
    void goodbye() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("result => " + "bye...");
        System.out.println("result => " + new Date().getTime()/1000);
    }
}
