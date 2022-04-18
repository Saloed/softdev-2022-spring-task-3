package kanban.Controller;

import kanban.Entity.BoardEntity;
import kanban.Entity.UserEntity;
import kanban.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping
    public ResponseEntity createBoard(@RequestBody BoardEntity board, @RequestParam Long userId){
        try{
            return ResponseEntity.ok(boardService.createBoard(board, userId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping
    public ResponseEntity changeBoard(@RequestBody List<UserEntity> users, @RequestParam Long id){
        try{
            return ResponseEntity.ok(boardService.changeBoard(users, id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
