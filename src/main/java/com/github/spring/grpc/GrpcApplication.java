package com.github.spring.grpc;

import com.github.spring.grpc.config.security.jwt.manager.JwtManager;
import com.github.spring.grpc.entity.UserEntity;
import com.github.spring.grpc.repository.UserRepository;
import com.github.spring.grpc.view.model.JwtUserModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
                UserEntity user = new UserEntity();
                user.setName("fahmi sulaiman" + i);
                user.setEmail("sulaimanfahmi@gmail.com" + i);
                user.setAddres("Apl Tower lt.26" + i);
                userRepository.save(user);
            }
            JwtUserModel jwtUserModel = new JwtUserModel();
            jwtUserModel.setId(123123123);
            jwtUserModel.setUsername("fahmi");

            jwtUserModel.setRole("BERAK");
            String token = JwtManager.generateToken(jwtUserModel);
            log.info(token);
        });
    }

}

