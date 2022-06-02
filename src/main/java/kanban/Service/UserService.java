package kanban.Service;

import kanban.Entity.BoardEntity;
import kanban.Entity.UserEntity;
import kanban.Exception.UserAlreadyExistException;
import kanban.Exception.UserNotFoundException;
import kanban.Model.Board;
import kanban.Model.User;
import kanban.Repository.BoardRepo;
import kanban.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BoardRepo boardRepo;

    public User registration(UserEntity user) throws UserAlreadyExistException {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException("User already exist");
        }
        UserEntity entity = userRepo.save(new UserEntity(user.getUsername(), user.getPassword()));
        return new User(entity.getId(),entity.getUsername(),entity.getPassword());
    }

    public User getOne(Long id)  {
        UserEntity user = userRepo.findById(id).get();
        List<Board> boards = new ArrayList<>();
        for(int i = 0; i < user.getBoards().toArray().length; i++){
            BoardEntity entity = user.getBoards().get(i);
            boards.add(new Board(entity.getId(),entity.getTitle()));
        }
        return new User(user.getId(), user.getUsername(),user.getPassword(), boards);
    }

    public User findByUsername(String username) throws UserNotFoundException {
        UserEntity user = userRepo.findByUsername(username);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        List<Board> boards = new ArrayList<>();
        for(int i = 0; i < user.getBoards().toArray().length; i++){
            BoardEntity entity = user.getBoards().get(i);

            boards.add(new Board(entity.getId(),entity.getTitle()));
        }
        return new User(user.getId(), user.getUsername(),user.getPassword(), boards);
    }

    public UserEntity addBoard(Board board, Long id) throws UserNotFoundException{
        UserEntity user = userRepo.findById(id).get();
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        user.addBoard(boardRepo.findById(board.getId()).get());
        return userRepo.save(user);
    }

    public Long deleteUser(Long id){
        userRepo.deleteById(id);
        return id;
    }
}
