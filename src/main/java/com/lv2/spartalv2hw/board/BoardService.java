package com.lv2.spartalv2hw.board;

import com.lv2.spartalv2hw.jwt.JwtUtil;
import com.lv2.spartalv2hw.user.User;
import com.lv2.spartalv2hw.user.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepositoy boardRepositoy;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public List<BoardResponseDto> getBoardList() {
        return boardRepositoy.findAllByOrderByCreatedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);

        User user = userRepository.findByUsername(tokenUsername(tokenValue)).orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        Board board = new Board(boardRequestDto,user);
        return new BoardResponseDto(boardRepositoy.save(board));
    }

    public BoardResponseDto getBoard(Long id) {
        return new BoardResponseDto(boardRepositoy.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다.")));
    }

    @Transactional
    public BoardResponseDto putBoard(Long id, BoardRequestDto boardRequestDto, HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);

        Board board = boardRepositoy.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        User user = userRepository.findByUsername(tokenUsername(tokenValue)).orElseThrow(IllegalArgumentException::new);

        if (!user.getRoleEnum().getAuthority().equals("ROLE_ADMIN")) {
            if (!user.getUsername().equals(board.getUser().getUsername())) {
                throw new IllegalArgumentException("이글은 작성자만 수정할 수 있습니다.");
            }
        }

        board.putBoard(boardRequestDto);

        return new BoardResponseDto(board);
    }

    public void deleteBoard(Long id, HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);
        Board board = boardRepositoy.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));
        User user = userRepository.findByUsername(tokenUsername(tokenValue)).orElseThrow(IllegalArgumentException::new);

        if (!user.getRoleEnum().getAuthority().equals("ROLE_ADMIN")) {
            if (!user.getUsername().equals(board.getUser().getUsername())) {
                throw new IllegalArgumentException("이글은 작성자만 삭제할 수 있습니다.");
            }
        }
        boardRepositoy.delete(board);
    }

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
