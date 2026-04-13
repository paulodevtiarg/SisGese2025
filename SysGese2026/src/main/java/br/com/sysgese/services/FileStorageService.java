package br.com.sysgese.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileStorageService {

    private final String BASE_DIR = System.getProperty("user.dir") + "/uploads/";

    public String salvar(MultipartFile file, String pasta) throws IOException {

        if (file == null || file.isEmpty()) return null;

        // valida tipo
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Somente imagens são permitidas");
        }

        // limpa nome
        String nomeOriginal = Paths.get(file.getOriginalFilename())
                .getFileName()
                .toString()
                .replaceAll("\\s+", "_");

        String nomeArquivo = UUID.randomUUID() + "_" + nomeOriginal;

        Path diretorio = Paths.get(BASE_DIR, pasta);
        Path caminho = diretorio.resolve(nomeArquivo);

        Files.createDirectories(diretorio);

        Files.copy(file.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);

        return nomeArquivo;
    }
}
