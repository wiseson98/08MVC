package client.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;




public class RestHttpClientApp2 {
	
	// main Method
	public static void main(String[] args) throws Exception{

//		1.1 Http Get 방식 Request : JsonSimple lib 사용
//		RestHttpClientApp2.getProductest_JsonSimple();		

//		1.2 Http Get 방식 Request : codehaus lib 사용
//		RestHttpClientApp2.getProductTest_codehaus();	
		
//		2.1 Http Post 방식 Request : JsonSimple lib 사용
//		RestHttpClientApp2.addProductTest_JsonSimple();	
		
//		2.2 Http Post 방식 Request : codehaus lib 사용
//		RestHttpClientApp2.addProductTest_codehaus();
		
//		3.1 Http Post 방식 Request : JsonSimple lib 사용
//		RestHttpClientApp2.updateProductTest_JsonSimple();	
		
//		3.2 Http Post 방식 Request : codehaus lib 사용
//		RestHttpClientApp2.updateProductTest_codehaus();
	
//		4.1 Http Post 방식 Request : JsonSimple lib 사용
//		RestHttpClientApp2.listProductTest_JsonSimple();	
		
//		4.2 Http Post 방식 Request : codehaus lib 사용
//		RestHttpClientApp2.listProductTest_codehaus();
		
		RestHttpClientApp2.getProductNameTest_codehaus();

	}	
	
	public static void getProductNameTest_codehaus() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/product/json/productNameList/";
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		System.out.println(httpResponse);
		
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream in = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		
		String serverData = br.readLine();
		System.out.println(serverData);
		
		JSONObject jsonObject = (JSONObject)JSONValue.parse(serverData);
		System.out.println(jsonObject);
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> list = objectMapper.readValue(jsonObject.get("productNames").toString(), new TypeReference<List<String>>() {});
		
		for(String str : list) {
			System.out.println(str);
		}
	}
	
	
	public static void getProductest_JsonSimple() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/product/json/getProduct/10010";
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept","application/json");
		httpGet.setHeader("Content-Type", "application/json");
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		System.out.println(httpResponse);
		System.out.println();
		
		HttpEntity httpEntity = httpResponse.getEntity();
		
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		System.out.println("[ Server에서 받은 Data 확인 ] ");
		String serverData = br.readLine();
		System.out.println(serverData);
		
		JSONObject jsonObject = (JSONObject)JSONValue.parse(serverData);
		System.out.println(jsonObject);
	}
	
	public static void getProductTest_codehaus() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/product/json/getProduct/10009";
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		System.out.println(httpResponse);
		System.out.println();
		
		HttpEntity httpEntity = httpResponse.getEntity();
		
		InputStream in = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		
		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(br.readLine(), Product.class);
		System.out.println(product);
	}
	
	public static void addProductTest_JsonSimple() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/product/json/addProduct";
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		JSONObject json = new JSONObject();
		json.put("prodName", "닌텐도");
		json.put("prodDetail", "스위치 배터리 개선");
		json.put("price", new Integer(298700));
		json.put("manuDate", "2021-09-05");
		
		HttpEntity httpEntity = new StringEntity(json.toString(), "utf-8");
		httpPost.setEntity(httpEntity);
		
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		System.out.println(httpResponse);
		
		HttpEntity httpEntity2 = httpResponse.getEntity();
		
		InputStream in = httpEntity2.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		
		JSONObject jsonObject = (JSONObject)JSONValue.parse(br.readLine());
		System.out.println(jsonObject);
	}
	
	public static void addProductTest_codehaus() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/product/json/addProduct";
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		Product product = new Product();
		product.setProdName("그릇");
		product.setProdDetail("장인이 만든 도자기");
		product.setPrice(50000);
		product.setManuDate("2020-02-10");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonValue = objectMapper.writeValueAsString(product);
		
		System.out.println(jsonValue);
		
		HttpEntity httpEntity = new StringEntity(jsonValue, "utf-8");
		httpPost.setEntity(httpEntity);
		
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		System.out.println(httpResponse);
		
		HttpEntity httpEntity2 = httpResponse.getEntity();
		
		InputStream in = httpEntity2.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		
		Product product2 = objectMapper.readValue(br.readLine(), Product.class);
		System.out.println(product2);
	}

	public static void updateProductTest_JsonSimple() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/product/json/updateProduct";
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		JSONObject json = new JSONObject();
		json.put("prodNo", 10016);
		json.put("prodName", "aaaa");
		json.put("prodDetail", "뚜레주르");
		json.put("manuDate", "2022-04-26");
		json.put("price", new Integer(25000));
		
		HttpEntity httpEntity = new StringEntity(json.toString(), "utf-8");
		httpPost.setEntity(httpEntity);
		
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		System.out.println(httpResponse);
		
		HttpEntity httpEntity2 = httpResponse.getEntity();
		InputStream in = httpEntity2.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		
		JSONObject jsonObject = (JSONObject)JSONValue.parse(br.readLine());
		System.out.println(jsonObject);
	}
	
	public static void updateProductTest_codehaus() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/product/json/updateProduct";
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		Product product = new Product();
		product.setProdNo(10016);
		product.setProdName("케이크");
		product.setProdDetail("스타벅스");
		product.setManuDate("2022-04-26");
		product.setPrice(15000);
		
		ObjectMapper objectMapper = new ObjectMapper();		
		HttpEntity httpEntity = new StringEntity(objectMapper.writeValueAsString(product), "utf-8");
		httpPost.setEntity(httpEntity);
		
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		System.out.println(httpResponse);
		
		HttpEntity httpEntity2 = httpResponse.getEntity();
		InputStream in = httpEntity2.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		Product product2 = objectMapper2.readValue(br.readLine(), Product.class);
		System.out.println(product2);		
	}

	public static void listProductTest_JsonSimple() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://127.0.0.1:8080/product/json/listProduct/manage";
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");	
		
//		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		JSONObject json = new JSONObject();
		HttpEntity httpEntity1 = new StringEntity(json.toString(), "utf-8");
		httpPost.setEntity(httpEntity1);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		System.out.println(httpResponse);
		
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream in = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		
		JSONObject jsonObject = (JSONObject)JSONValue.parse(br.readLine());
		JSONArray jsonArray = (JSONArray)jsonObject.get("list");
		System.out.println(jsonObject.get("menu"));
		System.out.println(jsonObject.get("search"));
		System.out.println(jsonObject.get("resultPage"));
		System.out.println(jsonArray);		
	}
	
	public static void listProductTest_codehaus() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/product/json/listProduct/search";
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		Search search = new Search();
		search.setSearchCondition("1");
		search.setSearchKeyword("주스");
				
		ObjectMapper objectMapper = new ObjectMapper();
		HttpEntity httpEntity = new StringEntity(objectMapper.writeValueAsString(search), "utf-8");
		httpPost.setEntity(httpEntity);
		
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity2 = httpResponse.getEntity();
		
		InputStream in = httpEntity2.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		JSONObject jsonObject = (JSONObject)JSONValue.parse(br.readLine());
		
		List<Product> list = objectMapper2.readValue(jsonObject.get("list").toString(), new TypeReference<List<Product>>() {});
		for(Product product : list) {
			System.out.println(product);
		}		
	}
}