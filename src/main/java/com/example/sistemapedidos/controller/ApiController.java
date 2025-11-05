package com.example.sistemapedidos.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sistemapedidos.model.Pedido;
import com.example.sistemapedidos.model.Usuario;
import com.example.sistemapedidos.repository.PedidoRepository;
import com.example.sistemapedidos.repository.UsuarioRepository;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private PedidoRepository pedidoRepo;

    static class LoginDTO { public String email; public String senha; }
    static class PedidoDTO { public String descricao; public Long clienteId; }
    static class RegistroDTO { public String nome; public String email; public String senha; }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        Optional<Usuario> u = usuarioRepo.findByEmailAndSenha(dto.email, dto.senha);
        if (u.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", "Credenciais inválidas"));
        return ResponseEntity.ok(u.get());
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody RegistroDTO dto) {
        if (dto.nome==null || dto.email==null || dto.senha==null) {
            return ResponseEntity.badRequest().body(Map.of("erro","Dados incompletos"));
        }
        if (usuarioRepo.findByEmail(dto.email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro","E-mail já cadastrado"));
        }
        Usuario u = new Usuario();
        u.setNome(dto.nome);
        u.setEmail(dto.email);
        u.setSenha(dto.senha);
        u.setTipo("CLIENTE");
        usuarioRepo.save(u);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/pedido")
    public ResponseEntity<?> criarPedido(@RequestBody PedidoDTO dto) {
        Optional<Usuario> cliente = usuarioRepo.findById(dto.clienteId);
        if (cliente.isEmpty() || !"CLIENTE".equals(cliente.get().getTipo())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("erro","Apenas clientes podem criar pedidos"));
        }
        Pedido p = new Pedido();
        p.setDescricao(dto.descricao);
        p.setCliente(cliente.get());
        pedidoRepo.save(p);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/pedidos/{clienteId}")
    public ResponseEntity<?> pedidosCliente(@PathVariable Long clienteId) {
        Optional<Usuario> cliente = usuarioRepo.findById(clienteId);
        if (cliente.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro","Cliente não encontrado"));
        List<Pedido> lista = pedidoRepo.findByCliente(cliente.get());
        return ResponseEntity.ok(lista);
    }
    
 // ✅ CLIENTE: Excluir o próprio pedido
    @DeleteMapping("/pedido/{pedidoId}")
    public ResponseEntity<?> deletarPedido(@PathVariable Long pedidoId, @RequestParam Long clienteId) {
        Optional<Usuario> cliente = usuarioRepo.findById(clienteId);
        if (cliente.isEmpty() || !"CLIENTE".equals(cliente.get().getTipo())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("erro", "Apenas clientes podem excluir pedidos"));
        }

        Optional<Pedido> pedido = pedidoRepo.findById(pedidoId);
        if (pedido.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Pedido não encontrado"));
        }

        if (!pedido.get().getCliente().getId().equals(clienteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("erro", "Você só pode excluir seus próprios pedidos"));
        }

        pedidoRepo.delete(pedido.get());
        return ResponseEntity.ok(Map.of("mensagem", "Pedido excluído com sucesso"));
    }


    

    @GetMapping("/admin/pedidos")
    public ResponseEntity<?> todosPedidos(@RequestParam String email, @RequestParam String senha) {
        Optional<Usuario> u = usuarioRepo.findByEmailAndSenha(email, senha);
        if (u.isEmpty() || !"ADMIN".equals(u.get().getTipo())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("erro","Acesso negado"));
        }
        return ResponseEntity.ok(pedidoRepo.findAll());
    }
    
}
