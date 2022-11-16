package ga.jsjyz.service.serviceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ga.jsjyz.mapper.TicketMapper;
import ga.jsjyz.pojo.Ticket;
import ga.jsjyz.service.TicketService;
import ga.jsjyz.util.ErrorCode;
import ga.jsjyz.util.Response;
import ga.jsjyz.vo.TicketKanbanVo;
import ga.jsjyz.vo.UserTicketVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public Response saveTicket(Ticket ticket) {
        int insert = ticketMapper.insert(ticket);
        if (insert == 0) {
            return new Response(ErrorCode.FAILED);
        }
        return new Response().ok(null);
    }

    @Override
    public Response getTicket() {
        List<Ticket> tickets = ticketMapper.selectList(new LambdaQueryWrapper<>());
        for (Ticket ticket : tickets) {
            if (ticket.getCreateTime().isBefore(LocalDateTime.now().plus(-2,ChronoUnit.WEEKS))){
                tickets.remove(ticket);
            };
        }
        Map<String, List<Ticket>> map= tickets.stream().collect(Collectors.groupingBy(Ticket::getState));
        List<TicketKanbanVo> ticketKanbanVos = new ArrayList<>();
        map.forEach((key, value) -> {
            TicketKanbanVo ticketKanbanVo = new TicketKanbanVo();
            ticketKanbanVo.setState(key);
            List<UserTicketVo> userTicketVos = copyKanbanVos(value);
            ticketKanbanVo.setUserTicketVoList(userTicketVos);
            ticketKanbanVos.add(ticketKanbanVo);
        });
        if (ticketKanbanVos.isEmpty()){
            return new Response(ErrorCode.FAILED);
        }
        return new Response().ok(ticketKanbanVos);
    }
    public List<UserTicketVo> copyKanbanVos(List<Ticket> tickets) {
        ArrayList<UserTicketVo> userTicketVos = new ArrayList<UserTicketVo>();
        tickets.forEach(ticket ->{
            UserTicketVo userTicketVo = new UserTicketVo();
            BeanUtils.copyProperties(ticket,userTicketVo);
            userTicketVos.add(userTicketVo);
        });
        return userTicketVos;
    }


}
