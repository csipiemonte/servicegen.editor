package it.csi.mddtools.servicegen.presentation;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.operation.*;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import java.io.*;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;

/**
 * This is a sample new wizard. Its role is to create a new file 
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace 
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "guigen". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class CommonFilesNewWizard extends Wizard implements INewWizard {
	private CommonFilesNewWizardPage page;
	private ISelection selection;

	private static final String BASETYPES_RESOURCE_NAME = "basetypes.servicegen";
	private static final String WS_BASETYPES_RESOURCE_NAME = "wsbasetypes.servicegen";

	/**
	 * Constructor for CommonTNSNewWizard.
	 */
	public CommonFilesNewWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new CommonFilesNewWizardPage(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		//final String fileName = page.getFileName();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 */

	private void doFinish(
		String containerName,
		IProgressMonitor monitor)
		throws CoreException {
		
		// create a sample file
		monitor.beginTask("Creating \""+BASETYPES_RESOURCE_NAME+"\" and \""+BASETYPES_RESOURCE_NAME+"\" ", 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}
		IContainer container = (IContainer) resource;

		///////
		final IFile fileTNS = container.getFile(new Path(BASETYPES_RESOURCE_NAME));
		try {
			InputStream stream = openContentStreamCommonTNS(BASETYPES_RESOURCE_NAME);
			if (fileTNS.exists()) {
				fileTNS.setContents(stream, true, true, monitor);
			} else {
				fileTNS.create(stream, true, monitor);
			}
			stream.close();
		} catch (IOException e) {
		}
		monitor.worked(1);
		
		///////
		final IFile fileWsTNS = container.getFile(new Path(WS_BASETYPES_RESOURCE_NAME));
		try {
			InputStream wsStream = openContentStreamCommonTNS(WS_BASETYPES_RESOURCE_NAME);
			if (fileWsTNS.exists()) {
				fileWsTNS.setContents(wsStream, true, true, monitor);
			} else {
				fileWsTNS.create(wsStream, true, monitor);
			}
			wsStream.close();
		} catch (IOException e) {
		}
		monitor.worked(1);
	}
	
	private InputStream openContentStreamCommonTNS(String fileName) {
		InputStream is = getClass().getResourceAsStream("/"+fileName);
		return is;
	}
	
	private void throwCoreException(String message) throws CoreException {
		IStatus status =
			new Status(IStatus.ERROR, "servicegen.editor", IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
}