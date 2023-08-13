package ua.edu.sumdu.nefodov.sheltered.application.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.nefodov.sheltered.application.repository.UserRepository;

import javax.transaction.Transactional;


@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final String USER_NOT_FOUND_MSG = "Користувача не знайдено";

    private final Logger LOG = LogManager.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info("LOAD USER BY USERNAME");
        LOG.info(username);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG));
    }
}
