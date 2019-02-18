package test;

import entity.Student;
import org.apache.log4j.Logger;
import org.junit.Test;
import util.Tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToolsTest {
    private PreparedStatement ps;
    private Connection conn;
    private ResultSet rs;
    private Logger logger = Logger.getLogger("ToolsTest.class");

    @Test
    public void testConnection() {
        conn = Tools.getConnection();
        logger.info(conn);
    }

    @Test
    public void testAddStudent() {
        Student student = new Student();
        student.setStudentNo(40013);
        student.setLoginPwd("123");
        student.setStudentName("张六");
        student.setSex("男");
        student.setGradeId(1);
        conn = Tools.getConnection();
        String sql = "INSERT INTO student(studentNo,loginPwd,studentName,sex,gradeId)" +
                " VALUES(?,?,?,?,?)";
        int result = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, student.getStudentNo());
            ps.setString(2, student.getLoginPwd());
            ps.setString(3, student.getStudentName());
            ps.setString(4, student.getSex());
            ps.setInt(5, student.getGradeId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
           logger.error(e.getMessage());
        } finally {
            Tools.close(conn,ps,null);
        }
        if (result > 0) {
            logger.info("插入成功");
        } else {
            logger.error("插入失败");
        }
    }
    @Test
    public void testUpdateStudent(){
        conn = Tools.getConnection();
        String sql = "UPDATE student SET loginPwd=? WHERE studentNo=?";
        Student student = new Student();
        student.setStudentNo(40013);
        student.setLoginPwd("123456");
        int result = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,student.getLoginPwd());
            ps.setInt(2,student.getStudentNo());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }finally {
            Tools.close(conn,ps,null);
        }
        if(result > 0){
            logger.info("更新成功");
        }else{
            logger.error("更新失败");
        }
    }

    @Test
    public void testDeleteStudentById(){
        conn = Tools.getConnection();
        String sql = "DELETE FROM student WHERE studentNo=?";
        Student student = new Student();
        student.setStudentNo(40013);
        int result = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,student.getStudentNo());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }finally {
            Tools.close(conn,ps,null);
        }
        if(result > 0){
            logger.info("删除成功");
        }else {
            logger.error("删除失败");
        }
    }

    @Test
    public void testQueryAll(){
        conn = Tools.getConnection();
        String sql = "SELECT * FROM student";
        List<Student> studentList = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Student student = new Student();
                student.setStudentNo(rs.getInt("studentNo"));
                student.setStudentName(rs.getString("studentName"));
                studentList.add(student);
            }
            if(studentList != null && studentList.size() > 0){
                for(Student stu : studentList){
                    logger.info("学号：" + stu.getStudentNo() + "，姓名："
                    + stu.getStudentName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Tools.close(conn,ps,rs);
        }
    }


    @Test
    public void testQueryStudentById(){
        String sql = "SELECT * FROM student WHERE studentNo=?";
        conn = Tools.getConnection();
        try {
            ps =  conn.prepareStatement(sql);
            ps.setInt(1,40013);
            rs = ps.executeQuery();
            while (rs.next()){
                Student student = new Student();
                student.setStudentNo(rs.getInt("studentNo"));
                student.setStudentName(rs.getString("studentName"));
                logger.info("学号：" + student.getStudentNo() + ",姓名："
                        + student.getStudentName());
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }finally {
            Tools.close(conn,ps,rs);
        }
    }


}
