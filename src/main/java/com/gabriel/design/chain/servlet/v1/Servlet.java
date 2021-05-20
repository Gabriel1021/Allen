package com.gabriel.design.chain.servlet.v1;

import java.util.ArrayList;
import java.util.List;

public class Servlet {
    public static void main(String[] args) {
        Request request = new Request();
        request.str = "大家好:)，<script>，欢迎访问 xxx.com ，大家都是996 ";
        Response response = new Response();
        response.str = "";

        FilterChain chain = new FilterChain();
        chain.add(new HtmlFilter()).add(new SensitiveFilter());
        chain.doFilter(request, response);
        System.out.println(request.str);

    }
}

interface Filter {
    boolean doFilter(Request request,Response response);
}

class HtmlFilter implements Filter {
    @Override
    public boolean doFilter(Request request,Response response) {
        request.str = request.str.replaceAll("<", "[").replaceAll(">", "]");
        return true;
    }
}

class Request {
    String str;
}

class Response {
    String str;
}

class SensitiveFilter implements Filter {
    @Override
    public boolean doFilter(Request request,Response response) {
        request.str = request.str.replaceAll("996", "955");
        return true;
    }
}


class FilterChain implements Filter {
    List<Filter> filters = new ArrayList<>();

    public FilterChain add(Filter f) {
        filters.add(f);
        return this;
    }

    @Override
    public boolean doFilter(Request request,Response response) {

        for(Filter f : filters ){
            f.doFilter(request, response);
        }
        return true;
    }
}