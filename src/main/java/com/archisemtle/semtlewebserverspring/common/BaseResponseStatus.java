package com.archisemtle.semtlewebserverspring.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum BaseResponseStatus {

    /**
     * 200: 요청 성공
     **/
    SUCCESS(HttpStatus.OK, true, 200, "요청에 성공했습니다."),

    /**
     * 400 : security 에러
     */
    WRONG_JWT_TOKEN(HttpStatus.UNAUTHORIZED, false, 401, "다시 로그인 해주세요"),

    /**
     * 900: 기타 에러
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 900, "Internal server error"),
    NO_EXIST_IMAGE(HttpStatus.NOT_FOUND, false, 901, "존재하지 않는 이미지 입니다"),


    /**
     * 1000 : Order Service Error
     */
    ALREADY_PAID_ORDER_ID(HttpStatus.BAD_REQUEST, false, 1000, "이미 결제된 주문번호입니다"),
    DOSE_NOT_EXIST_PAYMENT(HttpStatus.BAD_REQUEST, false, 1001, "결제내역이 존재하지 않습니다"),
    CANCELED_AMOUNT_EXCEEDED(HttpStatus.BAD_REQUEST, false, 1002, "취소 금액 한도를 초과하였습니다"),
    PAYMENT_DATA_TRANSFER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 1003, "결제 정산 정보 전송에 실패하였습니다"),

    /**
     * 2000 : members Service Error
     */
    // Token, Code
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED ,false, 2001, "토큰이 만료되었습니다."),
    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED , false, 2002, "토큰이 유효하지 않습니다."),
    TOKEN_NULL(HttpStatus.UNAUTHORIZED , false, 2003, "토큰이 존재하지 않습니다."),
    JWT_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR , false, 2004, "토큰 생성에 실패했습니다."),
    JWT_VALID_FAILED(HttpStatus.UNAUTHORIZED , false, 2005, "토큰 검증에 실패했습니다."),
    EXPIRED_AUTH_CODE(HttpStatus.UNAUTHORIZED , false, 2006, "인증번호가 만료되었거나 존재하지 않는 멤버입니다."),
    WRONG_AUTH_CODE(HttpStatus.UNAUTHORIZED , false, 2007, "인증번호가 일치하지 않습니다."),
    LOGOUT_TOKEN(HttpStatus.UNAUTHORIZED , false, 2008, "로그아웃된 토큰입니다."),

    // Members
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, false, 2100, "사용중인 이메일입니다."),
    DUPLICATED_MEMBERS(HttpStatus.CONFLICT, false, 2101, "이미 가입된 멤버입니다."),
    MASSAGE_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 2102, "인증번호 전송에 실패했습니다."),
    MASSAGE_VALID_FAILED(HttpStatus.NOT_FOUND, false, 2103, "인증번호가 일치하지 않습니다."),
    FAILED_TO_LOGIN(HttpStatus.UNAUTHORIZED , false, 2104, "아이디 또는 패스워드를 다시 확인하세요."),
    FAILED_TO_PASSWORD(HttpStatus.UNAUTHORIZED , false, 2104, "비밀번호를 다시 한번 확인 해 주세요."),
    WITHDRAWAL_MEMBERS(HttpStatus.FORBIDDEN, false, 2105, "탈퇴한 회원입니다."),
    NO_EXIST_MEMBERS(HttpStatus.NOT_FOUND, false, 2106, "존재하지 않는 멤버 정보입니다."),
    MEMBERS_STATUS_IS_NOT_FOUND(HttpStatus.NOT_FOUND, false, 2107, "존재하지 않는 멤버 상태입니다."),
    PASSWORD_SAME_FAILED(HttpStatus.BAD_REQUEST, false, 2108, "현재 사용중인 비밀번호 입니다."),
    PASSWORD_CONTAIN_NUM_FAILED(HttpStatus.BAD_REQUEST, false, 2109, "휴대폰 번호를 포함한 비밀번호 입니다."),
    PASSWORD_CONTAIN_EMAIL_FAILED(HttpStatus.BAD_REQUEST, false, 2110, "이메일이 포함된 비밀번호 입니다."),
    NO_EXIST_AUTH(HttpStatus.NOT_FOUND, false, 2106, "인증 정보가 없습니다"),

    DUPLICATE_SNS_MEMBERS(HttpStatus.CONFLICT, false, 2100, "이미 사용중인 SNS 회원입니다."),
    NO_EXIST_SNS_MEMBERS(HttpStatus.NOT_FOUND, false, 2106, "가입되지 않은 SNS 멤버 정보입니다."),



    // Address
    NO_EXIST_ADDRESS(HttpStatus.NOT_FOUND, false, 2300, "존재하지 않는 주소입니다."),

    /**
     * Review
     */
    NO_EXIST_REVIEW(HttpStatus.NOT_FOUND, false, 5001, "존재하지 않는 리뷰 입니다"),


    /**
     * 6000 : Cart & WishProductList Service Error
     */
    NO_DATA(HttpStatus.BAD_REQUEST, false, 6001, "존재하지 않는 정보입니다"),
    ALREADY_ADDED_PRODUCT(HttpStatus.CONFLICT, false, 6002, "이미 장바구니에 존재하는 상품입니다"),
    ALREADY_ADDED_WISH_PRODUCT(HttpStatus.CONFLICT, false, 6003, "이미 좋아요한 상품입니다"),
    INVALID_CART_QUANTITY(HttpStatus.BAD_REQUEST, false, 6004, "수량은 1보다 작을 수 없습니다."),
    ALREADY_ADDED_WISH_BRAND(HttpStatus.CONFLICT, false, 6005, "이미 좋아요한 브랜드입니다"),
    NO_EXIST_WISH_BRAND(HttpStatus.NOT_FOUND, false, 6006, "좋아요에 포함되지 않은 브랜드입니다"),
    NO_EXIST_WISH_PRODUCT(HttpStatus.NOT_FOUND, false, 6007, "좋아요에 포함되지 않은 상품입니다"),
    ALREADY_ADDED_WISH_CATEGORY(HttpStatus.CONFLICT, false, 6008, "이미 좋아요한 카테고리입니다"),
    ALREADY_ADDED_FOLDER(HttpStatus.CONFLICT, false, 6009, "이미 해당 폴더에 좋아요가 존재합니다"),
    NO_EXIST_WISH_FOLDER(HttpStatus.NOT_FOUND, false, 6010, "존재하지 않는 폴더 입니다"),







    /**
     * 7000 : Category Service Error
     */
    NO_EXIST_CATEGORY(HttpStatus.NOT_FOUND, false, 7000, "존재하지 않는 카테고리입니다."),
    NO_TINY_CATEGORY(HttpStatus.NOT_FOUND, false, 7001, "tinycategory가 존재하지 않는 카테고리입니다"),


    /**
     * 8000 : Product Service Error
     */
    // Product
    NO_PRODUCT(HttpStatus.NOT_FOUND, false,8001, "상품이 존재하지 않습니다"),
    NO_EXISTING_PRODUCT(HttpStatus.NOT_FOUND, false, 8002, "해당 상품이 갖고있지 않는 옵션입니다"),
    NO_SELECTED_OPTION_PRODUCT(HttpStatus.NOT_FOUND, false, 8003, "해당 옵션 선택이 완료된 상품을 조회할 수 없습니다"),
    OUT_OF_STOCK_PRODUCT(HttpStatus.NOT_FOUND, false, 8004, "재고가 없는 상품입니다"),
    INVALID_STOCK_QUANTITY(HttpStatus.BAD_REQUEST, false, 8005, "유효하지 않은 재고 수량입니다"),
    NO_VIEW_HISTORY(HttpStatus.NOT_FOUND, false, 8006, "해당 유저의 최근 상품 조회 기록이 없습니다"),
    NO_EXIST_VIEW_HISTORY_PRODUCT(HttpStatus.NOT_FOUND, false, 8007, "해당 유저의 최근 본 상품에는 없는 상품입니다"),
    NO_SEARCH_PRODUCT(HttpStatus.NOT_FOUND, false, 8008, "검색 결과가 없습니다"),
    NO_EXISTING_PRODUCT_CATEGORY(HttpStatus.NOT_FOUND, false, 8009, "해당 상품은 카테고리가 존재하지 않습니다"),
    NO_EXIST_TOTAL(HttpStatus.NOT_FOUND, false, 8009, "존재하지 않는 집계 정보입니다"),

    /**
     * 9000 : Brand Service Error
     */
    NO_EXIST_BRAND(HttpStatus.NOT_FOUND, false,9001, "브랜드가 존재하지 않습니다"),
    NO_EXIST_BRAND_PRODUCT(HttpStatus.NOT_FOUND, false, 9002, "브랜드에 해당하는 상품이 존재하지 않습니다"),


    /**
     * 10000 : Order Service Error
     */
    NO_EXIST_ORDER(HttpStatus.NOT_FOUND, false, 10000, "존재하지 않는 주문입니다"),
    NO_ORDER_HISTORY(HttpStatus.NOT_FOUND, false, 10001, "주문내역이 존재하지 않습니다"),
    NO_EXIST_ORDER_PRODUCT(HttpStatus.NOT_FOUND, false, 10002, "존재하지 않는 주문상품입니다"),
    ALREADY_CANCEL_ORDER(HttpStatus.BAD_REQUEST, false, 10003, "이미 취소된 주문입니다"),
    NOT_ENOUGH_STOCK(HttpStatus.BAD_REQUEST, false, 10004, "재고가 부족합니다");

    private final HttpStatusCode httpStatusCode;
    private final boolean isSuccess;
    private final int code;
    private final String message;

}
