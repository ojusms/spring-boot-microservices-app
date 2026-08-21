package com.DemoBank.Cards.Audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<String> {
    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor.
     */
    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("CARDS_MS");
    }
}
