package io.learn.reactiveprogramming.appservicesexample;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Mono;

import java.util.List;

public class BuildUserInfo
{
    record UserInformation(Integer userId, String username, Double balance, List<Order> orders)
    {

    }

    public static void main(String[] args)
    {
        UserService.getAllUsers()
                .flatMap(BuildUserInfo::getUserInfo)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    private static Mono<UserInformation> getUserInfo(User user)
    {
        return Mono.zip(
                        PaymentService.getUserBalance(user.Id()),
                        OrderService.getUserOrders(user.Id()).collectList()
                )
                .map(t -> new UserInformation(user.Id(), user.name(), t.getT1(), t.getT2()));
    }
}
