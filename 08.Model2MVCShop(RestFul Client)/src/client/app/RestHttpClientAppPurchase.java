package client.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

public class RestHttpClientAppPurchase {

	public static void main(String[] args) throws Exception{
//		System.out.println("\n====================================\n");
//		// 1.1 Http Get 방식 Request : JsonSimple lib 사용
//		RestHttpClientAppPurchase.getPurchaseTest_JsonSimple();
		
//		System.out.println("\n====================================\n");
//		// 1.2 Http Get 방식 Request : CodeHaus lib 사용
//		RestHttpClientAppPurchase.getPurchaseTest_Codehaus();
		
//		System.out.println("\n====================================\n");
//		// 2.1 Http Get 방식 Request : JsonSimple lib 사용
//		RestHttpClientAppPurchase.addPurchaseTest_JsonSimple();
		
//		System.out.println("\n====================================\n");
//		// 2.2 Http Get 방식 Request : CodeHaus lib 사용
		RestHttpClientAppPurchase.addPurchaseTest_Codehaus();
	}
	
	public static void getPurchaseTest_JsonSimple() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/purchase/json/getPurchase/10001";
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream in = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		
		JSONObject jsonObject = new JSONObject();
		jsonObject = (JSONObject)JSONValue.parse(br.readLine());
		System.out.println(jsonObject);
	}
	
	public static void getPurchaseTest_Codehaus() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/purchase/json/getPurchase/10001";
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream in = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		
		ObjectMapper objectMapper = new ObjectMapper();
		Purchase purchase = objectMapper.readValue(br.readLine(), Purchase.class);
		System.out.println(purchase);
	}
	
	public static void addPurchaseTest_JsonSimple() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/purchase/json/addPurchase";
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		JSONObject json = new JSONObject();
		JSONObject jsonProduct = new JSONObject();
		JSONObject jsonBuyer = new JSONObject();
		
		jsonProduct.put("prodNo", 10022);
		jsonBuyer.put("userId", "user15");
		json.put("paymentOption", "001");
		json.put("purchaseProd", jsonProduct);
		json.put("buyer", jsonBuyer);
		json.put("receiverName", "홍길동");
		json.put("receiverPhone", "010-1111-2222");
		json.put("dlvyAddr", "서울시 은평구");
		json.put("dlvyRequest", "도자기 취급 주의");
		json.put("tranCode", "002");
		json.put("dlvyDate", "2022-04-29");
		
		HttpEntity httpEntity = new StringEntity(json.toString(), "utf-8");
		httpPost.setEntity(httpEntity);
		
		HttpResponse httpResponse = httpClient.execute(httpPost);
		System.out.println(httpResponse);
		
		HttpEntity httpEntity2 = httpResponse.getEntity();
		InputStream in = httpEntity2.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		
		JSONObject jsonObject = new JSONObject();
		jsonObject = (JSONObject)JSONValue.parse(br.readLine());		
		System.out.println(jsonObject);
	}
	
	public static void addPurchaseTest_Codehaus() throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		
		String url = "http://127.0.0.1:8080/purchase/json/addPurchase";
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(new Product());
		purchase.getPurchaseProd().setProdNo(10008);
		purchase.setBuyer(new User());
		purchase.getBuyer().setUserId("user02");
		purchase.setPaymentOption("002");
		purchase.setReceiverName("이순신");
		purchase.setReceiverPhone("010-0000-0011");
		purchase.setDlvyAddr("서울시 강남구");
		purchase.setDlvyRequest("경비실 앞");
		purchase.setDlvyDate("2022-05-01");
		
		ObjectMapper objectMapper = new ObjectMapper();
		HttpEntity httpEntity = new StringEntity(objectMapper.writeValueAsString(purchase), "utf-8");
		httpPost.setEntity(httpEntity);
		
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		HttpEntity httpEntity2 = httpResponse.getEntity();
		InputStream in = httpEntity2.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		Purchase resultPurchase = objectMapper2.readValue(br.readLine(), Purchase.class);
		
		System.out.println(resultPurchase);
	}
}