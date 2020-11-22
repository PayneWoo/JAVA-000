package com.payne.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * JDBC原生操作
 *      1、使用 JDBC 原生接口，实现数据库的增删改查操作。
 *          1）加载 mysql 驱动
 *          2）创建数据库连接
 *          3）实例化Statement对象，执行数据库 curd 操作
 *      2、使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
 *      3、配置 Hikari 连接池，改进上述操作
 * @author Payne
 * @date 2020/11/22
 */
@Slf4j
public class JdbcUtil {

    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DATABASE_URL = "jdbc:mysql:/127.0.0.1:3306/online_hospital";
    private final static String DATABASE_URL_USER = "root";
    private final static String DATABASE_URL_PWD = "123456";

    private final static String QUERY = "query";
    private final static String INSERT = "insert";
    private final static String UPDATE = "update";
    private final static String DELETE = "delete";


    /**
     * 创建数据库连接
     * @return 数据库连接
     */
    public Connection getConnection(String url, String userName, String password) {

        Connection connection = null;
        try {
            // 1）加载mysql驱动
            Class.forName(JDBC_DRIVER);
            System.out.println("连接数据库...");
            connection = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            log.error("没有找到数据库驱动", e);
        } catch (SQLException sqlException) {
            log.error("数据库连接失败", sqlException);
        }
        return connection;
    }


    /**
     * 操作数据库
     * @param operationType 操作类型
     * @param sqlStatement sql
     */
    public void execute01(String operationType, String sqlStatement) throws SQLException {

        Connection conn = null;
        try {
            conn = getConnection(DATABASE_URL, DATABASE_URL_USER, DATABASE_URL_PWD);
            System.out.println(" 实例化Statement对象");
            Statement statement = conn.createStatement();
            if (operationType.equals(INSERT) || operationType.equals(UPDATE) || operationType.equals(DELETE)) {
                statement.executeUpdate(sqlStatement);
            } else if (operationType.equals(QUERY)) {
                ResultSet result = statement.executeQuery(sqlStatement);
                System.out.println(result.toString());
            }
        } catch (Exception e) {
            log.error("操作数据库出错", e);
        } finally {
            assert conn != null;
            conn.close();
        }
    }


    /**
     * 操作数据库（PrepareStatement方式）
     * @param operationType 操作类型
     * @param sqlStatement sql语句
     */
    public void execute02(String operationType, String sqlStatement) throws SQLException {

        Connection conn = null;
        try {
            conn = getConnection(DATABASE_URL, DATABASE_URL_USER, DATABASE_URL_PWD);
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            // 为占位符赋值
            preparedStatement.setString(1, "test");
            if (operationType.equals(INSERT) || operationType.equals(UPDATE) || operationType.equals(DELETE)) {
                preparedStatement.executeUpdate();
            } else if (operationType.equals(QUERY)) {
                ResultSet result = preparedStatement.executeQuery();
                System.out.println(result.toString());
            }
        }  catch (Exception e) {
            log.error("操作数据库出错", e);
        } finally {
            assert conn != null;
            conn.close();
        }
    }

    /**
     * 操作数据库（PrepareStatement方式 + 使用事务）
     * @param operationType 操作类型
     * @param sqlStatement sql语句
     */
    public void execute03(String operationType, String sqlStatement) throws SQLException {

        Connection conn = null;
        try {
            conn = getConnection(DATABASE_URL, DATABASE_URL_USER, DATABASE_URL_PWD);
            // 设置事务隔离级别
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            // 打开事务边界，取消自动提交，改为手动
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            // 为占位符赋值
            preparedStatement.setString(1, "test");
            if (operationType.equals(INSERT) || operationType.equals(UPDATE) || operationType.equals(DELETE)) {
                preparedStatement.executeUpdate();
                // 提交事务
                conn.commit();
            } else if (operationType.equals(QUERY)) {
                ResultSet result = preparedStatement.executeQuery();
                // 提交事务
                conn.commit();
                System.out.println(result.toString());
            }
        }  catch (Exception e) {
            // 出现异常，回滚
            assert conn != null;
            conn.rollback();
            log.error("操作数据库出错", e);
        } finally {
            assert conn != null;
            // 再把自动提交打开，避免影响其他需要自动提交的操作
            conn.setAutoCommit(true);
            conn.close();
        }
    }


    /**
     * 操作数据库（PrepareStatement方式 + 使用事务 + 批处理）
     * @param sqlStatement sql语句
     */
    public void execute04(String sqlStatement) throws SQLException {

        Connection conn = null;
        try {
            conn = getConnection(DATABASE_URL, DATABASE_URL_USER, DATABASE_URL_PWD);
            // 设置事务隔离级别
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            // 打开事务边界，取消自动提交，改为手动
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);

            for (int i = 0; i < 100; i++) {
                // 为占位符赋值
                preparedStatement.setString(1, "test");
                preparedStatement.addBatch();
            }

            // 执行批处理
            preparedStatement.executeBatch();

            conn.commit();
        }  catch (Exception e) {
            // 出现异常，回滚
            assert conn != null;
            conn.rollback();
            log.error("操作数据库出错", e);
        } finally {
            assert conn != null;
            // 再把自动提交打开，避免影响其他需要自动提交的操作
            conn.setAutoCommit(true);
            conn.close();
        }
    }




}
