package oceans.learn;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest

public class LearnAsync {
    @Resource
    LearnAsyncHelper learnAsyncHelper;

    // 无法实现异步
    @Test
    public void test1() {
        System.out.println("result => " + 1);
        this.hello();
        System.out.println("result => " + 2);
    }

    // 正常
    @Test
    public void test2() {
        System.out.println("result => " + 1);
        System.out.println("result => " + new Date().getTime()/1000);
        learnAsyncHelper.goodbye();
        System.out.println("result => " + 2);
        System.out.println("result => " + new Date().getTime()/1000);
    }

    // 不行
    @Async
    void hello() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result => " + "hello...");
    }
}
