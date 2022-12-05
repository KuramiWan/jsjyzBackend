package ga.jsjyz.contrller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ga.jsjyz.service.AnnouncementService;
import ga.jsjyz.util.ErrorCode;
import ga.jsjyz.util.Response;
import ga.jsjyz.vo.AnnouncementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/api")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;
    @PostMapping("/addAnnouncement")
    public Response saveAnnouncement(@RequestBody AnnouncementVo announcementVo){
        String description = announcementVo.getDescription();
        String title = announcementVo.getTitle();
        String detail = announcementVo.getDetail();
        if (StringUtils.isBlank(title) || StringUtils.isBlank(description)||StringUtils.isBlank(detail)){
            return new Response(ErrorCode.ERROR_PARAMETER);
        }else {
            return announcementService.saveAnnouncement(title,description,detail);
        }

    }
    @GetMapping("/getAnnouncement")
    public Response getAnnouncement(@RequestParam(required = false) String id){
        if(id != null){
            return announcementService.getAnnouncementById(id);
        }
        return announcementService.getAllAnnouncement();
    }
}
