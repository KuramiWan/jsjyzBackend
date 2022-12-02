package ga.jsjyz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Announcement {
    private Long id;
    private String title;
    private String description;
    private String detail;
}
