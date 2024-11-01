package ar.unrn.tp.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class JedisConfig {

    @Value("${redis.dominio:localhost}")
    String dominio;

    @Value("${redis.puerto:3310}")
    Integer puerto;

    @Value("${redis.pool.conexiones.total:10}")
    Integer maximasConexionesTotales;

    @Value("${redis.pool.conexiones.inactive.min:2}")
    Integer minimasConexionesInactivas;

    @Value("${redis.pool.conexiones.inactive.max:3}")
    Integer maximasConexionesInactivas;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jpc = new JedisPoolConfig();

        jpc.setMaxTotal(maximasConexionesTotales);
        jpc.setMinIdle(minimasConexionesInactivas);
        jpc.setMaxIdle(maximasConexionesInactivas);

        return jpc;
    }

    @Bean
    public JedisPool jedisPool() {
        JedisPool jp = new JedisPool(jedisPoolConfig(), dominio, puerto);
        log.info("JEDISPOOL {}:{} inicio.", dominio, puerto);
        return jp;
    }
}
