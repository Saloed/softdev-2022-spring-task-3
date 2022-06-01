package kanban.Controller;

import kanban.Entity.CardEntity;
import kanban.Entity.ListEntity;
import kanban.Service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lists")
public class ListController {
    @Autowired
    private ListService listService;

    @PostMapping
    public ResponseEntity createList(@RequestBody ListEntity card){
        try{
            return ResponseEntity.ok(listService.create(card));
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

    @PutMapping("/{id}/addcard")
    public ResponseEntity addCard(@RequestParam CardEntity card, @PathVariable Long id){
        try{
            return ResponseEntity.ok(listService.addCard(card, id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/{id}/deletecard")
    public ResponseEntity deleteCard(@RequestParam CardEntity card, @PathVariable Long id){
        try{
            return ResponseEntity.ok(listService.deleteCard(card, id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteList(@PathVariable Long id){
        try {
            return ResponseEntity.ok(listService.delete(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
