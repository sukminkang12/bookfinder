package com.sukminkang.bookfinder

const val SPLASH_DELAY = 3000L

enum class BookFinderStatus {
    NUMBER_EXCEED,
    NOT_CONTAIN_OPERATOR,
    SINGLE_KEYWORD,
    OR_OPERATOR,
    NOT_OPERATOR,
    NOT_TWO_KEYWORD,
    BLANK_KEYWORD,
    TOO_MANY_OPERATOR,
    DEFAULT_ERROR,
    NETWORK_ERROR,
}