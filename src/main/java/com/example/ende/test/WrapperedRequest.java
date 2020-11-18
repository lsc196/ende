package com.example.ende.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
public class WrapperedRequest extends HttpServletRequestWrapper {

    private String requestBody = null;
    HttpServletRequest req = null;

    public WrapperedRequest(HttpServletRequest request) {
        super(request);
        this.req = request;
    }

    public WrapperedRequest(HttpServletRequest request, String requestBody) {
        super(request);
        this.requestBody = requestBody;
        this.req = request;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.servlet.ServletRequestWrapper#getReader()
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new StringReader(requestBody));
    }

    /**
     * 按需求开启
     *
     * 由于请求的时候有时候请求头不是json，是* /*; charset=UTF-8
     *所以解码后是json,要修改内容类型
     * demo中只重写了getHeaders方法，实际上严谨的做法是getHeader(String name)方法也要被重写。
     * @return
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
        if (null != name && name.equals("content-type")) {
            return new Enumeration<String>() {
                private boolean hasGetted = false;

                @Override
                public String nextElement() {
                    if (hasGetted) {
                        throw new NoSuchElementException();
                    } else {
                        hasGetted = true;
                        return "application/json;charset=utf-8";
                    }
                }
                @Override
                public boolean hasMoreElements() {
                    return !hasGetted;
                }
            };
        }
        return super.getHeaders(name);
    }


    @Override
    public String getHeader(String name) {
        if (null != name && name.equals("content-type")) {
            return "application/json;charset=utf-8";
        }
        return super.getHeader(name);
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.servlet.ServletRequestWrapper#getInputStream()
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            private InputStream in = new ByteArrayInputStream(
                    requestBody.getBytes(req.getCharacterEncoding()));
            @Override
            public int read() throws IOException {
                return in.read();
            }
            @Override
            public boolean isFinished() {
                // TODO Auto-generated method stub
                return false;
            }
            @Override
            public boolean isReady() {
                // TODO Auto-generated method stub
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) {
                // TODO Auto-generated method stub

            }
        };
    }
}



