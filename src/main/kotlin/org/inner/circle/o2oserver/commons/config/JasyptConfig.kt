package org.inner.circle.o2oserver.commons.config

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableEncryptableProperties
class JasyptConfig {
    @Bean("jasyptEncryptor")
    fun stringEncryptor(): StringEncryptor {
        val config = SimpleStringPBEConfig().apply {
            password = System.getenv("JASYPT_ENCRYPTOR_PASSWORD") ?: "o2o-secret-key"
            algorithm = "PBEWithMD5AndDES"
            keyObtentionIterations = 1000
            poolSize = 1
            providerName = "SunJCE"
            stringOutputType = "base64"
        }
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator")

        return PooledPBEStringEncryptor().apply { setConfig(config) }
    }
}
