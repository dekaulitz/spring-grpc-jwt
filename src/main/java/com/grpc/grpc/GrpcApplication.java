package com.grpc.grpc;

import com.grpc.grpc.config.security.jwt.manager.JwtManager;
import com.grpc.grpc.view.model.JwtUserModel;
import com.grpc.grpc.entity.User;
import com.grpc.grpc.repository.UserRepository;
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
                User user = new User();
                user.setName("fahmi sulaiman" + i);
                user.setEmail("sulaimanfahmi@gmail.com" + i);
                user.setAddres("Apl Tower lt.26" + i);
                userRepository.save(user);
            }
            JwtUserModel jwtUserModel = new JwtUserModel();
            jwtUserModel.setId(123123123);
            jwtUserModel.setUsername("fahmi");

            jwtUserModel.setRole("ADMIN");
            String token = JwtManager.generateToken(jwtUserModel);
            log.info(token);
        });
    }


    public static void random(int a, int b) {
        log.info("A :" + a + "  b   :" + b);
        int intA = 0;
        int intB = 0;
        String berak = "aabbbb";
        String tmp = "";
        if (a > b) {
            for (int i = 0; i < a + b; i++) {
                if (((i + 1) % 3) == 0) {
                    tmp += "b";
                    intB++;
                } else {
                    intA++;
                    if ((intA > a) && (intB < b)) {
                        tmp += "b";
                    } else {
                        tmp += "a";
                    }
                    if (intB > b) {
                        System.out.println("invalid");
                        return;
                    }
                }
            }
        } else {
            for (int i = 0; i < a + b; i++) {
                if (((i + 1) % 3) == 0) {
                    tmp += "a";
                    System.out.println(((i + 1) % 3));
                    intA++;
                } else {
                    intB++;
                    if ((intB > b) && (intA < a)) {
                        tmp += "a";
                    } else {
                        tmp += "b";
                    }
                    if (intA > a) {
                        System.out.println("invalid");
                        return;
                    }
                }
            }
        }
        System.out.println(tmp);


    }

}

