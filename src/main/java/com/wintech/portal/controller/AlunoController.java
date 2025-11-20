package com.wintech.portal.controller;

import com.wintech.portal.dto.AlunoRequestDTO;
import com.wintech.portal.dto.AlunoResponseDTO;
import com.wintech.portal.service.AlunoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    
    
    private final AlunoService alunoService;

    @Autowired
    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> criarAluno(@RequestBody AlunoRequestDTO alunoRequestDTO) {
        AlunoResponseDTO alunoResponseDTO = alunoService.salvarNovoAluno(alunoRequestDTO);
        return ResponseEntity.ok(alunoResponseDTO);
    }

    // Exemplo de GET por id retornando ResponseDTO
    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable Long id) {
        AlunoResponseDTO dto = alunoService.buscarPorIdDTO(id); // implemente no Service
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }
}