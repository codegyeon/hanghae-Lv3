package com.lv2.spartalv2hw.board;

import com.lv2.spartalv2hw.user.ResultResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardRestController {

    private final BoardService boardService;


    public BoardRestController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping("")
    public List<BoardResponseDto> getBoardList(){
        return boardService.getBoardList();
    }

    @PostMapping("")
    public ResponseEntity<?> createBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest req){
        BoardResponseDto boardResponseDto;
        try{
            boardResponseDto = boardService.createBoard(boardRequestDto, req);
        }catch (Exception e){
            return new ResponseEntity<>(new ResultResponseEntity(e.getMessage()) ,HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(boardResponseDto,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putBoard(@PathVariable Long id,@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest req){
        BoardResponseDto boardResponseDto;
        try{
            boardResponseDto = boardService.putBoard(id,boardRequestDto, req);
        } catch (Exception e){
            return new ResponseEntity<>(new ResultResponseEntity(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id, HttpServletRequest req){
        try {
            boardService.deleteBoard(id, req);
        }catch (Exception e){
            return new ResponseEntity<>(new ResultResponseEntity("게시글 삭제 불가"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new ResultResponseEntity("게시글 삭제 완료"), HttpStatus.OK);
    }
}
