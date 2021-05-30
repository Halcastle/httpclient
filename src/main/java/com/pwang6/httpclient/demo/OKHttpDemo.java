//package com.pwang6.httpclient.demo;
//
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.log4j.Log4j2;
//import okhttp3.*;
//import okhttp3.Request.Builder;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
//@Log4j2
//public class OKHttpDemo {
//    public static void main(String[] args){
//        OKHttpDemo okHttpDemo = new OKHttpDemo();
//        okHttpDemo.getDemo();
//    }
//
//    /**
//     * 发送get请求demo
//     */
//    public void getDemo(){
//        OkHttpClient okHttpClient = new OkHttpClient();
////        OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                .connectTimeout(Duration.ofSeconds(10))//连接超时
////                .writeTimeout(Duration.ofSeconds(5))//写超时，也就是请求超时
////                .readTimeout(Duration.ofSeconds(5))//读取超时
////                .callTimeout(Duration.ofSeconds(15))//调用超时，也是整个请求过程的超时
////                .build();
//        Request request = new Request.Builder().url("http://localhost:8080/http/demo/get").get().build();
//        try(Response response = okHttpClient.newCall(request).execute()){
//            ResponseBody responseBody = response.body();
//            if(response.isSuccessful()){
//                log.info("success:{}",responseBody == null ? "" : responseBody.string());
//            }else{
//                log.error("error,statusCode={},body={}", response.code(), responseBody == null ? "" : responseBody.string());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 发送get请求,带查询参数demo
//     */
//    public void getDemoParameter(){
//        OkHttpClient okHttpClient = new OkHttpClient();
//        HttpUrl httpUrl = HttpUrl.parse("http://localhost:8080/http/demo/get").newBuilder().addQueryParameter("name", "HTTP").build();
//        Request request = new Request.Builder().url(httpUrl.toString()).get().build();
//        try(Response response = okHttpClient.newCall(request).execute()){
//            ResponseBody responseBody = response.body();
//            if(response.isSuccessful()){
//                log.info("success:{}",responseBody == null ? "" : responseBody.string());
//            }else{
//                log.error("error,statusCode={},body={}", response.code(), responseBody == null ? "" : responseBody.string());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 发送post请求
//     */
//    public void postDemo(){
//        OkHttpClient okHttpClient = new OkHttpClient();
//        //request body
//        Map<String,String> map = new HashMap<>();
//        map.put("name","HTTP");
//        map.put("age","25");
//        Request request = new Request.Builder().url("http://localhost:8080/http/demo/post2").post(RequestBody.create(MediaType.get("application/json"), JSONObject.toJSONString(map))).build();
//        try(Response response = okHttpClient.newCall(request).execute()){
//            ResponseBody responseBody = response.body();
//            if(response.isSuccessful()){
//                log.info("success:{}",responseBody == null ? "" : responseBody.string());
//            }else{
//                log.error("error,statusCode={},body={}", response.code(), responseBody == null ? "" : responseBody.string());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
