package ga.jsjyz.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ga.jsjyz.mapper.AnnouncementMapper;
import ga.jsjyz.pojo.Announcement;
import ga.jsjyz.service.AnnouncementService;
import ga.jsjyz.util.ErrorCode;
import ga.jsjyz.util.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private AnnouncementMapper announcementMapper;

    @Override
    public Response saveAnnouncement(String title, String description, String detail) {
        LambdaQueryWrapper<Announcement> announcementLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Announcement announcement = new Announcement(null, title, description, detail);
        int insert = announcementMapper.insert(announcement);
        if (insert == 0) {
            return new Response(ErrorCode.FAILED);
        }
        return new Response().ok(null);
    }

    @Override
    public Response getAnnouncementById(String id) {
        LambdaQueryWrapper<Announcement> announcementLambdaQueryWrapper = new LambdaQueryWrapper<>();
        announcementLambdaQueryWrapper.eq(Announcement::getId,id);
        Announcement announcement = announcementMapper.selectOne(announcementLambdaQueryWrapper);
        ArrayList<Announcement> announcements = new ArrayList<>();
        announcements.add(announcement);
        return new Response().ok(announcements);
    }

    @Override
    public Response getAllAnnouncement() {
        LambdaQueryWrapper<Announcement> announcementLambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<Announcement> announcements = announcementMapper.selectList(announcementLambdaQueryWrapper);
        if (!announcements.isEmpty()) {
            return new Response().ok(announcements);
        }
        return new Response(ErrorCode.ERROR_EMPTY);
    }
}
