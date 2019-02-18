package util;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 工具类
 */
public class Tools {
    private static Connection conn;
    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    private static Logger logger = Logger.getLogger(Tools.class);

    static {
        try {
            Properties pro = new Properties();
            InputStream in = new FileInputStream("resources/database.properties");
            pro.load(in);//加载输入pro对象中
            /**
             * 从pro对象中读取数据
             */
            driver = pro.getProperty("driver");
            url = pro.getProperty("url");
            user = pro.getProperty("user");
            password = pro.getProperty("password");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
        /**
         *获取数据库连接
         */
        public static Connection getConnection(){
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url,user,password);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return conn;
        }
        /**
         * 关闭资源
         * @author dylan
         */
        public static void close(Connection conn, PreparedStatement pst, ResultSet rs){
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pst != null){
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


}
