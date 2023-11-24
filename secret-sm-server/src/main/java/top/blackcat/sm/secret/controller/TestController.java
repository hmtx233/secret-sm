package top.blackcat.sm.secret.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.blackcat.sm.secret.User;
import top.blackcat.sm.secret.result.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: 写注释，下班早
 * @Desc: 密改 接口
 * @Author: Jelly
 * @Date: 2023/10/25
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {


    /***
     * 加解密 测试 map 里 放 list
     * @param realName
     * @param phone
     * @return
     */
    @RequestMapping(value = "/testMap", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Object> testMap(String realName, String phone) {
        HashMap data1 = new HashMap<>();
        data1.put("phone", phone);
        data1.put("realName", realName);
        HashMap data2 = new HashMap<>();
        data2.put("phone", phone);
        data2.put("realName", realName);

        HashMap data3 = new HashMap<>();
        data3.put("phone", phone);
        data3.put("realName", realName);

        List list = new ArrayList<>();
        list.add(data1);
        list.add(data2);
        list.add(data3);

        Map map = new HashMap<>();
        map.put("ds1", list);
        return Result.success(map);
    }

    /***
     * 测试 list
     * @param realName
     * @param phone
     * @return
     */
    @RequestMapping(value = "/testMap2", method = {RequestMethod.GET, RequestMethod.POST})
    public Object testMap2(String realName, String phone) {
        HashMap data1 = new HashMap<>();
        data1.put("phone", phone);
        data1.put("realName", realName);
        HashMap data2 = new HashMap<>();
        data2.put("phone", phone);
        data2.put("realName", realName);

        HashMap data3 = new HashMap<>();
        data3.put("phone", phone);
        data3.put("realName", realName);

        List list = new ArrayList<>();
        list.add(data1);
        list.add(data2);
        list.add(data3);
        return list;
    }

    /***
     * 测试 map
     * @param realName
     * @param phone
     * @return
     */
    @RequestMapping(value = "/testMap3", method = {RequestMethod.GET, RequestMethod.POST})
    public Object testMap3(String realName, String phone) {
        HashMap data1 = new HashMap<>();
        data1.put("phone", phone);
        data1.put("realName", realName);
        return data1;
    }


    /***
     * 测试 string
     * @param realName
     * @param phone
     * @return
     */
    @RequestMapping(value = "/testMap4", method = {RequestMethod.GET, RequestMethod.POST})
    public User testMap4(String realName, String phone) {
        User user = new User();
        user.setRealName(realName);
        user.setPhone(phone);
        return user;
    }

}
