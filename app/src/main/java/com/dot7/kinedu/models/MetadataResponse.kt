package com.dot7.kinedu.models

data class MetadataResponse(
    var page: Int = 0,
    var perPage: Int = 0,
    var totalPage:Int = 0,
    var total: Int = 0)


/*
page:1,
      per_page:20,
      total_pages:2,
      total:23
 */