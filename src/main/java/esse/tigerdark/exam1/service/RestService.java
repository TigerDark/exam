package esse.tigerdark.exam1.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

public interface RestService {
    String getConnection(HttpServletRequest request);
    String getConnection(String ip);
}
