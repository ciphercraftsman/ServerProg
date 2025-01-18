package com.yrgo.services.calls;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yrgo.domain.Action;
import com.yrgo.domain.Call;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import com.yrgo.services.diary.DiaryManagementService;

import java.util.Collection;

/**
 * Main service for customer calls. Dependent on these two services:
 *
 * @see CustomerManagementService
 * @see DiaryManagementService
 */
@Transactional
@Service
public class CallHandlingServiceImpl implements CallHandlingService {

    private final CustomerManagementService cms;
    private final DiaryManagementService dms;

    // Constructor to inject dependencies
    public CallHandlingServiceImpl(CustomerManagementService cms, DiaryManagementService dms) {
        this.cms = cms;
        this.dms = dms;
    }

    @Override
    public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException {
        // Record the call for the customer
        cms.recordCall(customerId, newCall);

        // Record actions in the diary management service
        actions.forEach(dms::recordAction);
    }
}
