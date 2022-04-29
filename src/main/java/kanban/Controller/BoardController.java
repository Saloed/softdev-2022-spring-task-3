package kanban.Controller;

import kanban.Entity.BoardEntity;
import kanban.Entity.UserEntity;
import kanban.Exception.BoardNotFoundException;
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
            return ResponseEntity.ok(boardService.create(board, userId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneBoard(@PathVariable Long id){
        try{
            return ResponseEntity.ok(boardService.getOne(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/{id}/users")
    public ResponseEntity getUsers(@PathVariable Long id){
        try {
            return ResponseEntity.ok(boardService.getUsers(id));
        }catch (BoardNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/{id}/users")
    public ResponseEntity changeUsers(@RequestBody List<UserEntity> users, @PathVariable Long id){
        try{
            return ResponseEntity.ok(boardService.changeUsers(users, id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/{id}/title")
    public ResponseEntity changeTitle(@RequestParam String title, @PathVariable Long id){
        try{
            return ResponseEntity.ok(boardService.changeTitle(title, id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(boardService.delete(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
