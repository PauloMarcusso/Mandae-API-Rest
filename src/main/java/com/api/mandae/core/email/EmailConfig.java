package com.api.mandae.core.email;

import com.api.mandae.domain.service.EnvioEmailService;
import com.api.mandae.infrastructure.service.email.FakeEnvioEmailService;
import com.api.mandae.infrastructure.service.email.SandboxEmailService;
import com.api.mandae.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandboxEmailService();
            default:
                return null;
        }

    }
}
