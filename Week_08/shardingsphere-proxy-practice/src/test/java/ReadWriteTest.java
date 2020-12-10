import com.payne.shardingspherejdbc.ShardingSphereJdbcDemoApplication;
import com.payne.shardingspherejdbc.model.OrderInfo;
import com.payne.shardingspherejdbc.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * ShardingSphere-proxy 测试类
 * @author Payne
 * @date 2020/12/7
 */
@Slf4j
@SpringBootTest(classes = ShardingSphereJdbcDemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(name = "classpath:application.properties")
public class ReadWriteTest {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 3 % 2 = 1, 所以会直接从 test1 库查数据
     */
    @Test
    public void queryByUserId() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", 3);
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderId(map);
        log.info("查数据：{}", orderInfo);
    }

    /**
     * 111 % 16 = 15, 所以会从 test0和test1库的 t_order_15 表查数据
     */
    @Test
    public void queryByOrderId() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", 111);
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderId(map);
        log.info("查数据：{}", orderInfo);
    }


    /**
     * 直接从 test1 库的 t_order_15 表查数据
     */
    @Test
    public void queryByUserIdAndOrderId() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", 3);
        map.put("orderId", 111);
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderId(map);
        log.info("查数据：{}", orderInfo);
    }


    /**
     *
     * 直接写入数据到 test1 库的 t_order_15 表
     */
    @Test
    public void testWrite() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(1);
        orderInfo.setUserId(3);
        orderInfo.setOrderId(111);
        orderInfo.setSellerId("seller0001");
        orderInfo.setGoodsNo("I000001");
        orderInfo.setSnapshotId("K0000001");
        orderInfo.setBusinessType(1);
        orderInfo.setOrderAmount(new BigDecimal("90.1"));
        orderInfo.setPayAmount(new BigDecimal("90.1"));
        orderInfo.setPayWay(1);
        orderInfo.setPayChannel(1);
        orderInfo.setPayUrl("https://pay.payne.com");
        orderInfo.setMerchantPayNo("82384875");
        orderInfo.setWebPayNo("1289238498");
        orderInfo.setOrderStatus(1);
        String result = orderInfoService.save(orderInfo);
        log.info("写数据结果：{}", result);
    }
}
