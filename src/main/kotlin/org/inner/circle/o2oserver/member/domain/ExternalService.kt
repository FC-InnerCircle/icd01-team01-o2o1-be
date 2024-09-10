package org.inner.circle.o2oserver.member.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ExternalService(
    private val memberOutPort: MemberOutPort,
) : ExternalUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun sendMemberData(member: Member): Boolean {
        return try {
            memberOutPort.sendMemberData(member)
        } catch (e: Exception) {
            log.error("Failed to send member data to external server", e)
            throw RuntimeException("Failed to send member data to external server", e)
        }
    }

    override fun sendMemberInfo(memberDetail: MemberDetail, address: Address): Boolean {
        return try {
            memberOutPort.sendMemberInfo(memberDetail, address)
        } catch (e: Exception) {
            log.error("Failed to send member info to external server", e)
            throw RuntimeException("Failed to send member info to external server", e)
        }
    }

    override fun sendDeleteMemberRequest(id: Long): Boolean {
        return try {
            memberOutPort.sendDeleteMemberRequest(id)
        } catch (e: Exception) {
            log.error("Failed to send delete member request to external server", e)
            throw RuntimeException("Failed to send delete member request to external server", e)
        }
    }

    override fun sendCreateAddressRequest(address: Address): Boolean {
        return try {
            memberOutPort.sendCreateAddressRequest(address)
        } catch (e: Exception) {
            log.error("Failed to send create address request to external server", e)
            throw RuntimeException("Failed to send create address request to external server", e)
        }
    }

    override fun sendDeleteAddressRequest(addressId: Long): Boolean {
        return try {
            memberOutPort.sendDeleteAddressRequest(addressId)
        } catch (e: Exception) {
            log.error("Failed to send delete address request to external server", e)
            throw RuntimeException("Failed to send delete address request to external server", e)
        }
    }
}
