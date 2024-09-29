package io.learn.reactiveprogramming.productexample;

import io.learn.reactiveprogramming.common.Util;

public class ProductServiceExecution
{
    public static void main(String[] args)
    {
        var productService = new ProductService();
        for(int i = 1; i <= 4; i++)
        {
            productService.getProductName(i).subscribe(Util.subscriber());
        }
        Util.sleepSeconds(5);
    }
}
