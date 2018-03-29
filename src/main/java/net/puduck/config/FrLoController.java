/*
package net.puduck.config;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kisti.creativekorea.common.auth.Authorization;
import org.kisti.creativekorea.common.base.BaseController;
import org.kisti.creativekorea.common.crypto.BlockCipherUtils;
import org.kisti.creativekorea.common.login.SessionUtils;
import org.kisti.creativekorea.common.support.IpinSupport;
import org.kisti.creativekorea.common.support.PhoneSupport;
import org.kisti.creativekorea.common.support.RealNameSupport;
import org.kisti.creativekorea.common.types.CommonType;
import org.kisti.creativekorea.common.util.CookieUtils;
import org.kisti.creativekorea.common.util.OauthCert;
import org.kisti.creativekorea.common.util.ObjectUtil;
import org.kisti.creativekorea.common.util.StringUtils;
import org.kisti.creativekorea.service.FrLoService;
import org.kisti.creativekorea.vo.common.SessionUser;
import org.kisti.creativekorea.vo.common.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

*/
/**
 * org.kisti.creativekorea.controller.LoginController LoginController.java
 * 
 * desc : 로그인을 관리하는 컨트롤러
 * 
 * @Company : COSCOI Incorporated
 * @Date : 2015.06.03
 * @Version : 1.0
 *//*


@Controller
@RequestMapping("")
public class FrLoController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(FrLoController.class);

	@Autowired
	private FrLoService frLoService;

	@Resource(name = "realNameSupport")
	private RealNameSupport realNameSupport;

	@Resource(name = "ipinSupport")
	private IpinSupport ipinSupport;

	@Resource(name = "phoneSupport")
	private PhoneSupport phoneSupport;

	*/
/**
	 * Desc : 로그인 페이지 열기
	 * 
	 * @methodName openLogin
	 * @RequestParam HashMap<String, Object> params
	 * @param model
	 * @return
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/openLogin", method = RequestMethod.GET)
	public String openLogin(
			@RequestParam HashMap<String, Object> params,
			@CookieValue(value = "ckorea_remember_cookie", required = false) String ckorea_remember_cookie,
			Model model) {

		SessionUser sessionUser = SessionUtils.getSessionObject();
		String rtUrl = (String) params.get("rtUrl");

		logger.debug("  ##### LOGIN OPEN : SessionUser ########## "
				+ sessionUser);

		if (sessionUser.isGuest()) {
			if (ckorea_remember_cookie != null) {
				// 쿠키가 있는 경우 아이디 표시.
				model.addAttribute("userId",
						BlockCipherUtils.AES_Decode(ckorea_remember_cookie));
			}

			*/
/*
			 * model.addAttribute("realNameReqInfo",
			 * realNameSupport.getReqInfo("FINDID"));
			 * model.addAttribute("realNameRetUrl",
			 * realNameSupport.getRetUrl()); model.addAttribute("ipinReqInfo",
			 * ipinSupport.getReqInfo("FINDID"));
			 * model.addAttribute("ipinRetUrl", ipinSupport.getRetUrl());
			 * model.addAttribute("phoneReqInfo",
			 * phoneSupport.getReqInfo("FINDID"));
			 * model.addAttribute("phoneRetUrl", phoneSupport.getRetUrl());
			 *//*


			return "/login/FRLO010000_write";
		} else {
			if (StringUtils.isBlank(rtUrl)) {
				return "redirect:/main";
			} else {
				return "redirect:" + rtUrl;
			}
		}

	}

	*/
/**
	 * Desc :
	 * 
	 * @methodName login
	 * @RequestParam HashMap<String, String> params
	 * @param model
	 * @return
	 * @throws Exception
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/login")
	public @ResponseBody HashMap<String, Object> login(
			@RequestParam HashMap<String, Object> params,
			HttpServletRequest request,
			HttpServletResponse response
			// , @RequestParam(value = "rememberMe", required = false) String
			// rememberMe
			,
			@RequestParam(value = "rtUrl", required = false) String rtUrl,
			@RequestParam(value = "retry", required = false, defaultValue = "false") String retry,
			Model model) throws Exception {

		logger.debug("  ##### LOGIN ACTION ##########");
		SessionUser sessionUser = SessionUtils.getSessionObject();

		// if ( !sessionUser.isGuest() ) return "redirect:/main";

		String rememberMe = (String) params.get("rememberMe");

		// 1. 로그인 아이디 기억
		if (rememberMe != null) { // 1-1. 파라미터가 있는 경우 쿠키 생성
			CookieUtils
					.addCookie(response, BlockCipherUtils
							.AES_Encode((String) params.get("USER_ID")));
		} else { // 1-2. 파라미터가 없는 경우 쿠키 제거
			CookieUtils.removeCookie(response);
		}

		if (org.springframework.util.StringUtils.hasText(rtUrl)) { // 2-1. 리턴
																	// URL 디코딩
			logger.debug("2-1. 리턴 URL 디코딩 rtUrl " + rtUrl);
			try {
				rtUrl = URLDecoder.decode(rtUrl, "UTF-8");
				logger.debug("2-1. 리턴 URL 디코딩 rtUrl " + rtUrl);
			} catch (UnsupportedEncodingException e) {
				logger.error("", e);
			}
		} else if (!org.springframework.util.StringUtils.hasText(rtUrl)) {
			rtUrl = request.getHeader("Referer");
			logger.debug("2-1 else . 리턴 URL 디코딩 rtUrl " + rtUrl);
		}

		logger.debug(">>>>>>>>>>>>> 1. rtUrl : " + rtUrl);

		if (!org.springframework.util.StringUtils.hasText(rtUrl)) {
			rtUrl = "/main";
		}

		logger.debug(">>>>>>>>>>>>> 2. rtUrl : " + rtUrl);
		HashMap<String, Object> result = new HashMap<String, Object>();
		params.put("ACES_IP_ADDR", request.getRemoteAddr());

		CommonType loginResult = frLoService.login(params,
				CommonType.USER_TYPE_LOGIN);

		if (loginResult == CommonType.USER_VALID_ID_PWD) {
			logger.debug("  ##### SESSION AFTER LOGIN ##########");

			result.put("rtUrl", rtUrl);
			result.put("LOGIN_RESULT", loginResult.getValue());
			// return String.format("redirect:%s", rtUrl);
		} else {
			logger.debug("  ##### NOT LOGGED IN ##########");

			result.put("rtUrl", rtUrl);
			result.put("LOGIN_RESULT", loginResult.getValue());

			retry = "true";

			// return
			// String.format("redirect:/login/openLogin?rtUrl=%s&retry=%s",
			// rtUrl, retry);
			// return String.format("redirect:/login/openLogin");
		}

		return result;
	}

	*/
/**
	 * 로그인창 url 만들기
	 * 
	 * @param request
	 * @PathVariable site_name
	 * @return
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/getLoginUrl/{site_name}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getLoginUrl(
			HttpServletRequest request,
			@PathVariable(value = "site_name") String site_name)
			throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("URL", OauthCert.getLoginUrl(request, site_name));
		resultMap.put("RESULT", "OK");

		return resultMap;
	}

	*/
/**
	 * 로그인 callback 페이지
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/callback/{site_name}", method = RequestMethod.GET)
	public String naverdaumCallback(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "site_name") String site_name,
			@RequestParam HashMap<String, Object> map
			*/
/*
			 * , @RequestParam(value = "code", required = false) String code ,
			 * @RequestParam(value = "state", required = false) String state ,
			 * @RequestParam(value = "error", required = false) String error ,
			 * @RequestParam(value = "error_description", required = false)
			 * String error_description
			 *//*

			, Model model) throws Exception {

		HashMap<String, Object> commandMap = new HashMap<String, Object>();

		SessionUser sessionUser = SessionUtils.getSessionObject();

		UserVO userVO = sessionUser.getCurrentUser();

		commandMap.put("code", map.get("code"));
		if (!ObjectUtil.isNull(map.get("error"))) {
			commandMap.put("error", map.get("error"));
			commandMap.put("error_description", map.get("error_description"));
		}

		// 네이버
		if ("naver".equals(site_name)) {
			commandMap.put("state", map.get("state"));
			map = (HashMap<String, Object>) OauthCert.getNaverUserInfo(request,
					commandMap);

			if (map != null && !map.get("enc_id").equals("")) {
				logger.debug("enc : " + map.get("enc_id"));
				logger.debug("Email : " + map.get("email"));

				if (!sessionUser.isGuest())
					return "redirect:/main";

				int sns_p = 90;
				String s_sns = "NAVER";
				String s_snskey = map.get("enc_id").toString();
				String s_email = map.get("email").toString();

				userVO.setSNS_LOGN_INFO(s_sns);
				userVO.setSNS_LOGN_KEY(s_snskey);
				userVO.setNAME(s_email);
				userVO.setEML(s_email);
				userVO.setUSER_ID(s_email);
				userVO.setUSER_ATHR_LEVL(sns_p);
				sessionUser.isNonMember();
				sessionUser.saveUser(userVO);
				SessionUtils.setSessionAttribute(sessionUser);
				model.addAttribute("result", "true");

				// SNS 사용 접근 로그 등록 : 추후 확인, 개발 필요
				insertSnsLoginHistory(s_sns, s_email);
				
			} else {
				model.addAttribute("result", "false");
			}

		}
		// 다음
		else if("daum".equals(site_name)){

			map = (HashMap<String, Object>) OauthCert.getDaumUserInfo(request,
					commandMap);

			if (map != null && !map.get("id").equals("")) {

				if (!sessionUser.isGuest())
					return "redirect:/main";

				int sns_p = 90;
				String s_sns = "DAUM";
				String id = map.get("id").toString();
				String userid = map.get("userid").toString();
				String nickname = map.get("nickname").toString();

				userVO.setSNS_LOGN_INFO(s_sns);
				userVO.setSNS_LOGN_KEY(id);
				userVO.setUSER_ID(userid);
				userVO.setUSER_ATHR_LEVL(sns_p);
				sessionUser.isNonMember();
				sessionUser.saveUser(userVO);
				SessionUtils.setSessionAttribute(sessionUser);
				model.addAttribute("result", "true");
				userVO.setNAME(nickname);
				
				// SNS 사용 접근 로그 등록 : 추후 확인, 개발 필요
				insertSnsLoginHistory(s_sns, nickname);
			} else {
				model.addAttribute("result", "false");
			}
		} else if("facebook".equals(site_name)) {

	            if (map != null && !map.get("s_snskey").equals("")) {
	                logger.debug("s_snskey : " + map.get("s_snskey"));
	                logger.debug("s_email : " + map.get("s_email"));

	                if (!sessionUser.isGuest())
	                    return "redirect:/main";

	                int sns_p = 90;
	                String s_sns = "FACEBOOK";
	                String s_snskey = map.get("s_snskey").toString();
	                String s_email = map.get("s_email").toString();

	                userVO.setSNS_LOGN_INFO(s_sns);
	                userVO.setSNS_LOGN_KEY(s_snskey);
	                userVO.setEML(s_email);
	                userVO.setNAME(s_email);
	                userVO.setUSER_ID(s_email);
	                userVO.setUSER_ATHR_LEVL(sns_p);
	                sessionUser.isNonMember();
	                sessionUser.saveUser(userVO);
	                SessionUtils.setSessionAttribute(sessionUser);
	                model.addAttribute("result", "true");

	                insertSnsLoginHistory(s_sns, s_email);
	            } else {
	                model.addAttribute("result", "false");
	            }
		} else if("googleplus".equals(site_name)) {

                if (map != null && !map.get("s_snskey").equals("")) {
                    logger.debug("s_snskey : " + map.get("s_snskey"));
                    logger.debug("s_email : " + map.get("s_email"));

                    if (!sessionUser.isGuest())
                        return "redirect:/main";

                    int sns_p = 90;
                    String s_sns = "GOOGLEPLUS";
                    String s_snskey = map.get("s_snskey").toString();
                    String s_email = map.get("s_email").toString();

                    userVO.setSNS_LOGN_INFO(s_sns);
                    userVO.setSNS_LOGN_KEY(s_snskey);
                    userVO.setEML(s_email);
                    userVO.setNAME(s_email);
                    userVO.setUSER_ATHR_LEVL(sns_p);
                    sessionUser.isNonMember();
                    sessionUser.saveUser(userVO);
                    SessionUtils.setSessionAttribute(sessionUser);
                    model.addAttribute("result", "true");
                    
                    insertSnsLoginHistory(s_sns, s_email);
                } else {
                    model.addAttribute("result", "false");
                }
		}

		return String.format("/login/FRLO010000_openid");
	}

	private void insertSnsLoginHistory(String s_sns, String s_email) {
		HashMap<String, String> snsLoginMap = new HashMap<String, String>();
		snsLoginMap.put("SNS_USER_ID", s_email);
		snsLoginMap.put("SNS_ACES_SRC", s_sns);
		frLoService.insertSnsUserLoginData(snsLoginMap);
	}

	*/
/**
	 * 로그아웃
	 * 
	 * @return
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response
			// , @RequestParam(value = "rtUrl", required = false) String rtUrl
			, @RequestParam HashMap<String, Object> map) {

		frLoService.logout();
		CookieUtils.removeCookie(response);
		*/
/*
		 * String rtUrl = (String) map.get("rtUrl");
		 * 
		 * // 1. 리턴 URL 처리 if
		 * (org.springframework.util.StringUtils.hasText(rtUrl)) { // 2-1. 리턴
		 * URL 디코딩 try { rtUrl = URLDecoder.decode(rtUrl, "UTF-8"); } catch
		 * (UnsupportedEncodingException e) { logger.error("", e); } } // 1-2.
		 * 리턴 URL 이 없는 경우 Referer 사용 if (
		 * !org.springframework.util.StringUtils.hasText(rtUrl) ) rtUrl =
		 * request.getHeader("Referer"); // 1-3. 리턴 URL 이 없는 경우 메인 if (
		 * !org.springframework.util.StringUtils.hasText(rtUrl) ) rtUrl = "/";
		 * 
		 * return String.format("redirect:%s", rtUrl);
		 *//*


		return "redirect:/main";
	}

	*/
/**
	 * Desc : 식별번호 체크
	 * 
	 * @methodName checkNO
	 * @RequestParam HashMap<String, Object> params
	 * @param model
	 * @return
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/checkNO")
	public @ResponseBody Map<String, String> checkNO(
			@RequestParam HashMap<String, Object> params) throws Exception {

		*/
/*
		 * Map<String, Object> parameters = new HashMap<String, Object>();
		 * parameters.put("VTUL_DSTN_VLUE", s_discr_no);
		 * parameters.put("VTUL_DSTN_HASH_VLUE", s_discr_hash);
		 *//*


		Map<String, String> result = new HashMap<String, String>();
		if (frLoService.checkNO(params))
			result.put("result", "ERROR");
		else
			result.put("result", "OK");

		return result;
	}

	*/
/**
	 * Desc : 아이디 찾기 초기 페이지 열기
	 * 
	 * @methodName findId
	 * @RequestParam HashMap<String, Object> params
	 * @param model
	 * @return
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/findId", method = RequestMethod.GET)
	public String openFindId(@RequestParam HashMap<String, Object> params,
			ModelMap model) throws Exception {

		try {
			SessionUser sessionUser = SessionUtils.getSessionObject();

			if (null == sessionUser || sessionUser.isGuest()) {
				model.addAttribute("realNameReqInfo",
						realNameSupport.getReqInfo("FINDID"));
				model.addAttribute("realNameRetUrl",
						realNameSupport.getRetUrl());
				model.addAttribute("ipinReqInfo",
						ipinSupport.getReqInfo("FINDID"));
				model.addAttribute("ipinRetUrl", ipinSupport.getRetUrl());
				model.addAttribute("phoneReqInfo",
						phoneSupport.getReqInfo("FINDID"));
				model.addAttribute("phoneRetUrl", phoneSupport.getRetUrl());

				return "/login/FRLO030000_search";
			} else {
				return "redirect:/main";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	*/
/**
	 * Desc : 아이디 찾기
	 * 
	 * @methodName findId
	 * @RequestParam HashMap<String, String> params
	 * @param model
	 * @return
	 * @throws Exception
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/findId", method = RequestMethod.POST)
	public @ResponseBody String findId(
			@RequestParam HashMap<String, Object> params, ModelMap model)
			throws Exception {

		logger.debug("  ##### 아이디 찾기 ##########");
		String rtnString = "";
		try {
			// s_discr_no, s_discr_hash, s_username
			rtnString = frLoService.findId(params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return rtnString;
	}

	*/
/**
	 * Desc : PASSWORD 찾기 초기 페이지 열기
	 * 
	 * @methodName openFindPw
	 * @RequestParam HashMap<String, Object> params
	 * @param model
	 * @return
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/findPw", method = RequestMethod.GET)
	public String openFindPw(@RequestParam HashMap<String, Object> params,
			ModelMap model) throws Exception {

		try {
			SessionUser sessionUser = SessionUtils.getSessionObject();

			if (sessionUser.isGuest()) {
				model.addAttribute("realNameReqInfo",
						realNameSupport.getReqInfo("FINDPW"));
				model.addAttribute("realNameRetUrl",
						realNameSupport.getRetUrl());
				
				//
				String ipinReqInfo = ipinSupport.getReqInfo("FINDPW");
				model.addAttribute("ipinReqInfo",ipinReqInfo);
				model.addAttribute("ipinRetUrl", ipinSupport.getRetUrl());

				System.out.println("openFindPw - phoneSupport [" + phoneSupport + "]");
				String phoneReqInfo = phoneSupport.getReqInfo("FINDPW");
				
				System.out.println("openFindPw - phoneReqInfo [" + phoneReqInfo + "]");
				model.addAttribute("phoneReqInfo", phoneReqInfo);
				model.addAttribute("phoneRetUrl", phoneSupport.getRetUrl());
				
				
				return "/login/FRLO050000_search";
			} else {
				return "redirect:/main";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	*/
/**
	 * Desc : 비밀번호 찾기
	 * 
	 * @methodName findPw
	 * @RequestParam HashMap<String, String> params
	 * @param model
	 * @return
	 * @throws Exception
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/findPw", method = RequestMethod.POST)
	public @ResponseBody boolean findPw(
			@RequestParam HashMap<String, Object> params, ModelMap model)
			throws Exception {

		logger.debug("  ##### 비밀번호 찾기 ##########");

		boolean rtnBoolean = false;
		try {
			rtnBoolean = frLoService.findPw(params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return rtnBoolean;
	}
	
	*/
/**
	 * Desc : 간편인증 SMS 비밀번호 찾기
	 * 
	 * @methodName findPw
	 * @RequestParam HashMap<String, String> params
	 * @param model
	 * @return
	 * @throws Exception
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/findPwByUserIdNUserName", method = RequestMethod.POST)
	public @ResponseBody boolean findPwByUserIdNUserName(
			@RequestParam HashMap<String, Object> params, ModelMap model)
			throws Exception {

		logger.debug("  ##### 비밀번호 찾기 ##########");

		boolean rtnBoolean = false;
		try {
			rtnBoolean = frLoService.findPwByUserIdNUserName(params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return rtnBoolean;
	}	

	*/
/**
	 * Desc : 인증하기
	 * 
	 * @methodName openAuth
	 * @RequestParam HashMap<String, Object> params
	 * @param model
	 * @return
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/auth", method = RequestMethod.GET)
	public String openAuth(@RequestParam HashMap<String, Object> params,
			ModelMap model) throws Exception {

		try {
			SessionUser sessionUser = SessionUtils.getSessionObject();

			if (sessionUser.isLogin()) {

				model.addAttribute("realNameReqInfo",
						realNameSupport.getReqInfo("AUTH"));
				model.addAttribute("realNameRetUrl",
						realNameSupport.getRetUrl());
				model.addAttribute("ipinReqInfo",
						ipinSupport.getReqInfo("AUTH"));
				model.addAttribute("ipinRetUrl", ipinSupport.getRetUrl());
				model.addAttribute("phoneReqInfo",
						phoneSupport.getReqInfo("AUTH"));
				model.addAttribute("phoneRetUrl", phoneSupport.getRetUrl());

				model.addAttribute("rtUrl", params.get("rtUrl"));

				return "/login/FRLO060000_write";
			} else {
				return "redirect:/main";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	*/
/**
	 * Desc : 인증하기
	 * 
	 * @methodName findPw
	 * @RequestParam HashMap<String, String> params
	 * @param model
	 * @return
	 * @throws Exception
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/auth", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> checkAuth(
			@RequestParam HashMap<String, Object> params, ModelMap model)
			throws Exception {

		logger.debug("  ##### 인증하기 ##########");

		String rtnBoolean = "";
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			SessionUser sessionUser = SessionUtils.getSessionObject();

			UserVO user = sessionUser.getCurrentUser();
			params.put("USER_NUM", user.getUSER_NUM());
			params.put("USER_ID", user.getUSER_ID());

			params.put("VTUL_DSTN_VLUE", BlockCipherUtils.AES_Encode(params.get("VTUL_DSTN_VLUE").toString()));

			result.put("RESULT", frLoService.updateAuth(params));
			result.put("LOCATION_HREF", params.get("rtUrl").toString());

			
			if( ( params.get("USER_ID").toString()).equals(user.getUSER_ID()) ) {
			    rtnBoolean = frLoService.updateAuth(params);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return result;
	}

	*/
/**
	 * IPIN 인증 요청 정보
	 * 
	 * @param retInfo
	 * @param model
	 * @return
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/responseIpin", method = RequestMethod.GET)
	public String ipin(@RequestParam String retInfo, Model model) {
		
		System.out.println("  ##### ResponseIpin : ipin 인증 후 response 처리 ##########");
		Map<String, String> retInfoMap = ipinSupport.getRetInfo(retInfo);
		model.addAttribute("ipinRetInfo", retInfoMap);

		return "/login/FRLO030000_ipin";
	}

	*/
/**
	 * 휴대폰 본인 인증 요청 정보
	 * 
	 * @param retInfo
	 * @param model
	 * @return
	 *//*

	@Authorization(level = 100)
	@RequestMapping(value = "/login/responsePhone", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String phone(@RequestParam String retInfo, Model model) {
		System.out.println("##### responsePhone : phone 인증 후 response 처리 ##########");
		Map<String, String> retInfoMap = phoneSupport.getRetInfo(retInfo);
		model.addAttribute("phoneRetInfo", retInfoMap);

		return "/login/FRLO030000_phone";
	}

	@Authorization(level = 100)
	@RequestMapping(value = "/account/user/access")
	public @ResponseBody String otherSiteUserAccess(String userid,
			String password) throws Exception {

		String rtnString = "";
		try {
			rtnString = frLoService.getUserInfoByJsonString(userid, password);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return rtnString;
	}

}
*/
