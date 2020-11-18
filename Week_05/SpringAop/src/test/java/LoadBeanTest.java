import com.payne.entity.*;
import com.payne.loadBean.ConditionBeanConfiguration;
import com.payne.loadBean.DataBaseConfiguration;
import com.payne.loadBean.JamesConfiguration;
import com.payne.loadBean.KyrieConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试 Spring bean 的装配
 * @author Payne
 * @date 2020/11/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@ActiveProfiles("dev")
public class LoadBeanTest {

    /**
     * 1。使用 @Autowired 「自动装配」 bean
     */
    @Autowired
    private Student student;

    /**
     * 2。通过 XML 装配 bean
     */
    @Test
    public void loadBeanByXml() {
        // 通过 XML 配置来实例化 Spring 容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 在Spring容器中获取Bean对象
        Student sss = context.getBean(Student.class);
        System.out.println("从 Spring 容器中主动获取到的Bean对象: " + sss.getName());
        System.out.println("Spring 容器通过 @Autowired 注入的Bean对象: " + student.getName());
        Assert.assertEquals("Jordan", student.getName());
    }


    /**
     * 3。配置类中显式的声明了 bean, @Configuration 注解自动来装配 bean
     */
    @Test
    public void loadBeanByJavaConfig() {

        // 通过Java配置来实例化 Spring 容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JamesConfiguration.class);

        // 在Spring容器中获取Bean对象
        James james = context.getBean(James.class);

        String team = james.getTeam();
        Assert.assertEquals("Lakers", james.getTeam());
        System.out.println(team);

    }


    /**
     * 4。通过 @ComponentScan 注解，开启组件扫描，查找带有 @Component 注解的类，并在 Spring 中自动为其创建一个 bean
     */
    @Test
    public void autoLoadBean() {

        // 通过Java配置来实例化 Spring 容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KyrieConfiguration.class);
        Kyrie kyrie =  context.getBean(Kyrie.class);
        System.out.println(kyrie.say());

        String[] definitionNames = context.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }


    /**
     * 5。通过 @Profile 注解条件化 bean
     */
    @Test
    public void loadBeanByProfile() {
        // 通过Java配置来实例化 Spring 容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataBaseConfiguration.class);
        String[] definitionNames = context.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
        DruidDataSource mySQL = (DruidDataSource) context.getBean("getMySQL");
        System.out.println(mySQL.getDriverClassName());
    }


    /**
     * 6。通过 @Conditional 注解条件化 bean
     *     可以用在带有 @Bean 注解的方法上，如果给定的条件计算结果为 true,就会创建这个 bean
     */
    @Test
    public void loadBeanByConditional() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConditionBeanConfiguration.class);
        String[] names = context.getBeanDefinitionNames();
        for(String name : names){
            // 通过 @Conditional 注解条件化 bean：getConditionBean
            System.out.println("name = " + name);
        }
    }

}
