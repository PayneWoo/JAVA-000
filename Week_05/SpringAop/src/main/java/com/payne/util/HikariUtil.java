package com.payne.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 使用 Hikari 优化数据库连接
 * @author Payne
 * @date 2020/11/22
 */
public class HikariUtil {

    private static DataSource dataSource;
    private static Connection connection;
    private final static String DATABASE_URL = "jdbc:mysql:/127.0.0.1:3306/online_hospital";
    private final static String DATABASE_URL_USER = "root";
    private final static String DATABASE_URL_PWD = "123456";

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DATABASE_URL);
        config.setUsername(DATABASE_URL_USER);
        config.setPassword(DATABASE_URL_PWD);
        // 从连接池获取连接时最大等待时间
        config.addDataSourceProperty("connectionTimeout", "1000");
        // 连接可以在池中的最大闲置时间
        config.addDataSourceProperty("idleTimeout", "60000");
        // 连接池中可以保留连接的最大数量
        config.addDataSourceProperty("maximumPoolSize", "10");
        // 创建 Hikari 数据源
        dataSource = new HikariDataSource(config);

    }

    /**
     * 创建数据库连接
     * @return 数据库连接
     */
    public Connection getConnection() {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

}
