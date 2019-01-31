package com.github.spring.grpc;

import com.github.spring.grpc.entity.UserEntity;
import com.github.spring.grpc.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@Log4j2
public class GrpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcApplication.class, args);
    }

    @Bean
    public CommandLineRunner dummyData(UserRepository userRepository) {
        return (args -> {
            for (int i = 0; i < 10; i++) {
                BCryptPasswordEncoder password = new BCryptPasswordEncoder();
                String pass = password.encode("fahmisulaiman");
                UserEntity user = new UserEntity();
                user.setName("fahmi" + i);
                user.setEmail("sulaimanfahmi@gmail.com" + i);
                user.setAddres("Apl Tower lt.26" + i);
                user.setRole("ADMIN");
                user.setPassword(pass);
                userRepository.save(user);
            }
        });
    }

}

