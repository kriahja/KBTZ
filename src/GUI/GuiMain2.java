/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BLL.DisplayCtrlManager;
import BLL.ImageManager;
import BLL.TextManager;
import Entities.DisplayCtrl;
import Entities.Image;
import Entities.Text;
import GUI.ImageTable.ImageTable;
import GUI.ImageTable.ImageTableModel;
import GUI.PresentationTable.PresentationTable;
import GUI.PresentationTable.PresentationTableModel;

import GUI.TextTable.TextTable;
import GUI.TextTable.TextTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Date;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Zalan
 */
public class GuiMain2 extends javax.swing.JFrame {

    /**
     * Creates new form GuiMain2
     */
    private TextTable textTable;
    private TextTableModel textModel;
    private ImageTable imageTable;
    private ImageTableModel imageModel;
    private PresentationTable presTable;
    private PresentationTableModel presModel;

    private final DisplayCtrlManager dcMgr;
    DisplayCtrl pres;

    private final TextManager tMgr;
    private final ImageManager iMgr;

    String title;
    String txt;
    Date startDate;
    Date endDate;
    double timer;
    int displayId;
    int index;
    int presTypeId;
    boolean notSafe = false;
    Image image;
    Text text;
    Text tt;
    Image img;
    int id;

    public GuiMain2() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        pnlEditFolder.setVisible(false);
        pnlCreateFolder.setVisible(false);

        tMgr = TextManager.getInstance();
        iMgr = ImageManager.getInstance();
        dcMgr = DisplayCtrlManager.getInstance();

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screensize.getWidth();
        int height = (int) screensize.getHeight();
        PresentationList();

        pnlTableCardText.setVisible(false);
        pnlTableCardImage.setVisible(false);
        btnEditChosen.setEnabled(false);
        btnRemoveChosen.setEnabled(false);
        pnlCreateTypeAndDisplay.setVisible(false);
        lblCreateWarningType.setVisible(false);
        lblCreateWarningDisplay.setVisible(false);
        pnlTableCardText.setVisible(false);

    }

    private void PresentationList() {
        presModel = new PresentationTableModel(dcMgr.readAllPres());

        presTable = new PresentationTable(presModel);

        pnlPresentationTableCont.add(new JScrollPane(presTable), BorderLayout.CENTER);

        presTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    if (presTable.getSelectedRow() != -1) {
                        showPresData();
                    } else {
                        clearEditData();
                    }
                }
            }
        });
        presModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                // Clearing and setting back the selection triggers the 
                // selection listener of the JTable 
                presTable.clearSelection();
                //  presTable.setRowSelectionInterval(0, 0);
                presTable.setRowSelectionInterval(tme.getFirstRow(), tme.getFirstRow());
            }
        });

        ((JComponent) presTable.getDefaultRenderer(Boolean.class)).setOpaque(true);
    }

    private void showPresData() {
        pres = presModel.getDisplayCtrlByRow(presTable.convertRowIndexToModel(presTable.getSelectedRow()));
    }

    private void presData() {
        //   pres = tableModel.getDisplayCtrlByRow(table.convertRowIndexToModel(table.getSelectedRow()));
    }

    private void TextList() {
        textModel = new TextTableModel(tMgr.readAll());

        textTable = new TextTable(textModel);

        pnlTableCardText.add(new JScrollPane(textTable), BorderLayout.CENTER);

//        textTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent lse) {
//                if (!lse.getValueIsAdjusting()) {
//                    if (textTable.getSelectedRow() != -1) {
//                        showTextData();
//                    } else {
//                        clearEditData();
//                    }
//                }
//            }
//        });
    }

    private void showTextData() {
        text = textModel.getTextByRow(textTable.convertRowIndexToModel(textTable.getSelectedRow()));

        txtEditTitle.setText(text.getTitle());
        txtEditTextArea.setText(text.getText());
        tt = tMgr.getByTitle(text.getTitle());
        id = tt.getId();

        dpEditStartDate.setDate(text.getStartDate());
        dpEditEndDate.setDate(text.getEndDate());
        //String.valueOf(double)
        txtEditTimer.setText(String.valueOf(text.getTimer()));
//        cbxChooseDisplay.setSelectedIndex(text.getDisplayId() - 1);
        //jPrior.setSelectedIndex(text.getPriorityId() - 1);
        cxEditNotSafe.setSelected(text.isNotSafe());

        btnEditChosen.setEnabled(true);
        btnRemoveChosen.setEnabled(true);
        System.out.println("Test for edit button");

    }

    private void ImageList() {
        imageModel = new ImageTableModel(iMgr.readAll());

        imageTable = new ImageTable(imageModel);

        pnlTableCardImage.add(new JScrollPane(imageTable), BorderLayout.CENTER);

//        imageTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
//        {
//            @Override
//            public void valueChanged(ListSelectionEvent lse)
//            {
//                if (!lse.getValueIsAdjusting())
//                {
//                    if (imageTable.getSelectedRow() != -1)
//                    {
//                        showImageData();
//                    }
//                    else
//                    {
//                        clearEditData();
//                    }
//                }
//            }
//        });
    }

    private void showImageData() {
        image = imageModel.getImageByRow(imageTable.convertRowIndexToModel(imageTable.getSelectedRow()));

        txtEditTitle.setText(image.getTitle());
        cbxFolder.setSelectedItem(image.getPath());
        img = iMgr.getByTitle(image.getTitle());
        id = img.getId();

        dpEditStartDate.setDate(image.getStartDate());
        dpEditEndDate.setDate(image.getEndDate());
        //String.valueOf(double)
        txtEditTimer.setText(String.valueOf(image.getTimer()));

        cxEditNotSafe.setSelected(image.isNotSafe());

        btnEdit.setEnabled(true);
        btnRemoveChosen.setEnabled(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        cardContainer = new javax.swing.JPanel();
        presentationCard = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        lblPresentationTitle = new javax.swing.JLabel();
        lblPresentationSubtitle = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlPresentationTableCont = new javax.swing.JPanel();
        settingsCard = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JSeparator();
        lblSettingsSubtitle = new javax.swing.JLabel();
        lblSettingsTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlLeftSettings = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        pnlCreateTypeAndDisplay = new javax.swing.JPanel();
        cbxChooseDisplay = new javax.swing.JComboBox();
        cbxPresentationType = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        lblCreateWarningType = new javax.swing.JLabel();
        lblCreateWarningDisplay = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JToggleButton();
        btnCreate = new javax.swing.JToggleButton();
        CardLayoutSet = new javax.swing.JPanel();
        pnlClean = new javax.swing.JPanel();
        pnlCreate = new javax.swing.JPanel();
        txtCreateTitle = new javax.swing.JTextField();
        createHeading = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        closeCreate = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCreateTimer = new javax.swing.JTextField();
        cxCreateNotSafe = new javax.swing.JCheckBox();
        dpCreateStartDate = new org.jdesktop.swingx.JXDatePicker();
        dpCreateEndDate = new org.jdesktop.swingx.JXDatePicker();
        btnCancelCreate = new javax.swing.JButton();
        btnCreateNew = new javax.swing.JButton();
        pnlCreateFolder = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cbxCreateFolder = new javax.swing.JComboBox();
        pnlTextArea = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCreateTextArea = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        pnlEdit = new javax.swing.JPanel();
        editHeader = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        MinimizeEdit = new javax.swing.JLabel();
        pnlEditCardContainer = new javax.swing.JPanel();
        pnlEditCard1 = new javax.swing.JPanel();
        btnRemoveChosen = new javax.swing.JButton();
        btnEditChosen = new javax.swing.JButton();
        pnlTableCardMain = new javax.swing.JPanel();
        pnlTableCardImage = new javax.swing.JPanel();
        pnlTableCardText = new javax.swing.JPanel();
        pnlTableClearLayout = new javax.swing.JPanel();
        pnlEditCard2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtEditTitle = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        cxEditNotSafe = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        dpEditStartDate = new org.jdesktop.swingx.JXDatePicker();
        dpEditEndDate = new org.jdesktop.swingx.JXDatePicker();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtEditTimer = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnCancelUpdate = new javax.swing.JButton();
        pnlEditFolder = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cbxFolder = new javax.swing.JComboBox();
        pnlTextAreaCont = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEditTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        navPanel.setBackground(new java.awt.Color(67, 74, 84));

        jButton1.setBackground(new java.awt.Color(76, 64, 84));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/btnPresentation.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(76, 64, 84));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/btnSettings.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/belmanLogoWhite.png"))); // NOI18N

        javax.swing.GroupLayout navPanelLayout = new javax.swing.GroupLayout(navPanel);
        navPanel.setLayout(navPanelLayout);
        navPanelLayout.setHorizontalGroup(
            navPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navPanelLayout.createSequentialGroup()
                .addGroup(navPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(navPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navPanelLayout.setVerticalGroup(
            navPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navPanelLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton1)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton2)
                .addGap(0, 0, 0)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 610, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        getContentPane().add(navPanel, java.awt.BorderLayout.LINE_START);

        cardContainer.setBackground(new java.awt.Color(255, 255, 255));
        cardContainer.setLayout(new java.awt.CardLayout());

        presentationCard.setBackground(new java.awt.Color(255, 255, 255));

        lblPresentationTitle.setBackground(new java.awt.Color(67, 74, 84));
        lblPresentationTitle.setFont(new java.awt.Font("Ebrima", 1, 28)); // NOI18N
        lblPresentationTitle.setForeground(new java.awt.Color(67, 74, 84));
        lblPresentationTitle.setText("Presentations");

        lblPresentationSubtitle.setBackground(new java.awt.Color(101, 109, 120));
        lblPresentationSubtitle.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        lblPresentationSubtitle.setForeground(new java.awt.Color(101, 109, 120));
        lblPresentationSubtitle.setText("See all available presentation for screens:");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Header3.png"))); // NOI18N

        pnlPresentationTableCont.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout presentationCardLayout = new javax.swing.GroupLayout(presentationCard);
        presentationCard.setLayout(presentationCardLayout);
        presentationCardLayout.setHorizontalGroup(
            presentationCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(presentationCardLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 368, Short.MAX_VALUE))
            .addGroup(presentationCardLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(presentationCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPresentationSubtitle)
                    .addComponent(lblPresentationTitle)
                    .addComponent(pnlPresentationTableCont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(473, 473, 473))
        );
        presentationCardLayout.setVerticalGroup(
            presentationCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(presentationCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPresentationTitle)
                .addGap(0, 0, 0)
                .addComponent(lblPresentationSubtitle)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPresentationTableCont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(355, 355, 355)
                .addComponent(jLabel3))
        );

        cardContainer.add(presentationCard, "card2");

        settingsCard.setBackground(new java.awt.Color(255, 255, 255));

        lblSettingsSubtitle.setBackground(new java.awt.Color(101, 109, 120));
        lblSettingsSubtitle.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        lblSettingsSubtitle.setForeground(new java.awt.Color(101, 109, 120));
        lblSettingsSubtitle.setText("Create, edit or delete presentations:");

        lblSettingsTitle.setBackground(new java.awt.Color(67, 74, 84));
        lblSettingsTitle.setFont(new java.awt.Font("Ebrima", 1, 28)); // NOI18N
        lblSettingsTitle.setForeground(new java.awt.Color(67, 74, 84));
        lblSettingsTitle.setText("Settings");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Header3.png"))); // NOI18N

        pnlLeftSettings.setBackground(new java.awt.Color(245, 247, 250));

        jPanel2.setBackground(new java.awt.Color(230, 233, 237));

        pnlCreateTypeAndDisplay.setBackground(new java.awt.Color(230, 233, 237));

        cbxChooseDisplay.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        cbxChooseDisplay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose Display:", "1", "2", "3", "4" }));
        cbxChooseDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxChooseDisplayActionPerformed(evt);
            }
        });

        cbxPresentationType.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        cbxPresentationType.setForeground(new java.awt.Color(67, 74, 84));
        cbxPresentationType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Presentation Type:", "Text", "Image", "Video", "Calendar" }));
        cbxPresentationType.setBorder(null);
        cbxPresentationType.setOpaque(false);
        cbxPresentationType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPresentationTypeItemStateChanged(evt);
            }
        });
        cbxPresentationType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxPresentationTypeActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel13.setText("Display:");

        javax.swing.GroupLayout pnlCreateTypeAndDisplayLayout = new javax.swing.GroupLayout(pnlCreateTypeAndDisplay);
        pnlCreateTypeAndDisplay.setLayout(pnlCreateTypeAndDisplayLayout);
        pnlCreateTypeAndDisplayLayout.setHorizontalGroup(
            pnlCreateTypeAndDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateTypeAndDisplayLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnlCreateTypeAndDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxChooseDisplay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlCreateTypeAndDisplayLayout.createSequentialGroup()
                        .addGroup(pnlCreateTypeAndDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxPresentationType, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlCreateTypeAndDisplayLayout.setVerticalGroup(
            pnlCreateTypeAndDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateTypeAndDisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxPresentationType, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxChooseDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblCreateWarningType.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        lblCreateWarningType.setForeground(new java.awt.Color(255, 0, 0));
        lblCreateWarningType.setText("Please select a Presentation Type!");

        lblCreateWarningDisplay.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        lblCreateWarningDisplay.setForeground(new java.awt.Color(255, 0, 0));
        lblCreateWarningDisplay.setText("Please select a Display!");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlCreateTypeAndDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCreateWarningType)
                    .addComponent(lblCreateWarningDisplay)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(pnlCreateTypeAndDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCreateWarningType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCreateWarningDisplay)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(67, 74, 84));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Select presentation:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEdit.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        btnEdit.setText("Edit Existing");
        btnEdit.setFocusPainted(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnCreate.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        btnCreate.setText("Create New");
        btnCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCreate.setFocusPainted(false);
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLeftSettingsLayout = new javax.swing.GroupLayout(pnlLeftSettings);
        pnlLeftSettings.setLayout(pnlLeftSettingsLayout);
        pnlLeftSettingsLayout.setHorizontalGroup(
            pnlLeftSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLeftSettingsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlLeftSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        pnlLeftSettingsLayout.setVerticalGroup(
            pnlLeftSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftSettingsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        CardLayoutSet.setLayout(new java.awt.CardLayout());

        pnlClean.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlCleanLayout = new javax.swing.GroupLayout(pnlClean);
        pnlClean.setLayout(pnlCleanLayout);
        pnlCleanLayout.setHorizontalGroup(
            pnlCleanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 969, Short.MAX_VALUE)
        );
        pnlCleanLayout.setVerticalGroup(
            pnlCleanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
        );

        CardLayoutSet.add(pnlClean, "card4");

        pnlCreate.setBackground(new java.awt.Color(255, 255, 255));

        txtCreateTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCreateTitleActionPerformed(evt);
            }
        });

        createHeading.setBackground(new java.awt.Color(67, 74, 84));

        jLabel5.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Create New Presentation:");

        closeCreate.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        closeCreate.setForeground(new java.awt.Color(255, 255, 255));
        closeCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/delete85.png"))); // NOI18N
        closeCreate.setToolTipText("Close and Reset Operation");
        closeCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeCreate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeCreateMouseClicked(evt);
            }
        });

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/minimize.png"))); // NOI18N
        minimize.setToolTipText("Hide window, progress wil not be reset.");
        minimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout createHeadingLayout = new javax.swing.GroupLayout(createHeading);
        createHeading.setLayout(createHeadingLayout);
        createHeadingLayout.setHorizontalGroup(
            createHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createHeadingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(minimize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeCreate)
                .addGap(10, 10, 10))
        );
        createHeadingLayout.setVerticalGroup(
            createHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createHeadingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(createHeadingLayout.createSequentialGroup()
                .addGroup(createHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(minimize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(closeCreate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        closeCreate.getAccessibleContext().setAccessibleName("Close");
        minimize.getAccessibleContext().setAccessibleName("Minimize");

        jLabel9.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel9.setText("Title:");

        jLabel11.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel11.setText("Start Date:");

        jLabel12.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel12.setText("End Date:");

        jLabel14.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel14.setText("Security:");

        jLabel15.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel15.setText("Timer:");

        cxCreateNotSafe.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        cxCreateNotSafe.setText("Not Safe");
        cxCreateNotSafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cxCreateNotSafeActionPerformed(evt);
            }
        });

        btnCancelCreate.setBackground(new java.awt.Color(67, 74, 84));
        btnCancelCreate.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        btnCancelCreate.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelCreate.setText("Cancel");
        btnCancelCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelCreateActionPerformed(evt);
            }
        });

        btnCreateNew.setBackground(new java.awt.Color(67, 74, 84));
        btnCreateNew.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        btnCreateNew.setForeground(new java.awt.Color(255, 255, 255));
        btnCreateNew.setText("Create");
        btnCreateNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateNewActionPerformed(evt);
            }
        });

        pnlCreateFolder.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel16.setText("Folder:");

        cbxCreateFolder.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlCreateFolderLayout = new javax.swing.GroupLayout(pnlCreateFolder);
        pnlCreateFolder.setLayout(pnlCreateFolderLayout);
        pnlCreateFolderLayout.setHorizontalGroup(
            pnlCreateFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateFolderLayout.createSequentialGroup()
                .addGroup(pnlCreateFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(cbxCreateFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 44, Short.MAX_VALUE))
        );
        pnlCreateFolderLayout.setVerticalGroup(
            pnlCreateFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateFolderLayout.createSequentialGroup()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxCreateFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        pnlTextArea.setBackground(new java.awt.Color(255, 255, 255));

        txtCreateTextArea.setColumns(20);
        txtCreateTextArea.setRows(5);
        jScrollPane1.setViewportView(txtCreateTextArea);

        jLabel10.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel10.setText("Text:");

        javax.swing.GroupLayout pnlTextAreaLayout = new javax.swing.GroupLayout(pnlTextArea);
        pnlTextArea.setLayout(pnlTextAreaLayout);
        pnlTextAreaLayout.setHorizontalGroup(
            pnlTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTextAreaLayout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
        );
        pnlTextAreaLayout.setVerticalGroup(
            pnlTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTextAreaLayout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlCreateLayout = new javax.swing.GroupLayout(pnlCreate);
        pnlCreate.setLayout(pnlCreateLayout);
        pnlCreateLayout.setHorizontalGroup(
            pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(createHeading, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlCreateLayout.createSequentialGroup()
                .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCreateLayout.createSequentialGroup()
                        .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCreateTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cxCreateNotSafe)
                            .addComponent(jLabel14)))
                    .addGroup(pnlCreateLayout.createSequentialGroup()
                        .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dpCreateStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(36, 36, 36)
                        .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(dpCreateEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtCreateTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addGroup(pnlCreateLayout.createSequentialGroup()
                        .addComponent(btnCreateNew)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelCreate))
                    .addComponent(pnlCreateFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 483, Short.MAX_VALUE))
        );
        pnlCreateLayout.setVerticalGroup(
            pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(createHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCreateTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cxCreateNotSafe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dpCreateStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dpCreateEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCreateTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 23, Short.MAX_VALUE)
                .addComponent(pnlCreateFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateNew)
                    .addComponent(btnCancelCreate))
                .addContainerGap())
        );

        CardLayoutSet.add(pnlCreate, "card2");

        pnlEdit.setBackground(new java.awt.Color(255, 255, 255));

        editHeader.setBackground(new java.awt.Color(67, 74, 84));

        jLabel6.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Edit Existing Presentation:");

        jLabel8.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/delete85.png"))); // NOI18N
        jLabel8.setToolTipText("Close and Reset Operation");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        MinimizeEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/minimize.png"))); // NOI18N
        MinimizeEdit.setToolTipText("Hide window, progress wil not be reset.");
        MinimizeEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MinimizeEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MinimizeEditMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout editHeaderLayout = new javax.swing.GroupLayout(editHeader);
        editHeader.setLayout(editHeaderLayout);
        editHeaderLayout.setHorizontalGroup(
            editHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(MinimizeEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(10, 10, 10))
        );
        editHeaderLayout.setVerticalGroup(
            editHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(editHeaderLayout.createSequentialGroup()
                .addGroup(editHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MinimizeEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel8.getAccessibleContext().setAccessibleName("Close");
        MinimizeEdit.getAccessibleContext().setAccessibleName("Minimize");

        pnlEditCardContainer.setBackground(new java.awt.Color(255, 255, 255));
        pnlEditCardContainer.setForeground(new java.awt.Color(67, 74, 84));
        pnlEditCardContainer.setLayout(new java.awt.CardLayout());

        pnlEditCard1.setBackground(new java.awt.Color(255, 255, 255));
        pnlEditCard1.setForeground(new java.awt.Color(67, 74, 84));

        btnRemoveChosen.setBackground(new java.awt.Color(67, 74, 84));
        btnRemoveChosen.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        btnRemoveChosen.setForeground(new java.awt.Color(255, 255, 255));
        btnRemoveChosen.setText("Remove");
        btnRemoveChosen.setBorderPainted(false);
        btnRemoveChosen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoveChosen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveChosenActionPerformed(evt);
            }
        });

        btnEditChosen.setBackground(new java.awt.Color(67, 74, 84));
        btnEditChosen.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        btnEditChosen.setForeground(new java.awt.Color(255, 255, 255));
        btnEditChosen.setText("Edit");
        btnEditChosen.setBorderPainted(false);
        btnEditChosen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditChosen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditChosenActionPerformed(evt);
            }
        });

        pnlTableCardMain.setLayout(new java.awt.CardLayout());

        pnlTableCardImage.setLayout(new java.awt.BorderLayout());
        pnlTableCardMain.add(pnlTableCardImage, "card2");

        pnlTableCardText.setLayout(new java.awt.BorderLayout());
        pnlTableCardMain.add(pnlTableCardText, "card2");

        pnlTableClearLayout.setBackground(new java.awt.Color(230, 233, 237));

        javax.swing.GroupLayout pnlTableClearLayoutLayout = new javax.swing.GroupLayout(pnlTableClearLayout);
        pnlTableClearLayout.setLayout(pnlTableClearLayoutLayout);
        pnlTableClearLayoutLayout.setHorizontalGroup(
            pnlTableClearLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 522, Short.MAX_VALUE)
        );
        pnlTableClearLayoutLayout.setVerticalGroup(
            pnlTableClearLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );

        pnlTableCardMain.add(pnlTableClearLayout, "card4");

        javax.swing.GroupLayout pnlEditCard1Layout = new javax.swing.GroupLayout(pnlEditCard1);
        pnlEditCard1.setLayout(pnlEditCard1Layout);
        pnlEditCard1Layout.setHorizontalGroup(
            pnlEditCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditCard1Layout.createSequentialGroup()
                .addComponent(pnlTableCardMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlEditCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRemoveChosen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditChosen, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(358, 358, 358))
        );
        pnlEditCard1Layout.setVerticalGroup(
            pnlEditCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditCard1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTableCardMain, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlEditCard1Layout.createSequentialGroup()
                        .addComponent(btnEditChosen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveChosen)))
                .addContainerGap(302, Short.MAX_VALUE))
        );

        pnlEditCardContainer.add(pnlEditCard1, "card3");

        pnlEditCard2.setBackground(new java.awt.Color(255, 255, 255));
        pnlEditCard2.setForeground(new java.awt.Color(67, 74, 84));

        jLabel17.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel17.setText("Title:");

        txtEditTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditTitleActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel19.setText("Security:");

        cxEditNotSafe.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        cxEditNotSafe.setText("Not Safe");
        cxEditNotSafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cxEditNotSafeActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel20.setText("Start Date:");

        jLabel21.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel21.setText("End Date:");

        jLabel22.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel22.setText("Timer:");

        btnUpdate.setBackground(new java.awt.Color(67, 74, 84));
        btnUpdate.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnCancelUpdate.setBackground(new java.awt.Color(67, 74, 84));
        btnCancelUpdate.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        btnCancelUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelUpdate.setText("Cancel");
        btnCancelUpdate.setBorderPainted(false);
        btnCancelUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelUpdateActionPerformed(evt);
            }
        });

        jLabel7.setText("Folder:");

        cbxFolder.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlEditFolderLayout = new javax.swing.GroupLayout(pnlEditFolder);
        pnlEditFolder.setLayout(pnlEditFolderLayout);
        pnlEditFolderLayout.setHorizontalGroup(
            pnlEditFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditFolderLayout.createSequentialGroup()
                .addGroup(pnlEditFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(cbxFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 25, Short.MAX_VALUE))
        );
        pnlEditFolderLayout.setVerticalGroup(
            pnlEditFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditFolderLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pnlTextAreaCont.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel18.setText("Text:");

        txtEditTextArea.setColumns(20);
        txtEditTextArea.setRows(5);
        jScrollPane2.setViewportView(txtEditTextArea);

        javax.swing.GroupLayout pnlTextAreaContLayout = new javax.swing.GroupLayout(pnlTextAreaCont);
        pnlTextAreaCont.setLayout(pnlTextAreaContLayout);
        pnlTextAreaContLayout.setHorizontalGroup(
            pnlTextAreaContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlTextAreaContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlTextAreaContLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pnlTextAreaContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        pnlTextAreaContLayout.setVerticalGroup(
            pnlTextAreaContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 239, Short.MAX_VALUE)
            .addGroup(pnlTextAreaContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlTextAreaContLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel18)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout pnlEditCard2Layout = new javax.swing.GroupLayout(pnlEditCard2);
        pnlEditCard2.setLayout(pnlEditCard2Layout);
        pnlEditCard2Layout.setHorizontalGroup(
            pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditCard2Layout.createSequentialGroup()
                .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEditTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(pnlEditFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(869, Short.MAX_VALUE))
            .addGroup(pnlEditCard2Layout.createSequentialGroup()
                .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlEditCard2Layout.createSequentialGroup()
                        .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEditTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cxEditNotSafe)
                            .addComponent(jLabel19)))
                    .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlEditCard2Layout.createSequentialGroup()
                            .addComponent(btnUpdate)
                            .addGap(26, 26, 26)
                            .addComponent(btnCancelUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlEditCard2Layout.createSequentialGroup()
                            .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dpEditStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel20))
                            .addGap(36, 36, 36)
                            .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel21)
                                .addComponent(dpEditEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(pnlTextAreaCont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlEditCard2Layout.setVerticalGroup(
            pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditCard2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEditTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cxEditNotSafe))
                .addGap(18, 18, 18)
                .addComponent(pnlTextAreaCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dpEditStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dpEditEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEditTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlEditFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelUpdate)
                    .addComponent(btnUpdate)))
        );

        pnlEditCardContainer.add(pnlEditCard2, "card2");

        javax.swing.GroupLayout pnlEditLayout = new javax.swing.GroupLayout(pnlEdit);
        pnlEdit.setLayout(pnlEditLayout);
        pnlEditLayout.setHorizontalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlEditCardContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlEditLayout.setVerticalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditLayout.createSequentialGroup()
                .addComponent(editHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlEditCardContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        CardLayoutSet.add(pnlEdit, "card3");

        javax.swing.GroupLayout settingsCardLayout = new javax.swing.GroupLayout(settingsCard);
        settingsCard.setLayout(settingsCardLayout);
        settingsCardLayout.setHorizontalGroup(
            settingsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator5)
            .addGroup(settingsCardLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(settingsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSettingsSubtitle)
                    .addComponent(lblSettingsTitle))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(settingsCardLayout.createSequentialGroup()
                .addGroup(settingsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsCardLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 831, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(settingsCardLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(pnlLeftSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CardLayoutSet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        settingsCardLayout.setVerticalGroup(
            settingsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSettingsTitle)
                .addGap(0, 0, 0)
                .addComponent(lblSettingsSubtitle)
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(settingsCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsCardLayout.createSequentialGroup()
                        .addComponent(CardLayoutSet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE))
                    .addGroup(settingsCardLayout.createSequentialGroup()
                        .addComponent(pnlLeftSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLabel2))
        );

        cardContainer.add(settingsCard, "card3");

        getContentPane().add(cardContainer, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        presentationCard.setVisible(true);
        settingsCard.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        settingsCard.setVisible(true);
        presentationCard.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        pnlCreateTypeAndDisplay.setVisible(true);
        btnEdit.setSelected(false);
        btnCreate.setSelected(true);
        pnlCreate.setVisible(true);
        pnlClean.setVisible(false);
        pnlEdit.setVisible(false);
        cbxChooseDisplay.setEnabled(true);
        cbxPresentationType.setSelectedIndex(0);
        cbxChooseDisplay.setSelectedIndex(0);
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        cbxPresentationType.setSelectedIndex(0);
        cbxChooseDisplay.setSelectedIndex(0);
        pnlCreateTypeAndDisplay.setVisible(true);
        cbxChooseDisplay.setEnabled(false);

        btnCreate.setSelected(false);
        btnEdit.setSelected(true);
        pnlEdit.setVisible(true);
        pnlCreate.setVisible(false);
        pnlClean.setVisible(false);
//        pnlCreateTypeAndDisplay.setVisible(false);
    }//GEN-LAST:event_btnEditActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        pnlEdit.setVisible(false);
        btnCreate.setSelected(false);
        btnEdit.setSelected(false);

    }//GEN-LAST:event_jLabel8MouseClicked

    private void closeCreateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeCreateMouseClicked
        clearCreateData();
    }//GEN-LAST:event_closeCreateMouseClicked

    private void btnEditChosenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditChosenActionPerformed
        pnlEditCard2.setVisible(true);
        pnlEditCard1.setVisible(false);
        
        if (cbxPresentationType.getSelectedIndex() == 1) {          
          showTextData();
        }
        else if (cbxPresentationType.getSelectedIndex() == 2) {           
           showImageData();
        }

    }//GEN-LAST:event_btnEditChosenActionPerformed

    private void btnRemoveChosenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveChosenActionPerformed
        pnlEditCard2.setVisible(false);
        pnlEditCard1.setVisible(true);
    }//GEN-LAST:event_btnRemoveChosenActionPerformed

    private void btnCancelUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelUpdateActionPerformed
        pnlEditCard2.setVisible(false);
        pnlEditCard1.setVisible(true);
        clearEditData();
    }//GEN-LAST:event_btnCancelUpdateActionPerformed

    private void btnCancelCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelCreateActionPerformed
        clearCreateData();
    }//GEN-LAST:event_btnCancelCreateActionPerformed

    private void txtCreateTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCreateTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreateTitleActionPerformed

    private void cxCreateNotSafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cxCreateNotSafeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cxCreateNotSafeActionPerformed

    private void txtEditTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEditTitleActionPerformed

    private void cxEditNotSafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cxEditNotSafeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cxEditNotSafeActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
         if(cbxPresentationType.getSelectedIndex() == 1){   
             updateText();
         }
         else if(cbxPresentationType.getSelectedIndex() == 2)
         {
             updateImage();
         }  
        
        clearEditData();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void cbxChooseDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxChooseDisplayActionPerformed
        if (cbxChooseDisplay.getSelectedIndex() != 0) {
            lblCreateWarningDisplay.setVisible(false);
        }
    }//GEN-LAST:event_cbxChooseDisplayActionPerformed

    private void btnCreateNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateNewActionPerformed
        if (cbxPresentationType.getSelectedIndex() == 0) {
            lblCreateWarningType.setVisible(true);
            lblCreateWarningDisplay.setVisible(false);
        } else if (cbxChooseDisplay.getSelectedIndex() == 0) {
            lblCreateWarningType.setVisible(false);
            lblCreateWarningDisplay.setVisible(true);
        } else {

            int selection = cbxPresentationType.getSelectedIndex();
            switch (selection) {
                case 1:
                    saveTextPresentation();
                    break;
                case 2:
                    saveImagePresentation();
                    break;
            }
            clearCreateData();
        }

    }//GEN-LAST:event_btnCreateNewActionPerformed

    private void saveTextPresentation() throws NumberFormatException {
        title = txtCreateTitle.getText();
        txt = txtCreateTextArea.getText();
        System.out.println(title + "  " + txt);
        presTypeId = cbxPresentationType.getSelectedIndex() + 1;
        startDate = new java.sql.Date(dpCreateStartDate.getDate().getTime());
        endDate = new java.sql.Date(dpCreateEndDate.getDate().getTime());
        timer = Double.parseDouble(txtCreateTimer.getText());
        displayId = cbxChooseDisplay.getSelectedIndex() + 1;
        notSafe = cxCreateNotSafe.isSelected();

        text = new Text(presTypeId, title, startDate, endDate, timer, notSafe, txt);
        tMgr.createText(text);
    }

    private void saveImagePresentation() throws NumberFormatException {
        title = txtCreateTitle.getText();

        System.out.println(title + "  " + txt);
        presTypeId = cbxPresentationType.getSelectedIndex() + 1;
        startDate = new java.sql.Date(dpCreateStartDate.getDate().getTime());
        endDate = new java.sql.Date(dpCreateEndDate.getDate().getTime());
        timer = Double.parseDouble(txtCreateTimer.getText());
        displayId = cbxChooseDisplay.getSelectedIndex() + 1;
        notSafe = cxCreateNotSafe.isSelected();

        image = new Image(presTypeId, title, startDate, endDate, timer, notSafe, txt);

    }
    
    private void updateText()
    {
        title = txtEditTitle.getText();
        txt = txtEditTextArea.getText();
        System.out.println(title + "  " + txt);
        presTypeId = cbxPresentationType.getSelectedIndex();
        startDate = new java.sql.Date(dpEditStartDate.getDate().getTime());
        endDate = new java.sql.Date(dpEditEndDate.getDate().getTime());
        timer = Double.parseDouble(txtEditTimer.getText());
        displayId = cbxChooseDisplay.getSelectedIndex() + 1;
        notSafe = cxCreateNotSafe.isSelected();
        System.out.println(presTypeId +  title + " "+ startDate + " "+  endDate + " "+  timer + " "+  notSafe + " "+  txt);
        text = new Text(presTypeId, title, startDate, endDate, timer, notSafe, txt);
        tMgr.updateText(text);
    }
    
    private void updateImage()
    {
        title = txtEditTitle.getText();

        System.out.println(title + "  " + txt);
        presTypeId = cbxPresentationType.getSelectedIndex() + 1;
        startDate = new java.sql.Date(dpEditStartDate.getDate().getTime());
        endDate = new java.sql.Date(dpEditEndDate.getDate().getTime());
        timer = Double.parseDouble(txtEditTimer.getText());
        displayId = cbxChooseDisplay.getSelectedIndex() + 1;
        notSafe = cxCreateNotSafe.isSelected();

        image = new Image(presTypeId, title, startDate, endDate, timer, notSafe, txt);
        iMgr.updateImage(image);
    }

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        pnlCreate.setVisible(false);
        pnlClean.setVisible(true);
    }//GEN-LAST:event_minimizeMouseClicked

    private void MinimizeEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinimizeEditMouseClicked
        pnlEdit.setVisible(false);
        pnlClean.setVisible(true);
    }//GEN-LAST:event_MinimizeEditMouseClicked

    private void cbxPresentationTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPresentationTypeActionPerformed
//        if (cbxPresentationType.getSelectedItem() == "Image") {
//            pnlEditFolder.setVisible(true);
//            pnlCreateFolder.setVisible(true);
//            pnlTextArea.setVisible(false);
//        } else {
//            pnlEditFolder.setVisible(false);
//            pnlCreateFolder.setVisible(false);
//            pnlTextArea.setVisible(true);
//        }
//        if (cbxPresentationType.getSelectedIndex() != 0) {
//            lblCreateWarningType.setVisible(false);
//        }
        
        if (cbxPresentationType.getSelectedIndex() == 1) {

            pnlTableCardText.setVisible(true);
            pnlTableCardImage.setVisible(false);
            pnlTableClearLayout.setVisible(false);
            TextList();
            btnEditChosen.setEnabled(true);
            btnRemoveChosen.setEnabled(true);

        } else if (cbxPresentationType.getSelectedIndex() == 2) {
            pnlEditFolder.setVisible(true);
            pnlCreateFolder.setVisible(true);
            pnlTextArea.setVisible(false);
            pnlTableCardImage.setVisible(true);
            pnlTableCardText.setVisible(false);
            pnlTableClearLayout.setVisible(false);
            ImageList();
            btnEditChosen.setEnabled(true);
            btnRemoveChosen.setEnabled(true);

        } else {
            pnlEditFolder.setVisible(false);
            pnlCreateFolder.setVisible(false);
            pnlTextArea.setVisible(true);
            pnlTableCardText.setVisible(false);
            pnlTableCardImage.setVisible(false);
            pnlTableClearLayout.setVisible(true);
            btnEditChosen.setEnabled(false);
            btnRemoveChosen.setEnabled(false);
        }

    }//GEN-LAST:event_cbxPresentationTypeActionPerformed

    private void cbxPresentationTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPresentationTypeItemStateChanged
//        pnlTableCardMain.setVisible(true);


    }//GEN-LAST:event_cbxPresentationTypeItemStateChanged

    public void clearCreateData() {
        pnlCreate.setVisible(false);
        btnCreate.setSelected(false);
        btnEdit.setSelected(false);
        txtCreateTitle.setText("");
        txtCreateTimer.setText("");
        txtCreateTextArea.setText("");
        cxCreateNotSafe.setSelected(false);
        cbxChooseDisplay.setSelectedIndex(0);
        cbxPresentationType.setSelectedIndex(0);
        dpCreateEndDate.setDate(null);
        dpCreateStartDate.setDate(null);
        pnlCreateTypeAndDisplay.setVisible(false);
        lblCreateWarningDisplay.setVisible(false);
        lblCreateWarningType.setVisible(false);
    }

    public void clearEditData() {
        pnlEditCard1.setVisible(false);
        btnCreate.setSelected(false);
        btnEdit.setSelected(true);
        txtEditTitle.setText("");
        txtEditTextArea.setText("");
        txtEditTextArea.setText("");
        cxEditNotSafe.setSelected(false);
        cbxChooseDisplay.setSelectedIndex(0);
        dpEditEndDate.setDate(null);
        dpEditStartDate.setDate(null);
        lblCreateWarningDisplay.setVisible(false);
        lblCreateWarningType.setVisible(false);
    }

    public void clearPresData() {
        pnlPresentationTableCont.setVisible(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuiMain2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiMain2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiMain2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiMain2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiMain2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CardLayoutSet;
    private javax.swing.JLabel MinimizeEdit;
    private javax.swing.JButton btnCancelCreate;
    private javax.swing.JButton btnCancelUpdate;
    private javax.swing.JToggleButton btnCreate;
    private javax.swing.JButton btnCreateNew;
    private javax.swing.JToggleButton btnEdit;
    private javax.swing.JButton btnEditChosen;
    private javax.swing.JButton btnRemoveChosen;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JPanel cardContainer;
    private javax.swing.JComboBox cbxChooseDisplay;
    private javax.swing.JComboBox cbxCreateFolder;
    private javax.swing.JComboBox cbxFolder;
    private javax.swing.JComboBox cbxPresentationType;
    private javax.swing.JLabel closeCreate;
    private javax.swing.JPanel createHeading;
    private javax.swing.JCheckBox cxCreateNotSafe;
    private javax.swing.JCheckBox cxEditNotSafe;
    private org.jdesktop.swingx.JXDatePicker dpCreateEndDate;
    private org.jdesktop.swingx.JXDatePicker dpCreateStartDate;
    private org.jdesktop.swingx.JXDatePicker dpEditEndDate;
    private org.jdesktop.swingx.JXDatePicker dpEditStartDate;
    private javax.swing.JPanel editHeader;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblCreateWarningDisplay;
    private javax.swing.JLabel lblCreateWarningType;
    private javax.swing.JLabel lblPresentationSubtitle;
    private javax.swing.JLabel lblPresentationTitle;
    private javax.swing.JLabel lblSettingsSubtitle;
    private javax.swing.JLabel lblSettingsTitle;
    private javax.swing.JLabel minimize;
    private javax.swing.JPanel navPanel;
    private javax.swing.JPanel pnlClean;
    private javax.swing.JPanel pnlCreate;
    private javax.swing.JPanel pnlCreateFolder;
    private javax.swing.JPanel pnlCreateTypeAndDisplay;
    private javax.swing.JPanel pnlEdit;
    private javax.swing.JPanel pnlEditCard1;
    private javax.swing.JPanel pnlEditCard2;
    private javax.swing.JPanel pnlEditCardContainer;
    private javax.swing.JPanel pnlEditFolder;
    private javax.swing.JPanel pnlLeftSettings;
    private javax.swing.JPanel pnlPresentationTableCont;
    private javax.swing.JPanel pnlTableCardImage;
    private javax.swing.JPanel pnlTableCardMain;
    private javax.swing.JPanel pnlTableCardText;
    private javax.swing.JPanel pnlTableClearLayout;
    private javax.swing.JPanel pnlTextArea;
    private javax.swing.JPanel pnlTextAreaCont;
    private javax.swing.JPanel presentationCard;
    private javax.swing.JPanel settingsCard;
    private javax.swing.JTextArea txtCreateTextArea;
    private javax.swing.JTextField txtCreateTimer;
    private javax.swing.JTextField txtCreateTitle;
    private javax.swing.JTextArea txtEditTextArea;
    private javax.swing.JTextField txtEditTimer;
    private javax.swing.JTextField txtEditTitle;
    // End of variables declaration//GEN-END:variables
}
