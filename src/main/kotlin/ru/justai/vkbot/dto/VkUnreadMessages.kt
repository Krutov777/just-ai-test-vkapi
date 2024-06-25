package ru.justai.vkbot.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming


data class VkUnreadMessages(
    val response: Response
) {
    data class Response(
        val items: List<Item>
    ) {
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class Item(
            val lastMessage: VkLastMessage
        )
    }
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class VkLastMessage(
    val text: String,
    val peerId: Int
)