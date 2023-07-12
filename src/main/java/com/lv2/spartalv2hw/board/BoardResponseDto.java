package com.lv2.spartalv2hw.board;

import com.lv2.spartalv2hw.comment.Comment;
import com.lv2.spartalv2hw.comment.CommentResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.username = board.getUser().getUsername();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.commentList= board.getCommentsList().stream().map(CommentResponseDto::new).toList();


//        데이터베이스 쿼리에서 정렬: 이 방법은 JPA @OrderBy 어노테이션을 사용하여 연관 엔티티 컬렉션을 정렬하며, 데이터베이스에서 쿼리 실행 시 정렬이 수행됩니다.
//        이는 쿼리 실행 시점에 데이터베이스에서 처리되므로 메모리 사용이 적습니다. 그러나, 이 방법은 전체 데이터 세트를 데이터베이스에서 가져온 후 정렬하는 방식이므로 큰 데이터 세트의 경우 성능이 떨어질 수 있습니다.
//
//        메모리에서 정렬: 이 방법은 Java의 Stream API를 사용하여 메모리에서 데이터를 정렬합니다. 이 방법은 전체 데이터를 메모리에 로드한 후 정렬하기 때문에 메모리 사용이 더 많습니다.
//        그러나, 일부 경우에는 데이터베이스 쿼리보다 더 빠르게 실행될 수 있습니다.

//        this.commentList= board.getCommentsList().stream().sorted(Comparator.comparing(Comment::getCreatedAt).reversed()).map(CommentResponseDto::new).collect(Collectors.toList());

//        for (Comment comment : board.getCommentsList()) {
//            commentList.add(new CommentResponseDto(comment));
//        }
    }
}
