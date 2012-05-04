package it.csi.mddtools.servicegen.presentation.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class Utility {

	/**
	 * 
	 * @param fullPath
	 * @return EList
	 */
	public static EList loadResource(String fullPath) {

		EList emfContent = null;
		if (fullPath != null && !fullPath.equalsIgnoreCase("")) {
			try {
				URI uri = URI.createPlatformResourceURI(fullPath, true);
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource resource = resourceSet.createResource(uri);
				Map<Object, Object> options = new HashMap<Object, Object>();

				resource.load(options);

				emfContent = (EList) resource.getContents();

			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			}
		}
		return emfContent;
	}
}
