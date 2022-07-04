package com.sdei.domaindata.data.remote.dto

import com.sdei.domaindata.domain.model.MemeData

data class MemeDataDto(
    val categoryId: String,
    val contractAddress: String,
    val contractSymbol: String,
    val createdAt: String,
    val createdBy: Any,
    val deletedAt: Any,
    val deletedBy: Any,
    val description: String,
    val editionCount: String,
    val floorPrice: String,
    val id: String,
    val isDeleted: Boolean,
    val isPublished: String,
    val purchaseType: String,
    val slug: String,
    val status: Boolean,
    val title: String,
    val updatedAt: String,
    val updatedBy: Any,
    val userId: String
){
   fun toMemeData(): MemeData {
      return MemeData(
         title = title,
         description = description,
         floorPrice = floorPrice,
         id = id
      )
   }
}