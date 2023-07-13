package esse.tigerdark.exam1.service.impl;

import esse.tigerdark.exam1.model.Person;
import esse.tigerdark.exam1.service.InternetProtocol;
import esse.tigerdark.exam1.service.RestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestServiceImpl implements RestService {
    private final InternetProtocol internetProtocol;
    @Value("${connection.delta_time_minutes:24}")
    private Long deltaTime;

    @Value("${connection.count:5}")
    private Long connectionCount;


    private static final Map<String, Person> personMap = new HashMap<>();

    public RestServiceImpl(InternetProtocol internetProtocol) {
        this.internetProtocol = internetProtocol;
    }

    @Override
    public String getConnection(HttpServletRequest request) {
        String currentIp = internetProtocol.getRequestIP(request);
        return getConnection(currentIp);
    }
    public String getConnection(String currentIp){
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        if (!personMap.isEmpty()) {
            Person person = personMap.get(currentIp);
            if (Objects.nonNull(person)) {
                long minutes = ChronoUnit.MINUTES.between(person.getConnectionTime(), currentLocalDateTime);
                if (minutes <= deltaTime && connectionCount <= person.getCountConnection()) {
                    throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
                }

                if (minutes >= deltaTime && connectionCount <= person.getCountConnection()) {
                    person.setCountConnection(0);
                    person.setConnectionTime(currentLocalDateTime);
                }

                if (minutes <= deltaTime && connectionCount > person.getCountConnection()) {
                    person.setCountConnection(person.getCountConnection() + 1);
                    person.setConnectionTime(currentLocalDateTime);
                }

            } else {
                personMap.put(currentIp, Person.builder().ip(currentIp).countConnection(1).connectionTime(currentLocalDateTime).build());
            }
        } else
            personMap.put(currentIp, Person.builder().ip(currentIp).countConnection(1).connectionTime(currentLocalDateTime).build());
        return currentIp;
    }
}
