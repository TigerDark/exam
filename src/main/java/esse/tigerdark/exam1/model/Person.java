package esse.tigerdark.exam1.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Person {
    private String ip;
    volatile private int countConnection;
    volatile private LocalDateTime connectionTime;
}
