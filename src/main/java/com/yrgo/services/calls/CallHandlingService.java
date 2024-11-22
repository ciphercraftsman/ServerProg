package com.yrgo.services.calls;

import com.yrgo.domain.Call;
import com.yrgo.domain.Action;
import com.yrgo.services.customers.CustomerNotFoundException;

import java.util.Collection;

public interface CallHandlingService {
	void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException;

	void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException;
}
