package ar.unrn.tp.config;

import jakarta.annotation.PreDestroy;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class JedisCloseConfiguration {

    JedisPool jedisPool;

    @PreDestroy
    public void closeJedisPool() {
        jedisPool.close();
        log.info("JEDISPOOL cerrado.");
    }
}
