package io.learn.reactiveprogramming.repeatretry.client;

public class ServerError extends RuntimeException
{
    public ServerError() {
        super("Server Error");
    }
}
