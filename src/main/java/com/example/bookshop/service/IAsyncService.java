package com.example.bookshop.service;

import java.util.concurrent.CompletableFuture;

public interface IAsyncService {
     CompletableFuture<Void> processDataAsync();
}
