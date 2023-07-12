package com.lv2.spartalv2hw.comment;

import com.lv2.spartalv2hw.user.ResultResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest req){
        CommentResponseDto commentResponseDto;

        try{
            commentResponseDto = commentService.createComment(commentRequestDto, req);
        }catch (Exception e){
            return new ResponseEntity<>(new ResultResponseEntity(e.getMessage()) ,HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(commentResponseDto,HttpStatus.OK);
    }

   //댓글 수정
    @PutMapping("{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id,@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest req){
        CommentResponseDto commentResponseDto;

        try{
            commentResponseDto = commentService.updateComment(id, commentRequestDto, req);
        }catch (Exception e){
            return new ResponseEntity<>(new ResultResponseEntity(e.getMessage()) ,HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(commentResponseDto,HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, HttpServletRequest req){
        try {
            commentService.deleteComment(id,req);
        }catch (Exception e){
            return new ResponseEntity<>(new ResultResponseEntity("댓글 삭제 불가"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new ResultResponseEntity("댓글 삭제 완료"), HttpStatus.OK);
    }
}
