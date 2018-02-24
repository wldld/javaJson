package DAO;

import dto.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DaoBase {
    public static String driver = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/wallpaper?useSSL=false";
    public static String username = "root";
//    public static String password = "mbkmbk";
    public static Connection connection = null;
    public static String password = "123456";

    public static Connection getConnection() throws Exception{
        Class.forName(driver);
        connection = DriverManager.getConnection(url,username,password);
        return connection;
    }

    //用于存入wallpaper数据库的wallpaper表
    public void updataSql(wallpaper wallpaper) {
        String sql = "insert into wallpaper (id,thumb_url,img_url,preview_url) values ('"+wallpaper.getId()+"','"
                +wallpaper.getThumb_url()+"','"+wallpaper.getImg_url()+"','"+wallpaper.getPreview_url()+"')";
        try {
            if(connection == null){
             connection = getConnection();
            }
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //用于存入wallpaper数据库wallpaper_category表
    public void updataSqlWapCa(wallpaper_category wallpaper_category){
        wallpaper_category wap_ca = new wallpaper_category();
        String sql = "insert into wallpaper_category(wallpaper_id,category_id) values('"+wallpaper_category.getWallpaper_id()+"','"+wallpaper_category.getCategory_id()+"')";
        try {
            if(connection == null) {
                connection = getConnection();
            }
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//-----------------------------------------------------------------------------------------
    //用于存入tag的值:true or false
    public void  updateTag(wallpaper wallpaper) throws Exception{
        if(connection == null){
            connection =  getConnection();
        }
        String sql  = "update wallpaper set tag ='"+wallpaper.getTag()+"' where id ='"+wallpaper.getId()+"'";
        PreparedStatement pre = connection.prepareStatement(sql);
        pre.executeUpdate();
    }

    //用于删除tag=false的记录
    public void deleteWallpaper() throws Exception{
        if(connection == null){
            connection = getConnection();
        }
        String sql = "delete from wallpaper where tag = 'false'";
        PreparedStatement pre = connection.prepareStatement(sql);
        pre.executeUpdate();
    }

    //select查找图片:tag=null 限制20张
    public List<wallpaper> selectWallpaper() throws Exception{
        if(connection == null){
            connection = getConnection();
        }
        List<wallpaper> list = new ArrayList<>();
        String sql = "select * from wallpaper where tag is null limit 20";
        PreparedStatement pre = connection.prepareStatement(sql);
        ResultSet resultSet = pre.executeQuery();
        while (resultSet.next()){
            wallpaper wallpaper = new wallpaper();
            wallpaper.setId(resultSet.getString("id"));
            wallpaper.setThumb_url(resultSet.getString("thumb_url"));
            wallpaper.setImg_url(resultSet.getString("img_url"));
            wallpaper.setPreview_url(resultSet.getString("preview_url"));
            wallpaper.setTag(resultSet.getString("tag"));
            list.add(wallpaper);
        }
        return list;
    }

//    public static void main(String[] args) throws Exception{
//        DaoBase daoBase = new DaoBase();
//        List<wallpaper> list = new ArrayList<>();
//        wallpaper wallpaper = new wallpaper();
//        list = daoBase.selectWallpaper();
//        Iterator it = list.iterator();
//        int i =1;
//        while (it.hasNext()){
//            wallpaper=(wallpaper)it.next();
//            System.out.println(i);
//            System.out.println("id:"+wallpaper.getId());
//            System.out.println("img_url:"+wallpaper.getImg_url());
//            System.out.println("thumb_url::"+wallpaper.getPreview_url());
//            i++;
//        }
//    }

}
