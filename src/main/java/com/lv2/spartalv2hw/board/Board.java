package com.lv2.spartalv2hw.board;

import com.lv2.spartalv2hw.comment.Comment;
import com.lv2.spartalv2hw.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "user")
    private List<Comment> commentsList = new ArrayList<>();

    public Board(BoardRequestDto boardRequestDto,User user) {
        this.contents= boardRequestDto.getContents();
        this.title= boardRequestDto.getTitle();
        this.user=user;
    }

    public void putBoard(BoardRequestDto boardRequestDto) {
        this.contents= boardRequestDto.getContents();
        this.title= boardRequestDto.getTitle();
    }
}
