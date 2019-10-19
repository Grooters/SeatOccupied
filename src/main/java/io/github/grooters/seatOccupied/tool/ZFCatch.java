package io.github.grooters.seatOccupied.tool;

import io.github.grooters.seatOccupied.model.User;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZFCatch {
	private static ZFCatch hfCatch;
	// 学号
	private String number = "201610098237";
	private String name = "李林浪";
	// 密码
	private String pass = "lll53041229519";
	// 个人信息http://jwxt.gcu.edu.cn/xsgrxx.aspx?xh=[学号]&xm=[编码的姓名(可以直接传中文)]&gnmkdm=N121501
	private String personInfoUrl = "http://jwxt.gcu.edu.cn/xsgrxx.aspx?xh=201610098237&xm=%C0%EE%C1%D6%C0%CB&gnmkdm=N121501";
	private final String HOME_URL = "http://jwxt.gcu.edu.cn/";
	private final String LOGIN_URL = HOME_URL + "default2.aspx";
	private final String CHECKCODE_URL = HOME_URL + "CheckCode.aspx";
	private final String TARGET_URL = "http://jwxt.gcu.edu.cn/xs_main.aspx?xh=";
	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36";
	private final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
	private final String ACCEPT_ENCODING = "gzip, deflate, sdch";
	private final String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.8";
	private final String CACHE_CONTROL = "max-age=0";
	private final String CONNECTION = "keep-alive";
	private final String HOST = "jwxt.gcu.edu.cn";
	private final String REX_RESULT = "(?<=alert\\(').+(?=\\！\\！\\'\\))";
	private final String REX_PASS = "(?<=alert\\(').+(?=\\!\\'\\))";
	private final String REX_NAME = "(?<=\\\"\\>).+(?=同学)";
	private final String REX_DEPARTMENT = "(?<=\\\"lbl_xy\\\">).+(?=\\<\\/span)";
	private final String REX_MAJOR = "(?<=\\\"lbl_xzb\\\">).+(?=\\<\\/span)";
	private final String REX_SEX = "(?<=\\\"lbl_xb\\\">).+(?=\\<\\/span)";
	private final String UPGRADE_INSECURE_REQUESTS = "1";
	private CloseableHttpClient client;
	private String viewState;
	private String checkCode;

	public ZFCatch() {
	}

	public int requestLogin(String number, String pass) {
		this.number = number;
		this.pass = pass;
		getViewState();
		getCheckCode();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("__VIEWSTATE", viewState));
		nvps.add(new BasicNameValuePair("txtUserName", number));
		nvps.add(new BasicNameValuePair("TextBox2", pass));
		nvps.add(new BasicNameValuePair("txtSecretCode", checkCode));
		nvps.add(new BasicNameValuePair("RadioButtonList1", "学生"));
		nvps.add(new BasicNameValuePair("Button1", "登录"));
		HttpPost httpost = new HttpPost("http://jwxt.gcu.edu.cn/default2.aspx");
		httpost.addHeader("Accept", ACCEPT);
		httpost.addHeader("Accept-Encoding", ACCEPT_ENCODING);
		httpost.addHeader("Accept-Language", ACCEPT_LANGUAGE);
		httpost.addHeader("Cache-Control", CACHE_CONTROL);
		httpost.addHeader("Connection", CONNECTION);
		httpost.addHeader("Host", HOST);
		httpost.addHeader("Referer", LOGIN_URL);
		httpost.addHeader("Upgrade-Insecure-Requests", UPGRADE_INSECURE_REQUESTS);
		httpost.addHeader("User-Agent", USER_AGENT);
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "GB2312"));
			RequestConfig config = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(1000)
					.setConnectionRequestTimeout(1000).setRedirectsEnabled(false).build();
			httpost.setConfig(config);
			CloseableHttpResponse loginResponse = client.execute(httpost);
			String resultCode = loginResponse.getStatusLine().toString();
			String body = EntityUtils.toString(loginResponse.getEntity());
			System.out.println("resultCode:" + resultCode);
			if (resultCode.equals("HTTP/1.1 302 Found")) {
				HttpGet httpGet = new HttpGet(TARGET_URL + number);
				CloseableHttpResponse targetResponse = client.execute(httpGet);
				String reslutContent = EntityUtils.toString(targetResponse.getEntity());
				name = getResult(REX_NAME, reslutContent);
				personInfoUrl = HOME_URL + "xsgrxx.aspx?xh=" + number + "&xm=" + name + "&gnmkdm=N121501";
				System.out.println("personInfoUrl:" + personInfoUrl);
				// writeBody(targetResponse.getEntity().getContent(),"GB2312");
				return 0;
			} else {
				// System.out.println("body:"+body);
				String result = getResult(REX_RESULT, body);
				String resultPass = getResult(REX_PASS, body);
				loginResponse.close();
				if (result == null && resultPass == null)
					System.out.println("未知错误");
				else if (result == null)
					System.out.println(resultPass);
				else
					System.out.println(result);
				if (body.contains("用户名不存在"))
					return 1;
				else if (body.contains("密码错误"))
					return 2;
				else
					return 3;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("未知错误");
		return 3;
	}

	public User getPersonalInfo(User user) {
		// if(loginResultCode!=0){
		// System.out.println("未成功登录，无法获取个人信息");
		// return;
		// }
		try {
			HttpGet httpGet = new HttpGet(personInfoUrl);
			httpGet.setHeader("Referer", TARGET_URL + number);
			CloseableHttpResponse personResponse = client.execute(httpGet);
			String reslutContent = EntityUtils.toString(personResponse.getEntity());
			String department = getResult(REX_DEPARTMENT, reslutContent);
			String major = getResult(REX_MAJOR, reslutContent);
			String sex = getResult(REX_SEX, reslutContent);
			System.out.println("department:" + department + " major:" + major + " sex:" + sex);
			user.setNumber(number);
			user.setPass(pass);
			user.setName(name);
			user.setDepartment(department);
			user.setMajor(major);
			user.setSex(sex);
			// writeBody(personResponse.getEntity().getContent(),"GB2312");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;

	}

	private String writeBody(InputStream in, String encoding) {
		try {
			ByteArrayOutputStream byteouts = new ByteArrayOutputStream();
			FileOutputStream out = new FileOutputStream("D:\\body.txt");
			byte[] bytes = new byte[1024];
			int len = 0;
			while ((len = in.read(bytes)) != -1) {
				byteouts.write(bytes, 0, len);
				out.write(bytes);
			}
			in.close();
			return new String(byteouts.toByteArray(), encoding);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void getViewState() {
		try {
			client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(LOGIN_URL);
			httpGet.addHeader("Accept", ACCEPT);
			httpGet.addHeader("Accept-Encoding", ACCEPT_ENCODING);
			httpGet.addHeader("Accept-Language", ACCEPT_LANGUAGE);
			httpGet.addHeader("Cache-Control", CACHE_CONTROL);
			httpGet.addHeader("Connection", CONNECTION);
			httpGet.addHeader("Upgrade-Insecure-Requests", UPGRADE_INSECURE_REQUESTS);
			httpGet.addHeader("User-Agent", USER_AGENT);
			httpGet.addHeader("Host", HOST);
			CloseableHttpResponse response = client.execute(httpGet);
			String body = EntityUtils.toString(response.getEntity());
			response.close();
			Document doc = Jsoup.parse(body);
			viewState = doc.getElementsByTag("input").get(0).val();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getCheckCode() {
		try {
			HttpGet httpGet = new HttpGet(CHECKCODE_URL);
			httpGet.addHeader("Host", HOST);
			httpGet.addHeader("Referer", LOGIN_URL);
			httpGet.addHeader("User-Agent", USER_AGENT);
			CloseableHttpResponse checkCodeResponse = client.execute(httpGet);
			BufferedImage image = ImageIO.read(checkCodeResponse.getEntity().getContent());
			checkCodeResponse.close();
			Map<BufferedImage, String> map = CheckCoder.loadTrainOcr();
			checkCode = CheckCoder.getAllOcr(CheckCoder.getImgBinary(image), map);
			System.out.println(checkCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getResult(String rex, String content) {
		Pattern pattern = Pattern.compile(rex);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
}
