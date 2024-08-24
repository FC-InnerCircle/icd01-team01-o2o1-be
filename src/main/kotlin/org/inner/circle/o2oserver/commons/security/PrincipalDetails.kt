package org.inner.circle.o2oserver.commons.security

import org.inner.circle.o2oserver.member.domain.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class PrincipalDetails(
    private val member: Member,
    private val attributes: MutableMap<String, Any>,
    private val memberCheck: Boolean
) : UserDetails {
    fun getName(): String {
        return attributes["email"] as String
    }

    fun getAttributes(): MutableMap<String, Any> {
        return attributes.toMutableMap()
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String {
//        return member.password!!
        return ""
    }

    override fun getUsername(): String {
//        return member.email!!
        return ""
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    /**
     * @return memberCheck : 회원가입 여부
     * 기존의 회원이면 true, 신규 회원이면 false
     * 신규 회원이라면 프로필 정보를 입력하는 화면으로 이동
     */
    override fun isCredentialsNonExpired(): Boolean {
        return memberCheck
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
