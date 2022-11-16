package ga.jsjyz.vo;

import lombok.Data;

import java.util.List;

@Data
public class TicketKanbanVo {
    private String state;
    private List<UserTicketVo> userTicketVoList;
}
