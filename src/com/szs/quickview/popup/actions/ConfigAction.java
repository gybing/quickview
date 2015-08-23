package com.szs.quickview.popup.actions;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ConfigAction implements IObjectActionDelegate {

	public static final String PORT_KEY = "port";
	public static final String URL_KEY = "url";
	public static final String MODE_KEY = "mode";
	public static final String ELIDES_KEY = "elides";

	public static String getDefaultConfig(){
		return PORT_KEY + "=8080\r\n" 
		+ URL_KEY + "=localhost\r\n"
		+ MODE_KEY + "=ie\r\n" 
		+ "#" + MODE_KEY + "=C:\\\\Program Files\\\\Mozilla Firefox\\\\firefox.exe\r\n"
		+ "#====== " + ELIDES_KEY + " multiple split by \",\" eg: (\"WebRoot/Start.tml\" ----> \"http://localhost:8080/***/Start\") ======\r\n"
		+ ELIDES_KEY + "=tml";
	}
	
	
	/**
	 * Constructor for Action1.
	 */
	public ConfigAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	@SuppressWarnings("restriction")
	public void run(IAction action) {
		java.io.File f = new java.io.File("quickView.prop");
		if (!f.exists()) {
			try {
				OutputStream s = new FileOutputStream(f);
				s.write(getDefaultConfig().getBytes());
				s.flush();
				s.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Runtime.getRuntime().exec(
					"cmd /c start notepad " + f.getAbsoluteFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	@SuppressWarnings("unused")
	private ISelection selection;

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
