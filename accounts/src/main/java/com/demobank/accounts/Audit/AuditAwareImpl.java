package com.demobank.accounts.Audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
// annotate it as a component and specify the bean name, same will be referenced in the main app
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {
    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        /* Accounts Microservice is the auditor. This will be populated in the CREATED and UPDATED metadata tables
         by Spring Data
         */
        return Optional.of("ACCOUNTS_MS");
    }
}
