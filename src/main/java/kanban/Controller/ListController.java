package kanban.Controller;

import kanban.Entity.ListEntity;
import kanban.Model.Card;
import kanban.Model.Column;
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
    public ResponseEntity createList(@RequestBody Column list){
        try{
            return ResponseEntity.ok(listService.create(new ListEntity(list.getTitle()), list.getBoard().getId()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneList(@PathVariable Long id){
        try{
            return ResponseEntity.ok(listService.getOne(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/{id}/addcard")
    public ResponseEntity addCard(@RequestBody Card card, @PathVariable Long id){
        try{
            return ResponseEntity.ok(listService.addCard(card.getId(), id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/{id}/deletecard")
    public ResponseEntity deleteCard(@RequestBody Card card, @PathVariable Long id){
        try{
            return ResponseEntity.ok(listService.deleteCard(card.getId(), id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteList(@PathVariable Long id){
        try {
            return ResponseEntity.ok(listService.delete(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
