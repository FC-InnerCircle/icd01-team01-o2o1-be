package org.inner.circle.o2oserver.commons.exception

enum class ErrorDetails(
    val message: String
) {
    MEMBER_NOT_FOUND("유저 정보를 찾을 수 없습니다."),
    STORE_NOT_FOUND("가게 정보를 찾을 수 없습니다."),
    ORDER_NOT_FOUND("주문 정보를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND("카테고리 정보를 찾을 수 없습니다."),
    FAILED_CREATE_REVIEW("리뷰 생성에 실패했습니다."),
    ALREADY_EXISTS_MEMBER("이미 존재하는 회원입니다.")
}
