package com.run.core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @desc: 通过 HTTP 方式读取远程文件
 * @author: AruNi_Lu
 * @date: 2023/3/14
 */
public class UrlResource implements Resource {

    private final URL url;

    public UrlResource(URL url) {
        Assert.notNull(url, "URL must not be null");
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        // 获取与远程 url 的连接
        URLConnection con = this.url.openConnection();
        try {
            // 返回连接的输入流
            return con.getInputStream();
        } catch (IOException ex) {
            // 如果遇到异常，先关闭连接再抛出
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).disconnect();
            }
            throw ex;
        }
    }
}
