package com.lv2.spartalv2hw.comment;

import com.lv2.spartalv2hw.board.Board;
import com.lv2.spartalv2hw.board.Timestamped;
import com.lv2.spartalv2hw.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    public Comment(String contents, User user, Board board) {
        this.contents = contents;
        this.user = user;
        this.board = board;
    }

    public void updateComment(String contents) {
        this.contents=contents;
    }
}
