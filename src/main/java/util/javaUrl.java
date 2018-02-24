package util;

import DAO.DaoBase;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dto.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class javaUrl {
    static ArrayList<String> list= null;

    public static void main(String[] args) throws IOException{
        list = new ArrayList<String>();
        wallpaper wallpaper = new wallpaper();
        wallpaper_category wallpaper_category = new wallpaper_category();
        DaoBase daoBase = new DaoBase();
        String jsonR = "";
        //新建了数组列表收藏固定的url
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000003/vertical?limit=30&adult=false&first=1&order=new");
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000002/vertical?limit=30&adult=false&first=1&order=new");
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000007/vertical?limit=30&adult=false&first=1&order=new");
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4fb479f75ba1c65561000027/vertical?limit=30&adult=false&first=1&order=new");
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4ef0a35c0569795756000000/vertical?limit=30&adult=false&first=1&order=new");
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4fb47a195ba1c60ca5000222/vertical?limit=30&adult=false&first=1&order=new");
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4fb47a465ba1c65561000028/vertical?limit=30&adult=false&first=1&order=new");
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4ef0a3330569795757000000/vertical?limit=30&adult=false&first=1&order=new");
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000004/vertical?limit=30&adult=false&first=1&order=new");
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000005/vertical?limit=30&adult=false&first=1&order=new");
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4fb47a305ba1c60ca5000223/vertical?limit=30&adult=false&first=1&order=new");
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000001/vertical?limit=30&adult=false&first=1&order=new");
        list.add("http://service.aibizhi.adesk.com/v1/vertical/category/4ef0a34e0569795757000001/vertical?limit=30&adult=false&first=1&order=new");

        Iterator it = list.iterator();
        String url = null;
        int category_id = 1;

        //For循环，外层（数组列表循环一遍）
        //For循环，内层（URL的skip循环一遍）break条件，result = null
        //单次
        while (it.hasNext()) {
            url =(String) it.next();
            int skip;//每次循环都把skip清零
//            System.out.println(url);
            for (skip=0; ; ) {
                //HTTP请求获取json数据
                jsonR = sendGet(url, "skip=" + skip);

                JSONObject json = JSONObject.parseObject(jsonR);
                JSONObject res = json.getJSONObject("res");
                JSONArray vertical = res.getJSONArray("vertical");
                if (vertical == null || vertical.isEmpty()) {
                    break;
                }
                //解析json:把json数组解析出来
                for (Object a : vertical) {
                    JSONObject b = (JSONObject) a;
                    //wallpaper表的实现类，设置完成后，存到数据库
                    wallpaper.setId(b.getString("id"));
                    wallpaper.setThumb_url(b.getString("thumb"));
                    wallpaper.setImg_url(b.getString("img"));
                    wallpaper.setPreview_url(b.getString("preview"));
                    daoBase.updataSql(wallpaper);
                    //wapaper_category表的实现类，设置完成后，存到数据库
                    wallpaper_category.setWallpaper_id(b.getString("id"));
                    wallpaper_category.setCategory_id(category_id);
                    daoBase.updataSqlWapCa(wallpaper_category);
                }
                skip = skip + 30;
                //延迟0.5秒
                try {
                    Thread.currentThread().sleep(500);//毫秒
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            category_id++;
        }

    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "&" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

//    public static void main(String[] args) {
//        String jsonR = null;
//        jsonR = sendGet("http://service.aibizhi.adesk.com/v1/vertical/category/4e4d610cdf714d2966000003/vertical?limit=30&adult=false&first=1&order=new","skip=8948");
//        System.out.println(jsonR);
//    }
}
