package org.n3r.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class BaseController {

    public static final String STATUS_PARAMETER_NAME = "status";// 操作状态参数名称
    public static final String MESSAGE_PARAMETER_NAME = "message";// 操作消息参数名称

    private final static String ATTACHMENT = "attachment";
    private final static String DEFAULT_FILE_NAME = "attachment";

    // 操作状态（警告、错误、成功）
    public enum Status {
        warn, error, success
    }

    // 设置Cookie
    protected void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(request.getContextPath() + "/");
        response.addCookie(cookie);
    }

    // 设置Cookie
    protected void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
            Integer maxAge) {
        Cookie cookie = new Cookie(name, value);
        if (maxAge != null) {
            cookie.setMaxAge(maxAge);
        }
        cookie.setPath(request.getContextPath() + "/");
        response.addCookie(cookie);
    }

    // 移除Cookie
    protected void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath() + "/");
        response.addCookie(cookie);
    }

    protected Map ajax(Status status) {
        return ImmutableMap.of(STATUS_PARAMETER_NAME, status.toString());
    }

    // 根据操作状态、消息内容输出AJAX
    protected Map ajax(Status status, String message) {
        return ImmutableMap.of(STATUS_PARAMETER_NAME, status.toString(), MESSAGE_PARAMETER_NAME, message);
    }

    // 根据操作状态、消息内容输出AJAX
    protected Map ajax(Status status, String message, Map messages) {
        Map returnMessage = Maps.newHashMap();
        returnMessage.put(STATUS_PARAMETER_NAME, status.toString());
        returnMessage.put(MESSAGE_PARAMETER_NAME, message);
        returnMessage.putAll(messages);
        return returnMessage;
    }

    //下载文件
    protected ResponseEntity<byte[]> responseEntity(String filePath, String fileName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData(ATTACHMENT, StringUtils.isEmpty(fileName) ? DEFAULT_FILE_NAME
                : new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filePath)),
                headers, HttpStatus.OK);
    }
}
