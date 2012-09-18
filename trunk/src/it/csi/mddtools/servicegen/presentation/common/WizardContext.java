package it.csi.mddtools.servicegen.presentation.common;

import org.eclipse.jface.wizard.IWizard;

public class WizardContext {

	public WizardContext(IWizard wizard) {
		this.wizard = wizard;
	}

	private IWizard wizard;

	public IWizard getWizard() {
		return wizard;
	}

	public void setWizard(IWizard wizard) {
		this.wizard = wizard;
	}

}
