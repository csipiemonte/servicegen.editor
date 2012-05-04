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
package it.csi.mddtools.servicedef.presentation;


import it.csi.mddtools.servicedef.OpTypeEnum;
import it.csi.mddtools.servicedef.Operation;
import it.csi.mddtools.servicedef.Param;
import it.csi.mddtools.servicedef.ServiceDef;
import it.csi.mddtools.servicedef.ServicedefFactory;
import it.csi.mddtools.servicedef.ServicedefPackage;
import it.csi.mddtools.servicedef.SrvTypeEnum;
import it.csi.mddtools.servicedef.Types;
import it.csi.mddtools.servicedef.provider.Servicedef_metamodelEditPlugin;
import it.csi.mddtools.servicegen.FlowModelImplCartridge;
import it.csi.mddtools.servicegen.ManualImplCartridge;
import it.csi.mddtools.servicegen.OrchestrationFlowCompositeSC;
import it.csi.mddtools.servicegen.ResourceBasedSimpleSC;
import it.csi.mddtools.servicegen.SOABEModel;
import it.csi.mddtools.servicegen.ServiceImpl;
import it.csi.mddtools.servicegen.ServicegenFactory;
import it.csi.mddtools.servicegen.presentation.Servicegen_metamodelEditorPlugin;
import it.csi.mddtools.servicegen.presentation.common.Utility;
import it.csi.mddtools.servicegen.provider.Servicegen_metamodelEditPlugin;
import it.csi.mddtools.typedef.CSIExceptionTypes;
import it.csi.mddtools.typedef.Entity;
import it.csi.mddtools.typedef.TypedefFactory;
import it.csi.mddtools.typedef.TypedefPackage;

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
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;


/**
 * This is a simple wizard for creating a new model file.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ServicedefModelWizard extends Wizard implements INewWizard {
	/**
	 * The supported extensions for created files.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<String> FILE_EXTENSIONS =
		Collections.unmodifiableList(Arrays.asList(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicedefEditorFilenameExtensions").split("\\s*,\\s*")));

	/**
	 * A formatted list of supported file extensions, suitable for display.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String FORMATTED_FILE_EXTENSIONS =
		Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicedefEditorFilenameExtensions").replaceAll("\\s*,\\s*", ", ");

	/**
	 * This caches an instance of the model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServicedefPackage servicedefPackage = ServicedefPackage.eINSTANCE;
	
	/**
	 * This caches an instance of a type package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated  NOT
	 */
	protected TypedefPackage typedefPackage = TypedefPackage.eINSTANCE;
	/**
	 * This caches an instance of the model factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServicedefFactory servicedefFactory = servicedefPackage.getServicedefFactory();
	
	
	/**
	 * This caches an instance of the type factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */	
	protected TypedefFactory typedefFactory = typedefPackage.getTypedefFactory();
	 
	protected ServicedefModelWizardNewFileCreationPage newFileCreationPage;

	protected ServicedefModelWizardInitialObjectCreationPage initialObjectCreationPage;
	
	protected ServicedefModelWizardServiceInfoCreationPage serviceInfoPage;
	
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

	private ServicedefModelWizardFileRefsCreationPage serviceFileRefsPage;
	
	/**
	 * This just records the information.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_Wizard_label"));
		setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(Servicedef_metamodelEditorPlugin.INSTANCE.getImage("full/wizban/NewServicedef")));
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
			for (EClassifier eClassifier : servicedefPackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					EClass eClass = (EClass)eClassifier;
					if (!eClass.isAbstract()&& canCreate(eClass)) {
						initialObjectNames.add(eClass.getName());
					}
				}
			}
			Collections.sort(initialObjectNames, CommonPlugin.INSTANCE.getComparator());
		}
		return initialObjectNames;
	}

	protected boolean canCreate(EClass cl){
		if (cl.getName().equals("ServiceDef"))
			return true;
		else
			return false;
	}

	/**
	 * Create a new model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected EObject createInitialModel() {
		EClass eClass = (EClass)servicedefPackage.getEClassifier(initialObjectCreationPage.getInitialObjectName());
		EObject rootObject = servicedefFactory.create(eClass);
		ServiceDef sd = (ServiceDef) rootObject;
		
		//set dei dati passati via wizard
		sd.setCodServizio(this.serviceInfoPage.getCodServizio());
		sd.setVersione(this.serviceInfoPage.getVerServizio());
		sd.setCodComponente(this.serviceInfoPage.getCodComponente());
		sd.setCodProdotto(this.serviceInfoPage.getCodProdotto());
		if("Servizio applicativo".equals(this.serviceInfoPage.getTipoServizio())){
			sd.setServiceType(SrvTypeEnum.APPL);
		}else if("Servizio di orchestrazione".equals(this.serviceInfoPage.getTipoServizio())){
			sd.setServiceType(SrvTypeEnum.ORCH);
		}else if("Servizio infrastrutturale".equals(this.serviceInfoPage.getTipoServizio())){
			sd.setServiceType(SrvTypeEnum.INFR);
		}
		//creazione del servizio dummy se specificato nella apposita textfield del wizard
		//operation dummy
		//entity dummy
		//exception dummy
		
		if(this.serviceInfoPage.getDummyNethodName()!=null && this.serviceInfoPage.getDummyNethodName().length()>0){
			Entity en = typedefFactory.createEntity();
			en.setName("DummyEntity");
			en.setVersionuid(1);
			it.csi.mddtools.typedef.Exception ex = typedefFactory.createException();
			ex.setName("DummyException");
			ex.setExceptionType(CSIExceptionTypes.USER);
			//crea l'operation dummy
			Operation op = servicedefFactory.createOperation();
			op.setName(this.serviceInfoPage.getDummyNethodName());
			op.setReturnType(en);
			op.setOpType(OpTypeEnum.SYNCH);
			//crea il param dummy
			Param p = servicedefFactory.createParam();
			p.setName("P1");
			p.setType(en);
			//setto param dentro operation
			op.getParams().add(p);
			op.getThrows().add(ex);
			//creo types e setto sia exception che entity
			Types t = servicedefFactory.createTypes();
			t.getTypes().add(en);
			t.getTypes().add(ex);
			//setto nel servicedef gli oggetti del metodo dummy appena creati
			sd.setTypes(t);
			sd.getOperations().add(op);
		}
		
		return sd;
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
			//
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
							
							Resource modPrincResource = null;

							// Add the initial model object to the contents.
							EObject rootObject = createInitialModel();
							
							// Save the contents of the resource to the file system.
							Map<Object, Object> options = new HashMap<Object, Object>();
							options.put(XMLResource.OPTION_ENCODING, initialObjectCreationPage.getEncoding());
							
							String modPrincFilePath =serviceFileRefsPage.getRootModelFile();
							
							if(serviceFileRefsPage.getModelloPrincipale()!= null &&(modPrincFilePath!=null && !"".equalsIgnoreCase(modPrincFilePath)) ){
								//LOAD MODELLO PRINCIPALE 
								EList emfModPrincContent = Utility.loadResource(modPrincFilePath);
								SOABEModel modPrincModule = (emfModPrincContent.get(0) instanceof SOABEModel) ? (SOABEModel)(emfModPrincContent.get(0)) : null;

								if(null!= modPrincModule && rootObject instanceof ServiceDef){
									ServiceDef serviceDef = (ServiceDef) rootObject;

									//CREARE SERVICE IMPL
									ServiceImpl serviceImpl = ServicegenFactory.eINSTANCE.createServiceImpl();
									//DEF SERVICEDEF
									serviceImpl.setProvides(serviceDef);	
									
									if(serviceDef.getServiceType().getName().equalsIgnoreCase(SrvTypeEnum.APPL.getName())){
										//DEF BASED RESOURCE
										ResourceBasedSimpleSC resourceBasedSimpleSC = ServicegenFactory.eINSTANCE.createResourceBasedSimpleSC();
										serviceImpl.setServiceComponent(resourceBasedSimpleSC);
										//DEF MANUAL CARTRIDGE 
										ManualImplCartridge manualImplCartridge = ServicegenFactory.eINSTANCE.createManualImplCartridge();
										manualImplCartridge.setUseInjectedPojo(true);
										serviceImpl.setImplCartridge(manualImplCartridge);
									}
									else{
										//DEF BASED RESOURCE
										OrchestrationFlowCompositeSC orchestrationFlowCompositeSC = ServicegenFactory.eINSTANCE.createOrchestrationFlowCompositeSC();
										serviceImpl.setServiceComponent(orchestrationFlowCompositeSC);
										//DEF MANUAL CARTRIDGE 
										FlowModelImplCartridge flowModelImplCartridge = ServicegenFactory.eINSTANCE.createFlowModelImplCartridge();
										
										serviceImpl.setImplCartridge(flowModelImplCartridge);
									}
									

									//ADD SERVICEIMPL al modello principale
									modPrincModule.getServiceimplementations().add(serviceImpl);
									
								

								}
							}
							
							if (rootObject != null) {
								resource.getContents().add(rootObject);
							}

							resource.save(options);
							if(modPrincResource!=null)
								modPrincResource.save(options);
						}
						catch (Exception exception) {
							Servicedef_metamodelEditorPlugin.INSTANCE.log(exception);
						}
						finally {
							progressMonitor.done();
						}
					}
				};

			getContainer().run(false, false, operation);

			// Select the new file resource in the current view.
			//
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
			//
			try {
				page.openEditor
					(new FileEditorInput(modelFile),
					 workbench.getEditorRegistry().getDefaultEditor(modelFile.getFullPath().toString()).getId());					 	 
			}
			catch (PartInitException exception) {
				MessageDialog.openError(workbenchWindow.getShell(), Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_OpenEditorError_label"), exception.getMessage());
				return false;
			}

			return true;
		}
		catch (Exception exception) {
			Servicedef_metamodelEditorPlugin.INSTANCE.log(exception);
			return false;
		}
	}

	/**
	 * This is the one page of the wizard.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public class ServicedefModelWizardNewFileCreationPage extends WizardNewFileCreationPage {
		/**
		 * Pass in the selection.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public ServicedefModelWizardNewFileCreationPage(String pageId, IStructuredSelection selection) {
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
					setErrorMessage(Servicedef_metamodelEditorPlugin.INSTANCE.getString(key, new Object [] { FORMATTED_FILE_EXTENSIONS }));
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
	public class ServicedefModelWizardInitialObjectCreationPage extends WizardPage {
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
		public ServicedefModelWizardInitialObjectCreationPage(String pageId) {
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
				containerLabel.setText(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_ModelObject"));

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
				encodingLabel.setText(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_XMLEncoding"));

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
				return Servicedef_metamodelEditPlugin.INSTANCE.getString("_UI_" + typeName + "_type");
			}
			catch(MissingResourceException mre) {
				Servicedef_metamodelEditorPlugin.INSTANCE.log(mre);
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
				for (StringTokenizer stringTokenizer = new StringTokenizer(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_XMLEncodingChoices")); stringTokenizer.hasMoreTokens(); ) {
					encodings.add(stringTokenizer.nextToken());
				}
			}
			return encodings;
		}
	}

	/**
		 * This is the page where the type of object to create is selected.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		public class ServicedefModelWizardServiceInfoCreationPage extends WizardPage {
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			protected org.eclipse.swt.widgets.Text codServizio;			
			protected org.eclipse.swt.widgets.Text codProdotto;
			protected org.eclipse.swt.widgets.Text codComponente;
			
			
			public void setCodServizio(String codServizio) {
				this.codServizio.setText(codServizio);
			}

			public void setCodProdotto(String codProdotto) {
				this.codProdotto.setText(codProdotto);
			}

			public void setCodComponente(String codComponente) {
				this.codComponente.setText(codComponente);
			}

			public void setVerServizio(String verServizio) {
				this.verServizio.setText(verServizio);
			}
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			protected Combo tipoServizio;
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			protected org.eclipse.swt.widgets.Text verServizio;
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			protected org.eclipse.swt.widgets.Text dummyNethodName;
		
			/**
			 * Pass in the selection.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public ServicedefModelWizardServiceInfoCreationPage(String pageId) {
				super(pageId);
			}
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public String getCodProdotto() {
				String txt = codProdotto.getText();
				if (txt==null || txt.length()==0)
					return null;
				else
					return txt;
			}
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public String getCodComponente() {
				String txt = codComponente.getText();
				if (txt==null || txt.length()==0)
					return null;
				else
					return txt;
			}
			
		
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public String getCodServizio() {
				String txt = codServizio.getText();
				if (txt==null || txt.length()==0)
					return null;
				else
					return txt;
			}
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public String getTipoServizio() {
				String txt = tipoServizio.getText();
				if (txt==null || txt.length()==0)
					return null;
				else
					return txt;
			}
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public String getVerServizio() {
				String txt = verServizio.getText();
				if (txt==null || txt.length()==0)
					return null;
				else
					return txt;
			}
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public String getDummyNethodName() {
				String txt = dummyNethodName.getText();
				if (txt==null || txt.length()==0)
					return null;
				else
					return txt;
			}
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public void createControl(Composite parent) {
				Composite composite = new Composite(parent, SWT.NONE);
				{
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
		
				// codice prodotto
				Label codProdottoLabel = new Label(composite, SWT.LEFT);
				{
					codProdottoLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_CodProdotto_label"));
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					codProdottoLabel.setLayoutData(data);
				}
				
				codProdotto = new org.eclipse.swt.widgets.Text(composite, SWT.BORDER);
				{
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					data.grabExcessHorizontalSpace = true;
					codProdotto.setLayoutData(data);
					codProdotto.addModifyListener(validator);
				}
				
				//codice componente
				Label codComponenteLabel = new Label(composite, SWT.LEFT);
				{
					codComponenteLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_CodComponente_label"));
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					codProdottoLabel.setLayoutData(data);
				}
				
				codComponente = new org.eclipse.swt.widgets.Text(composite, SWT.BORDER);
				{
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					data.grabExcessHorizontalSpace = true;
					codComponente.setLayoutData(data);
					codComponente.addModifyListener(validator);
				}
				
				//codice servizio
				Label codServizioLabel = new Label(composite, SWT.LEFT);
				{
					codServizioLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_CodServizio_label"));
		
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					codServizioLabel.setLayoutData(data);
				}
		
				codServizio = new org.eclipse.swt.widgets.Text(composite, SWT.BORDER);
				{
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					data.grabExcessHorizontalSpace = true;					
					codServizio.setLayoutData(data);
					codServizio.addModifyListener(validator);
				}
				
				//versione servizio
				Label verProdottoLabel = new Label(composite, SWT.LEFT);
				{
					verProdottoLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_VerServizio_label"));
		
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					verProdottoLabel.setLayoutData(data);
				}
				
				verServizio = new org.eclipse.swt.widgets.Text(composite, SWT.BORDER);
				{
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					data.grabExcessHorizontalSpace = true;
					verServizio.setLayoutData(data);
					verServizio.addModifyListener(validator);
				}
				
				//tipo servizio
				Label tipoServizioLabel = new Label(composite, SWT.LEFT);
				{
					tipoServizioLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_TipoServizio_label"));
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					tipoServizioLabel.setLayoutData(data);
				}
				
				tipoServizio = new Combo(composite, SWT.BORDER);
				{
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					data.grabExcessHorizontalSpace = true;
					tipoServizio.setLayoutData(data);
					tipoServizio.addModifyListener(validator);
				}
		
				tipoServizio.add("Servizio applicativo");
				tipoServizio.add("Servizio di orchestrazione");
				tipoServizio.add("Servizio infrastrutturale");
				
				if (tipoServizio.getItemCount() == 1) {
					tipoServizio.select(0);
				}
				//tipoServizio.addModifyListener(validator);
				
				Label dummyMethodLabel = new Label(composite, SWT.LEFT);
				{
					dummyMethodLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_DummyMethodName_label"));
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					dummyMethodLabel.setLayoutData(data);
				}
				
				dummyNethodName = new org.eclipse.swt.widgets.Text(composite, SWT.BORDER);
				{
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					data.grabExcessHorizontalSpace = true;
					dummyNethodName.setLayoutData(data);
					dummyNethodName.addModifyListener(validator);
				}
				
				setPageComplete(validatePage());
				setControl(composite);
			}
		
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
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
			 * @generated NOT
			 */
			protected boolean validatePage() {
				return getCodServizio()!=null && getTipoServizio()!=null &&
				getVerServizio()!=null ;
			}
		
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			@Override
			public void setVisible(boolean visible) {
				super.setVisible(visible);
				if (visible) {
	//				if (initialObjectField.getItemCount() == 1) {
	//					initialObjectField.clearSelection();
	//					encodingField.setFocus();
	//				}
	//				else {
	//					encodingField.clearSelection();
	//					initialObjectField.setFocus();
	//				}
				}
			}
		
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
	//		public String getInitialObjectName() {
	//			String label = initialObjectField.getText();
	//	
	//			for (String name : getInitialObjectNames()) {
	//				if (getLabel(name).equals(label)) {
	//					return name;
	//				}
	//			}
	//			return null;
	//		}
		
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
	//		public String getEncoding() {
	//			return encodingField.getText();
	//		}
		
			/**
			 * Returns the label for the specified type name.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			protected String getLabel(String typeName) {
				try {
					return Servicegen_metamodelEditPlugin.INSTANCE.getString("_UI_" + typeName + "_type");
				}
				catch(MissingResourceException mre) {
					Servicegen_metamodelEditorPlugin.INSTANCE.log(mre);
				}
				return typeName;
			}
		
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
	//		protected Collection<String> getEncodings() {
	//			if (encodings == null) {
	//				encodings = new ArrayList<String>();
	//				for (StringTokenizer stringTokenizer = new StringTokenizer(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_XMLEncodingChoices")); stringTokenizer.hasMoreTokens(); ) {
	//					encodings.add(stringTokenizer.nextToken());
	//				}
	//			}
	//			return encodings;
	//		}
		}

	/**
		 * This is the page where the type of object to create is selected.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		public class ServicedefModelWizardFileRefsCreationPage extends WizardPage {
		
			
			// property appoggio per inizializzazione WIZARD
			private boolean selectSOABEModel;
			private String selectFilePath;

			public boolean isSelectSOABEModel() {
				return selectSOABEModel;
			}

			public void setSelectSOABEModel(boolean selectSOABEModel) {
				this.selectSOABEModel = selectSOABEModel;
			}

			public String getSelectFilePath() {
				return selectFilePath;
			}

			public void setSelectFilePath(String selectFilePath) {
				this.selectFilePath = selectFilePath;
			}
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			protected org.eclipse.swt.widgets.Text rootModelFile;
			private SOABEModel modelloPrincipale;
			
			

			private Button selectSOABEModelCheck;
			
			/**
			 * Pass in the selection.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public SOABEModel getModelloPrincipale() {
				return modelloPrincipale;
			}


			/**
			 * Pass in the selection.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public ServicedefModelWizardFileRefsCreationPage(String pageId) {
				super(pageId);
			}
		
			
			/**
			 * Pass in the selection.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			@Override
			public IWizardPage getNextPage() {
				IWizardPage wiz = super.getNextPage();
				SOABEModel modello = getModelloPrincipale();
				
				if(modello != null){		
					if(wiz instanceof ServicedefModelWizardServiceInfoCreationPage){
						((ServicedefModelWizardServiceInfoCreationPage)wiz).setCodComponente(modello.getCodComponente());
						((ServicedefModelWizardServiceInfoCreationPage)wiz).setCodProdotto(modello.getCodProdotto());
					}
				}
				
				return wiz;
					
			}
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public String getRootModelFile() {
				String txt =  rootModelFile.getText();
				if (txt==null || txt.length()==0)
					return null;
				else
					return txt;
			}
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public void createControl(Composite parent) {
				Composite composite = new Composite(parent, SWT.NONE);
				{
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
		
				Label entichmentCheckBoxlabel = new Label(composite, SWT.LEFT);
				{
					entichmentCheckBoxlabel.setText("Abilita selezione modello principale");
					entichmentCheckBoxlabel.setToolTipText("Abilita selezione modello principale");
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					entichmentCheckBoxlabel.setLayoutData(data);
				}
				
				selectSOABEModelCheck= new Button(composite, SWT.CHECK | SWT.LEFT);
				selectSOABEModelCheck.setSelection(isSelectSOABEModel());
				
				selectSOABEModelCheck.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(selectSOABEModelCheck.getSelection()){
							rootModelFile.setEnabled(true);
							dialogChanged();
						}
						else{
							rootModelFile.setEnabled(false);
							updateStatus(null);
						}
						
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
					
					
				});
				
				Label label = new Label(composite, SWT.NULL);
				label.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_RootModelFile_label"));
				rootModelFile = new Text(composite, SWT.BORDER | SWT.SINGLE);
				GridData gd = new GridData(GridData.FILL_HORIZONTAL);
				rootModelFile.setLayoutData(gd);
				rootModelFile.setEnabled(isSelectSOABEModel());
				rootModelFile.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						dialogChanged();
					}
				});
				Button button = new Button(composite, SWT.PUSH);
				button.setText("Browse...");
				button.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						handleRootModelFileBrowse();
					}
				});
				
				setPageComplete(true);
				initialize();
				setControl(composite);
			}
		
			
			private void initialize() {
				if(null!=getSelectFilePath()){
					rootModelFile.setText(getSelectFilePath());
					validaModello();
				}
			}
			private void handleRootModelFileBrowse() {
				ResourceSelectionDialog dialog = new ResourceSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), "Scegliere file del modello principale");
				if (dialog.open() == ResourceSelectionDialog.OK) {
					Object[] result = dialog.getResult();
					if (result.length >0 && result[0] instanceof IFile) {
						String modelFileSelected = ((IFile) result[0]).getFullPath().toString(); 
						rootModelFile.setText(modelFileSelected);
					}
				}
			}
			
			private void dialogChanged() {
				
				if(rootModelFile.getEnabled()){
					String fileName = getRootModelFile();
									
					//// controlli sul model file
					if (fileName!=null && fileName.length() == 0) {
						updateStatus("Occorre specificare il percorso del file che contiene il modello principale");
						return;
					}
					
					if(!validaModello()){
						updateStatus("Il file scelto deve essere di tipo SERVICEGEN");
						return;
					}
				}
				updateStatus(null);
			}
			
			
			
			/**
			 * The framework calls this to see if the file is correct.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			protected boolean validaModello() {

				boolean res = false;

				EList emfRSContent = Utility.loadResource(getRootModelFile());

				// TEST TIPO RESOURCES SELEZIONATO
				if ((emfRSContent.get(0)) instanceof SOABEModel) {
					modelloPrincipale = (SOABEModel) (emfRSContent.get(0));
					res = true;
				}

				return res;
				
			}

			private void updateStatus(String message) {
				setErrorMessage(message);
				setPageComplete(message == null);
				
			}
			
		
		
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			@Override
			public void setVisible(boolean visible) {
				super.setVisible(visible);
				if (visible) {
	//				if (initialObjectField.getItemCount() == 1) {
	//					initialObjectField.clearSelection();
	//					encodingField.setFocus();
	//				}
	//				else {
	//					encodingField.clearSelection();
	//					initialObjectField.setFocus();
	//				}
				}
			}
		
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
	//		public String getInitialObjectName() {
	//			String label = initialObjectField.getText();
	//	
	//			for (String name : getInitialObjectNames()) {
	//				if (getLabel(name).equals(label)) {
	//					return name;
	//				}
	//			}
	//			return null;
	//		}
		
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
	//		public String getEncoding() {
	//			return encodingField.getText();
	//		}
		
			/**
			 * Returns the label for the specified type name.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			protected String getLabel(String typeName) {
				try {
					return Servicegen_metamodelEditPlugin.INSTANCE.getString("_UI_" + typeName + "_type");
				}
				catch(MissingResourceException mre) {
					Servicegen_metamodelEditorPlugin.INSTANCE.log(mre);
				}
				return typeName;
			}
		
	
		}

		
		
		
	/**
	 * The framework calls this to create the contents of the wizard.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
		@Override
	public void addPages() {
			
		String selectFilePathTmp = "";
		
		//PAGINA1:  definizione nome Serviced e Path
		// Create a page, set the title, and the initial model file name.
		newFileCreationPage = new ServicedefModelWizardNewFileCreationPage("Whatever", selection);
		newFileCreationPage.setTitle(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicedefModelWizard_label"));
		newFileCreationPage.setDescription(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicedefModelWizard_description"));
		newFileCreationPage.setFileName(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicedefEditorFilenameDefaultBase") + "." + FILE_EXTENSIONS.get(0));
		addPage(newFileCreationPage);
		
		//PAGINA2: definizione ModelObject (una sola opzione Servicedef)
		initialObjectCreationPage = new ServicedefModelWizardInitialObjectCreationPage("Whatever2");
		initialObjectCreationPage.setTitle(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicedefModelWizard_label"));
		initialObjectCreationPage.setDescription(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_Wizard_initial_object_description"));
		addPage(initialObjectCreationPage);
		
		//PAGINA3: associazione a modello principale (SOABEModel)
		serviceFileRefsPage = new ServicedefModelWizardFileRefsCreationPage("file_refs");
		serviceFileRefsPage.setTitle("Riferimenti a file");
		serviceFileRefsPage.setDescription("Inserire i riferimenti ai file referenziati");
		
		addPage(serviceFileRefsPage);
		
		// PAGINA4: definizione codici e informzioni sul servizio
		serviceInfoPage = new ServicedefModelWizardServiceInfoCreationPage("service_info");
		serviceInfoPage.setTitle("Informazioni del servizio");
		serviceInfoPage.setDescription("Inserire le informazioni principali del servizio da creare (identificativi, tipologia, ...)");
		addPage(serviceInfoPage);
		

		//INIZIALIZZAZIONE PAGINE
		// Try and get the resource selection to determine a current directory for the file dialog.
		if (selection != null && !selection.isEmpty()) {
			// Get the resource...
			Object selectedElement = selection.iterator().next();
			if (selectedElement instanceof IResource) {
				// Get the resource parent, if its a file.
				IResource selectedResource = (IResource)selectedElement;
				
				//path selezionato
				selectFilePathTmp = selectedResource.getFullPath().toPortableString();
				
				if (selectedResource.getType() == IResource.FILE) {			
					//pagina newFileCreationPage  --> path parent (IFolder, IProject)
					selectedResource = selectedResource.getParent();
					
					//pagina serviceFileRefsPage verifica tipo file selezionato
					URI modPrincFileURI = URI.createPlatformResourceURI(selectFilePathTmp, true);
					ResourceSet resourceSet = new ResourceSetImpl();
					Resource modPrincResource = resourceSet.createResource(modPrincFileURI);
					Map<Object, Object> options = new HashMap<Object, Object>();
							
					try {
						modPrincResource.load(options);
						EList emfModPrincContent = (EList)modPrincResource.getContents();
						//se SOABEMODEL
						if ( ((emfModPrincContent.get(0)) instanceof SOABEModel)){
							serviceFileRefsPage.setSelectFilePath(selectFilePathTmp);
							serviceFileRefsPage.setSelectSOABEModel(true);
						}
						else{
							selectFilePathTmp = selectedResource.getFullPath().toPortableString();
							serviceFileRefsPage.setSelectFilePath(selectFilePathTmp);
							serviceFileRefsPage.setSelectSOABEModel(false);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				// This gives us a directory...
				if (selectedResource instanceof IFolder || selectedResource instanceof IProject) {
					// Set this for the container.
					newFileCreationPage.setContainerFullPath(selectedResource.getFullPath());

					// Make up a unique new name here.
					String defaultModelBaseFilename = Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicedefEditorFilenameDefaultBase");
					String defaultModelFilenameExtension = FILE_EXTENSIONS.get(0);
					String modelFilename = defaultModelBaseFilename + "." + defaultModelFilenameExtension;
					for (int i = 1; ((IContainer)selectedResource).findMember(modelFilename) != null; ++i) {
						modelFilename = defaultModelBaseFilename + i + "." + defaultModelFilenameExtension;
					}
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

}
