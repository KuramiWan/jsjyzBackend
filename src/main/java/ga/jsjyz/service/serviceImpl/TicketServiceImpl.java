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
import java.util.*;
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
        Iterator<Ticket> iterator = tickets.iterator();
        while (iterator.hasNext()) {
            Ticket ticket = iterator.next();
            if (ticket.getCreateTime().isBefore(LocalDateTime.now().plus(-2,ChronoUnit.WEEKS))){
                iterator.remove();
            }
        }
        if (tickets.isEmpty()) {
            return new Response(ErrorCode.ERROR_EMPTY);
        }
        Map<String, List<Ticket>> map = tickets.stream().collect(Collectors.groupingBy(Ticket::getState));
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

    @Override
    public Response getTicketList(String state, String order) {
        LambdaQueryWrapper<Ticket> ticketLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (order.equals("最旧")){
            ticketLambdaQueryWrapper.orderByDesc(true,Ticket::getCreateTime);
        }else {
            ticketLambdaQueryWrapper.orderByAsc(true,Ticket::getCreateTime);
        }
        ticketLambdaQueryWrapper.eq(Ticket::getState,state);
        List<Ticket> tickets = ticketMapper.selectList(ticketLambdaQueryWrapper);
        if (tickets.isEmpty()){
            return new Response(ErrorCode.ERROR_EMPTY);
        }
        return new Response().ok(tickets);
    }

    @Override
    public Response alterTicket(String id, String state) {
        LambdaQueryWrapper<Ticket> ticketLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ticketLambdaQueryWrapper.eq(Ticket::getId,id);
        Ticket ticket = new Ticket();
        ticket.setState(state);
        int update = ticketMapper.update(ticket, ticketLambdaQueryWrapper);
        if (update == 0) {
            return new Response(ErrorCode.ALTER_FAILED);
        }
        return new Response().ok(null);

    }

    public Response deleteTicket(List<Long> ticketIds) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        ticketIds.forEach(ticketId -> {
         stringObjectHashMap.put("id", ticketId);
        });
        int i = ticketMapper.deleteByMap(stringObjectHashMap);
        if (i == 0){
            return new Response(ErrorCode.DELETE_FAILED);
        }
        return new Response().ok(null);
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
