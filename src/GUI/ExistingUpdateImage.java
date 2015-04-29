/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BLL.ImageManager;
import Entities.Image;
import GUI.ImageTable.ImageTable;
import GUI.ImageTable.ImageTableModel;
import java.awt.BorderLayout;
import java.io.File;
import java.sql.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author a.tamas
 */
public class ExistingUpdateImage extends javax.swing.JFrame
{

    private ImageTable imageTable;
    private ImageTableModel imageModel;
    private final ImageManager iMgr;

    String title;
    String path;
    Date startDate;
    Date endDate;
    double timer;
    int displayId;
    int priorityId;
    boolean notSafe = false;
    Image image;
    int id;
    Image tt;

    JFrame frame;

    File shared;
    String[] folders;

    /**
     * Creates new form ExistingUpdateImage
     */
    public ExistingUpdateImage()
    {
        iMgr = ImageManager.getInstance();
        initComponents();
        ImageList();

        shared = new File("c:/Info/images");

        folders = shared.list();
        //  folders = shared.listFiles();
        for (String path : folders)
        {
            jFolder.addItem(path);
        }

        btnEdit.setEnabled(false);
        btnRemove.setEnabled(false);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void ImageList()
    {
        imageModel = new ImageTableModel(iMgr.readAll());

        imageTable = new ImageTable(imageModel);

        pnlJTable.add(new JScrollPane(imageTable), BorderLayout.CENTER);

        imageTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent lse)
            {
                if (!lse.getValueIsAdjusting())
                {
                    if (imageTable.getSelectedRow() != -1)
                    {
                        showImageData();
                    }
                    else
                    {
                        clearData();
                    }
                }
            }
        });
    }

    private void showImageData()
    {
        image = imageModel.getImageByRow(imageTable.convertRowIndexToModel(imageTable.getSelectedRow()));

        txtTitle.setText(image.getTitle());
        jFolder.setSelectedItem(image.getPath());
        tt = iMgr.getByTitle(image.getTitle());
        id = tt.getId();

        jStartDate.setDate(image.getStartDate());
        jEndDate.setDate(image.getEndDate());
        //String.valueOf(double)
        jTimer.setText(String.valueOf(image.getTimer()));
        jDisplay.setSelectedIndex(image.getDisplayId() - 1);
        jPrior.setSelectedIndex(image.getPriorityId() - 1);
        jNotSafe.setSelected(image.isNotSafe());

        btnEdit.setEnabled(true);
        btnRemove.setEnabled(true);

    }

    private void clearData()
    {
        txtTitle.setText("");

        btnEdit.setEnabled(false);
        btnRemove.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jTabPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        pnlJTable = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtTitle = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnNext2 = new javax.swing.JButton();
        jFolder = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTimer = new javax.swing.JTextField();
        jDisplay = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPrior = new javax.swing.JComboBox();
        jNotSafe = new javax.swing.JCheckBox();
        btnUpdate = new javax.swing.JButton();
        jStartDate = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        jEndDate = new org.jdesktop.swingx.JXDatePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlJTable.setLayout(new java.awt.BorderLayout());

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnEditActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemove)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlJTable, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlJTable, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabPane.addTab("tab1", jPanel1);

        txtTitle.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                txtTitleActionPerformed(evt);
            }
        });

        jLabel1.setText("Title");

        btnNext2.setText("Next");
        btnNext2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnNext2ActionPerformed(evt);
            }
        });

        jFolder.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jFolderActionPerformed(evt);
            }
        });

        jLabel8.setText("Folder Name");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnNext2)))
                        .addGap(35, 35, 35))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(129, 129, 129)
                .addComponent(btnNext2)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabPane.addTab("tab2", jPanel2);

        jLabel4.setText("End Date");

        jLabel6.setText("Timer");

        jDisplay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));

        jLabel7.setText("Display Nr.");

        jLabel5.setText("Priority");

        jPrior.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "High", "Medium", "Low" }));

        jNotSafe.setText("Not Safe");
        jNotSafe.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jNotSafeActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel3.setText("Start Date");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jPrior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(305, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jNotSafe)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdate)))
                        .addGap(25, 25, 25))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jPrior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(jNotSafe))
                .addGap(21, 21, 21))
        );

        jTabPane.addTab("tab3", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabPane, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnEditActionPerformed
    {//GEN-HEADEREND:event_btnEditActionPerformed
        // TODO add your handling code here:
        jTabPane.setSelectedIndex(1);
    }//GEN-LAST:event_btnEditActionPerformed

    private void txtTitleActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_txtTitleActionPerformed
    {//GEN-HEADEREND:event_txtTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTitleActionPerformed

    private void btnNext2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNext2ActionPerformed
    {//GEN-HEADEREND:event_btnNext2ActionPerformed
        // TODO add your handling code here:
        jTabPane.setSelectedIndex(2);
    }//GEN-LAST:event_btnNext2ActionPerformed

    private void jNotSafeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jNotSafeActionPerformed
    {//GEN-HEADEREND:event_jNotSafeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jNotSafeActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnUpdateActionPerformed
    {//GEN-HEADEREND:event_btnUpdateActionPerformed
        // TODO add your handling code here:

        title = txtTitle.getText();
        path = jFolder.getSelectedItem().toString();
        System.out.println(title + "  " + path);

        startDate = new java.sql.Date(jStartDate.getDate().getTime());
        //       startDate = (java.sql.Date) jStartDate.getDate().getTime();
        endDate = new java.sql.Date(jEndDate.getDate().getTime());
        timer = Double.parseDouble(jTimer.getText());
        displayId = jDisplay.getSelectedIndex() + 1;
        priorityId = jPrior.getSelectedIndex() + 1;
        notSafe = jNotSafe.isSelected();
        image = new Image(id, title, path, startDate, endDate, timer, displayId, notSafe, priorityId);
        System.out.println(image.getId());
        iMgr.updateImage(image);

        dispose();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnRemoveActionPerformed
    {//GEN-HEADEREND:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        int row = imageTable.getSelectedRow();

        // fetch the text to remove from the table model.
        image = imageModel.getImageByRow(row);
        int option = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to remove: " + image.getTitle(),
                "Remove Window",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION)
        {
            try
            {
                // remove the employee from the model and also from the table model.
                iMgr.deleteImage(id);

                imageModel.setImageList(iMgr.readAll());

                // adjust the selection since the selected employee is now gone.
                adjustSelection(row);
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(this, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (option == JOptionPane.NO_OPTION)
        {
            //Do Nothing here.
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void jFolderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jFolderActionPerformed
    {//GEN-HEADEREND:event_jFolderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFolderActionPerformed

    private void adjustSelection(int row)
    {
        if (imageModel.getRowCount() > 0)
        {
            if (row == imageModel.getRowCount())
            {
                row--;
            }

            imageTable.setRowSelectionInterval(row, row);
        }
        else
        {
            imageTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(ExistingUpdateImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(ExistingUpdateImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(ExistingUpdateImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(ExistingUpdateImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new ExistingUpdateImage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNext2;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox jDisplay;
    private org.jdesktop.swingx.JXDatePicker jEndDate;
    private javax.swing.JComboBox jFolder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JCheckBox jNotSafe;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JComboBox jPrior;
    private org.jdesktop.swingx.JXDatePicker jStartDate;
    private javax.swing.JTabbedPane jTabPane;
    private javax.swing.JTextField jTimer;
    private javax.swing.JPanel pnlJTable;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables
}
