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
import it.csi.mddtools.servicedef.Param;
import it.csi.mddtools.servicedef.ServiceDef;
import it.csi.mddtools.servicegen.OrchestrationFlowCompositeSC;
import it.csi.mddtools.servicegen.SOABEModel;
import it.csi.mddtools.servicegen.ServiceComponent;
import it.csi.mddtools.servicegen.ServiceImpl;
import it.csi.mddtools.servicegen.genutils.CodeGenerationUtils;
import it.csi.mddtools.svcorch.DataSlot;
import it.csi.mddtools.svcorch.DataSlots;
import it.csi.mddtools.svcorch.InputParamBindings;
import it.csi.mddtools.svcorch.Nodes;
import it.csi.mddtools.svcorch.Orchestration;
import it.csi.mddtools.svcorch.ParamBinding;
import it.csi.mddtools.svcorch.StartNode;
import it.csi.mddtools.svcorch.StopNode;
import it.csi.mddtools.svcorch.SvcorchFactory;
import it.csi.mddtools.svcorch.SvcorchPackage;
import it.csi.mddtools.svcorch.provider.SvcorchEditPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;


/**
 * This is a simple wizard for creating a new model file.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SvcorchModelWizard extends Wizard implements INewWizard {
	/**
	 * The supported extensions for created files.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<String> FILE_EXTENSIONS =
		Collections.unmodifiableList(Arrays.asList(SvcorchEditorPlugin.INSTANCE.getString("_UI_SvcorchEditorFilenameExtensions").split("\\s*,\\s*")));

	/**
	 * A formatted list of supported file extensions, suitable for display.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String FORMATTED_FILE_EXTENSIONS =
		SvcorchEditorPlugin.INSTANCE.getString("_UI_SvcorchEditorFilenameExtensions").replaceAll("\\s*,\\s*", ", ");

	/**
	 * This caches an instance of the model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SvcorchPackage svcorchPackage = SvcorchPackage.eINSTANCE;

	/**
	 * This caches an instance of the model factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SvcorchFactory svcorchFactory = svcorchPackage.getSvcorchFactory();

	/**
	 * This is the file creation page.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SvcorchModelWizardNewFileCreationPage newFileCreationPage;

	/**
	 * This is the initial object creation page.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SvcorchModelWizardInitialObjectCreationPage initialObjectCreationPage;

	/**
	 * Remember the selection during initialization for populating the default container.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IStructuredSelection selection;

	/**
	 * Remember the workbench during initialization.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IWorkbench workbench;

	/**
	 * Caches the names of the types that can be created as the root object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected List<String> initialObjectNames;

	private OrchestrationFilesLocChooserWizardPage orchestrationFilesLocChooserWizardPage;

	/**
	 * This just records the information.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle(SvcorchEditorPlugin.INSTANCE.getString("_UI_Wizard_label"));
		setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(SvcorchEditorPlugin.INSTANCE.getImage("full/wizban/NewSvcorch")));
	}

	/**
	 * Returns the names of the types that can be created as the root object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected Collection<String> getInitialObjectNames() {
		if (initialObjectNames == null) {
			initialObjectNames = new ArrayList<String>();
			for (EClassifier eClassifier : svcorchPackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					EClass eClass = (EClass)eClassifier;
					if (!eClass.isAbstract() && canCreate(eClass)) {
						initialObjectNames.add(eClass.getName());
					}
				}
			}
			Collections.sort(initialObjectNames, CommonPlugin.INSTANCE.getComparator());
		}
		return initialObjectNames;
	}

	
	protected boolean canCreate(EClass cl){
		if (cl.getName().equals("Orchestration"))
			return true;
		else
			return false;
	}
	/**
	 * Create a new model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EObject createInitialModel() {
		EClass eClass = (EClass)svcorchPackage.getEClassifier(initialObjectCreationPage.getInitialObjectName());
		EObject rootObject = svcorchFactory.create(eClass);
		return rootObject;
	}

	/**
	 * Do the work after everything is specified.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean performFinish() {
		try {
			// Remember the file.
			//
			final IFile modelFile = getModelFile();

			// Do the work within an operation.
			WorkspaceModifyOperation operation =
					new WorkspaceModifyOperation() {
				@Override
				protected void execute(IProgressMonitor progressMonitor) {
					try {
						// Create a resource set
						ResourceSet resourceSet = new ResourceSetImpl();
						// Get the URI of the model file.
						URI fileURI = URI.createPlatformResourceURI(modelFile.getFullPath().toString(), true);
						// Create a resource for this file.
						Resource resource = resourceSet.createResource(fileURI);
						
						// Add the initial model object to the contents.
						EObject rootObject = createInitialModel();

						if (rootObject != null) {
							resource.getContents().add(rootObject);

							// Save the contents of the resource to the file system.
							Map<Object, Object> options = new HashMap<Object, Object>();
							options.put(XMLResource.OPTION_ENCODING, initialObjectCreationPage.getEncoding());

							if(rootObject instanceof Orchestration){

								Orchestration orchestration = (Orchestration) rootObject;

								ServiceDef service = orchestrationFilesLocChooserWizardPage.getServiceDefOrch();
								Operation operation = orchestrationFilesLocChooserWizardPage.getOperationOrch();
								
								URI modPrincFileURI = URI.createPlatformResourceURI(orchestrationFilesLocChooserWizardPage.getFileSOABModelContainerText(), true);
								Resource modPrincResource = resourceSet.createResource(modPrincFileURI);
								modPrincResource.load(options);
								
								EList emfModPrincContent = (EList)modPrincResource.getContents();
								SOABEModel model = (SOABEModel)(emfModPrincContent.get(0));
								//ASSOCIO ORCHESTRAZIONE AL MODELLO PRINCIPALE
								List<ServiceImpl> srvimplList = model.getServiceimplementations();
								String codServizioOrch = service.getCodServizio();
								OrchestrationFlowCompositeSC orchSC = null;
								for (ServiceImpl serviceImplTmp : srvimplList) {
									ServiceComponent sc = serviceImplTmp.getServiceComponent();
									if(sc instanceof OrchestrationFlowCompositeSC){
										if(codServizioOrch.equalsIgnoreCase(serviceImplTmp.getProvides().getCodServizio())){
											orchSC = (OrchestrationFlowCompositeSC) sc;
											orchSC.getOrchestrations().add(orchestration);
											modPrincResource.save(options);
											break;
										}
									}
								}

								//SET SERVIZIO
								orchestration.setService(service);
								
								//SET OPERATION
								orchestration.setOperation(operation);

								//DEF DATASLOTS
								DataSlots dataSlots = SvcorchFactory.eINSTANCE.createDataSlots();

								//SET ReturnSLOT e DATASLOT
								DataSlot dataSlotRT = SvcorchFactory.eINSTANCE.createDataSlot();
								dataSlotRT.setType(operation.getReturnType());
								dataSlotRT.setName("result");

								//ADD DataSlots
								orchestration.setReturnSlot(dataSlotRT);

								//DEF InputParamBindings
								InputParamBindings inputParamBindings = SvcorchFactory.eINSTANCE.createInputParamBindings();

								//SET InputParamBindingc --> InputParams method e data slot
								EList<Param> paramsOperation = operation.getParams();
								for (Param param : paramsOperation) {
									ParamBinding paramBinding = SvcorchFactory.eINSTANCE.createParamBinding();
									paramBinding.setParam(param);
									DataSlot dataSlotIPB = SvcorchFactory.eINSTANCE.createDataSlot();
									dataSlotIPB.setType(param.getType());
									dataSlotIPB.setName("in"+CodeGenerationUtils.toFirstUpper(param.getName()));
									paramBinding.setSlot(dataSlotIPB);
									dataSlots.getSlots().add(dataSlotIPB);	
									inputParamBindings.getInputParams().add(paramBinding);
								}

								//ADD InputParamBindings
								orchestration.setInputParamBindings(inputParamBindings);						

								dataSlots.getSlots().add(dataSlotRT);			
								orchestration.setGlobalSlots(dataSlots);

								//DEF NODES
								Nodes nodes = SvcorchFactory.eINSTANCE.createNodes();

								////SET Nodes (start - stop)
								StartNode startNode = SvcorchFactory.eINSTANCE.createStartNode();
								startNode.setName("startNode");
								startNode.setDescription("Nodo Start orchestrazione");								
								nodes.getNodes().add(startNode);

								StopNode stopNode = SvcorchFactory.eINSTANCE.createStopNode();
								stopNode.setName("stopNode");
								stopNode.setDescription("Nodo stop Orchestrazione");
								nodes.getNodes().add(stopNode);		
								startNode.setNext(stopNode);

								//ADD Nodes
								orchestration.setNodes(nodes);
								
								

							}

							resource.save(options);
						}
					}
					catch (Exception exception) {
						SvcorchEditorPlugin.INSTANCE.log(exception);
					}
					finally {
						progressMonitor.done();
					}
				}


			};

			getContainer().run(false, false, operation);

			// Select the new file resource in the current view.
			IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage page = workbenchWindow.getActivePage();
			final IWorkbenchPart activePart = page.getActivePart();
			if (activePart instanceof ISetSelectionTarget) {
				final ISelection targetSelection = new StructuredSelection(modelFile);
				getShell().getDisplay().asyncExec
				(new Runnable() {
					public void run() {
						((ISetSelectionTarget)activePart).selectReveal(targetSelection);
					}
				});
			}

			// Open an editor on the new file.
			try {
				page.openEditor
				(new FileEditorInput(modelFile),
						workbench.getEditorRegistry().getDefaultEditor(modelFile.getFullPath().toString()).getId());					 	 
			}
			catch (PartInitException exception) {
				MessageDialog.openError(workbenchWindow.getShell(), SvcorchEditorPlugin.INSTANCE.getString("_UI_OpenEditorError_label"), exception.getMessage());
				return false;
			}

			return true;
		}
		catch (Exception exception) {
			SvcorchEditorPlugin.INSTANCE.log(exception);
			return false;
		}
	}

	/**
	 * This is the one page of the wizard.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public class SvcorchModelWizardNewFileCreationPage extends WizardNewFileCreationPage {
		/**
		 * Pass in the selection.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public SvcorchModelWizardNewFileCreationPage(String pageId, IStructuredSelection selection) {
			super(pageId, selection);
		}

		/**
		 * The framework calls this to see if the file is correct.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		@Override
		protected boolean validatePage() {
			if (super.validatePage()) {
				String extension = new Path(getFileName()).getFileExtension();
				if (extension == null || !FILE_EXTENSIONS.contains(extension)) {
					String key = FILE_EXTENSIONS.size() > 1 ? "_WARN_FilenameExtensions" : "_WARN_FilenameExtension";
					setErrorMessage(SvcorchEditorPlugin.INSTANCE.getString(key, new Object [] { FORMATTED_FILE_EXTENSIONS }));
					return false;
				}
				return true;
			}
			return false;
		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public IFile getModelFile() {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()));
		}
	}

	/**
	 * This is the page where the type of object to create is selected.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public class SvcorchModelWizardInitialObjectCreationPage extends WizardPage {
		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected Combo initialObjectField;

		/**
		 * @generated
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 */
		protected List<String> encodings;

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected Combo encodingField;

		/**
		 * Pass in the selection.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public SvcorchModelWizardInitialObjectCreationPage(String pageId) {
			super(pageId);
		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		public void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE); {
				GridLayout layout = new GridLayout();
				layout.numColumns = 1;
				layout.verticalSpacing = 12;
				composite.setLayout(layout);

				GridData data = new GridData();
				data.verticalAlignment = GridData.FILL;
				data.grabExcessVerticalSpace = true;
				data.horizontalAlignment = GridData.FILL;
				composite.setLayoutData(data);
			}

			Label containerLabel = new Label(composite, SWT.LEFT);
			{
				containerLabel.setText(SvcorchEditorPlugin.INSTANCE.getString("_UI_ModelObject"));

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				containerLabel.setLayoutData(data);
			}

			initialObjectField = new Combo(composite, SWT.BORDER);
			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				initialObjectField.setLayoutData(data);
			}

			for (String objectName : getInitialObjectNames()) {
				initialObjectField.add(getLabel(objectName));
			}

			if (initialObjectField.getItemCount() == 1) {
				initialObjectField.select(0);
			}
			initialObjectField.addModifyListener(validator);
			initialObjectField.setEnabled(false);

			Label encodingLabel = new Label(composite, SWT.LEFT);
			{
				encodingLabel.setText(SvcorchEditorPlugin.INSTANCE.getString("_UI_XMLEncoding"));

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				encodingLabel.setLayoutData(data);
			}
			encodingField = new Combo(composite, SWT.BORDER);
			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				encodingField.setLayoutData(data);
			}

			for (String encoding : getEncodings()) {
				encodingField.add(encoding);
			}

			encodingField.select(0);
			encodingField.addModifyListener(validator);

			setPageComplete(validatePage());
			setControl(composite);
		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected ModifyListener validator =
			new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					setPageComplete(validatePage());
				}
			};

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected boolean validatePage() {
			return getInitialObjectName() != null && getEncodings().contains(encodingField.getText());
		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		@Override
		public void setVisible(boolean visible) {
			super.setVisible(visible);
			if (visible) {
				if (initialObjectField.getItemCount() == 1) {
					initialObjectField.clearSelection();
					encodingField.setFocus();
				}
				else {
					encodingField.clearSelection();
					initialObjectField.setFocus();
				}
			}
		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public String getInitialObjectName() {
			String label = initialObjectField.getText();

			for (String name : getInitialObjectNames()) {
				if (getLabel(name).equals(label)) {
					return name;
				}
			}
			return null;
		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public String getEncoding() {
			return encodingField.getText();
		}

		/**
		 * Returns the label for the specified type name.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected String getLabel(String typeName) {
			try {
				return SvcorchEditPlugin.INSTANCE.getString("_UI_" + typeName + "_type");
			}
			catch(MissingResourceException mre) {
				SvcorchEditorPlugin.INSTANCE.log(mre);
			}
			return typeName;
		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected Collection<String> getEncodings() {
			if (encodings == null) {
				encodings = new ArrayList<String>();
				for (StringTokenizer stringTokenizer = new StringTokenizer(SvcorchEditorPlugin.INSTANCE.getString("_UI_XMLEncodingChoices")); stringTokenizer.hasMoreTokens(); ) {
					encodings.add(stringTokenizer.nextToken());
				}
			}
			return encodings;
		}
	}

	/**
	 * The framework calls this to create the contents of the wizard.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void addPages() {

		//PAGINA 1: create NewFile	
		// Create a page, set the title, and the initial model file name.
		newFileCreationPage = new SvcorchModelWizardNewFileCreationPage("Whatever", selection);
		newFileCreationPage.setTitle(SvcorchEditorPlugin.INSTANCE.getString("_UI_SvcorchModelWizard_label"));
		newFileCreationPage.setDescription(SvcorchEditorPlugin.INSTANCE.getString("_UI_SvcorchModelWizard_description"));
		newFileCreationPage.setFileName(SvcorchEditorPlugin.INSTANCE.getString("_UI_SvcorchEditorFilenameDefaultBase") + "." + FILE_EXTENSIONS.get(0));
		addPage(newFileCreationPage);

		//PAGINA 2: selezione modello (SVCORC)
		initialObjectCreationPage = new SvcorchModelWizardInitialObjectCreationPage("Whatever2");
		initialObjectCreationPage.setTitle(SvcorchEditorPlugin.INSTANCE.getString("_UI_SvcorchModelWizard_label"));
		initialObjectCreationPage.setDescription(SvcorchEditorPlugin.INSTANCE.getString("_UI_Wizard_initial_object_description"));
		addPage(initialObjectCreationPage);

		//PAGINA 3: definzione servizio orchestrazione (associazione SOABEModel-> ServiceDef --> Operation)
		orchestrationFilesLocChooserWizardPage = new OrchestrationFilesLocChooserWizardPage(selection);	
		addPage(orchestrationFilesLocChooserWizardPage);

		//INIZIALIZZAZIONE DATI BY 'selection'
		// Try and get the resource selection to determine a current directory for the file dialog.
		boolean isSOABEModelSelect = false;
		if (selection != null && !selection.isEmpty()) {
			// Get the resource...
			//
			Object selectedElement = selection.iterator().next();
			if (selectedElement instanceof IResource) {
				// Get the resource parent, if its a file.
				//
				IResource selectedResource = (IResource)selectedElement;
				
				//Test selezione SOABEModel
				if (selectedResource.getType() == IResource.FILE) {
					String filePathTmp= "";
					filePathTmp = selectedResource.getFullPath().toPortableString();
					
					isSOABEModelSelect = getSOABEModelByFullPath(filePathTmp);
					if(isSOABEModelSelect)
						orchestrationFilesLocChooserWizardPage.setFilePathSOABModelSelect(filePathTmp);
					
					//RISALGO AL PADRE
					selectedResource = selectedResource.getParent();
				}

				// This gives us a directory...
				//
				if (selectedResource instanceof IFolder || selectedResource instanceof IProject) {
					// Set this for the container.
					//Definizione --> CONTAINER NewFile
					newFileCreationPage.setContainerFullPath(selectedResource.getFullPath());
					
					if(!isSOABEModelSelect)
						orchestrationFilesLocChooserWizardPage.setFilePathSOABModelSelect(selectedResource.getFullPath().toPortableString());
					// Make up a unique new name here.
					//
					String defaultModelBaseFilename = SvcorchEditorPlugin.INSTANCE.getString("_UI_SvcorchEditorFilenameDefaultBase");
					String defaultModelFilenameExtension = FILE_EXTENSIONS.get(0);
					String modelFilename = defaultModelBaseFilename + "." + defaultModelFilenameExtension;
					for (int i = 1; ((IContainer)selectedResource).findMember(modelFilename) != null; ++i) {
						modelFilename = defaultModelBaseFilename + i + "." + defaultModelFilenameExtension;
					}
					//Definizione --> NAME NewFile
					newFileCreationPage.setFileName(modelFilename);
				}
			}

		}
	}

	/**
	 * Get the file from the page.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IFile getModelFile() {
		return newFileCreationPage.getModelFile();
	}
	
	/**
	 * Se TIPO RESOURCES SELEZIONATO non di tipo SOABEModel return false
	 * @param fullPath
	 * @return boolean
	 */
	private  boolean getSOABEModelByFullPath(String fullPath) {
		boolean res = false;

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
					res = true;
				}

			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		return res;
		
	}

}
