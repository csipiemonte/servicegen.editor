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
import it.csi.mddtools.servicedef.SrvTypeEnum;
import it.csi.mddtools.servicegen.SOABEModel;
import it.csi.mddtools.servicegen.ServiceImpl;

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

	private ISelection selection;
	
	// TODO definire label
	private static String TITLE_WIZARD = "Selezionare Modello Principale";
	private static String DESCRIPTION_WIZARD = "Selezionare Modello Principale";
	private static String LABEL_WIZARD = "Modello Principale";
	
	private Text fileSOABModelContainerText;
	
	private Button modelButtonBrowse;
	
	private Button modelButtonLoad;

	private Combo serviceDefCombo;

	private Combo operationCombo;

	private SOABEModel modelloPrincipale;
	
	private ServiceDef serviceDefOrch;
	
	private Operation operationOrch;

	
	public ServiceDef getServiceDefOrch() {
		return serviceDefOrch;
	}
	

	public Operation getOperationOrch() {
		return operationOrch;
	}

	public SOABEModel getModelloPrincipale() {
		return modelloPrincipale;
	}


	public void setOperationOrch(Operation operationOrch) {
		this.operationOrch = operationOrch;
	}



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

		// DEFINZIONE LABEL BOX SELEZIONE
		Label label = new Label(container, SWT.NULL);
		label.setText(LABEL_WIZARD);

		// DEFINZIONE BOX SELEZIONE
		fileSOABModelContainerText = new Text(container, SWT.BORDER
				| SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		fileSOABModelContainerText.setLayoutData(gd);
		fileSOABModelContainerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setMessage(null);

			}
		});

		// DEFINIZIONE PULSANTE BROWSE FOLDER
		modelButtonBrowse = new Button(container, SWT.PUSH);
		modelButtonBrowse.setText("Browse...");
		modelButtonBrowse.addSelectionListener(validatorBrowse);

		// DEFINIZIONE PULSANTE CARICA MODELLO
		modelButtonLoad = new Button(container, SWT.PUSH);
		modelButtonLoad.setText("Carica Modello");
		modelButtonLoad.addSelectionListener(validatorLoad);

		//TODO si può fare in altro modo???
		//SPAZI
		Label blanck = new Label(container, SWT.NULL);
		Label blanck2 = new Label(container, SWT.NULL);
		
		Label labelServicedef = new Label(container, SWT.NULL);
		labelServicedef.setText("Servizio di Orchestrazione");

		serviceDefCombo = new Combo(container, SWT.BORDER);
		{
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			serviceDefCombo.setLayoutData(data);
			serviceDefCombo.addModifyListener(serviceDefComboValidator);
		}

		Label blanck3 = new Label(container, SWT.NULL);

		Label labelServicedefOperation = new Label(container, SWT.NULL);
		labelServicedefOperation.setText("Operation");

		// DEFINIZIONE COMBO SCELTA OPERATORE
		operationCombo = new Combo(container, SWT.BORDER);
		{
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			operationCombo.setLayoutData(data);
			operationCombo.addModifyListener(operationComboValidator);
		}

		initialize();
		setControl(container);
	}


	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		
		//IMPOSTO VALORI DI DEFAULT
		setPageComplete(false);
		fileSOABModelContainerText.setText(getFilePathSOABModelSelect());
		modelButtonLoad.setEnabled(false);
		serviceDefCombo.setEnabled(false);
		operationCombo.setEnabled(false);
		
		// Se SOABEModel può essere abilitato il load
		if (validaModelloSelezionato()) 
			modelButtonLoad.setEnabled(true);
	}

	private void abilitaAssociazioneModello(){
		setMessage(null);
		modelButtonLoad.setEnabled(true);
		
	}
	
	public String getFileSOABModelContainerText() {
		return fileSOABModelContainerText != null ? fileSOABModelContainerText
				.getText() : "";
	}

	// BROWSE SELECTION
	protected SelectionAdapter validatorBrowse = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			
			// Default Not Enabled
			modelButtonLoad.setEnabled(false);
			serviceDefCombo.setEnabled(false);
			operationCombo.setEnabled(false);
			
			// Browse
			handleBrowseCommonContainer();
			
			// Valida Selezione
			IResource servicedefRes = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(new Path(getFileSOABModelContainerText()));

			if (!servicedefRes.isAccessible()) {
				setMessage("Il progetto deve essere writable");
				return;
			}

			if (!validaModelloSelezionato()) {
				setMessage("Selezionare un modello di tipo SOABEModel");
				return;
			}
			//SE non ci sono stati KO
			abilitaAssociazioneModello();
			
		}

	};

	
	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowseCommonContainer() {

		ResourceSelectionDialog dialog = new ResourceSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(),
				"Scegliere file del modello");
		if (dialog.open() == ResourceSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length > 0 && result[0] instanceof IFile) {
				String modelFileSelected = ((IFile) result[0]).getFullPath()
						.toString();
				fileSOABModelContainerText.setText(modelFileSelected);
			}
		}
	}

	


	/**
	 * Verifica se modello sezionato è di tipo SOABEModel return 'true'
	 * altrimenti 'false' 
	 * @return boolean
	 */
	
	private boolean validaModelloSelezionato() {
		boolean res = false;
		
		if (!getFileSOABModelContainerText().equalsIgnoreCase("")) {
			//CLEAN COMBO 
			serviceDefCombo.removeAll();
			operationCombo.removeAll();
			
			//LOAD SOABEModel
			modelloPrincipale = getSOABEModelByFullPath(getFileSOABModelContainerText());
			
			//TEST 
			if(modelloPrincipale!=null)
				res = true;
		}

		return res;
		
	}
	
	
	// LOAD SELECTION
	protected SelectionAdapter validatorLoad = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			if(loadServiceDefCombo()){
				serviceDefCombo.setEnabled(true);
				setMessage(null);
			}
			else
				setMessage("Non è possibile caricare Interfacce di Servizio di tipo Orchestrato valide per il Modello Selezionato");
			
		}
	};

	/**
	 * Dato il SOABEModel --> load ServiceDef di type orch
	 * Se non è possibile caricare la combo return false
	 * @return boolean
	 */
	private boolean loadServiceDefCombo() {

		boolean res = false;
		if(modelloPrincipale!=null){
			for (ServiceImpl serviceImplTmp : modelloPrincipale
					.getServiceimplementations()) {
				ServiceDef serviceDefTmp = serviceImplTmp.getProvides();
				if (serviceDefTmp.getServiceType().getName()
						.equalsIgnoreCase(SrvTypeEnum.ORCH.getName())) {
					serviceDefCombo.add(serviceDefTmp.getCodServizio());
					res = true;
				}
			}		
		}
		return res;

	}
	
	// SELECTION COMBO Servizi
	ModifyListener serviceDefComboValidator = new ModifyListener() {
		public void modifyText(ModifyEvent e) {
			operationCombo.removeAll();
			setMessage(null);
			if (modelloPrincipale != null) {

				for (ServiceImpl serviceImplTmp : modelloPrincipale.getServiceimplementations()) {
					ServiceDef serviceDefTmp = serviceImplTmp.getProvides();
					if (serviceDefTmp.getCodServizio().equalsIgnoreCase(serviceDefCombo.getText())) {
						serviceDefOrch = serviceDefTmp;
						operationCombo.setEnabled(true);
						break;
					}
				}

				if(serviceDefOrch!= null){
					EList<Operation> listaOperation = serviceDefOrch.getOperations();
					for (Operation operation : listaOperation) {
						operationCombo.add(operation.getName());
					}
				}

				else
					
					setMessage("Non è possibile caricare Operation valide per il servizio Selezionato");
			}
		}
	};
	
	// SELECTION COMBO Operation
	ModifyListener operationComboValidator = new ModifyListener() {
		public void modifyText(ModifyEvent e) {
			if(getServiceDefOrch()!=null){
				EList<Operation> listaOperation = getServiceDefOrch().getOperations();
				for (Operation operation : listaOperation) {
					if(operationCombo.getText().equalsIgnoreCase(operation.getName())){
						operationOrch = operation;
						setPageComplete(true);
						break;
					}
				}					
			}
		}
	};
	
	// Definizione Default PathSOABModel
	private String filePathSOABModelSelect;
	public String getFilePathSOABModelSelect() {
		return filePathSOABModelSelect;
	}

	public void setFilePathSOABModelSelect(String filePathSOABModelSelect) {
		this.filePathSOABModelSelect = filePathSOABModelSelect;
	}
	
	

	/**
	 * Se TIPO RESOURCES SELEZIONATO non di tipo SOABEModel return Null
	 * @param fullPath
	 * @return SOABEModel
	 */
	private  SOABEModel getSOABEModelByFullPath(String fullPath) {
		SOABEModel modelloPrincipale = null;

		if (!fullPath.equalsIgnoreCase("")) {
			try {
								
				//CARICO RESOURCES
				URI modPrincFileURI = URI.createPlatformResourceURI(fullPath, true);
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource modPrincResource = resourceSet
						.createResource(modPrincFileURI);
				Map<Object, Object> options = new HashMap<Object, Object>();

				modPrincResource.load(options);

				EList emfModPrincContent = (EList) modPrincResource.getContents();
				
				//TEST TIPO RESOURCES SELEZIONATO
				if ((emfModPrincContent.get(0)) instanceof SOABEModel) {
					modelloPrincipale = (SOABEModel) (emfModPrincContent.get(0));
				}

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		return modelloPrincipale;
		
	}

}