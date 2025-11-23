package com.wintech.portal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FileController {

    // Define a pasta onde os arquivos serão salvos
    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadArquivo(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Cria a pasta "uploads" se ela não existir
            Path pathDir = Paths.get(UPLOAD_DIR);
            if (!Files.exists(pathDir)) {
                Files.createDirectories(pathDir);
            }

            // 2. Gera um nome único para o arquivo (para não sobrescrever outros)
            String nomeArquivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // 3. Define o caminho final e salva o arquivo
            Path caminhoFinal = pathDir.resolve(nomeArquivo);
            Files.copy(file.getInputStream(), caminhoFinal, StandardCopyOption.REPLACE_EXISTING);

            // 4. Retorna o caminho do arquivo salvo (simulando uma URL)
            return ResponseEntity.ok(caminhoFinal.toString());

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}