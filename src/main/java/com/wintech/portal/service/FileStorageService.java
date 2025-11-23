package com.wintech.portal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Service responsável por salvar e gerenciar arquivos no servidor
 */
@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            // Cria o diretório se não existir
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Não foi possível criar o diretório de upload!", ex);
        }
    }

    /**
     * Salva um arquivo e retorna o nome único gerado
     */
    public String salvarArquivo(MultipartFile file) {
        // Validações
        if (file.isEmpty()) {
            throw new RuntimeException("Arquivo está vazio!");
        }

        // Validar tipo de arquivo (apenas imagens)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Apenas imagens são permitidas!");
        }

        // Validar tamanho (máximo 5MB)
        long maxSize = 5 * 1024 * 1024; // 5MB em bytes
        if (file.getSize() > maxSize) {
            throw new RuntimeException("Arquivo muito grande! Tamanho máximo: 5MB");
        }

        // Pegar extensão do arquivo original
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";
        if (originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // Gerar nome único (UUID + extensão)
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        try {
            // Copiar arquivo para o diretório de upload
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return uniqueFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao salvar arquivo: " + uniqueFileName, ex);
        }
    }

    /**
     * Deleta um arquivo
     */
    public void deletarArquivo(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao deletar arquivo: " + fileName, ex);
        }
    }

    /**
     * Verifica se um arquivo existe
     */
    public boolean arquivoExiste(String fileName) {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        return Files.exists(filePath);
    }
}
