package com.github.zlwqa.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.zlwqa.helpers.Attach;
import com.github.zlwqa.pages.RegistrationsPage;
import config.CredentialsConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static java.lang.String.format;

public class TestBase {

    RegistrationsPage registrationsPage = new RegistrationsPage();
    private static CredentialsConfig credentials = ConfigFactory.create(CredentialsConfig.class);
    private static String login = credentials.login();
    private static String password = credentials.password();
    private static String selenoidURL = System.getProperty("remoteURL");
    private static String remoteURL = format("https://%s:%s@%s", login, password, selenoidURL);

    @BeforeAll
    public static void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        Configuration.browserSize = "1920x1080";
        Configuration.remote = remoteURL;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    public void tearDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
