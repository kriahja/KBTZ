/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BLL.DisplayCtrlManager;
import BLL.DisplayManager;
import BLL.ImageManager;
import BLL.TextManager;
import Entities.DisplayCtrl;
import Entities.Image;
import Entities.Text;
import GUI.EditTable.EditTable;
import GUI.EditTable.EditTableModel;
import GUI.ImageTable.ImageTable;
import GUI.ImageTable.ImageTableModel;
import GUI.PresentationTable.PresentationTable;
import GUI.PresentationTable.PresentationTableModel;

import GUI.TextTable.TextTable;
import GUI.TextTable.TextTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
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
public class GuiMain2 extends javax.swing.JFrame
{

    /**
     * Creates new form GuiMain2
     */
    private TextTable textTable;
    private TextTableModel textModel;
    private ImageTable imageTable;
    private ImageTableModel imageModel;
    private PresentationTable presTable;
    private PresentationTableModel presModel;

    private EditTable editTable;
    private EditTableModel editModel;

    private final DisplayCtrlManager dcMgr;
    DisplayCtrl pres;

    private final TextManager tMgr;
    private final ImageManager iMgr;

    private final DisplayManager dMgr;
    ArrayList<DisplayCtrl> dcList;
    ArrayList<String> dispList;
    String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    String title;
    String txt;
    String fontType = "Arial";
    int fontSize = 12;
    int fontBold = Font.BOLD;
    int fontPlain = Font.PLAIN;
    int fontItalic = Font.ITALIC;
    Date startDate;
    Date endDate;
    double timer;
    int[] displayId;
    int index;
    int presTypeId;
    boolean notSafe = false;
    Image image;
    Text text;
    Text tt;
    Image img;
    int id;
    boolean disable;
    String path;
    String font;
    int size;
    

    File shared;
    //  File[] folders;
//    DisplayManager dm;
    String[] folders;
//    ArrayList<Display> dispList = new ArrayList<>();

    JFrame frame;

    public GuiMain2()
    {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        pnlEditFolder.setVisible(false);
        pnlCreateFolder.setVisible(false);

        tMgr = TextManager.getInstance();
        iMgr = ImageManager.getInstance();
        dcMgr = DisplayCtrlManager.getInstance();
        dMgr = DisplayManager.getInstance();

        dispList = new ArrayList<>();
        dcList = new ArrayList<>();

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screensize.getWidth();
        int height = (int) screensize.getHeight();
        PresentationList();
        EditList();

        shared = new File("c:/Info/images");

        folders = shared.list();
        //  folders = shared.listFiles();
        for (String paths : folders) {
            cbxFolder.addItem(paths);
            cbxCreateFolder.addItem(paths);
        }
//        dispList = dm.readAll();
//        for (Display disp : dispList)
//        {
//            cbxChooseDisplay.addItem(disp.getScreenName());
//        }

        pnlTableCardText.setVisible(false);

        btnEditChosen.setEnabled(true);
        btnRemoveChosen.setEnabled(true);
        pnlCreateTypeAndDisplay.setVisible(false);
        lblCreateWarningType.setVisible(false);
        lblCreateWarningDisplay.setVisible(false);
        pnlTableCardText.setVisible(false);

    }

    private void PresentationList()
    {
        dcList = dcMgr.readAllEditPres();
        Date now = new Date(System.currentTimeMillis());
        ArrayList<DisplayCtrl> current;
        current = new ArrayList<>();
        for (int i = 0; i < dcList.size(); ++i) {
            if ((dcList.get(i).getStartDate().before(now) || dcList.get(i).getStartDate() == now)
                    && (dcList.get(i).getEndDate().after(now) || dcList.get(i).getEndDate() == now)) {
                current.add(dcList.get(i));
            }

        }

        presModel = new PresentationTableModel(current);

        presTable = new PresentationTable(presModel);

        pnlPresentationTableCont.add(new JScrollPane(presTable), BorderLayout.CENTER);

        presTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent lse)
            {
                if (!lse.getValueIsAdjusting()) {
                    if (presTable.getSelectedRow() != -1) {
                        showPresData();
                    } else {
                        clearEditData();
                    }
                }
            }
        });
        presModel.addTableModelListener(new TableModelListener()
        {
            @Override
            public void tableChanged(TableModelEvent tme)
            {
                // Clearing and setting back the selection triggers the 
                // selection listener of the JTable 
                presTable.clearSelection();
                //  presTable.setRowSelectionInterval(0, 0);
                presTable.setRowSelectionInterval(tme.getFirstRow(), tme.getFirstRow());
            }
        });

        ((JComponent) presTable.getDefaultRenderer(Boolean.class)).setOpaque(true);
    }

    private void showPresData()
    {
        pres = presModel.getDisplayCtrlByRow(presTable.convertRowIndexToModel(presTable.getSelectedRow()));
    }

    private void TextList()
    {
        textModel = new TextTableModel(tMgr.readAll());

        textTable = new TextTable(textModel);

        pnlTableCardText.add(new JScrollPane(textTable), BorderLayout.CENTER);

    }

    private void EditList()
    {
        editModel = new EditTableModel(dcMgr.readAllEditPres());

        editTable = new EditTable(editModel);

        pnlTableCardText.add(new JScrollPane(editTable), BorderLayout.CENTER);

    }

    private void showTextData(String presTitle)
    {

        System.out.println(presTitle);
        text = tMgr.getByTitle(presTitle);

        txtEditTitle.setText(text.getTitle());
        txtEditTextArea.setText(text.getText());
        tt = tMgr.getByTitle(text.getTitle());
        id = tt.getId();

        dpEditStartDate.setDate(text.getStartDate());
        dpEditEndDate.setDate(text.getEndDate());
        //String.valueOf(double)
        txtEditTimer.setText(String.valueOf(text.getTimer()));   
        System.out.println(text.getFontSize());
        
        fontSize = text.getFontSize();
        String fontS = String.valueOf(fontSize);
        cbxFonts1.setSelectedItem(text.getFont());
        cbxFontSize1.setSelectedItem(fontS);
        
        btnEditChosen.setEnabled(true);
        btnRemoveChosen.setEnabled(true);
        System.out.println("Test for edit button");

    }

    private void showImageData(String presTitle)
    {
        image = iMgr.getByTitle(presTitle);

        txtEditTitle.setText(image.getTitle());
        cbxFolder.setSelectedItem(image.getPath());
        img = iMgr.getByTitle(image.getTitle());
        id = img.getId();

        dpEditStartDate.setDate(image.getStartDate());
        dpEditEndDate.setDate(image.getEndDate());
        //String.valueOf(double)
        txtEditTimer.setText(String.valueOf(image.getTimer()));
        cbxFolder.setSelectedItem(image.getPath());
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

        jdColorPicker = new javax.swing.JDialog();
        colorPicker = new javax.swing.JColorChooser();
        btnSelectColor = new javax.swing.JButton();
        navPanel = new javax.swing.JPanel();
        btnPresentation = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        btnSettings = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        cardContainer = new javax.swing.JPanel();
        settingsCard = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JSeparator();
        lblSettingsSubtitle = new javax.swing.JLabel();
        lblSettingsTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlLeftSettings = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        pnlCreateTypeAndDisplay = new javax.swing.JPanel();
        cbxPresentationType = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstDisplay = new javax.swing.JList();
        jLabel23 = new javax.swing.JLabel();
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
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCreateTimer = new javax.swing.JTextField();
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
        cbxFonts = new javax.swing.JComboBox();
        cbxFontSize = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbxFontWeight = new javax.swing.JComboBox();
        btnChooseColor = new javax.swing.JButton();
        pnlEdit = new javax.swing.JPanel();
        editHeader = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        pnlEditCardContainer = new javax.swing.JPanel();
        pnlEditCard1 = new javax.swing.JPanel();
        btnRemoveChosen = new javax.swing.JButton();
        btnEditChosen = new javax.swing.JButton();
        pnlTableCardMain = new javax.swing.JPanel();
        pnlTableCardText = new javax.swing.JPanel();
        pnlTableClearLayout = new javax.swing.JPanel();
        cbxSorting = new javax.swing.JComboBox();
        pnlEditCard2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtEditTitle = new javax.swing.JTextField();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEditTextArea = new javax.swing.JTextArea();
        jLabel24 = new javax.swing.JLabel();
        cbxFonts1 = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        cbxFontSize1 = new javax.swing.JComboBox();
        cbxFontWeight1 = new javax.swing.JComboBox();
        btnChooseColor1 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        presentationCard = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        lblPresentationTitle = new javax.swing.JLabel();
        lblPresentationSubtitle = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlPresentationTableCont = new javax.swing.JPanel();

        btnSelectColor.setText("Select");
        btnSelectColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectColorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jdColorPickerLayout = new javax.swing.GroupLayout(jdColorPicker.getContentPane());
        jdColorPicker.getContentPane().setLayout(jdColorPickerLayout);
        jdColorPickerLayout.setHorizontalGroup(
            jdColorPickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdColorPickerLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(colorPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
            .addGroup(jdColorPickerLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnSelectColor)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jdColorPickerLayout.setVerticalGroup(
            jdColorPickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdColorPickerLayout.createSequentialGroup()
                .addComponent(colorPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnSelectColor)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        navPanel.setBackground(new java.awt.Color(67, 74, 84));

        btnPresentation.setBackground(new java.awt.Color(76, 64, 84));
        btnPresentation.setForeground(new java.awt.Color(255, 255, 255));
        btnPresentation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/btnPresentation.png"))); // NOI18N
        btnPresentation.setBorderPainted(false);
        btnPresentation.setContentAreaFilled(false);
        btnPresentation.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPresentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPresentationActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));

        btnSettings.setBackground(new java.awt.Color(76, 64, 84));
        btnSettings.setForeground(new java.awt.Color(255, 255, 255));
        btnSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/btnSettings.png"))); // NOI18N
        btnSettings.setBorderPainted(false);
        btnSettings.setContentAreaFilled(false);
        btnSettings.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingsActionPerformed(evt);
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
                    .addComponent(btnPresentation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3)
                    .addComponent(btnSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(btnPresentation)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnSettings)
                .addGap(0, 0, 0)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7830, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        getContentPane().add(navPanel, java.awt.BorderLayout.LINE_START);

        cardContainer.setBackground(new java.awt.Color(255, 255, 255));
        cardContainer.setLayout(new java.awt.CardLayout());

        settingsCard.setBackground(new java.awt.Color(255, 255, 255));
        settingsCard.setPreferredSize(new java.awt.Dimension(1199, 500));

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

        cbxPresentationType.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        cbxPresentationType.setForeground(new java.awt.Color(67, 74, 84));
        cbxPresentationType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Presentation Type:", "Text", "Image" }));
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

        jLabel13.setFont(new java.awt.Font("Ebrima", 0, 16)); // NOI18N
        jLabel13.setText("Displays:");

        lstDisplay.setBackground(new java.awt.Color(230, 233, 237));
        lstDisplay.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        lstDisplay.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "ProductionArea", "Lobby", "IT Department", "CEO", "Office", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstDisplay.setRequestFocusEnabled(false);
        lstDisplay.setSelectionBackground(new java.awt.Color(67, 74, 84));
        lstDisplay.setSelectionForeground(new java.awt.Color(250, 250, 250));
        lstDisplay.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstDisplayValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(lstDisplay);

        jLabel23.setFont(new java.awt.Font("Ebrima", 0, 16)); // NOI18N
        jLabel23.setText("Presentation:");

        javax.swing.GroupLayout pnlCreateTypeAndDisplayLayout = new javax.swing.GroupLayout(pnlCreateTypeAndDisplay);
        pnlCreateTypeAndDisplay.setLayout(pnlCreateTypeAndDisplayLayout);
        pnlCreateTypeAndDisplayLayout.setHorizontalGroup(
            pnlCreateTypeAndDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateTypeAndDisplayLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlCreateTypeAndDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(pnlCreateTypeAndDisplayLayout.createSequentialGroup()
                        .addGroup(pnlCreateTypeAndDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(cbxPresentationType, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlCreateTypeAndDisplayLayout.setVerticalGroup(
            pnlCreateTypeAndDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateTypeAndDisplayLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel23)
                .addGap(0, 0, 0)
                .addComponent(cbxPresentationType, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblCreateWarningType.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        lblCreateWarningType.setForeground(new java.awt.Color(255, 0, 0));
        lblCreateWarningType.setText("Please select a Presentation Type!");

        lblCreateWarningDisplay.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        lblCreateWarningDisplay.setForeground(new java.awt.Color(255, 0, 0));
        lblCreateWarningDisplay.setText("Please select Display!");

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
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblCreateWarningType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCreateWarningDisplay)
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        CardLayoutSet.setMaximumSize(new java.awt.Dimension(1440, 400));
        CardLayoutSet.setPreferredSize(new java.awt.Dimension(969, 450));
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
            .addGap(0, 7966, Short.MAX_VALUE)
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

        javax.swing.GroupLayout createHeadingLayout = new javax.swing.GroupLayout(createHeading);
        createHeading.setLayout(createHeadingLayout);
        createHeadingLayout.setHorizontalGroup(
            createHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createHeadingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 759, Short.MAX_VALUE)
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
                .addComponent(closeCreate)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        closeCreate.getAccessibleContext().setAccessibleName("Close");

        jLabel9.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel9.setText("Title:");

        jLabel11.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel11.setText("Start Date:");

        jLabel12.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel12.setText("End Date:");

        jLabel15.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel15.setText("Timer:");

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

        cbxCreateFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCreateFolderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCreateFolderLayout = new javax.swing.GroupLayout(pnlCreateFolder);
        pnlCreateFolder.setLayout(pnlCreateFolderLayout);
        pnlCreateFolderLayout.setHorizontalGroup(
            pnlCreateFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateFolderLayout.createSequentialGroup()
                .addComponent(jLabel16)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlCreateFolderLayout.createSequentialGroup()
                .addComponent(cbxCreateFolder, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlCreateFolderLayout.setVerticalGroup(
            pnlCreateFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateFolderLayout.createSequentialGroup()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxCreateFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pnlTextArea.setBackground(new java.awt.Color(255, 255, 255));

        txtCreateTextArea.setColumns(20);
        txtCreateTextArea.setLineWrap(true);
        txtCreateTextArea.setRows(5);
        jScrollPane1.setViewportView(txtCreateTextArea);

        jLabel10.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel10.setText("Font:");

        cbxFonts.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxFonts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFontsActionPerformed(evt);
            }
        });

        cbxFontSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "8", "10", "11", "12", "14", "16", "18", "20", "24", "30", "36", "40", "48", "60", "72" }));
        cbxFontSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFontSizeActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel8.setText("Size");

        jLabel14.setText("Style:");

        cbxFontWeight.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Plain", "Bold", "Italic" }));
        cbxFontWeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFontWeightActionPerformed(evt);
            }
        });

        btnChooseColor.setText("Color");

        javax.swing.GroupLayout pnlTextAreaLayout = new javax.swing.GroupLayout(pnlTextArea);
        pnlTextArea.setLayout(pnlTextAreaLayout);
        pnlTextAreaLayout.setHorizontalGroup(
            pnlTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTextAreaLayout.createSequentialGroup()
                .addGroup(pnlTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTextAreaLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(cbxFonts, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxFontWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnChooseColor))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );
        pnlTextAreaLayout.setVerticalGroup(
            pnlTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTextAreaLayout.createSequentialGroup()
                .addGroup(pnlTextAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cbxFonts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel14)
                    .addComponent(cbxFontWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChooseColor))
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
                    .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtCreateTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCreateTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addGroup(pnlCreateLayout.createSequentialGroup()
                        .addComponent(btnCreateNew)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelCreate))
                    .addGroup(pnlCreateLayout.createSequentialGroup()
                        .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pnlCreateFolder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dpCreateStartDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(36, 36, 36)
                        .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(dpCreateEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(97, 560, Short.MAX_VALUE))
            .addGroup(pnlCreateLayout.createSequentialGroup()
                .addComponent(pnlTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlCreateLayout.setVerticalGroup(
            pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(createHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCreateTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dpCreateStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dpCreateEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCreateTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlCreateFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7378, Short.MAX_VALUE)
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

        lblClose.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        lblClose.setForeground(new java.awt.Color(255, 255, 255));
        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/delete85.png"))); // NOI18N
        lblClose.setToolTipText("Close and Reset Operation");
        lblClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
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
                .addComponent(lblClose)
                .addGap(10, 10, 10))
        );
        editHeaderLayout.setVerticalGroup(
            editHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(editHeaderLayout.createSequentialGroup()
                .addComponent(lblClose)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        lblClose.getAccessibleContext().setAccessibleName("Close");

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

        pnlTableCardText.setLayout(new java.awt.BorderLayout());
        pnlTableCardMain.add(pnlTableCardText, "card2");

        pnlTableClearLayout.setBackground(new java.awt.Color(230, 233, 237));

        javax.swing.GroupLayout pnlTableClearLayoutLayout = new javax.swing.GroupLayout(pnlTableClearLayout);
        pnlTableClearLayout.setLayout(pnlTableClearLayoutLayout);
        pnlTableClearLayoutLayout.setHorizontalGroup(
            pnlTableClearLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 462, Short.MAX_VALUE)
        );
        pnlTableClearLayoutLayout.setVerticalGroup(
            pnlTableClearLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );

        pnlTableCardMain.add(pnlTableClearLayout, "card4");

        cbxSorting.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All Presentations", "Past Presentations", "Current Presentations", "Future Presentations", " " }));
        cbxSorting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSortingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEditCard1Layout = new javax.swing.GroupLayout(pnlEditCard1);
        pnlEditCard1.setLayout(pnlEditCard1Layout);
        pnlEditCard1Layout.setHorizontalGroup(
            pnlEditCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditCard1Layout.createSequentialGroup()
                .addComponent(pnlTableCardMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlEditCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnRemoveChosen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditChosen, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbxSorting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(358, 358, 358))
        );
        pnlEditCard1Layout.setVerticalGroup(
            pnlEditCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditCard1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTableCardMain, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlEditCard1Layout.createSequentialGroup()
                        .addComponent(cbxSorting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditChosen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveChosen)))
                .addContainerGap(261, Short.MAX_VALUE))
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

        pnlEditFolder.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("Folder:");

        javax.swing.GroupLayout pnlEditFolderLayout = new javax.swing.GroupLayout(pnlEditFolder);
        pnlEditFolder.setLayout(pnlEditFolderLayout);
        pnlEditFolderLayout.setHorizontalGroup(
            pnlEditFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditFolderLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 66, Short.MAX_VALUE))
            .addComponent(cbxFolder, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        txtEditTextArea.setColumns(20);
        txtEditTextArea.setLineWrap(true);
        txtEditTextArea.setRows(5);
        jScrollPane2.setViewportView(txtEditTextArea);

        jLabel24.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel24.setText("Font:");

        cbxFonts1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxFonts1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFonts1ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel25.setText("Size");

        cbxFontSize1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "8", "10", "11", "12", "14", "16", "18", "20", "24", "30", "36", "40", "48", "60", "72" }));
        cbxFontSize1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFontSize1ActionPerformed(evt);
            }
        });

        cbxFontWeight1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Plain", "Bold", "Italic" }));
        cbxFontWeight1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFontWeight1ActionPerformed(evt);
            }
        });

        btnChooseColor1.setText("Color");
        btnChooseColor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseColor1ActionPerformed(evt);
            }
        });

        jLabel18.setText("Style:");

        javax.swing.GroupLayout pnlTextAreaContLayout = new javax.swing.GroupLayout(pnlTextAreaCont);
        pnlTextAreaCont.setLayout(pnlTextAreaContLayout);
        pnlTextAreaContLayout.setHorizontalGroup(
            pnlTextAreaContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(pnlTextAreaContLayout.createSequentialGroup()
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(cbxFonts1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxFontSize1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxFontWeight1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnChooseColor1)
                .addGap(0, 41, Short.MAX_VALUE))
        );
        pnlTextAreaContLayout.setVerticalGroup(
            pnlTextAreaContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTextAreaContLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTextAreaContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(cbxFontSize1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(cbxFonts1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlTextAreaContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(cbxFontWeight1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnChooseColor1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlEditCard2Layout = new javax.swing.GroupLayout(pnlEditCard2);
        pnlEditCard2.setLayout(pnlEditCard2Layout);
        pnlEditCard2Layout.setHorizontalGroup(
            pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditCard2Layout.createSequentialGroup()
                .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEditTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(pnlEditFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEditTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(pnlTextAreaCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(483, Short.MAX_VALUE))
        );
        pnlEditCard2Layout.setVerticalGroup(
            pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditCard2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEditTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlTextAreaCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelUpdate)
                    .addComponent(btnUpdate))
                .addContainerGap())
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
                .addGap(10, 10, 10)
                .addComponent(pnlEditCardContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
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
                        .addComponent(CardLayoutSet, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
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
                    .addComponent(CardLayoutSet, javax.swing.GroupLayout.DEFAULT_SIZE, 7966, Short.MAX_VALUE)
                    .addComponent(pnlLeftSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jLabel2))
        );

        cardContainer.add(settingsCard, "card3");

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
                    .addComponent(pnlPresentationTableCont, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE))
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
                .addComponent(pnlPresentationTableCont, javax.swing.GroupLayout.DEFAULT_SIZE, 7631, Short.MAX_VALUE)
                .addGap(355, 355, 355)
                .addComponent(jLabel3))
        );

        cardContainer.add(presentationCard, "card2");

        getContentPane().add(cardContainer, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPresentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPresentationActionPerformed
        presentationCard.setVisible(true);
        settingsCard.setVisible(false);
        dcList = dcMgr.readAllEditPres();
        Date now = new Date(System.currentTimeMillis());
        ArrayList<DisplayCtrl> current;
        current = new ArrayList<>();
        for (int i = 0; i < dcList.size(); ++i) {
            if ((dcList.get(i).getStartDate().before(now) || dcList.get(i).getStartDate() == now)
                    && (dcList.get(i).getEndDate().after(now) || dcList.get(i).getEndDate() == now)) {
                current.add(dcList.get(i));
            }

        }

        presModel.setDisplayCtrlList(current);
        presTable.setModel(presModel);
    }//GEN-LAST:event_btnPresentationActionPerformed

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingsActionPerformed
        settingsCard.setVisible(true);
        presentationCard.setVisible(false);
    }//GEN-LAST:event_btnSettingsActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        pnlCreateTypeAndDisplay.setVisible(true);
        btnEdit.setSelected(false);
        btnCreate.setSelected(true);
        pnlCreate.setVisible(true);
        pnlClean.setVisible(false);
        pnlEdit.setVisible(false);

        dispList = dMgr.readAllPres();

        for (int i = 0; i < dispList.size(); ++i) {
            lstDisplay.setListData(dispList.toArray());

        }        
        cbxFonts.setModel(new DefaultComboBoxModel(fonts));       


    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        cbxPresentationType.setSelectedIndex(0);
        pnlCreateTypeAndDisplay.setVisible(false);
        cbxPresentationType.setEnabled(true);
        btnCreate.setSelected(false);
        btnEdit.setSelected(true);
        pnlEdit.setVisible(true);
        pnlCreate.setVisible(false);
        pnlClean.setVisible(false);
        TextList();

        dispList = dMgr.readAllPres();

        for (int i = 0; i < dispList.size(); ++i) {
            lstDisplay.setListData(dispList.toArray());

        }
        cbxFonts1.setModel(new DefaultComboBoxModel(fonts));
        
    }//GEN-LAST:event_btnEditActionPerformed

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        pnlEdit.setVisible(false);
        btnCreate.setSelected(false);
        btnEdit.setSelected(false);
        pnlCreateTypeAndDisplay.setVisible(false);

    }//GEN-LAST:event_lblCloseMouseClicked

    private void closeCreateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeCreateMouseClicked
        clearCreateData();
    }//GEN-LAST:event_closeCreateMouseClicked

    private void btnEditChosenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditChosenActionPerformed
        pnlEditCard2.setVisible(true);
        pnlEditCard1.setVisible(false);
        pnlCreateTypeAndDisplay.setVisible(true);

        int selectedRow = editTable.getSelectedRow();
        selectedRow = editTable.convertRowIndexToModel(selectedRow);
        String val1 = (String) editTable.getModel().getValueAt(selectedRow, 1);
        String title = (String) editTable.getModel().getValueAt(selectedRow, 0);

        System.out.println(val1 + " " + title);

        if (val1.equals("Text")) {
            showTextData(title);
            pnlTextAreaCont.setVisible(true);
            pnlEditFolder.setVisible(false);
            cbxPresentationType.setSelectedIndex(1);
            cbxFontSize1.setSelectedItem(fontSize);
        }
        if (val1.equals("Image")) {
            showImageData(title);
            pnlTextAreaCont.setVisible(false);
            pnlEditFolder.setVisible(true);
            cbxPresentationType.setSelectedIndex(2);    
        }
        cbxPresentationType.setEnabled(false);

    }//GEN-LAST:event_btnEditChosenActionPerformed

    private void btnRemoveChosenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveChosenActionPerformed
        pnlEditCard2.setVisible(false);
        pnlEditCard1.setVisible(true);

        int selectedRow = editTable.getSelectedRow();
        selectedRow = editTable.convertRowIndexToModel(selectedRow);
        String val1 = (String) editTable.getModel().getValueAt(selectedRow, 1);
        String title = (String) editTable.getModel().getValueAt(selectedRow, 0);
        if (val1.equals("Text"))
        {
            showTextData(title);   
            cbxPresentationType.setSelectedIndex(1);
            tMgr.deleteText(tMgr.getByTitle(title).getId());
        
        }
        if (val1.equals("Image"))
        {
            showImageData(title);
            cbxPresentationType.setSelectedIndex(2);
            iMgr.deleteImage(iMgr.getByTitle(title).getId());
            
        }
            editModel.setDisplayCtrlList(dcMgr.readAllEditPres());
            editTable.setModel(editModel);

    }//GEN-LAST:event_btnRemoveChosenActionPerformed

    private void adjustSelection(int row)
    {
        if (textModel.getRowCount() > 0) {
            if (row == textModel.getRowCount()) {
                row--;
            }

            textTable.setRowSelectionInterval(row, row);
        } else {
            textTable.getSelectionModel().clearSelection();
        }
    }
    private void btnCancelUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelUpdateActionPerformed
        pnlEditCard2.setVisible(false);
        pnlEditCard1.setVisible(true);
        cbxPresentationType.setEnabled(true);

        clearEditData();
    }//GEN-LAST:event_btnCancelUpdateActionPerformed

    private void btnCancelCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelCreateActionPerformed
        clearCreateData();
    }//GEN-LAST:event_btnCancelCreateActionPerformed

    private void txtCreateTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCreateTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreateTitleActionPerformed

    private void txtEditTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEditTitleActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
       if(lstDisplay.getSelectedIndex() != -1){
            if (cbxPresentationType.getSelectedIndex() == 1) {
                updateText();
                pnlEditCard2.setVisible(false);
                cbxPresentationType.setEnabled(true);
            } else {
                if (cbxPresentationType.getSelectedIndex() == 2) {
                    updateImage();
                    pnlEditCard2.setVisible(false);
                    cbxPresentationType.setEnabled(true);
                }
            }
            clearEditData();
       }
       else{
           lblCreateWarningDisplay.setVisible(true);
       }

        
//          EditList();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnCreateNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateNewActionPerformed
        if (cbxPresentationType.getSelectedIndex() == 0) {
            lblCreateWarningType.setVisible(true);
            lblCreateWarningDisplay.setVisible(false);
        } else {
            if (lstDisplay.getSelectedIndex() < 0) {
                lblCreateWarningType.setVisible(false);
                lblCreateWarningDisplay.setVisible(true);
            } else {

                int selection = cbxPresentationType.getSelectedIndex();
                switch (selection) {
                    case 1:
                        saveTextPresentation();
                        cbxPresentationType.setEnabled(false);
                        break;
                    case 2:
                        saveImagePresentation();
                        cbxPresentationType.setEnabled(false);
                        break;
                }
                clearCreateData();
            }
        }

    }//GEN-LAST:event_btnCreateNewActionPerformed

    private void saveTextPresentation() throws NumberFormatException
    {
        title = txtCreateTitle.getText();
        txt = txtCreateTextArea.getText();
        System.out.println(title + "  " + txt);
        presTypeId = cbxPresentationType.getSelectedIndex();
        startDate = new java.sql.Date(dpCreateStartDate.getDate().getTime());
        endDate = new java.sql.Date(dpCreateEndDate.getDate().getTime());
        timer = Double.parseDouble(txtCreateTimer.getText());
        displayId = lstDisplay.getSelectedIndices();
        
        font = cbxFonts.getSelectedItem().toString();
        size = Integer.parseInt(cbxFontSize.getSelectedItem().toString());
        
        System.out.println(font + " " + size);

        System.out.println(displayId);

        text = new Text(presTypeId, title, startDate, endDate, timer, notSafe, txt, font, size);

        tMgr.createText(text);

        int id = tMgr.getByTitle(title).getId();

        for (int i = 0; i < displayId.length; ++i) {
            dcMgr.create(id, displayId[i] + 1);
        }

        editModel.setDisplayCtrlList(dcMgr.readAllEditPres());
        editTable.setModel(editModel);

    }

    private void saveImagePresentation() throws NumberFormatException
    {
        title = txtCreateTitle.getText();
        path = cbxCreateFolder.getSelectedItem().toString();
        System.out.println(title + "  " + path);
        presTypeId = cbxPresentationType.getSelectedIndex();
        startDate = new java.sql.Date(dpCreateStartDate.getDate().getTime());
        endDate = new java.sql.Date(dpCreateEndDate.getDate().getTime());
        timer = Double.parseDouble(txtCreateTimer.getText());
        displayId = lstDisplay.getSelectedIndices();
        image = new Image(presTypeId, title, startDate, endDate, timer, notSafe, path);
        iMgr.createImage(image);

        int id = iMgr.getByTitle(title).getId();

        for (int i = 0; i < displayId.length; ++i) {
            dcMgr.create(id, displayId[i] + 1);
        }

        editModel.setDisplayCtrlList(dcMgr.readAllEditPres());
        editTable.setModel(editModel);

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
        displayId = lstDisplay.getSelectedIndices();
        System.out.println(presTypeId + title + " " + startDate + " " + endDate + " " + timer + " " + notSafe + " " + txt);
        dcMgr.delete(id);
        
        font = cbxFonts1.getSelectedItem().toString();
        size = Integer.parseInt(cbxFontSize1.getSelectedItem().toString());
        
        text = new Text(id, 1, title, startDate, endDate, timer, notSafe, txt, font, size);
        
        tMgr.updateText(text);
        displayId = lstDisplay.getSelectedIndices();
        dcMgr.delete(id);

        for (int i = 0; i < displayId.length; ++i) {
            dcMgr.create(id, displayId[i] + 1);
        }

        editModel.setDisplayCtrlList(dcMgr.readAllEditPres());
        editTable.setModel(editModel);
    }

    private void updateImage()
    {
        title = txtEditTitle.getText();

        path = cbxFolder.getSelectedItem().toString();

        System.out.println(title + "  " + path);
        presTypeId = cbxPresentationType.getSelectedIndex() + 1;
        startDate = new java.sql.Date(dpEditStartDate.getDate().getTime());
        endDate = new java.sql.Date(dpEditEndDate.getDate().getTime());
        timer = Double.parseDouble(txtEditTimer.getText()); 
        displayId = lstDisplay.getSelectedIndices();
        dcMgr.delete(id);
        
        image = new Image(id, 2, title, startDate, endDate, timer, notSafe, path);
        iMgr.updateImage(image);
        
        displayId = lstDisplay.getSelectedIndices();
        

        for (int i = 0; i < displayId.length; ++i) {
            dcMgr.create(id, displayId[i] + 1);
        }
        
        editModel.setDisplayCtrlList(dcMgr.readAllEditPres());
        editTable.setModel(editModel);

    }

    private void cbxPresentationTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPresentationTypeActionPerformed
        if (cbxPresentationType.getSelectedIndex() == 0) {
            createDisable();
        } else {
            if (cbxPresentationType.getSelectedIndex() == 1) {
                pnlTableCardText.setVisible(true);
                pnlTableClearLayout.setVisible(false);
                pnlTextAreaCont.setVisible(true);
//            TextList();
                btnEditChosen.setEnabled(true);
                btnRemoveChosen.setEnabled(true);
                pnlEditFolder.setVisible(false);
                pnlCreateFolder.setVisible(false);
                pnlTextArea.setVisible(true);
            } else {
                if (cbxPresentationType.getSelectedIndex() == 2) {
                    pnlEditFolder.setVisible(true);
                    pnlCreateFolder.setVisible(true);
                    pnlTextArea.setVisible(false);
                    pnlTableCardText.setVisible(false);
                    pnlTableClearLayout.setVisible(false);
                    pnlTextAreaCont.setVisible(false);
                    btnEditChosen.setEnabled(true);
                    btnRemoveChosen.setEnabled(true);
                } else {
                    pnlEditFolder.setVisible(false);
                    pnlCreateFolder.setVisible(false);
                    pnlTextArea.setVisible(true);
                    pnlTableCardText.setVisible(true);
                    pnlTableClearLayout.setVisible(false);
                    btnRemoveChosen.setEnabled(false);

                }
            }
        }

    }//GEN-LAST:event_cbxPresentationTypeActionPerformed

    private void createDisable()
    {
        txtCreateTitle.setEnabled(false);
        txtCreateTextArea.setEnabled(false);
        dpCreateStartDate.setEnabled(false);
        dpCreateEndDate.setEnabled(false);
        txtCreateTimer.setEnabled(false);
        cbxCreateFolder.setEnabled(false);
        btnCreateNew.setEnabled(false);
    }

    private void createEnable()
    {
        txtCreateTitle.setEnabled(true);
        txtCreateTextArea.setEnabled(true);
        dpCreateStartDate.setEnabled(true);
        dpCreateEndDate.setEnabled(true);
        txtCreateTimer.setEnabled(true);
        cbxCreateFolder.setEnabled(true);
        btnCreateNew.setEnabled(true);
    }

    private void cbxPresentationTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPresentationTypeItemStateChanged
        if (cbxPresentationType.getSelectedIndex() != 0) {
            lblCreateWarningType.setVisible(false);
            createEnable();
        }
    }//GEN-LAST:event_cbxPresentationTypeItemStateChanged

    private void cbxCreateFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCreateFolderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxCreateFolderActionPerformed

    private void lstDisplayValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstDisplayValueChanged
        lblCreateWarningDisplay.setVisible(false);
    }//GEN-LAST:event_lstDisplayValueChanged

    private void cbxSortingActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cbxSortingActionPerformed
    {//GEN-HEADEREND:event_cbxSortingActionPerformed
        dcList = dcMgr.readAllEditPres();
        System.out.println(cbxSorting.getSelectedIndex());

        if (cbxSorting.getSelectedIndex() == 0) {
            editModel.setDisplayCtrlList(dcList);
            editTable.setModel(editModel);
        }

        if (cbxSorting.getSelectedIndex() == 1) {

            Date now = new Date(System.currentTimeMillis());
            ArrayList<DisplayCtrl> past;
            past = new ArrayList<>();
            for (int i = 0; i < dcList.size(); ++i) {
                if (dcList.get(i).getStartDate().before(now)
                        && dcList.get(i).getEndDate().before(now)) {
                    past.add(dcList.get(i));
                }
            }
            System.out.println(past);
            editModel.setDisplayCtrlList(past);
            editTable.setModel(editModel);

        }
        if (cbxSorting.getSelectedIndex() == 2) {

            Date now = new Date(System.currentTimeMillis());
            ArrayList<DisplayCtrl> current;
            current = new ArrayList<>();
            for (int i = 0; i < dcList.size(); ++i) {
                if ((dcList.get(i).getStartDate().before(now) || dcList.get(i).getStartDate() == now)
                        && (dcList.get(i).getEndDate().after(now) || dcList.get(i).getEndDate() == now)) {
                    current.add(dcList.get(i));
                }
            }
            System.out.println(current);
            editModel.setDisplayCtrlList(current);
            editTable.setModel(editModel);

        }
        if (cbxSorting.getSelectedIndex() == 3) {

            Date now = new Date(System.currentTimeMillis());
            ArrayList<DisplayCtrl> future;
            future = new ArrayList<>();
            for (int i = 0; i < dcList.size(); ++i) {
                if (dcList.get(i).getStartDate().after(now)
                        && dcList.get(i).getEndDate().after(now)) {
                    future.add(dcList.get(i));
                }
            }
            System.out.println(future);
            editModel.setDisplayCtrlList(future);
            editTable.setModel(editModel);

        }


    }//GEN-LAST:event_cbxSortingActionPerformed

    private void cbxFontsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFontsActionPerformed
        
        String currentFont = cbxFonts.getSelectedItem().toString();
        txtCreateTextArea.setFont(new Font(currentFont, Font.PLAIN, fontSize));
        fontType = new String(currentFont);
       
    }//GEN-LAST:event_cbxFontsActionPerformed

    private void cbxFontSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFontSizeActionPerformed
        int currentSize = Integer.parseInt((String)cbxFontSize.getSelectedItem());       
        fontSize = new Integer(currentSize);
        txtCreateTextArea.setFont(new Font(fontType, Font.PLAIN, fontSize));
    }//GEN-LAST:event_cbxFontSizeActionPerformed

    private void cbxFonts1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFonts1ActionPerformed
        String currentFont = cbxFonts1.getSelectedItem().toString();
        txtEditTextArea.setFont(new Font(currentFont, Font.PLAIN, fontSize));
        fontType = new String(currentFont);
    }//GEN-LAST:event_cbxFonts1ActionPerformed

    private void cbxFontSize1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFontSize1ActionPerformed
       int currentSize = Integer.parseInt((String)cbxFontSize1.getSelectedItem());       
        fontSize = new Integer(currentSize);
        txtEditTextArea.setFont(new Font(fontType, Font.PLAIN, fontSize));
    }//GEN-LAST:event_cbxFontSize1ActionPerformed

    private void cbxFontWeightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFontWeightActionPerformed
        String currentFont = cbxFonts1.getSelectedItem().toString();
        if(cbxFontWeight.getSelectedIndex() == 0){
            txtCreateTextArea.setFont(new Font(currentFont, fontPlain, fontSize));
            fontType = new String(currentFont);
            System.out.println(fontPlain);
        }else if (cbxFontWeight.getSelectedIndex() == 1){
            txtCreateTextArea.setFont(new Font(currentFont, fontBold, fontSize)); 
            fontType = new String(currentFont);
            System.out.println(fontBold);
        }else if (cbxFontWeight.getSelectedIndex() == 2){
            txtCreateTextArea.setFont(new Font(currentFont, fontItalic, fontSize));
            fontType = new String(currentFont);
            System.out.println(fontItalic);
            
        }
    }//GEN-LAST:event_cbxFontWeightActionPerformed

    private void btnSelectColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectColorActionPerformed
        Color color = colorPicker.getColor();
        int rgbColor = colorPicker.getColor().getRGB();
        if(btnCreate.isSelected()){
            txtCreateTextArea.setForeground(new Color(rgbColor));
        }
        if(btnEdit.isSelected()){
            txtEditTextArea.setForeground(new Color(rgbColor));
        }
        System.out.println(rgbColor);
        jdColorPicker.setVisible(false);
    }//GEN-LAST:event_btnSelectColorActionPerformed

    private void cbxFontWeight1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFontWeight1ActionPerformed
         String currentFont = cbxFonts1.getSelectedItem().toString();
        if(cbxFontWeight1.getSelectedIndex() == 0){
            txtEditTextArea.setFont(new Font(currentFont, fontPlain, fontSize));
            fontType = new String(currentFont);
            System.out.println(fontPlain);
        }else if (cbxFontWeight1.getSelectedIndex() == 1){
            txtEditTextArea.setFont(new Font(currentFont, fontBold, fontSize)); 
            fontType = new String(currentFont);
            System.out.println(fontBold);
        }else if (cbxFontWeight1.getSelectedIndex() == 2){
            txtEditTextArea.setFont(new Font(currentFont, fontItalic, fontSize));
            fontType = new String(currentFont);
            System.out.println(fontItalic);
            
        }
    }//GEN-LAST:event_cbxFontWeight1ActionPerformed

    private void btnChooseColor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseColor1ActionPerformed
        jdColorPicker.setVisible(true);
    }//GEN-LAST:event_btnChooseColor1ActionPerformed

    public void clearCreateData()
    {
        pnlCreate.setVisible(false);
        btnCreate.setSelected(false);
        btnEdit.setSelected(false);
        txtCreateTitle.setText("");
        txtCreateTimer.setText("");
        txtCreateTextArea.setText("");
        cbxPresentationType.setSelectedIndex(0);
        lstDisplay.clearSelection();
        dpCreateEndDate.setDate(null);
        dpCreateStartDate.setDate(null);
        pnlCreateTypeAndDisplay.setVisible(false);
        lblCreateWarningDisplay.setVisible(false);
        lblCreateWarningType.setVisible(false);
    }

    public void clearEditData()
    {
        pnlEditCard1.setVisible(false);
        pnlCreateTypeAndDisplay.setVisible(false);
        btnCreate.setSelected(false);
        btnEdit.setSelected(true);
        txtEditTitle.setText("");
        txtEditTextArea.setText("");
        txtEditTextArea.setText("");
        dpEditEndDate.setDate(null);
        dpEditStartDate.setDate(null);
        lblCreateWarningDisplay.setVisible(false);
        lblCreateWarningType.setVisible(false);
    }

    public void clearPresData()
    {
        pnlPresentationTableCont.setVisible(false);
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
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new GuiMain2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CardLayoutSet;
    private javax.swing.JButton btnCancelCreate;
    private javax.swing.JButton btnCancelUpdate;
    private javax.swing.JButton btnChooseColor;
    private javax.swing.JButton btnChooseColor1;
    private javax.swing.JToggleButton btnCreate;
    private javax.swing.JButton btnCreateNew;
    private javax.swing.JToggleButton btnEdit;
    private javax.swing.JButton btnEditChosen;
    private javax.swing.JButton btnPresentation;
    private javax.swing.JButton btnRemoveChosen;
    private javax.swing.JButton btnSelectColor;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JPanel cardContainer;
    private javax.swing.JComboBox cbxCreateFolder;
    private javax.swing.JComboBox cbxFolder;
    private javax.swing.JComboBox cbxFontSize;
    private javax.swing.JComboBox cbxFontSize1;
    private javax.swing.JComboBox cbxFontWeight;
    private javax.swing.JComboBox cbxFontWeight1;
    private javax.swing.JComboBox cbxFonts;
    private javax.swing.JComboBox cbxFonts1;
    private javax.swing.JComboBox cbxPresentationType;
    private javax.swing.JComboBox cbxSorting;
    private javax.swing.JLabel closeCreate;
    private javax.swing.JColorChooser colorPicker;
    private javax.swing.JPanel createHeading;
    private org.jdesktop.swingx.JXDatePicker dpCreateEndDate;
    private org.jdesktop.swingx.JXDatePicker dpCreateStartDate;
    private org.jdesktop.swingx.JXDatePicker dpEditEndDate;
    private org.jdesktop.swingx.JXDatePicker dpEditStartDate;
    private javax.swing.JPanel editHeader;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JDialog jdColorPicker;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblCreateWarningDisplay;
    private javax.swing.JLabel lblCreateWarningType;
    private javax.swing.JLabel lblPresentationSubtitle;
    private javax.swing.JLabel lblPresentationTitle;
    private javax.swing.JLabel lblSettingsSubtitle;
    private javax.swing.JLabel lblSettingsTitle;
    private javax.swing.JList lstDisplay;
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
