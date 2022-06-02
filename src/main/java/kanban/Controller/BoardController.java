package kanban.Controller;

import kanban.Entity.BoardEntity;
import kanban.Model.Board;
import kanban.Model.Column;
import kanban.Model.User;
import kanban.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/boards", consumes = {"*/*"})
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping(consumes = {"*/*"})
    public ResponseEntity createBoard(@RequestBody Board board){
        try{
            return ResponseEntity.ok(boardService.create(new BoardEntity(board.getTitle()), board.getUsers().get(0).getId()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneBoard(@PathVariable Long id){
        try{
            return ResponseEntity.ok(boardService.getOne(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/{id}/user")
    public ResponseEntity addUser(@RequestBody User user, @PathVariable Long id){
        try{
            return ResponseEntity.ok(boardService.addUser(user.getId(), id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/{id}/list")
    public ResponseEntity addColumn(@RequestBody Column list, @PathVariable Long id){
        try{
            return ResponseEntity.ok(boardService.addColumn(list.getId(), id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(boardService.delete(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
