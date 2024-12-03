package com.yrgo.services.calls;

import com.yrgo.domain.Call;
import com.yrgo.domain.Action;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import com.yrgo.services.diary.DiaryManagementService;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
@Primary
@Transactional
public class CallHandlingServiceImpl implements CallHandlingService {

    private final CustomerManagementService customerService;
    private final DiaryManagementService diaryService;

    public CallHandlingServiceImpl(CustomerManagementService customerService, DiaryManagementService diaryService) {
        this.customerService = customerService;
        this.diaryService = diaryService;
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        // Anropa CustomerManagementService för att registrera samtalet
        customerService.recordCall(customerId, callDetails);

        // Registrera alla actions i DiaryManagementService
        if (callDetails.getActions() != null) {
            for (Action action : callDetails.getActions()) {
                diaryService.recordAction(action);
            }
        }
    }

    @Override
    public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException {
        // Anropa CustomerManagementService för att registrera samtalet
        customerService.recordCall(customerId, newCall);

        // Registrera varje action från listan
        for (Action action : actions) {
            diaryService.recordAction(action);
        }
    }
}
