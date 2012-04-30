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
package it.csi.mddtools.svcorch.presentation;

import it.csi.mddtools.servicedef.Operation;
import it.csi.mddtools.servicedef.ServiceDef;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (guigen).
 */

public class OrchestrationFilesLocChooserWizardPage extends WizardPage {

	private ServiceDef sercidefOrchestration;
	private String serviceDefFilePath;

	private Text servicedefFilesContainerText;

	private Button servicedefButtonBrowse;

	
	private Combo comboOperation;

	public Combo getComboOperation() {
		return comboOperation;
	}

	private ISelection selection;
	private Button servicedefButtonLoad;

	//TODO
	private static String TITLE_WIZARD = "Selezionare Modello Principale";
	private static String DESCRIPTION_WIZARD = "Selezionare Modello Principale";
	private static String LABEL_WIZARD = "Modello Principale";

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public OrchestrationFilesLocChooserWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle(TITLE_WIZARD);
		setDescription(DESCRIPTION_WIZARD);
		this.selection = selection;

	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);

		// DEFINIZIONE LAYOUT PAGINA WIZARD
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;	

		//DEFINZIONE LABEL BOX SELEZIONE 
		Label label = new Label(container, SWT.NULL);
		label.setText(LABEL_WIZARD);

		//DEFINZIONE BOX SELEZIONE
		servicedefFilesContainerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		servicedefFilesContainerText.setLayoutData(gd);		
		servicedefFilesContainerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogCommonContainerChanged();

			}
		});

		//DEFINIZIONE PULSANTE BROWSE FOLDER
		servicedefButtonBrowse = new Button(container, SWT.PUSH);
		servicedefButtonBrowse.setText("Browse...");
		servicedefButtonBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowseCommonContainer();
				dialogCommonContainerChanged();
			}
		});
		
		//DEFINIZIONE PULSANTE CARICA MODELLO
		servicedefButtonLoad = new Button(container, SWT.PUSH);
		servicedefButtonLoad.setText("Carica Modello");
		servicedefButtonLoad.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(!validateServiceDefSelected()){
					updateStatus("Selezionare un modello di tipo Servicedef");
					return;
				}
			}
		});

		Label blanck = new Label(container, SWT.NULL);
		Label blanck2 = new Label(container, SWT.NULL);
		Label labelServicedefOperation = new Label(container, SWT.NULL);
		labelServicedefOperation.setText("Operation");

		//DEFINIZIONE COMBO SCELTA OPERATORE
		comboOperation = new Combo(container, SWT.BORDER);
		{
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			comboOperation.setLayoutData(data);
			comboOperation.addModifyListener(new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					
					if(getOperationSelected()==null){
						updateStatus("Selezionare un Operation");
						return;
					}	
					updateStatus(null);
				}
			});
		}

		initialize();
		setControl(container);
	}

	public Operation getOperationSelected() {
		if(comboOperation!=null && comboOperation.getText()!=null && sercidefOrchestration!=null){
			EList<Operation> listaOperation = sercidefOrchestration.getOperations();
			for (Operation operation : listaOperation) {
				if(operation.getName().equalsIgnoreCase(comboOperation.getText()))
					return operation;
			}
		}
		return null;
	}


	public ServiceDef getSercidefOrchestration() {
		return sercidefOrchestration;
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		servicedefFilesContainerText.setText(getServiceDefFilePath());
		if(sercidefOrchestration!=null){
			EList<Operation> listaOperation = sercidefOrchestration.getOperations();
			for (Operation operation : listaOperation) {
				getComboOperation().add(operation.getName());
			}
			getComboOperation().setEnabled(true);
		}
		else
			getComboOperation().setEnabled(false);
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowseCommonContainer() {


		ResourceSelectionDialog dialog = new ResourceSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), "Scegliere file del modello");
		if (dialog.open() == ResourceSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length >0 && result[0] instanceof IFile) {
				String modelFileSelected = ((IFile) result[0]).getFullPath().toString(); 
				servicedefFilesContainerText.setText(modelFileSelected);
			}
		}
	}



	/**
	 * Validazione Selezione Text
	 */

	private void dialogCommonContainerChanged() {

		IResource servicedefRes = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(getServicedefFilesContainerText()));

		if (servicedefRes != null && !(servicedefRes.getType() == IResource.FILE)){
			updateStatus("Selezionare un file valido");
			return;	
		}

		if (!servicedefRes.isAccessible()) {
			updateStatus("Il progetto deve essere writable");
			return;
		}

		if (getServicedefFilesContainerText().length() == 0) {
			updateStatus("Specificare il path del modello principale");
			return;
		}

		else
			updateStatus(null);
	}

	private void updateStatus(String message) {
		if(servicedefFilesContainerText.isEnabled()){
			setErrorMessage(message);
			setPageComplete(message == null);
		}
		else{
			setErrorMessage("");
			setPageComplete(true);
		}
	}


	private boolean validateServiceDefSelected() {
		boolean res = false;

		if(!getServicedefFilesContainerText().equalsIgnoreCase("")){
			try {

				URI modPrincFileURI = URI.createPlatformResourceURI(getServicedefFilesContainerText(), true);
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource modPrincResource = resourceSet.createResource(modPrincFileURI);
				Map<Object, Object> options = new HashMap<Object, Object>();

				modPrincResource.load(options);

				EList emfModPrincContent = (EList)modPrincResource.getContents();
				if ( (emfModPrincContent.get(0)) instanceof ServiceDef){
					sercidefOrchestration = (ServiceDef)(emfModPrincContent.get(0));					
					EList<Operation> listaOperation = sercidefOrchestration.getOperations();
					comboOperation.removeAll();
					for (Operation operation : listaOperation) {
						comboOperation.add(operation.getName());
					}

					comboOperation.setEnabled(true);
					res = true;
				}
				else{
					comboOperation.setEnabled(false);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return res;

	}
	
	public String getServicedefFilesContainerText() {
		return servicedefFilesContainerText != null ? servicedefFilesContainerText
				.getText() : "";
	}

	public void setServiceDefFilePath(String serviceDefFilePath) {
		this.serviceDefFilePath = serviceDefFilePath;
	}

	public String getServiceDefFilePath() {
		return serviceDefFilePath;
	}

	public void setSercidefOrchestration(ServiceDef sercidefOrchestration) {
		this.sercidefOrchestration = sercidefOrchestration;
	}

}