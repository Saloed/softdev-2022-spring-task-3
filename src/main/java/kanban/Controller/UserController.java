package kanban.Controller;

import kanban.Entity.UserEntity;
import kanban.Exception.UserAlreadyExistException;
import kanban.Exception.UserNotFoundException;
import kanban.Model.Board;
import kanban.Model.User;
import kanban.Service.BoardService;
import kanban.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BoardService boardService;

    @PostMapping
    public ResponseEntity registration(@RequestBody User user){
        try{
            return ResponseEntity.ok(userService.registration(new UserEntity(user.getUsername(), user.getPassword())));
        }catch (UserAlreadyExistException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneUser(@PathVariable Long id){
        try{
            return ResponseEntity.ok(userService.getOne(id));
        }catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/find")
    public ResponseEntity findByUsername(@RequestParam(name="username") String username){
        try{
            return ResponseEntity.ok(userService.findByUsername(username));
        }catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/{id}/board")
    public ResponseEntity addBoard(@RequestBody Board board, @PathVariable Long id){
        try{
            return ResponseEntity.ok(userService.addBoard(boardService.getOne(board.getId()), id));
        }catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
