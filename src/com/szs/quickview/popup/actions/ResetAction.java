package com.szs.quickview.popup.actions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.internal.resources.File;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ResetAction implements IObjectActionDelegate {


	/**
	 * Constructor for Action1.
	 */
	public ResetAction() {
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

		try {
			OutputStream s = new FileOutputStream(f);
			s.write(ConfigAction.getDefaultConfig().getBytes());
//			s.write("\r\n\r\n".getBytes());
//			s.write("#说明：\r\n".getBytes());
//			s.write("#1. port:服务器的Tomcat的端口号 （默认为8080）\r\n".getBytes());
//			s.write("#2. url :要浏览的服务器的 （默认为本机localhost）（默认为8080）\r\n".getBytes());
//			s.write("#3. mode:使用的浏览器，填写浏览器的路径，路径中不能使用“\\”，应使用“/”或“\\\\”代替“\\”（默认为ie）\r\n".getBytes());
			s.flush();
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		MessageDialog.openInformation(
				new Shell(),
				"Message                            --Xiaoshan",
				"Reset succeed.");
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	private ISelection selection;

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
