package com.example.sistemapedidos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.sistemapedidos.model.Usuario;
import com.example.sistemapedidos.repository.UsuarioRepository;

@SpringBootApplication
public class SistemaPedidosApplication {
    public static void main(String[] args) {
        SpringApplication.run(SistemaPedidosApplication.class, args);
    }

   
    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepo) {
        return args -> {

            // Verifica e cria o admin padrão
            if (usuarioRepo.findByEmail("admin@teste.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setEmail("admin@teste.com");
                admin.setSenha("123");
                admin.setTipo("ADMIN");
                usuarioRepo.save(admin);
                System.out.println("✅ Administrador padrão criado (email: admin@teste.com / senha: 123)");
            } else {
                System.out.println("ℹ️ Administrador já existente, nenhum novo criado.");
            }

            // Verifica e cria o cliente padrão
            if (usuarioRepo.findByEmail("cliente@teste.com").isEmpty()) {
                Usuario cliente = new Usuario();
                cliente.setNome("Cliente Teste");
                cliente.setEmail("cliente@teste.com");
                cliente.setSenha("123");
                cliente.setTipo("CLIENTE");
                usuarioRepo.save(cliente);
                System.out.println("✅ Cliente padrão criado (email: cliente@teste.com / senha: 123)");
            } else {
                System.out.println("ℹ️ Cliente já existente, nenhum novo criado.");
            }
        };
    }
}
