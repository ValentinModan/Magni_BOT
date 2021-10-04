package api;

import org.springframework.http.HttpMethod;

public interface RequestAPI
{
    HttpMethod getRequestType();

    String getAPI();
}
