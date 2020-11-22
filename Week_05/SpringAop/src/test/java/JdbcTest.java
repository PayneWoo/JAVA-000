import com.payne.service.DataServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

/**
 * @author Payne
 * @date 2020/11/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class JdbcTest {
//    @Autowired
//    DataServiceImpl dataService;
//
//    @Test
//    public void testQuery() throws SQLException {
//        dataService.query();
//    }
}
