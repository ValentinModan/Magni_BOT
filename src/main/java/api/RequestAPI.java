package api;

import org.springframework.http.HttpMethod;

import java.net.http.HttpRequest;

public interface RequestAPI
{
    HttpMethod getRequestType();

    String getAPI();
}
