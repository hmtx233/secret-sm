package top.blackcat.sm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        JSONObject data = new JSONObject();
        data.put("name", "海波");
        data.put("phone", "18701559738");
        JSONArray array = new JSONArray();
        array.add(new JSONObject(data));
        array.add(new JSONObject(data));
        array.add(new JSONObject(data));
        System.out.println(array.toString());
        data.put("name", "郭海波");
        System.out.println(array.toString());


    }


}