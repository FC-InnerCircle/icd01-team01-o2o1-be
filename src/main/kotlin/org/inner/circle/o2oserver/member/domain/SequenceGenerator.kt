package org.inner.circle.o2oserver.member.domain

interface SequenceGenerator {
    fun generate(sequenceName: String): Long
}
