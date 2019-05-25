package com.aoye.product.service;

import com.aoye.common.utils.IdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.applet.Main;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InstallerServiceTest {

    @Autowired
    private IdWorker idWorker;

    @Value("${installersPath}")
    private String filesPath;

    @Test
    public void test1(){
        System.out.println(idWorker.nextId());
        System.out.println(filesPath);
    }

    @Test
    public void test2() {
        String string = "helloWorld";
        System.out.println(string.substring(3));
    }

}