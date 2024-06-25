package ru.justai.vkbot.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "vk", ignoreUnknownFields = false)
data class VkConfig(
    val vkApiKey: String,
    val vkApiVersion: String,
    val vkApiUrl: String,
    val vkUnreadMessageUrl: String,
    val vkSendMessageUrl: String,
    val messageCount: Int
)