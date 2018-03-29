/*
package net.puduck.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.kisti.creativekorea.common.util.ManagedStateMap.ManagedStateBean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class OauthCert {
    */
/**
     * N_CLIENT_ID      네이버 API 클라이언트 아이디
     * D_CLIENT_ID      다음 API 클라이언트 아이디
     * N_SECRET         네이버 API 클라이언트 시크릿
     * D_SECRET         다음 API 클라이언트 시크릿
     * N_CALLBACK_URL   네이버 API 콜백 URL
     * D_CALLBACK_URL   다음 API 콜백 URL 
     *//*

    

    */
/*로컬용
    private static final String  N_CLIENT_ID ="gxOKUVmdvOuqoNhehHXU";           
    private static final String  N_SECRET ="TfYIEXxXO9";
    private static final String  N_CALLBACK_URL ="http://jinhee.co.kr:8080/login/callback/naver";    
    
    private static final String  D_CLIENT_ID ="381340833864015079";
    private static final String  D_SECRET ="193015a3cbd5bea330c8140056077340";
    private static final String  D_CALLBACK_URL ="http://jinhee.co.kr:8080/login/callback/daum";
    *//*

    
    */
/*베타 서버용
    private static final String  N_CLIENT_ID ="MZOOs8ZIcCcvhmPl76ij";           
    private static final String  N_SECRET ="Id1aE4wNfv";                
    private static final String  N_CALLBACK_URL ="https://nebeta.creativekorea.or.kr/account/callback/naver";
    
    private static final String  D_CLIENT_ID ="9038539864401847480";
    private static final String  D_SECRET ="ef02610df1fda8cf9a026b1602b7da6b";
    private static final String  D_CALLBACK_URL ="https://beta.creativekorea.or.kr/account/callback/daum";
    *//*

   
    */
/*리얼 서버용 *//*

    private static final String  N_CLIENT_ID ="BYadzyXcwjScuCXgvY_D";           
    private static final String  N_SECRET ="XuhWRRyUVd";                
    private static final String  N_CALLBACK_URL ="https://www.creativekorea.or.kr/login/callback/naver";
    
    private static final String  D_CLIENT_ID ="3787372041015970965";
    private static final String  D_SECRET ="d99a33cecb550797dfc5d9b01b99c19c";
    private static final String  D_CALLBACK_URL ="https://www.creativekorea.or.kr/login/callback/daum";
    
    public static String getLoginUrl(HttpServletRequest request, String type) throws Exception {
        if(type.equals("naver")){
            String state = generateState(); // 고유 세션을 나타내는 값 
//            request.getSession().setAttribute("state", state);
            ManagedStateMap.INSTANCE.put(state, state);
            
            String certUrl  = "https://nid.naver.com/oauth2.0/authorize?"
                            + "client_id=" + N_CLIENT_ID
                            + "&response_type=code"
                            + "&redirect_uri=" + N_CALLBACK_URL
                            + "&state=" + state;
            
            return certUrl;
            
            
        } else if(type.equals("daum")){
            String certUrl  = "https://apis.daum.net/oauth2/authorize?"
                            + "client_id=" + D_CLIENT_ID
                            + "&response_type=code"
                            + "&redirect_uri=" + D_CALLBACK_URL;
            
            return certUrl;
        } else {
            return null;
        }
    }
    
    public static Map<String, Object> getNaverUserInfo(HttpServletRequest request, Map<String, Object> commandMap) throws Exception {
        Map<String, Object> userInfoMap = new HashMap<String, Object>();
        
        // CSRF 방지를 위한 state token 검증
        String state = commandMap.get("state").toString(); // callback request에서 state파라미터의 값을 가져옴
        String code = commandMap.get("code").toString(); // callback의 응답으로 전달받은 authorization code        

        //세션에 저장된 state 값 가져오기
//        String storedState = request.getSession().getAttribute("state").toString();
        ManagedStateBean managedStateBean = ManagedStateMap.INSTANCE.remove(state);
        String storedState = "";;
        if(managedStateBean != null) {
        	storedState = managedStateBean.getValue();
        } 
        
        // state token 검증
        if(state.equals(storedState)) {
            HttpURLConnection conn = null;
            URL url = null;
            
            try {
                */
/********************** Access Token 요청 시작 *//*

                String accessTokenurl = "https://nid.naver.com/oauth2.0/token?";
                
                String client_id = N_CLIENT_ID; // 네이버 로그인 등록 완료 후 발급받은 ClientID 값 
                String client_secret = N_SECRET; // 네이버 로그인 등록 완료 후 발급받은 ClientSecret 값 
                String grant_type = "authorization_code"; // authorization_code 로 고정
                String accessTokenValue = "";  
                String tokenString = "";
                String jsonString = "";   
                
                // URL 생성
                accessTokenurl = accessTokenurl
                        + "client_id=" + client_id
                        + "&client_secret=" + client_secret
                        + "&grant_type=" + grant_type
                        + "&state=" + state
                        + "&code=" + code;
                
                // Access Token 요청 return JSON
                url = new URL(accessTokenurl);
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                BufferedReader accessTokenResult = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
                
                while((tokenString = accessTokenResult.readLine()) != null) {
                    jsonString += tokenString;
                }
                
                
                if(!("").equals(jsonString)) {                    
                    // JSON 파싱
                    ObjectMapper objectMapper = new ObjectMapper(); 
                    JsonNode jsonNode = objectMapper.readTree(jsonString);
                    accessTokenValue = jsonNode.get("access_token").toString();
                }
                
                conn.disconnect();        
                
                */
/********************** Access Token 요청 종료 *//*

                
                */
/********************** 사용자 정보  요청 시작 *//*

                // 요청 URL
                String uerInfoUrl = "https://apis.naver.com/nidlogin/nid/getUserProfile.xml";
                String xmlString = "";
                
                // 사용자 기본정보 조회 요청 return XML (Authorization 헤더값에는 인증토큰을 포함)
                url = new URL(uerInfoUrl);
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Content-Type", "application/xml");
                conn.setRequestProperty("Accept", "application/xml");
                conn.setRequestProperty("Authorization: Bearer", accessTokenValue);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                
                BufferedReader userInfoResult = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                

                tokenString = "";
                while((tokenString = userInfoResult.readLine()) != null) {
                    xmlString += tokenString;
                }
                // 사용자 XML Parse
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new InputSource(new StringReader(xmlString)));

                // XML NodeList (data > response > 사용자 정보)
                Element root = doc.getDocumentElement();
                NodeList resNodeList = root.getElementsByTagName("response");
                
                for(int i=0; i<resNodeList.getLength(); i++) {
                    Node resNode = resNodeList.item(i);
                    
                    if(("response").equals(resNode.getNodeName())) {
                        NodeList userNodeList = resNode.getChildNodes();
                        
                        // 사용자 정보를 Map에 담는다
                        for(int j=0; j<userNodeList.getLength(); j++) {
                            Node userNode = userNodeList.item(j);
                            userInfoMap.put(userNode.getNodeName(), userNode.getTextContent());
                        }
                        break;
                    }
                }
                
                conn.disconnect();                
                */
/********************** 사용자 정보  요청 종료 *//*

            }    
            catch(Exception e) {
                if(conn != null)  {
                    try{ 
                        conn.disconnect();
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
        return userInfoMap;
    }
    
    public static Map<String, Object> getDaumUserInfo(HttpServletRequest request, Map<String, Object> commandMap) throws Exception {
        Map<String, Object> userInfoMap = new HashMap<String, Object>();
        
        String code = commandMap.get("code").toString(); // callback의 응답으로 전달받은 authorization code        
        
        HttpURLConnection conn = null;
        URL url = null;
            
        try {
            */
/********************** Access Token 요청 시작 *//*

            String accessTokenurl = "https://apis.daum.net/oauth2/token";
            String param = "client_id=" + D_CLIENT_ID
                    + "&client_secret=" + D_SECRET
                    + "&grant_type=" + "authorization_code"
                    + "&redirect_uri=" + D_CALLBACK_URL
                    + "&code=" + code;
            
            String accessTokenValue = "";  
            String tokenString = "";
            String jsonString = "";   
            
            // URL 생성
            
            
            // Access Token 요청 return JSON
            url = new URL(accessTokenurl);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", Integer.toString(param.getBytes().length));
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            OutputStream out_stream = conn.getOutputStream();

            out_stream.write( param.getBytes("UTF-8") );
            out_stream.flush();
            out_stream.close();
            
            BufferedReader accessTokenResult = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            
            while((tokenString = accessTokenResult.readLine()) != null) {
                jsonString += tokenString;
            }
            
            
            if(!("").equals(jsonString)) {                    
                // JSON 파싱
                ObjectMapper objectMapper = new ObjectMapper(); 
                JsonNode jsonNode = objectMapper.readTree(jsonString);
                
                accessTokenValue = jsonNode.get("access_token").toString().replaceAll("\"", "");
            }
            
            
            conn.disconnect();              
            
            */
/********************** Access Token 요청 종료 *//*

        
            */
/********************** 사용자 정보  요청 시작 *//*

            // 요청 URL
            String uerInfoUrl = "https://apis.daum.net/user/v1/show?access_token=" + accessTokenValue;
            String profileString = "";
            String jsonString2 = "";  
            
            url = new URL(uerInfoUrl);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            BufferedReader profileResult = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));  
            
            while((profileString = profileResult.readLine()) != null) {
                jsonString2 += profileString;
            }
            
            System.out.println("jsonString2 : "+jsonString2);
            if(!("").equals(jsonString2)) {                    
                // JSON 파싱
                ObjectMapper objectMapper = new ObjectMapper(); 
                JsonNode jsonNode = objectMapper.readTree(jsonString2);
                jsonNode = jsonNode.get("result");
                userInfoMap.put("id", jsonNode.get("id").toString().replace("\"", ""));
                userInfoMap.put("userid", jsonNode.get("userid").toString().replace("\"", ""));
                userInfoMap.put("nickname", jsonNode.get("nickname").toString().replace("\"", ""));
            }
            
            conn.disconnect();    
                
            
            */
/********************** 사용자 정보  요청 종료 *//*

        }    
        catch(Exception e) {
            if(conn != null)  {
                try{ 
                    conn.disconnect();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("id : " + userInfoMap.get("id"));
        System.out.println("userid : " + userInfoMap.get("userid"));
        
        return userInfoMap;
    }
    
    public static String generateState() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}
*/
