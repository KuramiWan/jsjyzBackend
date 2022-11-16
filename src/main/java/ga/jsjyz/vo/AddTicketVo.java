package ga.jsjyz.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class AddTicketVo {
    private String name;
    private String phone;
    private String question;
    private List<MultipartFile> images;
}
