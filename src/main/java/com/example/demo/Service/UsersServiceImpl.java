package com.example.demo.Service;

import com.example.demo.Entity.Phones;
import com.example.demo.Entity.Users;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsersServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Users> listUser() {
        return userRepository.findAll();
    }

    @Override
    public Users createUser(Users users) {
        if (users.getId() != null){
            throw new RuntimeException("Error with the id");
        }
        controlUser(users);
        users.setCreation(new Date());
        users.setActive(true);

        return userRepository.save(users);
    }

    @Override
    public Users editUser(Users users) {
        if (users.getId() == null)
            throw new RuntimeException("Error, the id does not exist");
        controlUser(users);

        users.setModified(new Date());

        return userRepository.save(users);
    }

    @Override
    public void deleteUser(String id) {
        if (id == null)
            throw new RuntimeException("The id is null");
        if (!userRepository.existsById(id))
            throw new RuntimeException(String.format("The id %s does not exist in the database.",id));
        userRepository.deleteById(id);
    }

    private void controlUser(Users users){
        if (userRepository.findByEmail(users.getEmail()) != null)
            throw new RuntimeException(String.format("The email %s exist in database",users.getEmail()));
        if (validateEmail(users.getEmail()))
            throw new RuntimeException(String.format("The email %s does not match the format",users.getEmail()));
        if (validatePassword(users.getPassword()))
            throw new RuntimeException("The password The password must have a lowercase letter, an uppercase letter and two numbers");
    }

    private boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("^(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    private boolean validatePassword(String password){
        Pattern pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
