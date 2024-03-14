package com.kt.edu.thirdproject.common.jwt;

import java.util.Set;

record ProfileResponse(String username, Set<String> roles) {
}