/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package it.csi.mddtools.servicegen.presentation;

import java.util.Properties;

import it.csi.mddtools.appresources.provider.Resources_metamodelEditPlugin;

import it.csi.mddtools.servicedef.provider.Servicedef_metamodelEditPlugin;

import it.csi.mddtools.svcorch.provider.SvcorchEditPlugin;
import it.csi.mddtools.typedef.provider.Typedef_metamodelEditPlugin;

import mddtools.usagetracking.ProfilingPacketBuilder;
import mddtools.usagetracking.TrackingSender;

import org.eclipse.emf.common.EMFPlugin;

import org.eclipse.emf.common.ui.EclipseUIPlugin;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.ui.IStartup;

/**
 * This is the central singleton for the Servicegen_metamodel editor plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class Servicegen_metamodelEditorPlugin extends EMFPlugin {
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final Servicegen_metamodelEditorPlugin INSTANCE = new Servicegen_metamodelEditorPlugin();
	
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Implementation plugin;

	/**
	 * Create the instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Servicegen_metamodelEditorPlugin() {
		super
			(new ResourceLocator [] {
				Resources_metamodelEditPlugin.INSTANCE,
				Servicedef_metamodelEditPlugin.INSTANCE,
				SvcorchEditPlugin.INSTANCE,
				Typedef_metamodelEditPlugin.INSTANCE,
			});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}
	
	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin() {
		return plugin;
	}
	
	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public static class Implementation extends EclipseUIPlugin{
		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated NOT
		 */
		public Implementation() {
			super();
	
			// Remember the static instance.
			//
			plugin = this;
			
			manageTracking();
		}
		
		private static final String PLUGIN_NAME = "servicegen";
		private static final String PLUGIN_VERSION = "1.1.0.001";
		
		/**
		 * @generated NOT
		 */
		public static void manageTracking(){
			Properties packet = mddtools.usagetracking.ProfilingPacketBuilder.packStartupInfo(PLUGIN_NAME, PLUGIN_VERSION);
			packet.list(System.out);
			String whoName = packet.getProperty(ProfilingPacketBuilder.P_WHO_NAME);
			if (whoName == null || whoName.length()==0){
				//ask for registration
				// TODO
				System.out.println("ask for registration");
			}
			else{
				TrackingSender.sendTrackingInfo(packet);
			}
		}

	}

}
