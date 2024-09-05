package org.inner.circle.o2oserver.member.presentation.api

import org.inner.circle.o2oserver.member.application.MemberInfoFacade
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberInfoController(
    private val memberInfoFacade: MemberInfoFacade,
) {
}
