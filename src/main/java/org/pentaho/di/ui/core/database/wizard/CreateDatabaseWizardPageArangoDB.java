/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2018 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.ui.core.database.wizard;

import org.pentaho.di.ui.core.widget.LabelComboVar;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.database.arangodb.ArangoDBDatabaseMeta;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.ui.core.PropsUI;

/**
 *
 * On page one we select the database connection ArangoDB specific settings 
 *
 * @author Adam Fowler <adam.fowler@hitachivantara.com>
 * @since 1.0 2018-03-06
 */
public class CreateDatabaseWizardPageArangoDB extends WizardPage {
  private static Class<?> PKG = ArangoDBDatabaseMeta.class; // for i18n purposes, needed by Translator2!!

  // ArangoDB Server
  private Label wlHost, wlPort, wlName, wlAuthScheme /*, wlUsername, wlPassword*/;
  private Text wHost, wPort, wName /*, wAuthScheme, wUsername, wPassword*/;
  private LabelComboVar cAuthScheme;
  private FormData fdlHost, fdlPort, fdlName, fdlAuthScheme /*, fdlUsername, fdlPassword*/;
  private FormData fdHost, fdPort, fdName, fdAuthScheme /*, fdUsername, fdPassword*/;

  private PropsUI props;
  private DatabaseMeta info;

  public CreateDatabaseWizardPageArangoDB(String arg, PropsUI props, DatabaseMeta info) {
    super(arg);
    this.props = props;
    this.info = info;

    setTitle(BaseMessages.getString(PKG, "CreateDatabaseWizardPageArangoDB.DialogTitle"));
    setDescription(BaseMessages.getString(PKG, "CreateDatabaseWizardPageArangoDB.DialogMessage"));

    setPageComplete(false);
  }

  public void createControl(Composite parent) {
    int margin = Const.MARGIN;
    int middle = props.getMiddlePct();

    // create the composite to hold the widgets
    Composite composite = new Composite(parent, SWT.NONE);
    props.setLook(composite);

    FormLayout compLayout = new FormLayout();
    compLayout.marginHeight = Const.FORM_MARGIN;
    compLayout.marginWidth = Const.FORM_MARGIN;
    composite.setLayout(compLayout);

    // HOST
    wlHost = new Label(composite, SWT.RIGHT);
    wlHost.setText(BaseMessages.getString(PKG, "CreateDatabaseWizardPageArangoDB.Host.Label"));
    props.setLook(wlHost);
    fdlHost = new FormData();
    fdlHost.top = new FormAttachment(0, 0);
    fdlHost.left = new FormAttachment(0, 0);
    fdlHost.right = new FormAttachment(middle, 0);
    wlHost.setLayoutData(fdlHost);
    wHost = new Text(composite, SWT.SINGLE | SWT.BORDER);
    props.setLook(wHost);
    fdHost = new FormData();
    fdHost.top = new FormAttachment(0, 0);
    fdHost.left = new FormAttachment(middle, margin);
    fdHost.right = new FormAttachment(100, 0);
    wHost.setLayoutData(fdHost);
    wHost.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent arg0) {
        setPageComplete(false);
      }
    });

    wlPort = new Label(composite, SWT.RIGHT);
    wlPort.setText(BaseMessages.getString(PKG, "CreateDatabaseWizardPageArangoDB.Port.Label"));
    props.setLook(wlPort);
    fdlPort = new FormData();
    fdlPort.top = new FormAttachment(wHost, margin);
    fdlPort.left = new FormAttachment(0, 0);
    fdlPort.right = new FormAttachment(middle, 0);
    wlPort.setLayoutData(fdlPort);
    wPort = new Text(composite, SWT.SINGLE | SWT.BORDER);
    props.setLook(wPort);
    fdPort = new FormData();
    fdPort.top = new FormAttachment(wHost, margin);
    fdPort.left = new FormAttachment(middle, margin);
    fdPort.right = new FormAttachment(100, 0);
    wPort.setLayoutData(fdPort);
    wPort.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent arg0) {
        setPageComplete(false);
      }
    });

    // Database name
    wlName = new Label(composite, SWT.RIGHT);
    wlName.setText(BaseMessages.getString(PKG, "CreateDatabaseWizardPageArangoDB.DatabaseName.Label"));
    props.setLook(wlName);
    fdlName = new FormData();
    fdlName.top = new FormAttachment(wPort, margin);
    fdlName.left = new FormAttachment(0, 0);
    fdlName.right = new FormAttachment(middle, 0);
    wlName.setLayoutData(fdlName);
    wName = new Text(composite, SWT.SINGLE | SWT.BORDER);
    props.setLook(wName);
    fdName = new FormData();
    fdName.top = new FormAttachment(wPort, margin);
    fdName.left = new FormAttachment(middle, margin);
    fdName.right = new FormAttachment(100, 0);
    wName.setLayoutData(fdName);
    wName.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent arg0) {
        setPageComplete(false);
      }
    });

    // set the composite as the control for this page
    setControl(composite);
  }

  public void setData() {
    wHost.setText(Const.NVL(info.getHostname(), "localhost"));
    wPort.setText(Const.NVL(info.getDatabasePortNumberString(), "8529"));
    wName.setText(Const.NVL(info.getDatabaseName(), "_system"));
  }

  public boolean canFlipToNextPage() {
    String server = wHost.getText() != null ? wHost.getText().length() > 0 ? wHost.getText() : null : null;
    String port = wPort.getText() != null ? wPort.getText().length() > 0 ? wPort.getText() : null : null;

    //if (authScheme == null) {
    //  setErrorMessage(BaseMessages.getString(PKG, "CreateDatabaseWizardPageArangoDB.ErrorMessage.InvalidInput"));
    //  return false;
    //} else {
      getDatabaseInfo();
      setErrorMessage(null);
      setMessage(BaseMessages.getString(PKG, "CreateDatabaseWizardPageArangoDB.Message.Next"));
      return true;
    //}

  }

  public DatabaseMeta getDatabaseInfo() {
    if (wHost.getText() != null && wHost.getText().length() > 0) {
      //  info.getAttributes().put(ArangoDBDatabaseMeta.ATTRIBUTE_HOST,wHost.getText());
      info.setHostname(wHost.getText());
    }

    if (wPort.getText() != null && wPort.getText().length() > 0) {
      //  info.getAttributes().put(ArangoDBDatabaseMeta.ATTRIBUTE_PORT, wPort.getText());
      info.setDBPort(wPort.getText());
    }

    if (wName.getText() != null && wName.getText().length() > 0) {
      info.setDBName(wName.getText());
    }

    return info;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
   */
  public IWizardPage getNextPage() {
    IWizard wiz = getWizard();
    return wiz.getPage("2");
  }

}
