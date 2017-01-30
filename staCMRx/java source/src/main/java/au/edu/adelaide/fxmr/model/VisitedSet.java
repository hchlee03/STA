package au.edu.adelaide.fxmr.model;

import java.util.ArrayList;
import java.util.HashSet;

public class VisitedSet {
	private HashSet<HashableAdjSet> store = new HashSet<>();

	public boolean add(CMRxTrial current) {
		HashableAdjSet newAdjeSet = new HashableAdjSet(current);
		return store.add(newAdjeSet);
	}

	public boolean contains(CMRxTrial newTrial) {
		return store.contains(newTrial.gethAdjSet());
	}

	public int size() {
		return store.size();
	}

	public void addAll(ArrayList<CMRxTrial> tmpTrials) {
		for (CMRxTrial t : tmpTrials)
			add(t);
	}
}
