package com.item.image.easydl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.item.entity.AccessToken;
import com.item.face.Base64Convert;
import com.item.tool.GsonUtils;
import com.item.tool.HttpUtil;
import com.item.tool.TrustHttp;

public class EasyDL {

	// 设置APPID/AK/SK
	public static final String APP_ID = "20207447";
	public static final String API_KEY = "05F0SL3jAO2N2frG3gWYgHXa";
	public static final String SECRET_KEY = "laIEWrbrrumyoY5SOZhKi9EjaU8oF89b";
	public static final String URL = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/segmentation/hengxiang";

	/**
	 * 获取认证
	 * 
	 * @return
	 */
	public static AccessToken getAuth() {
		// 官网获取的 API Key 更新为你注册的
		String clientId = API_KEY;
		// 官网获取的 Secret Key 更新为你注册的
		String clientSecret = SECRET_KEY;
		return getAuth(clientId, clientSecret);
	}

	/**
	 * 获取API访问token 该token有一定的有效期，需要自行管理，当失效时需重新获取.
	 * 
	 * @param ak - 百度云官网获取的 API Key
	 * @param sk - 百度云官网获取的 Securet Key
	 * @return assess_token 示例：
	 *         "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
	 */
	public static AccessToken getAuth(String ak, String sk) {
		AccessToken accessToken = new AccessToken();
		// 获取token地址
		TrustHttp.trustEveryone();
		String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
		String getAccessTokenUrl = authHost
				// 1. grant_type为固定参数
				+ "grant_type=client_credentials"
				// 2. 官网获取的 API Key
				+ "&client_id=" + ak
				// 3. 官网获取的 Secret Key
				+ "&client_secret=" + sk;
		try {
			URL realUrl = new URL(getAccessTokenUrl);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.err.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String result = "";
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			/**
			 * 返回结果示例
			 */
			System.err.println("result:" + result);
			JSONObject jsonObject = new JSONObject(result);
			String access_token = jsonObject.getString("access_token");
			Long lexpires_in = jsonObject.getLong("expires_in");
			String expires_in = lexpires_in + "";
			accessToken.setAccess_token(access_token);
			accessToken.setExpires_in(expires_in);
			return accessToken;
		} catch (Exception e) {
			System.err.printf("获取token失败！");
			e.printStackTrace(System.err);
		}
		return null;
	}

	public static String easydlObjectDetection(String path, String accessToken) {
		// 请求url
		String url = URL;
		String base64 = Base64Convert.GetImageStr(path);
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("image", base64);
			map.put("threshold", 0.6);
			String param = GsonUtils.toJson(map);

			// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
			// String accessToken = "[调用鉴权接口获取的token]";

			TrustHttp.trustEveryone();
			String result = HttpUtil.post(url, accessToken, "application/json", param);
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
