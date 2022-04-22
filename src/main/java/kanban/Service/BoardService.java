package kanban.Service;

import kanban.Entity.BoardEntity;
import kanban.Entity.UserEntity;
import kanban.Exception.BoardNotFoundException;
import kanban.Exception.UserNotFoundException;
import kanban.Repository.BoardRepo;
import kanban.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepo boardRepo;
    @Autowired
    private UserRepo userRepo;

    public BoardEntity create(BoardEntity board, Long userId){
        UserEntity user = userRepo.findById(userId).get();
        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        board.setUsers(users);
        return boardRepo.save(board);
    }

    public BoardEntity getOne(Long id){
        BoardEntity board = boardRepo.findById(id).get();
        return board;
    }

    public List<UserEntity> getUsers(Long id) throws BoardNotFoundException {
        BoardEntity board = boardRepo.findById(id).get();
        if(board == null) {
            throw new BoardNotFoundException("Доска не найдена");
        }
        return board.getUsers();
    }

    public BoardEntity changeUsers(List<UserEntity> users, Long id){
        BoardEntity board = boardRepo.findById(id).get();
        board.setUsers(users);
        return boardRepo.save(board);
    }

    public BoardEntity changeTitle(String title, Long id){
        BoardEntity board = boardRepo.findById(id).get();
        board.setTitle(title);
        return boardRepo.save(board);
    }

    public Long delete(Long id){
        boardRepo.findById(id);
        return id;
    }
}
