/**
 * <copyright>
 * (C) Copyright 2011 CSI-PIEMONTE;

 * Concesso in licenza a norma dell'EUPL, esclusivamente versione 1.1;
 * Non e' possibile utilizzare l'opera salvo nel rispetto della Licenza.
 * E' possibile ottenere una copia della Licenza al seguente indirizzo:
 *
 * http://www.eupl.it/opensource/eupl-1-1
 *
 * Salvo diversamente indicato dalla legge applicabile o concordato per 
 * iscritto, il software distribuito secondo i termini della Licenza e' 
 * distribuito "TAL QUALE", SENZA GARANZIE O CONDIZIONI DI ALCUN TIPO,
 * esplicite o implicite.
 * Si veda la Licenza per la lingua specifica che disciplina le autorizzazioni
 * e le limitazioni secondo i termini della Licenza.
 * </copyright>
 *
 * $Id$
 */
package it.csi.mddtools.servicegen.presentation;

import it.csi.mddtools.appresources.ResourceSet;
import it.csi.mddtools.appresources.presentation.AppresourcesModelWizard;
import it.csi.mddtools.servicegen.presentation.common.WizardContext;
import it.csi.mddtools.servicegen.presentation.common.WizardMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (guigen).
 */

public class ResourceSetFilesLocChooserWizardPage extends WizardPage {

	private IStructuredSelection selection;
	private IWorkbench workbench;
	private Button newButton;
	private Button browseButton;
	private ResourceSet resourceSet;
	private Text resourceSetFileContainerText;
	private Button selectResourceSetCheck;

	private WizardContext wizardContext;

	public WizardContext getWizardContext() {
		return wizardContext;
	}

	public void setWizardContext(WizardContext wizardContext) {
		this.wizardContext = wizardContext;
	}

	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	public boolean associaResourceSet() {
		return selectResourceSetCheck != null ? selectResourceSetCheck
				.getSelection() : false;
	}

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param workbench
	 * 
	 * @param pageName
	 */
	public ResourceSetFilesLocChooserWizardPage(IStructuredSelection selection,
			IWorkbench workbench) {
		super("wizardPage");
		this.selection = selection;
		this.workbench = workbench;

		setTitle("Associa Resource Set");
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);

		// DEFINIZIONE LAYOUT PAGINA WIZARD
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;


		selectResourceSetCheck= new Button(container, SWT.CHECK | SWT.LEFT);
		selectResourceSetCheck.setSelection(false);
		selectResourceSetCheck.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				newButton.setEnabled(selectResourceSetCheck.getSelection());
				browseButton.setEnabled(selectResourceSetCheck.getSelection());
				if(!selectResourceSetCheck.getSelection())
					resourceSetFileContainerText.setText("");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Label labelCheck = new Label(container, SWT.NULL);
		labelCheck.setText("Associa un ResourceSet");


		Label labelRS = new Label(container, SWT.NULL);
		labelRS.setText("Resource Set");

		resourceSetFileContainerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		resourceSetFileContainerText.setLayoutData(gd);		
		resourceSetFileContainerText.setEnabled(false);

	
		newButton = new Button(container, SWT.PUSH);
		newButton.setText("Crea Nuovo ResourceSet");
		newButton.addSelectionListener(new SelectionListener() {

			

			@Override
			public void widgetSelected(SelectionEvent e) {
				setMessage(null);
				IWizard wiz = wizardContext.getWizard();
				if(wiz instanceof ServicegenModelWizard) {
					ServicegenModelWizard servicegenModelWizard =(ServicegenModelWizard)wiz;
					String fileName = servicegenModelWizard.newFileCreationPage.getFileName();
					String[] arg= fileName.split(servicegenModelWizard.FILE_EXTENSIONS.get(0));
					String containerFullPath = servicegenModelWizard.newFileCreationPage.getContainerFullPath().toPortableString();
					WizardMessage wizardMessageInput = new WizardMessage(containerFullPath, arg[0]);
					AppresourcesModelWizard amw = new AppresourcesModelWizard();
					amw.init(workbench, selection);				
					amw.setWizardMessage(wizardMessageInput);
					WizardDialog dialog = new WizardDialog(getShell(),amw);
					dialog.open();
					
					WizardMessage wizardMessageOutput = amw.getWizardMessage();
					resourceSet = (ResourceSet) wizardMessageOutput.getCreatedObject();		
					
					String fullPath = wizardMessageOutput.getCreateObjectContainerFullPath() +"/"+wizardMessageOutput.getCreateObjectFileName();
					resourceSetFileContainerText.setText(fullPath+servicegenModelWizard.FILE_EXTENSIONS.get(0));
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		browseButton = new Button(container, SWT.PUSH);
		browseButton.setText("Carica ResourceSet Esistente");
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowseResourceSet();
				loadResourceSet();
			}

			

		});
		newButton.setEnabled(false);
		browseButton.setEnabled(false);

		setControl(container);
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowseResourceSet() {

		ResourceSelectionDialog dialog = new ResourceSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(),
				"Scegliere file del modello");
		if (dialog.open() == ResourceSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length > 0 && result[0] instanceof IFile) {
				String modelFileSelected = ((IFile) result[0]).getFullPath()
						.toString();
				resourceSetFileContainerText.setText(modelFileSelected);
			}
		}
	}

	/**
	 * Carica ResourceSet
	 * 
	 */
	private void loadResourceSet() {

		if (resourceSetFileContainerText != null
				&& !resourceSetFileContainerText.getText().equalsIgnoreCase("")) {
			try {
				URI rsFileURI = URI.createPlatformResourceURI(
						resourceSetFileContainerText.getText(), true);
				org.eclipse.emf.ecore.resource.ResourceSet resourceSetImpl = new ResourceSetImpl();
				Resource rsResource = resourceSetImpl.createResource(rsFileURI);
				Map<Object, Object> options = new HashMap<Object, Object>();

				rsResource.load(options);

				EList emfRSContent = (EList) rsResource.getContents();

				// TEST TIPO RESOURCES SELEZIONATO
				if ((emfRSContent.get(0)) instanceof ResourceSet) {
					resourceSet = (ResourceSet) (emfRSContent.get(0));
				}

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}