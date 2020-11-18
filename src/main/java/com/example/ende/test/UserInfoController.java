package com.example.ende.test;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;



@RestController
@RequestMapping("/v1/api/")

public class UserInfoController {

    //测试
    @PostMapping(value = "test", produces = "application/json;charset=UTF-8")
    public User test(@RequestBody User json) {
//        JSONObject jsonObject = new JSONObject();
//        JSONObject clientJson = getClientJson(request, response);
//        System.out.println("【客户端收集到的数据为】clientJson={}"+ clientJson);
//        return clientJson.toString();
        return json;
    }

    public JSONObject getClientJson(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = null;
        try {
            //提取json
            //response.setContentType("application/json; charset=utf-8");
            response.setContentType("text/json");
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            BufferedReader br = null;

                br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String temp;
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }
                json = JSONObject.parseObject(sb.toString());


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("客户端请求数据异常->" + e.getMessage());
        }
        System.out.println(json.toString());
        return json;
    }

}
