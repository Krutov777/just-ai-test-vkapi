package ru.justai.vkbot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class WebClientConfig(
    private val vkConfig: VkConfig
) {
    @Bean
    fun vkWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(vkConfig.vkApiUrl)
            .build()
    }
}