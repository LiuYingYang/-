package com.medusa.basemall.messages.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* 
 * 利用HttpClient进行post请求的工具类 
 */  
public class HttpClientUtil {

    public String doPost(String url, Map<String,String> map, String charset){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{  
            httpClient = new SSLClient();
	        HttpPost  httpPost1= new HttpPost(url);
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                httpPost.setEntity(entity);  
            }  
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){  
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);
                }  
            }  
        }catch(Exception ex){
            ex.printStackTrace();  
        }  
        return result;  
    }  
    
         public static String postByJSON(JSONObject json, String URL) {
    	 
    	         HttpPost post = new HttpPost(URL);
    	         
    	         post.setHeader("Content-Type", "application/json");
    	         post.addHeader("Authorization", "Basic YWRtaW46");
    	         String result = "";
    	         
    	         try {
    	 
    	             StringEntity s = new StringEntity(json.toString(), "utf-8");
    	             s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
    	                     "application/json"));
    	             post.setEntity(s);
    	 
    	             // 发送请求
    	             HttpClient client = new DefaultHttpClient();
    	             HttpResponse httpResponse = client.execute(post);
    	 
    	             // 获取响应输入流
    	             InputStream inStream = httpResponse.getEntity().getContent();
    	             BufferedReader reader = new BufferedReader(new InputStreamReader(
    	                     inStream, "utf-8"));
    	             StringBuilder strber = new StringBuilder();
    	             String line = null;
    	             while ((line = reader.readLine()) != null) {
						 strber.append(line + "\n");
					 }
    	             inStream.close();
    	 
    	             result = strber.toString();
    	             
    	             if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
    	                 
    	                    // System.out.println("请求服务器成功，做相应处理");
    	                 
    	             } else {
    	                 
    	                 System.out.println("请求服务端失败");
    	                 
    	             }
    	             
    	 
    	         } catch (Exception e) {
    	             System.out.println("请求异常");
    	            throw new RuntimeException(e);
    	        }
    	 
    	         return result;
    	     }
         
     	public static String HttpGet(String url) throws Exception {
    		// 第一步：把HttpClient使用的jar包添加到工程中。
    		// 第二步：创建一个HttpClient的测试类
    		// 第三步：创建测试方法。
    		// 第四步：创建一个HttpClient对象
    		CloseableHttpClient httpClient = HttpClients.createDefault();
    		// 第五步：创建一个HttpGet对象，需要制定一个请求的url
    		HttpGet get = new HttpGet(url);
    		// 第六步：执行请求。
    		CloseableHttpResponse response = httpClient.execute(get);
    		// 第七步：接收返回结果。HttpEntity对象。
    		HttpEntity entity = response.getEntity();
    		// 第八步：取响应的内容。
    		String html = EntityUtils.toString(entity);
    		System.out.println(html);
    		// 第九步：关闭response、HttpClient。
    		response.close();
    		httpClient.close();
    		
    		return html;
    	}
    	
    	public static String testHttpPost() throws Exception {
    		
    		// 第一步：创建一个httpClient对象
    		CloseableHttpClient httpClient = HttpClients.createDefault();
    		// 第二步：创建一个HttpPost对象。需要指定一个url
    		HttpPost post = new HttpPost("http://localhost:8082/posttest.html");
    		// 第三步：创建一个list模拟表单，list中每个元素是一个NameValuePair对象
    		List<NameValuePair> formList = new ArrayList<NameValuePair>();
    		formList.add(new BasicNameValuePair("name", "张三"));
    		formList.add(new BasicNameValuePair("pass", "1243"));
    		// 第四步：需要把表单包装到Entity对象中。StringEntity
    		StringEntity entity = new UrlEncodedFormEntity(formList, "utf-8");
    		post.setEntity(entity);
    		// 第五步：执行请求。
    		CloseableHttpResponse response = httpClient.execute(post);
    		// 第六步：接收返回结果
    		HttpEntity httpEntity = response.getEntity();
    		String result = EntityUtils.toString(httpEntity);
    		System.out.println(result);
    		// 第七步：关闭流。
    		response.close();
    		httpClient.close();
    		return result;
    	}
    	
    	public static String WxHttpPost(String urlPath, String json)
    			throws Exception {
    		System.out.println(urlPath);
    		URL url = new URL(urlPath);
    		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    		urlConnection.setDoOutput(true);
    		urlConnection.setRequestProperty("Charsert", "utf-8");
    		urlConnection.setRequestProperty("content-type",
    				"application/x-www-form-urlencoded");
    		// 得到请求的输出流对象
    		DataOutputStream out = new DataOutputStream(
    				urlConnection.getOutputStream());
    		out.write(json.getBytes("UTF-8"));
    		out.flush();
    		out.close();
    		InputStream inputStream = urlConnection.getInputStream();
    		String body = IOUtils.toString(inputStream, "UTF-8");
    		return body;
    	}

	public static void main(String[] args) {
		try {
			String s = HttpClientUtil.HttpGet("http://restapi.amap.com/v3/config/district?keywords=&subdistrict=3&key=3d0918c9dc956b167c7d6af57bed4460");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}  