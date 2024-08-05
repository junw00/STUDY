package db.study.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter @Setter
public class Cart {

    private int cartId;
    private String userId;
    private Timestamp createDate;
}
