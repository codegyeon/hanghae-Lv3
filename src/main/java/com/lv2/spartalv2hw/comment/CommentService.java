package com.lv2.spartalv2hw.comment;

import com.lv2.spartalv2hw.board.Board;
import com.lv2.spartalv2hw.board.BoardRepositoy;
import com.lv2.spartalv2hw.jwt.JwtUtil;
import com.lv2.spartalv2hw.user.User;
import com.lv2.spartalv2hw.user.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepositoy boardRepositoy;
    private final JwtUtil jwtUtil;

    //댓글 생성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);

        //1.어느 게시물에 달릴지
        Board board = boardRepositoy.findById(commentRequestDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("해당 게시물 없음"));
        User user = userRepository.findByUsername(tokenUsername(tokenValue)).orElseThrow(() -> new IllegalArgumentException("해당 유저 없음"));
        Comment comment = new Comment(commentRequestDto.getContents(), user, board );

        return new CommentResponseDto(commentRepository.save(comment));
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto,HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글 없음"));
        User user = userRepository.findByUsername(tokenUsername(tokenValue)).orElseThrow(IllegalArgumentException::new);

        //권한 체크
        if(!user.getRoleEnum().getAuthority().equals("ROLE_ADMIN")) {
            if (!comment.getUser().getId().equals(user.getId())) {
                throw new IllegalArgumentException("해당 유저가 아님");
            }
        }


        comment.updateComment(commentRequestDto.getContents());
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long id,HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글 없음"));
        User user = userRepository.findByUsername(tokenUsername(tokenValue)).orElseThrow(IllegalArgumentException::new);

        //권한 체크
        if(!user.getRoleEnum().getAuthority().equals("ROLE_ADMIN")) {
            if (!comment.getUser().getId().equals(user.getId())) {
                throw new IllegalArgumentException("해당 유저가 아님");
            }
        }

        commentRepository.delete(comment);
    }



    //토큰에서 유저네임 추출
    private String tokenUsername(String tokenValue){
        String token = jwtUtil.substringToken(tokenValue);

        try {
            jwtUtil.validateToken(token);
        }catch (Exception e){
            throw new IllegalArgumentException("Token Error");
        }
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        return claims.getSubject();
    }
}
