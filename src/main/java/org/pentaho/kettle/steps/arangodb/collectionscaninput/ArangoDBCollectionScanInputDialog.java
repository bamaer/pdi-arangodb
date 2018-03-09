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

package org.pentaho.kettle.steps.arangodb.collectionscaninput;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.database.arangodb.ArangoDBDatabaseMeta;

import org.pentaho.di.core.Const;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.util.List;
import java.util.ArrayList;

/**
 * Dialog box for the ArangoDB collection scan input step
 * 
 * @author Adam Fowler {@literal <adam.fowler@hitachivantara.com>}
 * @since 1.0 2018-03-07
 */
public class ArangoDBCollectionScanInputDialog extends BaseStepDialog implements StepDialogInterface {
  private static Class<?> PKG = ArangoDBCollectionScanInputMeta.class; // for i18n purposes, needed by Translator2!!

  private ArangoDBCollectionScanInputMeta input;

  private Label wlName;
  private Text wName;

  private FormData fdlName, fdName;

  private CCombo wConnection;
  /*
  private Label cplName;
  private Text cpName;
  private FormData fcplName, fcpName;
  
  private Label plName;
  private Text pName;
  private FormData fplName, fpName;
  
  private Label dslName;
  private Text dsName;
  private FormData fdslName, fdsName;
  
  private Label tlName;
  private Text tName;
  private FormData ftlName, ftName;
  */

  private Label lblCollection;
  private Text collection;
  private FormData flblCollection, fcollection;

  private Label lblDocUri;
  private Text docUri;
  private FormData flblDocUri, fdocUri;

  private Label lblDocContent;
  private Text docContent;
  private FormData flblDocContent, fdocContent;

  /*
  private Label wlFields;
  private TableView wFields;
  private FormData fdlFields,fdFields;
  */

  private Button wOK, wCancel;

  private Listener lsOK, lsCancel;

  private SelectionAdapter lsDef;

  private boolean changed = false;

  /**
   * Standard PDI dialog constructor
   */
  public ArangoDBCollectionScanInputDialog(Shell parent, Object in, TransMeta tr, String sname) {
    super(parent, (BaseStepMeta) in, tr, sname);
    input = (ArangoDBCollectionScanInputMeta) in;
  }

  /**
   * Initialises and displays the dialog box
   */
  public String open() {
    Shell parent = getParent();
    Display display = parent.getDisplay();

    shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MAX | SWT.MIN);
    props.setLook(shell);
    setShellImage(shell, input);

    ModifyListener lsMod = new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        input.setChanged();
      }
    };
    changed = input.hasChanged();

    ModifyListener lsConnectionMod = new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        input.setChanged();
      }
    };

    FormLayout formLayout = new FormLayout();
    formLayout.marginWidth = Const.FORM_MARGIN;
    formLayout.marginHeight = Const.FORM_MARGIN;

    shell.setLayout(formLayout);
    shell.setText(BaseMessages.getString(PKG, "ArangoDBCollectionScanInput.Title"));

    int middle = props.getMiddlePct();
    int margin = Const.MARGIN;

    // Step Name
    wlName = new Label(shell, SWT.RIGHT);
    wlName.setText(BaseMessages.getString(PKG, "ArangoDBCollectionScanInput.Name.Label"));
    props.setLook(wlName);
    fdlName = new FormData();
    fdlName.left = new FormAttachment(0, 0);
    fdlName.right = new FormAttachment(middle, -margin);
    fdlName.top = new FormAttachment(0, margin);
    wlName.setLayoutData(fdlName);
    wName = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    wName.setToolTipText(BaseMessages.getString(PKG, "ArangoDBCollectionScanInput.Name.Tooltip"));
    props.setLook(wName);
    wName.addModifyListener(lsMod);
    fdName = new FormData();
    fdName.left = new FormAttachment(middle, 0);
    fdName.top = new FormAttachment(0, margin);
    fdName.right = new FormAttachment(100, 0);
    wName.setLayoutData(fdName);

    // Database Connection

    wConnection = addConnectionLine(shell, wName, middle, margin);
    List<String> items = new ArrayList<String>();
    for (DatabaseMeta dbMeta : transMeta.getDatabases()) {
      if (dbMeta.getDatabaseInterface() instanceof ArangoDBDatabaseMeta) {
        items.add(dbMeta.getName());
      }
    }
    wConnection.setItems(items.toArray(new String[items.size()]));
    if (input.getDatabaseMeta() == null && transMeta.nrDatabases() == 1) {
      wConnection.select(0);
    }
    wConnection.addModifyListener(lsConnectionMod);

    // collection field
    lblCollection = new Label(shell, SWT.RIGHT);
    lblCollection.setText(BaseMessages.getString(PKG, "ArangoDBCollectionScanInput.Collection.Label"));
    props.setLook(lblCollection);
    flblCollection = new FormData();
    flblCollection.left = new FormAttachment(0, 0);
    flblCollection.right = new FormAttachment(middle, -margin);
    flblCollection.top = new FormAttachment(wConnection, 2 * margin);
    lblCollection.setLayoutData(flblCollection);
    collection = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    collection.setToolTipText(BaseMessages.getString(PKG, "ArangoDBCollectionScanInput.Collection.Tooltip"));
    props.setLook(collection);
    collection.addModifyListener(lsMod);
    fcollection = new FormData();
    fcollection.left = new FormAttachment(middle, 0);
    fcollection.top = new FormAttachment(wConnection, 2 * margin);
    fcollection.right = new FormAttachment(100, 0);
    collection.setLayoutData(fcollection);

    // documentUri Field
    lblDocUri = new Label(shell, SWT.RIGHT);
    lblDocUri.setText(BaseMessages.getString(PKG, "ArangoDBCollectionScanInput.DocKeyField.Label"));
    props.setLook(lblDocUri);
    flblDocUri = new FormData();
    flblDocUri.left = new FormAttachment(0, 0);
    flblDocUri.right = new FormAttachment(middle, -margin);
    flblDocUri.top = new FormAttachment(collection, margin);
    lblDocUri.setLayoutData(flblDocUri);
    docUri = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    docUri.setToolTipText(BaseMessages.getString(PKG, "ArangoDBCollectionScanInput.DocKeyField.Tooltip"));
    props.setLook(docUri);
    docUri.addModifyListener(lsMod);
    fdocUri = new FormData();
    fdocUri.left = new FormAttachment(middle, 0);
    fdocUri.top = new FormAttachment(collection, margin);
    fdocUri.right = new FormAttachment(100, 0);
    docUri.setLayoutData(fdocUri);

    // document Content field
    lblDocContent = new Label(shell, SWT.RIGHT);
    lblDocContent.setText(BaseMessages.getString(PKG, "ArangoDBCollectionScanInput.DocContentField.Label"));
    props.setLook(lblDocContent);
    flblDocContent = new FormData();
    flblDocContent.left = new FormAttachment(0, 0);
    flblDocContent.right = new FormAttachment(middle, -margin);
    flblDocContent.top = new FormAttachment(docUri, margin);
    lblDocContent.setLayoutData(flblDocContent);
    docContent = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    docContent.setToolTipText(BaseMessages.getString(PKG, "ArangoDBCollectionScanInput.DocContentField.Tooltip"));
    props.setLook(docContent);
    docContent.addModifyListener(lsMod);
    fdocContent = new FormData();
    fdocContent.left = new FormAttachment(middle, 0);
    fdocContent.top = new FormAttachment(docUri, margin);
    fdocContent.right = new FormAttachment(100, 0);
    docContent.setLayoutData(fdocContent);

    wOK = new Button(shell, SWT.PUSH);
    wOK.setText(BaseMessages.getString(PKG, "System.Button.OK"));
    wCancel = new Button(shell, SWT.PUSH);
    wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel"));

    BaseStepDialog.positionBottomButtons(shell, new Button[] { wOK, wCancel }, margin, docContent);

    // Add listeners
    lsCancel = new Listener() {
      public void handleEvent(Event e) {
        cancel();
      }
    };
    lsOK = new Listener() {
      public void handleEvent(Event e) {
        ok();
      }
    };

    wCancel.addListener(SWT.Selection, lsCancel);
    wOK.addListener(SWT.Selection, lsOK);

    lsDef = new SelectionAdapter() {
      public void widgetDefaultSelected(SelectionEvent e) {
        ok();
      }
    };
    // Detect X or ALT-F4 or something that kills this window...
    shell.addShellListener(new ShellAdapter() {
      public void shellClosed(ShellEvent e) {
        cancel();
      }
    });

    getData();
    //activeCopyFromPrevious();
    //activeUseKey();

    BaseStepDialog.setSize(shell);

    shell.open();
    props.setDialogSize(shell, "ArangoDBCollectionScanInputDialogSize");
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return stepname;

  }

  /**
   * Copy information from the meta-data input to the dialog fields.
   */
  public void getData() {
    wName.setText(Const.nullToEmpty(stepname));
    if (input.getDatabaseMeta() != null) {
      wConnection.setText(input.getDatabaseMeta().getName());
    } else if (transMeta.nrDatabases() == 1) {
      wConnection.setText(transMeta.getDatabase(0).getName());
    }
    collection.setText(Const.nullToEmpty(input.getCollection()));
    docUri.setText(Const.nullToEmpty(input.getDocumentKeyField()));
    docContent.setText(Const.nullToEmpty(input.getDocumentContentField()));

    wName.selectAll();
    wName.setFocus();
  }

  /**
   * Handles clicking cancel
   */
  private void cancel() {
    stepname = null;
    input.setChanged(changed);
    dispose();
  }

  private int showDatabaseWarning(boolean includeCancel) {
    MessageBox mb = new MessageBox(shell, SWT.OK | (includeCancel ? SWT.CANCEL : SWT.NONE) | SWT.ICON_ERROR);
    mb.setMessage(BaseMessages.getString(PKG, "ArangoDBCollectionScanInputDialog.InvalidConnection.DialogMessage"));
    mb.setText(BaseMessages.getString(PKG, "ArangoDBCollectionScanInputDialog.InvalidConnection.DialogTitle"));
    return mb.open();
  }

  /**
   * Saves data to the meta class instance
   */
  private void ok() {
    if (null == wName.getText() || "".equals(wName.getText().trim())) {
      MessageBox mb = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
      mb.setText(BaseMessages.getString(PKG, "System.StepJobEntryNameMissing.Title"));
      mb.setMessage(BaseMessages.getString(PKG, "System.JobEntryNameMissing.Msg"));
      mb.open();
      return;
    }
    stepname = wName.getText();
    //input.setName( wName.getText() );

    if (transMeta.findDatabase(wConnection.getText()) == null) {
      int answer = showDatabaseWarning(true);
      if (answer == SWT.CANCEL) {
        return;
      }
    } else {
      input.setDatabaseMeta(transMeta.findDatabase(wConnection.getText()));
    }
    //input.setHost(cpName.getText());
    //input.setPort(Integer.parseInt(pName.getText()));
    //input.setUsername(dsName.getText());
    //input.setPassword(tName.getText());
    String collectionField = collection.getText();
    if (null == collectionField || "".equals(collectionField.trim())) {
      MessageBox mb = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
      mb.setText(BaseMessages.getString(PKG, "ArangoDBCollectionScanInputDialog.CollectionMissing.Title"));
      mb.setMessage(BaseMessages.getString(PKG, "ArangoDBCollectionScanInputDialog.CollectionMissing.Msg"));
      mb.open();
      return;
    }
    input.setCollection(collectionField);
    input.setDocumentKeyField(docUri.getText());
    input.setDocumentContentField(docContent.getText());

    dispose();
  }

}