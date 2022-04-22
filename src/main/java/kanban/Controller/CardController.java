package kanban.Controller;

import kanban.Entity.CardEntity;
import kanban.Entity.UserEntity;
import kanban.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity createCard(@RequestBody CardEntity card, @RequestParam Long boardId){
        try{
            return ResponseEntity.ok(cardService.create(card, boardId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity getOneCard(@RequestParam Long id){
        try{
            return ResponseEntity.ok(cardService.getOne(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/users")
    public ResponseEntity getUsers(@RequestParam Long id){
        try {
            return ResponseEntity.ok(cardService.getUsers(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/{id}/users")
    public ResponseEntity changeUsers(@RequestBody List<UserEntity> users, @PathVariable Long id){
        try{
            return ResponseEntity.ok(cardService.changeUsers(users, id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/{id}/title")
    public ResponseEntity changeTitle(@RequestParam String title, @PathVariable Long id){
        try{
            return ResponseEntity.ok(cardService.changeTitle(title, id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/{id}/desc")
    public ResponseEntity changeDescription(@RequestParam String desc, @PathVariable Long id){
        try{
            return ResponseEntity.ok(cardService.changeTitle(desc, id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(cardService.delete(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
