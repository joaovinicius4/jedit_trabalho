package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path pastaJEdit = Path.of(args[0]);
        if (!Files.isDirectory(pastaJEdit)) {
            System.err.println("Pasta nao encontrada: " + pastaJEdit);
            return;
        }

        try (Stream<Path> arquivos = Files.walk(pastaJEdit)) {
            List<Path> arquivosJava = arquivos
                    .filter(Files::isRegularFile)
                    .filter(arquivo -> arquivo.toString().endsWith(".java"))
                    .sorted()
                    .toList();

            System.out.println("Pasta analisada: " + pastaJEdit.toAbsolutePath());
            System.out.println("Arquivos Java encontrados: " + arquivosJava.size());
            System.out.println("Primeiros arquivos:");
            arquivosJava.stream()
                    .limit(10)
                    .map(pastaJEdit::relativize)
                    .forEach(arquivo -> System.out.println("- " + arquivo));
        } catch (IOException e) {
            System.err.println("Erro ao ler os arquivos: " + e.getMessage());
        }
    }
}
