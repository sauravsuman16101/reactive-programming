package io.learn.reactiveprogramming.fileservice;

import io.learn.reactiveprogramming.common.Util;

public class FileIO
{
    public static void main(String[] args)
    {
        var fileService = new FileServiceImpl();
        fileService.write("file.txt", "Hello World").subscribe(Util.subscriber());
        fileService.read("file.txt").subscribe(Util.subscriber());
        fileService.delete("file.txt").subscribe();
    }
}
