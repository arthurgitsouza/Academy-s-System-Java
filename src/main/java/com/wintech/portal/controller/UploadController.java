package com.wintech.portal.controller;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.repository.AlunoRepository;
import com.wintech.portal.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller para upload de arquivos (fotos de alunos)
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private final FileStorageService fileStorageService;
    private final AlunoRepository alunoRepository;

    @Autowired
    public UploadController(FileStorageService fileStorageService,
                            AlunoRepository alunoRepository) {
        this.fileStorageService = fileStorageService;
        this.alunoRepository = alunoRepository;
    }

    /**
     * Upload de foto do aluno
     * POST /api/upload/aluno/{id}/foto
     */
    @PostMapping("/aluno/{id}/foto")
    public ResponseEntity<Map<String, String>> uploadFotoAluno(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        try {
            // 1. Buscar o aluno
            Aluno aluno = alunoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));

            // 2. Se já tinha uma foto antiga, deletar
            if (aluno.getFoto() != null && !aluno.getFoto().isEmpty()) {
                fileStorageService.deletarArquivo(aluno.getFoto());
            }

            // 3. Salvar a nova foto
            String nomeArquivo = fileStorageService.salvarArquivo(file);

            // 4. Atualizar o banco de dados
            aluno.setFoto(nomeArquivo);
            alunoRepository.save(aluno);

            // 5. Retornar resposta
            Map<String, String> response = new HashMap<>();
            response.put("message", "Foto enviada com sucesso!");
            response.put("fileName", nomeArquivo);
            response.put("fileUrl", "/uploads/" + nomeArquivo); // URL para acessar a foto

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Deletar foto do aluno
     * DELETE /api/upload/aluno/{id}/foto
     */
    @DeleteMapping("/aluno/{id}/foto")
    public ResponseEntity<Map<String, String>> deletarFotoAluno(@PathVariable Long id) {
        try {
            Aluno aluno = alunoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));

            if (aluno.getFoto() != null && !aluno.getFoto().isEmpty()) {
                // Deletar arquivo físico
                fileStorageService.deletarArquivo(aluno.getFoto());

                // Limpar no banco
                aluno.setFoto(null);
                alunoRepository.save(aluno);
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Foto deletada com sucesso!");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}