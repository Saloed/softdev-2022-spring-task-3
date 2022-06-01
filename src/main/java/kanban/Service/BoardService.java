package kanban.Service;

import kanban.Entity.BoardEntity;
import kanban.Entity.ListEntity;
import kanban.Entity.UserEntity;
import kanban.Exception.BoardNotFoundException;
import kanban.Repository.BoardRepo;
import kanban.Repository.ListRepo;
import kanban.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepo boardRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ListRepo listRepo;

    public BoardEntity create(BoardEntity board) {
        System.out.println(board.getTitle()+" BoardService");
        return boardRepo.save(board);
    }

    public BoardEntity getOne(Long id) throws BoardNotFoundException {
        BoardEntity board = boardRepo.findById(id).get();
        if(board == null){
            throw new BoardNotFoundException("Board not found");
        }
        return board;
    }

    public BoardEntity findByTitle(String title) throws BoardNotFoundException {
        BoardEntity board = boardRepo.findByTitle(title);
        if(board == null){
            throw new BoardNotFoundException("Board not found");
        }
        return board;
    }

    public BoardEntity addUser(UserEntity user, Long id){
        BoardEntity board = boardRepo.findById(id).get();
        board.addUser(userRepo.findById(user.getId()).get());
        return boardRepo.save(board);
    }

    public BoardEntity addColumn(ListEntity list, Long id){
        BoardEntity board = boardRepo.findById(id).get();
        board.addColumn(listRepo.findById(list.getId()).get());
        return boardRepo.save(board);
    }

    public Long delete(Long id){
        boardRepo.findById(id);
        return id;
    }
}
