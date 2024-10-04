package io.learn.reactiveprogramming.repeatretry.client;

public class ClientError extends RuntimeException
{
    public ClientError()
    {
        super("bad request");
    }
}
