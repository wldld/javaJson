package v1;

import DAO.DaoBase;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dto.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class imgShow {
    List<wallpaper> list = new ArrayList<>();

    //imgshow图片展示
    public String imgShow() throws Exception{
        List<wallpaper> list = new ArrayList<>();
        DaoBase daoBase = new DaoBase();
        list = daoBase.selectWallpaper();
        String jsonStr = JSON.toJSONString(list);
        return jsonStr;
    }

    //updateImg更新图片：先从前端页面获取了列表，然后将tag存入数据库
    public void updateImg(List<wallpaper> list) throws Exception{
        Iterator it = list.iterator();
        DaoBase daoBase = new DaoBase();
        while (it.hasNext()){
            wallpaper wallpaper =(wallpaper) it.next();
            daoBase.updateTag(wallpaper);
        }
    }

    //delete删除图片：确认删除图片后，从数据库中进行删除图片
    public void deleteImg() throws Exception{
        DaoBase daoBase = new DaoBase();
        daoBase.deleteWallpaper();
    }

    public JSONArray strToJson(String res){
        JSONArray jsonArray = JSON.parseArray(res);
        return  jsonArray;
    }

}
