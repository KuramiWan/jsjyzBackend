package ga.jsjyz.contrller;
import ga.jsjyz.pojo.Ticket;
import ga.jsjyz.service.serviceImpl.TicketServiceImpl;
import ga.jsjyz.util.FileUtil;
import ga.jsjyz.util.Response;
import ga.jsjyz.vo.AddTicketVo;
import ga.jsjyz.vo.AlterStateTicketVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TicketController {
    @Autowired
    private TicketServiceImpl ticketService;
    @Autowired
    private FileUtil fileUtil;

    @PostMapping("/addTicket")
    public Response saveTicket(@ModelAttribute AddTicketVo addTicketVo){
        List<MultipartFile> images = addTicketVo.getImages();
        String path = "/images";
        String s = fileUtil.uploadListReturnStr(path, images);
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(addTicketVo, ticket);
        ticket.setImages(s);
        ticket.setCreateTime(LocalDateTime.now());
        ticket.setState("未完成");
        return ticketService.saveTicket(ticket);
    }
    @GetMapping ("/getTicket")
    public Response getTicket(){
        return ticketService.getTicket();
    }
    @GetMapping("/admin/getTicketList")
    public Response getTicketList(@RequestParam String state,@RequestParam String order){
        return ticketService.getTicketList(state,order);
    }

    @PostMapping ( "/admin/alterTicket")
    public Response alterTicket(@RequestBody AlterStateTicketVo alterStateTicketVo){
        return ticketService.alterTicket(alterStateTicketVo.getId(),alterStateTicketVo.getState());
    }
}
