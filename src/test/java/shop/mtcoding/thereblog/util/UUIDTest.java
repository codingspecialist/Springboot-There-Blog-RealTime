package shop.mtcoding.thereblog.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTest {

    @Test
    public void uuid_test(){
        // 1b1fd257-e5f7-4f54-a48c-5f260e721d33_person.png
        // 1047da6f-ca49-4380-a772-319aedc1bc02
        // e0d42f2c-6e68-41a8-8b83-dd43cc9aa92d
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }
}
