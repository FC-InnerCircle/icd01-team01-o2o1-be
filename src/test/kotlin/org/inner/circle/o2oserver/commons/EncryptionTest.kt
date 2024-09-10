package org.inner.circle.o2oserver.commons

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.iv.RandomIvGenerator
import org.junit.jupiter.api.Test

class EncryptionTest {
    @Test
    fun encryptValues() {
        val username = "o2oadmin"
        val password = "inner123!"

        println("암호화된 사용자명: ${encrypt(username)}")
        println("암호화된 비밀번호: ${encrypt(password)}")
    }

    private fun encrypt(value: String): String {
        val key = "o2o-secret-key"
        val apply = StandardPBEStringEncryptor().apply {
            setPassword(key)
            setAlgorithm("PBEWithMD5AndDES")
            setIvGenerator(RandomIvGenerator())
        }
        return apply.encrypt(value)
    }
}
