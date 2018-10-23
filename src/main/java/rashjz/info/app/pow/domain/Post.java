package rashjz.info.app.pow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;
}
