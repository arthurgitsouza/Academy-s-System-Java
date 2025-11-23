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
 * Controller UNIFICADO para upload de arquivos (fotos de alunos)
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
     *
     * Teste no Postman:
     * - Method: POST
     * - URL: http://localhost:8080/api/upload/aluno/1/foto
     * - Headers: Authorization: Bearer {seu_token}
     * - Body: form-data
     *   - Key: file (tipo FILE)
     *   - Value: Selecione uma imagem
     */
    @PostMapping("/aluno/{id}/foto")
    public ResponseEntity<Map<String, String>> uploadFotoAluno(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        try {
            // Validação do arquivo
            if (file.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Arquivo não pode estar vazio!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            // 1. Buscar o aluno
            Aluno aluno = alunoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));

            // 2. Se já tinha uma foto antiga, deletar
            if (aluno.getFoto() != null && !aluno.getFoto().isEmpty()) {
                try {
                    fileStorageService.deletarArquivo(aluno.getFoto());
                } catch (Exception e) {
                    // Se falhar ao deletar foto antiga, continua (não é crítico)
                    System.err.println("Erro ao deletar foto antiga: " + e.getMessage());
                }
            }

            // 3. Salvar a nova foto
            String nomeArquivo = fileStorageService.salvarArquivo(file);

            // 4. Atualizar o banco de dados
            aluno.setFoto(nomeArquivo);
            alunoRepository.save(aluno);

            // 5. Retornar resposta de sucesso
            Map<String, String> response = new HashMap<>();
            response.put("message", "Foto enviada com sucesso!");
            response.put("fileName", nomeArquivo);
            response.put("fileUrl", "/uploads/" + nomeArquivo);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erro ao processar upload: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
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

                Map<String, String> response = new HashMap<>();
                response.put("message", "Foto deletada com sucesso!");
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Aluno não possui foto cadastrada.");
                return ResponseEntity.ok(response);
            }

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erro ao deletar foto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Buscar URL da foto do aluno
     * GET /api/upload/aluno/{id}/foto
     */
    @GetMapping("/aluno/{id}/foto")
    public ResponseEntity<Map<String, String>> buscarFotoAluno(@PathVariable Long id) {
        try {
            Aluno aluno = alunoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));

            Map<String, String> response = new HashMap<>();

            if (aluno.getFoto() != null && !aluno.getFoto().isEmpty()) {
                response.put("fileName", aluno.getFoto());
                response.put("fileUrl", "/uploads/" + aluno.getFoto());
            } else {
                response.put("message", "Aluno não possui foto cadastrada.");
            }

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}