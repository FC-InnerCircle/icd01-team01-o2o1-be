package org.inner.circle.o2oserver

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class O2oServerApplication

fun main(args: Array<String>) {
    runApplication<O2oServerApplication>(*args)
}
