package com.PohonTautan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.PohonTautan.Entity.Users;
import com.PohonTautan.Repository.UsersRepository;

@Component
public class CmdRunner implements CommandLineRunner {

        private static final Logger logger = LoggerFactory.getLogger(CmdRunner.class);

        @Autowired
        private UsersRepository usersRepository;

        @Autowired
        private PasswordEncoder encoder;

        @Override
        public void run(String... args) throws Exception {

                usersRepository.save(new Users(1, "admin", encoder.encode("admin")));

                logger.info("Person Has been created");
        }
}