package ga.jsjyz.service;

import ga.jsjyz.util.Response;
import ga.jsjyz.vo.AnnouncementVo;
import org.springframework.stereotype.Service;


public interface AnnouncementService {
    Response saveAnnouncement(String title, String description, String detail);

    Response getAnnouncementById(String id);

    Response getAllAnnouncement();
}
