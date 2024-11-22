package com.yrgo.services.diary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yrgo.domain.Action;

public class DiaryManagementServiceMockImpl implements DiaryManagementService {

	private Set<Action> allActions = new HashSet<>();

	@Override
	public void recordAction(Action action) {
		// Lägg till action till samlingen allActions
		allActions.add(action);
		System.out.println("Action recorded: " + action.getDetails());
	}

	@Override
	public List<Action> getAllIncompleteActions(String requiredUser) {
		List<Action> incompleteActions = new ArrayList<>();

		// Loopa igenom alla actions och filtrera efter villkor
		for (Action action : allActions) {
			if (action.getOwningUser().equals(requiredUser) && !action.isComplete()) {
				incompleteActions.add(action);
			}
		}

		return incompleteActions;
	}
}
