package upsimulator.recognizer.actions;

import java.util.List;

import upsimulator.interfaces.Membrane;
import upsimulator.interfaces.Rule;

/**
 * Add a set of rule to one membrane
 * 
 * @author quan
 *
 */
public class RuleSetDeclareAction extends RecognizerAction {

	private String ruleSetName, prefix;
	private Membrane to;// add ruleset to 'to'

	public RuleSetDeclareAction(String ruleSetName, String prefix, Membrane to) {
		super();
		this.ruleSetName = ruleSetName;
		this.prefix = prefix;
		this.to = to;
	}

	@Override
	public void init() {
		addUnreadyCount(to);
	}

	@Override
	public boolean ready() {
		if (Rule.getRuleSet(ruleSetName) == null)
			throw new RuntimeException("No such rule set：" + ruleSetName);
		return true;
	}

	@Override
	public void doAction() {
		List<Rule> targetRuleSet = Rule.getRuleSet(ruleSetName);
		for (int i = 0; i < targetRuleSet.size(); i++) {
			Rule rule = targetRuleSet.get(i).deepClone();
			if (prefix != null)
				rule.setName(prefix + rule.getName());
			to.addRule(rule);
		}
		minusUnreadyCount(to);
	}

	@Override
	public String toString() {
		return "RulePrioritySetAction : add ruleset with name prefix to " + to.getNameDim() + ", where name's prefix= " + prefix;
	}
}
