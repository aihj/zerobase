package data;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class historyDTO {

    private int id;
    private String lat;
    private String lnt;
    private String searchDttm;
}
