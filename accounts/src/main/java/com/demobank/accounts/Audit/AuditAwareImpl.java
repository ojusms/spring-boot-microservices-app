package com.demobank.accounts.Audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware {
    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor.
     */
    @Override
    public Optional getCurrentAuditor() {
        /* Accounts Microservice is the auditor. This will be populated in the CREATED and UPDATED metadata tables
         by Spring Data
         */
        return Optional.of("ACCOUNTS_MS");
    }
}
