package ga.jsjyz.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserTicketVo {
    private String name;
    private String phone;
    private String question;
    private LocalDateTime createTime;
}
