import com.payne.shardingspherejdbc.ShardingSphereJdbcDemoApplication;
import com.payne.shardingspherejdbc.model.User;
import com.payne.shardingspherejdbc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * ShardingSphere-jdbc 读写分离测试类
 * @author Payne
 * @date 2020/12/7
 */
@Slf4j
@SpringBootTest(classes = ShardingSphereJdbcDemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(name = "classpath:application.properties")
public class ReadWriteTest {

    @Autowired
    private UserService userService;

    /**
     * 从库读数据，轮询算法
     */
    @Test
    public void testRead() {
        List<User> list = userService.list();
        log.info("查数据：{}", list);
    }

    /**
     * 主库写数据
     */
    @Test
    public void testWrite() {
        String result = userService.save(new User(4, "奥尼尔",  36));
        log.info("写数据结果：{}", result);
    }
}
