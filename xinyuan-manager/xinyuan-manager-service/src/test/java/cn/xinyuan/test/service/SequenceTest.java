package cn.xinyuan.test.service;

import cn.xinyuan.mapper.SequenceValueItemMapper;
import cn.xinyuan.pojo.SequenceValueItem;
import cn.xinyuan.service.SequenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * User:josli li
 * Date:17/8/25
 * Time:下午2:41
 * Mail:josli@kargocard.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class SequenceTest {
    @Autowired
    private SequenceService sequenceService ;
    @Test
    public void test(){
        System.out.println("-------------");
//       // final java.util.List list = new ArrayList();
//        for (int i=0;i<30;i++){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for ( int j=0;j<10;j++){
//                        System.out.println(sequenceService.getNextSeqId("hello",1)+"  序列号");
//                       // list.add(sequenceService.getNextSeqId("hello",1)+"  序列号");
//                    }
//                }
//            }).start();
//        }
//        while (true){
//
//        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
        System.out.println(dateFormat.format(new Date())+sequenceService.getNextSeqId("hello",1));
    }

}
