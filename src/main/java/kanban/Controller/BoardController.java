package kanban.Controller;

import kanban.Entity.BoardEntity;
import kanban.Entity.ListEntity;
import kanban.Entity.UserEntity;
import kanban.Exception.BoardNotFoundException;
import kanban.Model.Board;
import kanban.Model.Column;
import kanban.Model.User;
import kanban.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping
    public ResponseEntity createBoard(@RequestBody Board board){
        try{
            System.out.println(board.getTitle()+" BoardController");
            return ResponseEntity.ok(boardService.create(new BoardEntity(board.getTitle())));
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

    @GetMapping("/find")
    public ResponseEntity findByTitle(@RequestParam(name="title") String title){
        try{
            return ResponseEntity.ok(boardService.findByTitle(title));
        }catch (BoardNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/{id}/user")
    public ResponseEntity addUser(@RequestBody User user, @PathVariable Long id){
        try{
            return ResponseEntity.ok(boardService.addUser(new UserEntity(user.getId()), id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/{id}/list")
    public ResponseEntity addColumn(@RequestBody Column list, @PathVariable Long id){
        try{
            return ResponseEntity.ok(boardService.addColumn(new ListEntity(list.getId()), id));
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
