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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

import org.eclipse.emf.common.CommonPlugin;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.xmi.XMLResource;

import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;

import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.jface.viewers.IStructuredSelection;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import org.eclipse.swt.SWT;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import org.eclipse.ui.actions.WorkspaceModifyOperation;

import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;

import it.csi.mddtools.servicedef.ServicedefFactory;
import it.csi.mddtools.servicedef.ServicedefPackage;
import it.csi.mddtools.servicedef.provider.Servicedef_metamodelEditPlugin;
import it.csi.mddtools.servicegen.presentation.CommonFilesLocChooserWizardPage;
import it.csi.mddtools.servicegen.presentation.Servicegen_metamodelEditorPlugin;
import it.csi.mddtools.servicegen.provider.Servicegen_metamodelEditPlugin;


import org.eclipse.core.runtime.Path;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;


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
	 * This caches an instance of the model factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServicedefFactory servicedefFactory = servicedefPackage.getServicedefFactory();

	/**
	 * This is the file creation page.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServicedefModelWizardNewFileCreationPage newFileCreationPage;

	/**
	 * This is the initial object creation page.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServicedefModelWizardInitialObjectCreationPage initialObjectCreationPage;

	
	protected CommonFilesLocChooserWizardPage commonFilesPage;
	
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
	 * @generated
	 */
	protected EObject createInitialModel() {
		EClass eClass = (EClass)servicedefPackage.getEClassifier(initialObjectCreationPage.getInitialObjectName());
		EObject rootObject = servicedefFactory.create(eClass);
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
		 * @generated
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
		
				Label codProdottoLabel = new Label(composite, SWT.LEFT);
				{
					codProdottoLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_CodServizio_label"));
		
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					codProdottoLabel.setLayoutData(data);
				}
		
				codServizio = new org.eclipse.swt.widgets.Text(composite, SWT.BORDER);
				{
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					data.grabExcessHorizontalSpace = true;
					codServizio.setLayoutData(data);
					codServizio.addModifyListener(validator);
				}
				
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
				
				Label codComponenteLabel = new Label(composite, SWT.LEFT);
				{
					codComponenteLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_TipoServizio_label"));
		
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					codComponenteLabel.setLayoutData(data);
				}
				
				tipoServizio = new Combo(composite, SWT.BORDER);
				{
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					data.grabExcessHorizontalSpace = true;
					tipoServizio.setLayoutData(data);
				}
		
				tipoServizio.add("Servizio applicativo");
				tipoServizio.add("Servizio di orchestrazione");
				tipoServizio.add("Servizio infrastrutturale");
				
				if (tipoServizio.getItemCount() == 1) {
					tipoServizio.select(0);
				}
				//tipoServizio.addModifyListener(validator);
				
				Label verComponenteLabel = new Label(composite, SWT.LEFT);
				{
					verComponenteLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_DummyMethodName_label"));
		
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL;
					verComponenteLabel.setLayoutData(data);
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
				//return getInitialObjectName() != null && getEncodings().contains(encodingField.getText());
				return getCodServizio()!=null && getTipoServizio()!=null&&
				getVerServizio()!=null && getDummyNethodName()!=null;
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
			
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			protected org.eclipse.swt.widgets.Text rootModelFile;
			private Text modelFileText;
			private String modelFileName;
			
			
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
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated NOT
			 */
			public String getRootModelFile() {
				String txt = rootModelFile.getText();
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
		
				Label label = new Label(composite, SWT.NULL);
				label.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_RootModelFile_label"));
				rootModelFile = new Text(composite, SWT.BORDER | SWT.SINGLE);
				GridData gd = new GridData(GridData.FILL_HORIZONTAL);
				rootModelFile.setLayoutData(gd);
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
				
				
//				Label codProdottoLabel = new Label(composite, SWT.LEFT);
//				{
//					codProdottoLabel.setText(Servicegen_metamodelEditorPlugin.INSTANCE.getString("_UI_RootModelFile_label"));
//		
//					GridData data = new GridData();
//					data.horizontalAlignment = GridData.FILL;
//					codProdottoLabel.setLayoutData(data);
//				}
//		
//				rootModelFile = new org.eclipse.swt.widgets.Text(composite, SWT.BORDER);
//				{
//					GridData data = new GridData();
//					data.horizontalAlignment = GridData.FILL;
//					data.grabExcessHorizontalSpace = true;
//					rootModelFile.setLayoutData(data);
//					rootModelFile.addModifyListener(validator);
//				}
				
				
				
				
				
				
				setPageComplete(validatePage());
				setControl(composite);
			}
		
			private void handleRootModelFileBrowse() {
//				org.eclipse.swt.widgets.FileDialog dialog = new org.eclipse.swt.widgets.FileDialog(
//						getShell());
				ResourceSelectionDialog dialog = new ResourceSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), "Scegliere file del modello principale");
				if (dialog.open() == ResourceSelectionDialog.OK) {
					Object[] result = dialog.getResult();
					if (result.length >0 && result[0] instanceof IFile) {
						String modelFileSelected = ((IFile) result[0]).getFullPath().toString(); 
						rootModelFile.setText(modelFileSelected);
					}
				}
				//modelFileText.setText(dialog.open());
			}
			
			private void dialogChanged() {
//				IResource container = ResourcesPlugin.getWorkspace().getRoot()
//						.findMember(new Path(getCommonFilesContainerName()));
				modelFileName=modelFileText.getText();
				
				String fileName = getRootModelFile();

				
				
//				IResource commonAppdataRes = ResourcesPlugin.getWorkspace().getRoot()
//				.findMember(new Path(getCommonFilesContainerName()+"/"+"commonAppdata.guigen"));
//				
//				if (commonTNSRes==null || commonAppdataRes==null || !commonTNSRes.exists() || !commonAppdataRes.exists()){
//					updateStatus("La cartella specificata deve contenere i file [commonTNS.guigen] e [commonAppdata.guigen]");
//					return;
//				}
				
				//// controlli sul model file
				if (fileName.length() == 0) {
					updateStatus("Occorre specificare il percorso del file che contiene il modello principale");
					return;
				}
				if (!fileName.endsWith(".servicegen")){
					updateStatus("Il file che contiene il modello principale deve avere l'estensione 'servicegen'");
					return;
				}
				IResource modelFile = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(new Path(getRootModelFile()));
				if (!modelFile.exists()){
					updateStatus("Il file che contiene il modello principale deve esistere");
					return;
				}
				
//				if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
//					updateStatus("File name must be valid");
//					return;
//				}
				int dotLoc = fileName.lastIndexOf('.');
				if (dotLoc != -1) {
					String ext = fileName.substring(dotLoc + 1);
					if (ext.equalsIgnoreCase("servicegen") == false) {
						updateStatus("File extension must be \"servicegen\"");
						return;
					}
				}
				
				updateStatus(null);
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
				return true; // TODO
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
		newFileCreationPage = new ServicedefModelWizardNewFileCreationPage("Whatever", selection);
		newFileCreationPage.setTitle(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicedefModelWizard_label"));
		newFileCreationPage.setDescription(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicedefModelWizard_description"));
		newFileCreationPage.setFileName(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicedefEditorFilenameDefaultBase") + "." + FILE_EXTENSIONS.get(0));
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
		initialObjectCreationPage = new ServicedefModelWizardInitialObjectCreationPage("Whatever2");
		initialObjectCreationPage.setTitle(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_ServicedefModelWizard_label"));
		initialObjectCreationPage.setDescription(Servicedef_metamodelEditorPlugin.INSTANCE.getString("_UI_Wizard_initial_object_description"));
		addPage(initialObjectCreationPage);
		
		commonFilesPage = new CommonFilesLocChooserWizardPage(selection);
		commonFilesPage.setTitle("Cartella file comuni");
		commonFilesPage.setDescription("Selezionare la cartella contenente i file \"commonTNS.guigen\" e \"commonAppdata.guigen\"");
		addPage(commonFilesPage);
		
		serviceFileRefsPage = new ServicedefModelWizardFileRefsCreationPage("file_refs");
		serviceFileRefsPage.setTitle("Riferimenti a file");
		serviceFileRefsPage.setDescription("Inserire i riferimenti ai file referenziati");
		addPage(serviceFileRefsPage);
		
		serviceInfoPage = new ServicedefModelWizardServiceInfoCreationPage("service_info");
		serviceInfoPage.setTitle("Informazioni del servizio");
		serviceInfoPage.setDescription("Inserire le informazioni principali del servizio da creare (identificativi, tipologia, ...)");
		addPage(serviceInfoPage);
		
		
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
