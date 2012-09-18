package it.csi.mddtools.servicegen.presentation.common;

import org.eclipse.emf.ecore.EObject;

public class WizardMessage {
	
	public WizardMessage(EObject createdObject,
			String createObjectContainerFullPath, String createObjectFileName) {
		this.createdObject = createdObject;
		this.createObjectContainerFullPath = createObjectContainerFullPath;
		this.createObjectFileName = createObjectFileName;
	}
	
	public WizardMessage(String createObjectContainerFullPath, String createObjectFileName) {
		this.createObjectContainerFullPath = createObjectContainerFullPath;
		this.createObjectFileName = createObjectFileName;
	}
	
	
	private EObject createdObject;

	public EObject getCreatedObject() {
		return createdObject;
	}

	public void setCreatedObject(EObject createdObject) {
		this.createdObject = createdObject;
	}

	private String createObjectContainerFullPath;

	public String getCreateObjectContainerFullPath() {
		return createObjectContainerFullPath;
	}

	public void setCreateObjectContainerFullPath(
			String createObjectContainerFullPath) {
		this.createObjectContainerFullPath = createObjectContainerFullPath;
	}

	private String createObjectFileName;

	public String getCreateObjectFileName() {
		return createObjectFileName;
	}

	public void setCreateObjectFileName(String createObjectFileName) {
		this.createObjectFileName = createObjectFileName;
	}
}
