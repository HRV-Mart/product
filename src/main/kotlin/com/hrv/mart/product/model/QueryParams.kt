package com.hrv.mart.product.model

import java.util.Optional

data class QueryParams(
    val size: Optional<Long>,
    val page: Optional<Long>
) {
    fun getQueryParamForURL(): String{
        var query = ""
        query = addParamInString(query, "size", size)
        query = addParamInString(query, "page", page)
        return query

    }
    private fun <T>addParamInString(currentQuery: String, paramName: String, param: Optional<T>) =
        if (param.isPresent)
            if (currentQuery.isEmpty())
                "?${paramName}=${param.get()}"
            else
                "${currentQuery}&${paramName}=${param.get()}"
        else
            currentQuery
}