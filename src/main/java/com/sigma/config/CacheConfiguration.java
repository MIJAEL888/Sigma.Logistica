package com.sigma.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.sigma.domain.Equipo.class.getName());
            createCache(cm, com.sigma.domain.TipoEquipo.class.getName());
            createCache(cm, com.sigma.domain.TipoEquipo.class.getName() + ".modelos");
            createCache(cm, com.sigma.domain.Modelo.class.getName());
            createCache(cm, com.sigma.domain.Modelo.class.getName() + ".equipos");
            createCache(cm, com.sigma.domain.Marca.class.getName());
            createCache(cm, com.sigma.domain.Marca.class.getName() + ".modelos");
            createCache(cm, com.sigma.domain.Laboratorio.class.getName());
            createCache(cm, com.sigma.domain.Laboratorio.class.getName() + ".laboratorios");
            createCache(cm, com.sigma.domain.Monitorista.class.getName());
            createCache(cm, com.sigma.domain.Monitorista.class.getName() + ".tipoSeguros");
            createCache(cm, com.sigma.domain.TipoSeguro.class.getName());
            createCache(cm, com.sigma.domain.TipoSeguro.class.getName() + ".monitoristas");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
