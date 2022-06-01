package kanban.Controller;

import kanban.Entity.CardEntity;
import kanban.Entity.ListEntity;
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
            return ResponseEntity.ok(listService.create(new ListEntity(list.getTitle(), list.getBoardTitle())));
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

    @GetMapping("/find")
    public ResponseEntity findByBoard(@RequestParam(name="boardTitle") String board){
        try{
            return ResponseEntity.ok(listService.findByTitle(board));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/{id}/addcard")
    public ResponseEntity addCard(@RequestParam CardEntity card, @PathVariable Long id){
        try{
            return ResponseEntity.ok(listService.addCard(card, id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/{id}/deletecard")
    public ResponseEntity deleteCard(@RequestParam CardEntity card, @PathVariable Long id){
        try{
            return ResponseEntity.ok(listService.deleteCard(card, id));
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
