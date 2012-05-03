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


import it.csi.mddtools.servicegen.SOABEModel;
import it.csi.mddtools.servicegen.ServicegenFactory;
import it.csi.mddtools.servicegen.ServicegenPackage;
import it.csi.mddtools.servicegen.TargetPlatformCodes;
import it.csi.mddtools.servicegen.provider.Servicegen_metamodelEditPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
public class ServicegenModelWizard extends Wizard implements INewWizard {
	
	private static final String BASETYPES_RES_NAME = "basetypes.servicegen";
	
	/**
	 * The supported extensions for created files.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<String> FILE_EXTENSIONS =
		Collections.unmodifiableList(Arrays.asList(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicegenEditorFilenameExtensions").split("\\s*,\\s*")));

	/**
	 * A formatted list of supported file extensions, suitable for display.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String FORMATTED_FILE_EXTENSIONS =
		Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicegenEditorFilenameExtensions").replaceAll("\\s*,\\s*", ", ");

	/**
	 * This caches an instance of the model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServicegenPackage servicegenPackage = ServicegenPackage.eINSTANCE;

	/**
	 * This caches an instance of the model factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServicegenFactory servicegenFactory = servicegenPackage.getServicegenFactory();

	/**
	 * This is the file creation page.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServicegenModelWizardNewFileCreationPage newFileCreationPage;

	/**
	 * This is the initial object creation page.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServicegenModelWizardInitialObjectCreationPage initialObjectCreationPage;

	
	/**
	 * This is the initial object creation page.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected ServicegenModelWizardAnaprodDataCreationPage anaprodDataCreationPage;
	
//	protected CommonFilesLocChooserWizardPage commonFilesPage;
	
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

	/**
	 * This just records the information.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_Wizard_label"));
		setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(Servicegen_metamodelEditorPlugin.INSTANCE.getImage("full/wizban/NewServicegen")));
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
			for (EClassifier eClassifier : servicegenPackage.getEClassifiers()) {
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
		if (cl.getName().equals("SOABEModel"))
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
		EClass eClass = (EClass)servicegenPackage.getEClassifier(initialObjectCreationPage.getInitialObjectName());
		EObject rootObject = servicegenFactory.create(eClass);
		
		
		if (initialObjectCreationPage.getInitialObjectName().indexOf("SOABEModel")==-1)
			throw new IllegalArgumentException("wizard ServicegenModelWizard supporta solo SOABEModel");
		{
			// crea tipi base
//			Type [] baseCSITypes = it.csi.mddtools.servicegen.genutils.CodeGenerationUtils.generateCSIBaseTypes();
			SOABEModel model = (SOABEModel)rootObject;
//			BaseTypes baseTypesContainer = servicegenFactory.createBaseTypes();
//			model.getBaseTypes().add(baseTypesContainer);
//			for (int i = 0; i < baseCSITypes.length; i++) {
//				baseTypesContainer.getBaseTypes().add(baseCSITypes[i]);
//			}
			// codici anaprod
			model.setCodProdotto(anaprodDataCreationPage.getCodProdotto());
			model.setCodComponente(anaprodDataCreationPage.getCodComponente());
			model.setVersioneProdotto(anaprodDataCreationPage.getVerProdotto());
			model.setVersioneComponente(anaprodDataCreationPage.getVerComponente());
			model.setTargetPlatform(ServicegenFactory.eINSTANCE.createTargetPlatform());
		}
		// se la classe � BaseTypes creo un modello di tipi base
//		else if (initialObjectCreationPage.getInitialObjectName().indexOf("BaseTypes")!=-1){
//			// crea tipi base
//			Type [] baseCSITypes = it.csi.mddtools.servicegen.genutils.CodeGenerationUtils.generateCSIBaseTypes();
//			BaseTypes model = (BaseTypes)rootObject;
//			for (int i = 0; i < baseCSITypes.length; i++) {
//				model.getBaseTypes().add(baseCSITypes[i]);
//			}
//		}
		return rootObject;
	}

	/**
	 * Do the work after everything is specified.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
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
							//
							ResourceSet resourceSet = new ResourceSetImpl();

							// Get the URI of the model file.
							//
							URI fileURI = URI.createPlatformResourceURI(modelFile.getFullPath().toString(), true);

							// Create a resource for this file.
							//
							Resource resource = resourceSet.createResource(fileURI);

							// Add the initial model object to the contents.
							//
							EObject rootObject = createInitialModel();
							if (rootObject != null) {
								resource.getContents().add(rootObject);
							}

							// Save the contents of the resource to the file system.
							//
							Map<Object, Object> options = new HashMap<Object, Object>();
							options.put(XMLResource.OPTION_ENCODING, initialObjectCreationPage.getEncoding());
							resource.save(options);
						}
						catch (Exception exception) {
							Servicegen_metamodelEditorPlugin.INSTANCE.log(exception);
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
				MessageDialog.openError(workbenchWindow.getShell(), Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_OpenEditorError_label"), exception.getMessage());
				return false;
			}

			return true;
		}
		catch (Exception exception) {
			Servicegen_metamodelEditorPlugin.INSTANCE.log(exception);
			return false;
		}
	}

	/**
	 * This is the one page of the wizard.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public class ServicegenModelWizardNewFileCreationPage extends WizardNewFileCreationPage {
		/**
		 * Pass in the selection.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public ServicegenModelWizardNewFileCreationPage(String pageId, IStructuredSelection selection) {
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
					setErrorMessage(Servicegen_metamodelEditorPlugin.INSTANCE.getString(key, new Object [] { FORMATTED_FILE_EXTENSIONS }));
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
	public class ServicegenModelWizardInitialObjectCreationPage extends WizardPage {
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
		public ServicegenModelWizardInitialObjectCreationPage(String pageId) {
			super(pageId);
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

			Label containerLabel = new Label(composite, SWT.LEFT);
			{
				containerLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_ModelObject"));

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
				encodingLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_XMLEncoding"));

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
		 * @generated NOT
		 */
		protected ModifyListener validator =
			new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					setPageComplete(validatePage());
					// TODO abilita/disabilita la pagina dei codici anaprod in base al tipo di oggetto selezionato
					
//					if (initialObjectField.getSelection().toString().indexOf("SOABEModel")!=-1){
//						anaprodDataCreationPage.setVisible(true);
//					}
//					else{
//						anaprodDataCreationPage.setVisible(false);
//						
//						anaprodDataCreationPage.codComponente.setText("N.U");
//						anaprodDataCreationPage.codProdotto.setText("N.U");
//						anaprodDataCreationPage.verComponente.setText("N.U");
//						anaprodDataCreationPage.verProdotto.setText("N.U");
//					}
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
		 * @generated
		 */
		protected Collection<String> getEncodings() {
			if (encodings == null) {
				encodings = new ArrayList<String>();
				for (StringTokenizer stringTokenizer = new StringTokenizer(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_XMLEncodingChoices")); stringTokenizer.hasMoreTokens(); ) {
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
	public class ServicegenModelWizardAnaprodDataCreationPage extends WizardPage {
		
		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		protected org.eclipse.swt.widgets.Text codProdotto;
		
		
		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		protected org.eclipse.swt.widgets.Text codComponente;
		
		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		protected org.eclipse.swt.widgets.Text verProdotto;
		
		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		protected org.eclipse.swt.widgets.Text verComponente;


		private Combo codeServerCombo;
	
		/**
		 * Pass in the selection.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		public ServicegenModelWizardAnaprodDataCreationPage(String pageId) {
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
		public String getVerProdotto() {
			String txt = verProdotto.getText();
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
		public String getVerComponente() {
			String txt = verComponente.getText();
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
			
			Label verProdottoLabel = new Label(composite, SWT.LEFT);
			{
				verProdottoLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_VerProdotto_label"));
	
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				verProdottoLabel.setLayoutData(data);
			}
			
			verProdotto = new org.eclipse.swt.widgets.Text(composite, SWT.BORDER);
			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				verProdotto.setLayoutData(data);
				verProdotto.addModifyListener(validator);
			}
			
			Label codComponenteLabel = new Label(composite, SWT.LEFT);
			{
				codComponenteLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_CodComponente_label"));
	
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				codComponenteLabel.setLayoutData(data);
			}
			
			codComponente = new org.eclipse.swt.widgets.Text(composite, SWT.BORDER);
			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				codComponente.setLayoutData(data);
				codComponente.addModifyListener(validator);
			}
	
			Label verComponenteLabel = new Label(composite, SWT.LEFT);
			{
				verComponenteLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_VerComponente_label"));
	
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				verComponenteLabel.setLayoutData(data);
			}
			
			verComponente = new org.eclipse.swt.widgets.Text(composite, SWT.BORDER);
			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				verComponente.setLayoutData(data);
				verComponente.addModifyListener(validator);
			}
			
			Label codeServerLabel = new Label(composite, SWT.LEFT);
			{
				codeServerLabel.setText("Application Server Target");
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				codeServerLabel.setLayoutData(data);
			}

			codeServerCombo = new Combo(composite, SWT.BORDER);
			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				codeServerCombo.setLayoutData(data);
			}
			
			
			for (Iterator<TargetPlatformCodes> iterator = TargetPlatformCodes.VALUES.iterator(); iterator.hasNext();) {
				TargetPlatformCodes targetPlatformCodes = (TargetPlatformCodes) iterator.next();
				codeServerCombo.add(targetPlatformCodes.getName());
				
			}
			codeServerCombo.select(0);
			codeServerCombo.addModifyListener(validator);
			
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
			//return getInitialObjectName() != null && getEncodings().contains(encodingField.getText());
			return getCodProdotto()!=null && getCodComponente()!=null&&
			getVerProdotto()!=null && getVerComponente()!=null;
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
	 * The framework calls this to create the contents of the wizard.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
		@Override
	public void addPages() {
		// Create a page, set the title, and the initial model file name.
		//
		newFileCreationPage = new ServicegenModelWizardNewFileCreationPage("Whatever", selection);
		newFileCreationPage.setTitle(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicegenModelWizard_label"));
		newFileCreationPage.setDescription(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicegenModelWizard_description"));
		newFileCreationPage.setFileName(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicegenEditorFilenameDefaultBase") + "." + FILE_EXTENSIONS.get(0));
		addPage(newFileCreationPage);

		// Try and get the resource selection to determine a current directory for the file dialog.
		//
		if (selection != null && !selection.isEmpty()) {
			// Get the resource...
			//
			Object selectedElement = selection.iterator().next();
			if (selectedElement instanceof IResource) {
				// Get the resource parent, if its a file.
				//
				IResource selectedResource = (IResource)selectedElement;
				if (selectedResource.getType() == IResource.FILE) {
					selectedResource = selectedResource.getParent();
				}

				// This gives us a directory...
				//
				if (selectedResource instanceof IFolder || selectedResource instanceof IProject) {
					// Set this for the container.
					//
					newFileCreationPage.setContainerFullPath(selectedResource.getFullPath());

					// Make up a unique new name here.
					//
					String defaultModelBaseFilename = Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicegenEditorFilenameDefaultBase");
					String defaultModelFilenameExtension = FILE_EXTENSIONS.get(0);
					String modelFilename = defaultModelBaseFilename + "." + defaultModelFilenameExtension;
					for (int i = 1; ((IContainer)selectedResource).findMember(modelFilename) != null; ++i) {
						modelFilename = defaultModelBaseFilename + i + "." + defaultModelFilenameExtension;
					}
					newFileCreationPage.setFileName(modelFilename);
				}
			}
		}
		initialObjectCreationPage = new ServicegenModelWizardInitialObjectCreationPage("Whatever2");
		initialObjectCreationPage.setTitle(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicegenModelWizard_label"));
		initialObjectCreationPage.setDescription(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_Wizard_initial_object_description"));
		addPage(initialObjectCreationPage);
		
		
		anaprodDataCreationPage = new ServicegenModelWizardAnaprodDataCreationPage("anaprodData");
		anaprodDataCreationPage.setTitle("Dati identificazione del componente");
		anaprodDataCreationPage.setDescription("Inserire i dati di identificazione del componente risultante come da specifiche ANAPROD");
		addPage(anaprodDataCreationPage);
	
		// TODO per ora non � utilizzato => commento
//		commonFilesPage = new CommonFilesLocChooserWizardPage(selection);
//		commonFilesPage.setTitle("Cartella file comuni");
//		commonFilesPage.setDescription("Selezionare la cartella contenente i file \"commonTNS.guigen\" e \"commonAppdata.guigen\"");
//		addPage(commonFilesPage);
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
	
	
	//////
	
	//////
	

}
