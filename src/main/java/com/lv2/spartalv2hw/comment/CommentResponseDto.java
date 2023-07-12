package com.lv2.spartalv2hw.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
//          "id": 10,
//        "content": "댓글2",
//        "createdAt": "2022-12-01T12:54:57.049359",
//        "modifiedAt": "2022-12-01T12:54:57.049359",
//        "username": "bin1234"
    private Long id;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
