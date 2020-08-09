package oceans.other;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OtherTest {
    @Test
    public void testJDK8() {
        List<String> oldList = new ArrayList<>(Arrays.asList("111", "222", "333"));
        Stream<String> stringStream = oldList.stream().map(item -> item.substring(0, 1));
        List<String> newList = stringStream.collect(Collectors.toList());
        System.out.println("result => " + newList);
    }

    @Test
    public void testResponseEntity() {
        ResponseEntity<String> entity = new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
        System.out.println("result => " + entity.getBody());
        System.out.println("result => " + entity.getStatusCode());
        System.out.println("result => " + entity.getStatusCodeValue());
        System.out.println("result => " + entity.getHeaders());
        System.out.println("result => " + entity.toString());
    }

    @Test
    public void testBcrypt() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String str = encoder.encode("root");
        System.out.println("result => " + str);
    }

    /**
     * HTTP METHOD必须大写
     */
    @Test
    public void testEnumValueOf() {
        HttpMethod get = HttpMethod.valueOf("GET");
        System.out.println("result => " + get);
    }

    /**
     * 使用正则表达式
     */
    @Test
    public void testRegex() {
        Pattern pattern = Pattern.compile("hello");
        String string = "hello world hello world";
        Matcher matcher = pattern.matcher(string);
        boolean matches = matcher.matches();
        boolean b1 = matcher.find();
        String group = matcher.group(); // 位置很重要
        boolean b2 = matcher.find();
        boolean b3 = matcher.find();
        System.out.println("result => " + matches);
        System.out.println("result => " + b1);
        System.out.println("result => " + b2);
        System.out.println("result => " + b3);
        System.out.println("result => " + group);
    }

    @Test
    public void testSubString(){
        String s = "1234567";
        String substring = s.substring(0, -1);
        System.out.println("result => " + substring);
    }
}
