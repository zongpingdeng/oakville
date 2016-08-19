package com.netment.jcip.http;

/**
 * Created by jeff on 16/5/25.
 */
public class HttpServerConfig {

    private static final String DEFAULT_PAGE = "default.html";

    public static String getRealPath(String uri) {
        StringBuilder sb=new StringBuilder("/Users/jeff/IdeaProjects/jcip/web");
        sb.append(uri);
        if (!uri.endsWith("/")) {
            sb.append('/');
        }
        return sb.toString();
    }

    public static String getDefaultPage(){
        StringBuilder sb=new StringBuilder("/Users/jeff/IdeaProjects/jcip/web/"+DEFAULT_PAGE);
        return sb.toString();
    }
}
