package it.csi.mddtools.servicegen.presentation;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.FileSelectionDialog;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (guigen).
 */

public class CommonFilesLocChooserWizardPage extends WizardPage {
	private Text commonFilesContainerText;

	private Path commonFilesFolder;
	
	private ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public CommonFilesLocChooserWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("Selezione risorse comuni");
		setDescription("Selezionare la posizione dei file di risorse comuni (TypeNamespace e AppData) che saranno utilizzati nel modello");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText("Common &Type Namespace:");

		commonFilesContainerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		commonFilesContainerText.setLayoutData(gd);
		commonFilesContainerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogCommonContainerChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowseCommonContainer();
			}
		});
		
		
		
		
//		label = new Label(container, SWT.NULL);
//		label.setText("&File name:");
//
//		commonAppdataText = new Text(container, SWT.BORDER | SWT.SINGLE);
//		gd = new GridData(GridData.FILL_HORIZONTAL);
//		commonAppdataText.setLayoutData(gd);
//		commonAppdataText.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent e) {
//				dialogTNSChanged();
//			}
//		});
		initialize();
		dialogCommonContainerChanged();
		
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				commonFilesContainerText.setText(container.getFullPath().toString());
			}
		}
//		commonAppdataText.setText("commonTNS.guigen");
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowseCommonContainer() {
//		ResourceSelectionDialog dialog = new ResourceSelectionDialog(
//				getShell(), ResourcesPlugin.getWorkspace().getRoot(), "Seleziona il file commmonTNS");
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
				"Seleziona il folder che contiene i file [commonTNS.guigen] e [commonAppdata.guigen] (devono essere già presenti)");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				Path f = (Path) result[0];
				commonFilesContainerText.setText(f.toString());
				commonFilesFolder = f;
			}
			else
				commonFilesContainerText.setText(null);
		}
	}

	
	
	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogCommonContainerChanged() {
		IResource _commonContainer = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(getCommonContainerName()));
		commonFilesFolder=new Path(getCommonContainerName());
		//String fileName = getFileName();

		if (getCommonContainerName().length() == 0) {
			updateStatus("Specificare il nome del file commonTNS");
			return;
		}
		if (_commonContainer == null
				|| (_commonContainer.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("La directory "+getCommonContainerName()+" deve esistere");
			return;
		}
		if (!_commonContainer.isAccessible()) {
			updateStatus("Il progetto deve essere writable");
			return;
		}
		
		IResource _commonTNS = ResourcesPlugin.getWorkspace().getRoot()
			.findMember(new Path(getCommonContainerName()+"/commonTNS.guigen"));
		
		IResource _commonAppdata = ResourcesPlugin.getWorkspace().getRoot()
			.findMember(new Path(getCommonContainerName()+"/commonAppdata.guigen"));
		
		if (_commonTNS == null){
			updateStatus("Il file [commonTNS.guigen] deve esistere");
			return;	
		}
		if (_commonAppdata == null){
			updateStatus("Il file [commonAppdata.guigen] deve esistere");
			return;	
		}
//		if (fileName.length() == 0) {
//			updateStatus("File name must be specified");
//			return;
//		}
//		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
//			updateStatus("File name must be valid");
//			return;
//		}
//		int dotLoc = fileName.lastIndexOf('.');
//		if (dotLoc != -1) {
//			String ext = fileName.substring(dotLoc + 1);
//			if (ext.equalsIgnoreCase("guigen") == false) {
//				updateStatus("File extension must be \"guigen\"");
//				return;
//			}
//		}
		updateStatus(null);
	}

	
	

	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getCommonContainerName() {
		if (commonFilesContainerText.getText().endsWith("/"))
			return commonFilesContainerText.getText();
		else
			return commonFilesContainerText.getText()+"/";
	}
	public Path getCommonFilesFolder(){
		return commonFilesFolder;
	}

//	public String getFileName() {
//		return fileText.getText();
//	}
}