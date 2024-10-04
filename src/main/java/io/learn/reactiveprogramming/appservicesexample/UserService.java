package io.learn.reactiveprogramming.appservicesexample;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public class UserService
{
    private static final Map<String, Integer> userTable = Map.of(
            "John", 1,
            "Jane", 2,
            "Jack", 3
    );

    public static Flux<User> getAllUsers()
    {
        return Flux.fromIterable(userTable.entrySet())
                .map(entry -> new User(entry.getValue(), entry.getKey()));
    }

    public static Mono<Integer> getUserId(String name)
    {
        return Mono.fromSupplier(() -> userTable.get(name));
    }
}
