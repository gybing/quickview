package com.szs.quickview.popup.actions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.szs.quickview.utils.Static;

public class ViewAction implements IObjectActionDelegate {

	/**
	 * Constructor for Action1.
	 */
	public ViewAction() {
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
		Shell shell = new Shell();
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		File f = (File)structuredSelection.getFirstElement();
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new java.io.File("quickView.prop")));
		} catch (InvalidPropertiesFormatException e) {
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		String port = p.getProperty(ConfigAction.PORT_KEY, "8080");
		String url = p.getProperty(ConfigAction.URL_KEY, "localhost");
		String ie = p.getProperty(ConfigAction.MODE_KEY, "iexplore");
		String elidesStr = p.getProperty(ConfigAction.ELIDES_KEY, "");
		String[] houZhuiArray = null;
		if(elidesStr != null && elidesStr.trim().length() > 0){
			houZhuiArray = elidesStr.split(",");
		}
		

		ie = ie.toLowerCase().trim().equals("ie") ? "iexplore" : ie;

		String fileName = "/" + f.getLocation().toOSString().substring(f.getLocation().toOSString().lastIndexOf("WebRoot") + "WebRoot".length() + 1);
		String propUrl = f.getProject().getLocation().toOSString() + "\\.project";
		String webName = getProWebName(propUrl);

		if(houZhuiArray != null && houZhuiArray.length > 0){
			for(String houZhui : houZhuiArray){
				if(f.getFileExtension().equals(houZhui.trim())){
					fileName = fileName.replaceAll("(?i)\\."+houZhui.trim()+"$", "");
					break;
				}
			}
		}
		String ml = "\"Quick View Title\" \"" + ie + "\" \"http://" + url + ":" + port + webName + fileName + "\"";

		try {
			Runtime.getRuntime().exec("cmd /c start " + ml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	private ISelection selection;
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
	
	
	public String getProWebName(String url){
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
        Document doc = null;
		try {
			java.io.File f = new java.io.File(url);
			if (!f.exists()) {
				MessageDialog.openInformation(
						new Shell(),
						Static.TITLE,
						".project not found");
				return null;
			}
			doc = docBuilder.parse (f);
		} catch (SAXException e) {
			e.printStackTrace();
			MessageDialog.openInformation(
					new Shell(),
					Static.TITLE,
					"读取.project失败");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			MessageDialog.openInformation(
					new Shell(),
					Static.TITLE,
					"读取.project失败");
			return null;
		}

        NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
        String nameNode = null;
        for (int i = 0, j = nodeList.getLength(); i < j; i++) {
        	Node n = nodeList.item(i);
        	if (n.getNodeName().equals("name")) {
        		nameNode = n.getTextContent();
        		break;
        	}
        }
        		
        return "/"+ nameNode;
	}
}
