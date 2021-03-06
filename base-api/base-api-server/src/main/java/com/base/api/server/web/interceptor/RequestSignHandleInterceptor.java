package com.base.api.server.web.interceptor;

import com.base.api.server.web.RequestParamsUtil;
import com.google.common.collect.Maps;
import com.base.api.common.ConfigUtil;
import com.base.api.common.constants.Constants;
import com.base.api.common.enums.ResCodeEnums;
import com.base.api.common.utils.StrUtils;
import com.base.api.common.web.RequestHeaderEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 
 * 请求签名校验
 * @author xiaohaizi
 * @date 2017年3月8日 下午5:43:15   
 *
 */
public class RequestSignHandleInterceptor extends HandlerInterceptorAdapter{
	
	private Logger logger = LoggerFactory.getLogger(RequestSignHandleInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding(Constants.CHARSET_UTF8);
		String sign = request.getParameter(Constants.SIGN);
		if(StrUtils.isNotBlank(sign)){
			try {
				Map<String, String[]> parameterMap = request.getParameterMap();
				List<RequestHeaderEnums> headerEnums = RequestHeaderEnums.getRequestSignHeader();
				if(headerEnums != null){
					Map<String, String[]> signParameter = Maps.newHashMap();
					signParameter.putAll(parameterMap);
					for(RequestHeaderEnums header : headerEnums){
						String headerParam = request.getHeader(header.getHeader());
						if(headerParam != null){
							signParameter.put(header.getHeader(), new String[] { headerParam });
						}else{
							logger.error("必要参数{}不能为空", header.getHeader());
						}
					}
					Map<String, String> paraFilter = RequestParamsUtil.paraFilter(signParameter);
					String serverSign = RequestParamsUtil.buildRequestByMD5(paraFilter, ConfigUtil.getInstance().getSysEncryptMD5());
					if (sign.equals(serverSign)) {
						return super.preHandle(request, response, handler);
					}else{
						logger.warn("签名不正确:md5("+RequestParamsUtil.createLinkString(paraFilter, false)+")"+serverSign + "!="+ sign);
					}
				}
			} catch (Exception ex) {
				logger.error("签名异常", ex);
			}
		}
		RequestParamsUtil.resultData(response, ResCodeEnums.SIGN_ERROR);
		return false;
	}
}
