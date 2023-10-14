package br.com.matsaguiar.todolist.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public UserModel createUser(UserModel userModel) throws Exception{

        if(userRepository.findByUserName(userModel.getUserName()) != null)
            throw new DataException ("Usuário já existe!", null);
        
        userModel.setPasswordEncoded(sha512(userModel.getPassword()));
        userModel.setPassword(null);
        return userRepository.save(userModel);
    }

    public byte[] sha512(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] digest = md.digest(password.getBytes());

			return digest;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
            return null;
		}
	}

    public UserModel findByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public UserModel findById(UUID username){
        return userRepository.findById(username).get();
    }
}
