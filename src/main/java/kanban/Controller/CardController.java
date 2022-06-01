package kanban.Controller;

import kanban.Entity.CardEntity;
import kanban.Entity.UserEntity;
import kanban.Model.Card;
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
    public ResponseEntity createCard(@RequestBody Card card){
        try{
            return ResponseEntity.ok(cardService.create(new CardEntity(card.getTitle(),card.getDescription())));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneCard(@PathVariable Long id){
        try{
            return ResponseEntity.ok(cardService.getOne(id));
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


    @PutMapping("/{id}/desc")
    public ResponseEntity changeDescription(@RequestParam String desc, @PathVariable Long id){
        try{
            return ResponseEntity.ok(cardService.changeDescription(desc, id));
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
