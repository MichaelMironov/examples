package ru.lanit.config

import com.typesafe.config.Config
import com.typesafe.config.ConfigBeanFactory
import com.typesafe.config.ConfigFactory

@Synchronized
fun getInstance(): TestConfigFactory {
    return TestConfigFactory()
}

class TestConfigFactory {

    @Volatile
    private var config: Config = ConfigFactory.systemProperties()
        .withFallback(ConfigFactory.systemEnvironment())
        .withFallback(ConfigFactory.parseResources("test.conf"))
    @Volatile
    private lateinit var webConfig: WebConfig

    @Synchronized
    fun getWebConfig(): WebConfig {
        return webConfig ?: ConfigBeanFactory.create(config, WebConfig::class.java)
    }

}