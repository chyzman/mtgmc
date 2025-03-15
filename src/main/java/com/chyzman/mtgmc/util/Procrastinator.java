package com.chyzman.mtgmc.util;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.function.*;

public class Procrastinator<T> implements CompletionStage<T> {
    private final CompletableFuture<T> future;

    public static <T> Procrastinator<T> procrastinated(T value) {
        return new Procrastinator<>(CompletableFuture.completedFuture(value));
    }

    public static <U> Procrastinator<U> supplyAsync(Supplier<U> supplier) {
        return new Procrastinator<>(CompletableFuture.supplyAsync(supplier));
    }


    public static <U> Procrastinator<U> supplyAsync(Supplier<U> supplier, Executor executor) {
        return new Procrastinator<>(CompletableFuture.supplyAsync(supplier, executor));
    }

    public Procrastinator() {
        this.future = new CompletableFuture<>();
    }

    public Procrastinator(CompletableFuture<T> future) {
        this.future = future;
    }

    @Override
    public <U> Procrastinator<U> thenApply(Function<? super T, ? extends U> fn) {
        return new Procrastinator<>(future.thenApply(fn));
    }

    @Override
    public <U> Procrastinator<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
        return new Procrastinator<>(future.thenApplyAsync(fn));
    }

    @Override
    public <U> Procrastinator<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor) {
        return new Procrastinator<>(future.thenApplyAsync(fn, executor));
    }

    @Override
    public Procrastinator<Void> thenAccept(Consumer<? super T> action) {
        return new Procrastinator<>(future.thenAccept(action));
    }

    @Override
    public Procrastinator<Void> thenAcceptAsync(Consumer<? super T> action) {
        return new Procrastinator<>(future.thenAcceptAsync(action));
    }

    @Override
    public Procrastinator<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor) {
        return new Procrastinator<>(future.thenAcceptAsync(action, executor));
    }

    @Override
    public Procrastinator<Void> thenRun(Runnable action) {
        return new Procrastinator<>(future.thenRun(action));
    }

    @Override
    public Procrastinator<Void> thenRunAsync(Runnable action) {
        return new Procrastinator<>(future.thenRunAsync(action));
    }

    @Override
    public Procrastinator<Void> thenRunAsync(Runnable action, Executor executor) {
        return new Procrastinator<>(future.thenRunAsync(action, executor));
    }

    @Override
    public <U, V> Procrastinator<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return new Procrastinator<>(future.thenCombine(other, fn));
    }

    @Override
    public <U, V> Procrastinator<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return new Procrastinator<>(future.thenCombineAsync(other, fn));
    }

    @Override
    public <U, V> Procrastinator<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn, Executor executor) {
        return new Procrastinator<>(future.thenCombineAsync(other, fn, executor));
    }

    @Override
    public <U> Procrastinator<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return new Procrastinator<>(future.thenAcceptBoth(other, action));
    }

    @Override
    public <U> Procrastinator<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return new Procrastinator<>(future.thenAcceptBothAsync(other, action));
    }

    @Override
    public <U> Procrastinator<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor) {
        return new Procrastinator<>(future.thenAcceptBothAsync(other, action, executor));
    }

    @Override
    public Procrastinator<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
        return new Procrastinator<>(future.runAfterBoth(other, action));
    }

    @Override
    public Procrastinator<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
        return new Procrastinator<>(future.runAfterBothAsync(other, action));
    }

    @Override
    public Procrastinator<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return new Procrastinator<>(future.runAfterBothAsync(other, action, executor));
    }

    @Override
    public <U> Procrastinator<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return new Procrastinator<>(future.applyToEither(other, fn));
    }

    @Override
    public <U> Procrastinator<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return new Procrastinator<>(future.applyToEitherAsync(other, fn));
    }

    @Override
    public <U> Procrastinator<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor) {
        return new Procrastinator<>(future.applyToEitherAsync(other, fn, executor));
    }

    @Override
    public Procrastinator<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return new Procrastinator<>(future.acceptEither(other, action));
    }

    @Override
    public Procrastinator<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return new Procrastinator<>(future.acceptEitherAsync(other, action));
    }

    @Override
    public Procrastinator<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor) {
        return new Procrastinator<>(future.acceptEitherAsync(other, action, executor));
    }

    @Override
    public Procrastinator<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
        return new Procrastinator<>(future.runAfterEither(other, action));
    }

    @Override
    public Procrastinator<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
        return new Procrastinator<>(future.runAfterEitherAsync(other, action));
    }

    @Override
    public Procrastinator<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return new Procrastinator<>(future.runAfterEitherAsync(other, action, executor));
    }

    @Override
    public <U> Procrastinator<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
        return new Procrastinator<>(future.thenCompose(fn));
    }

    @Override
    public <U> Procrastinator<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) {
        return new Procrastinator<>(future.thenComposeAsync(fn));
    }

    @Override
    public <U> Procrastinator<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) {
        return new Procrastinator<>(future.thenComposeAsync(fn, executor));
    }

    @Override
    public <U> Procrastinator<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) {
        return new Procrastinator<>(future.handle(fn));
    }

    @Override
    public <U> Procrastinator<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn) {
        return new Procrastinator<>(future.handleAsync(fn));
    }

    @Override
    public <U> Procrastinator<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
        return new Procrastinator<>(future.handleAsync(fn, executor));
    }

    @Override
    public Procrastinator<T> whenComplete(BiConsumer<? super T, ? super Throwable> action) {
        return new Procrastinator<>(future.whenComplete(action));
    }

    @Override
    public Procrastinator<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action) {
        return new Procrastinator<>(future.whenCompleteAsync(action));
    }

    @Override
    public Procrastinator<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor) {
        return new Procrastinator<>(future.whenCompleteAsync(action, executor));
    }

    @Override
    public Procrastinator<T> exceptionally(Function<Throwable, ? extends T> fn) {
        return new Procrastinator<>(future.exceptionally(fn));
    }

    @Override
    public Procrastinator<T> exceptionallyAsync(Function<Throwable, ? extends T> fn) {
        return new Procrastinator<>(future.exceptionallyAsync(fn));
    }

    @Override
    public Procrastinator<T> exceptionallyAsync(Function<Throwable, ? extends T> fn, Executor executor) {
        return new Procrastinator<>(future.exceptionallyAsync(fn, executor));
    }

    @Override
    public Procrastinator<T> exceptionallyCompose(Function<Throwable, ? extends CompletionStage<T>> fn) {
        return new Procrastinator<>(future.exceptionallyCompose(fn));
    }

    @Override
    public Procrastinator<T> exceptionallyComposeAsync(Function<Throwable, ? extends CompletionStage<T>> fn) {
        return new Procrastinator<>(future.exceptionallyComposeAsync(fn));
    }

    @Override
    public Procrastinator<T> exceptionallyComposeAsync(Function<Throwable, ? extends CompletionStage<T>> fn, Executor executor) {
        return new Procrastinator<>(future.exceptionallyComposeAsync(fn, executor));
    }

    @Override
    public CompletableFuture<T> toCompletableFuture() {
        return future;
    }

    public Procrastinator<T> thenExcept(Consumer<Throwable> action) {
        return new Procrastinator<>(future.handle((result, throwable) -> {
            if (throwable != null) action.accept(throwable);
            return result;
        }));
    }

    public T getNow(T valueIfAbsent) {
        return future.getNow(valueIfAbsent);
    }
}
