package com.example.bookshop.service.impl;

import com.example.bookshop.entity.BaseEntity;
import com.example.bookshop.entity.CommentEntity;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CommentRepository;
import com.example.bookshop.service.IAsyncService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AsyncService implements IAsyncService {
    BookRepository bookRepository;
    CommentRepository commentRepository;

    @NonFinal
    @Value("${api.key}")
    protected String API_KEY;

    @NonFinal
    @Value("${api.url}")
    protected String URL;

    @Async
    @Override
    public CompletableFuture<Void> processDataAsync() {
        return CompletableFuture.runAsync(
                () -> {
                    RestTemplate restTemplate = new RestTemplate();

                    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                            .queryParam("key", API_KEY);

                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Content-Type", "application/json");
                    var books = bookRepository.findAll();
                    books.forEach(book -> {
                        var comments = commentRepository.findAllByBookId(book.getId())
                                .orElseThrow(() -> new RuntimeException("Book not found"));
                        Map<String, Object> data = comments.stream().collect(Collectors.toMap(
                                BaseEntity::getId, CommentEntity::getContent
                        ));
                        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);
                        ResponseEntity<String> response = restTemplate.exchange(
                                builder.toUriString(),
                                HttpMethod.POST,
                                entity,
                                String.class
                        );
                        String jsonResponse = response.getBody();
                        ObjectMapper objectMapper = new ObjectMapper();
                        String text = "";
                        try {
                            JsonNode rootNode = objectMapper.readTree(jsonResponse);
                            JsonNode candidatesNode = rootNode.path("candidates").get(0);
                            JsonNode textNode = candidatesNode.path("content").path("parts").get(0).path("text");

                            text = textNode.asText();
                            System.out.println("Text: " + text);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        book.setResume(text);
                        bookRepository.save(book);
                    });

                }
        ).exceptionally(ex ->
                {
                    System.err.println("Error occurred: " + ex.getMessage());
                    return null;
                }
        ).completeOnTimeout(null, 10, TimeUnit.MINUTES);
    }
}
