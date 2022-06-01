package kanban.Controller;

import kanban.Entity.BoardEntity;
import kanban.Entity.ListEntity;
import kanban.Entity.UserEntity;
import kanban.Model.Board;
import kanban.Model.User;
import kanban.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping
    public ResponseEntity createBoard(@RequestBody Board board){
        try{
            List<ListEntity> lists = new ArrayList<>();
            return ResponseEntity.ok(boardService.create(new BoardEntity(board.getTitle())));
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

    @PutMapping("/{id}/user")
    public ResponseEntity addUser(@RequestBody User user, @PathVariable Long id){
        try{
            return ResponseEntity.ok(boardService.addUser(new UserEntity(user.getId()), id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/{id}/list")
    public ResponseEntity addColumn(@RequestBody ListEntity list, @PathVariable Long id){
        try{
            return ResponseEntity.ok(boardService.addColumn(new ListEntity(list.getId()), id));
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
