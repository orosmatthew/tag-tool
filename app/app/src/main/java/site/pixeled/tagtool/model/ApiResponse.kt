package site.pixeled.tagtool.model

data class ApiResponse<T>(val data: T, val status: Int?, val error: String?)