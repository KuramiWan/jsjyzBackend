package ga.jsjyz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class Ticket {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String name;
    private String phone;
    private LocalDateTime createTime;
    private String state;
    private String question;
    private String images;
}
