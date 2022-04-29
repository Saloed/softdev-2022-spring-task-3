package kanban.Controller;

import kanban.Entity.CardEntity;
import kanban.Entity.ListEntity;
import kanban.Service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lists")
public class ListController {
    @Autowired
    private ListService listService;

    @PostMapping
    public ResponseEntity createList(@RequestBody ListEntity card, @RequestParam Long boardId){
        try{
            return ResponseEntity.ok(listService.create(card, boardId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneList(@PathVariable Long id){
        try{
            return ResponseEntity.ok(listService.getOne(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/{id}/title")
    public ResponseEntity changeTitle(@RequestParam String title, @PathVariable Long id){
        try{
            return ResponseEntity.ok(listService.changeTitle(title, id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/{id}/cards")
    public ResponseEntity changeCards(@RequestParam List<CardEntity> cards, @PathVariable Long id){
        try{
            return ResponseEntity.ok(listService.changeCards(cards, id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(listService.delete(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
