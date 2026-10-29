/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.dsa_rps_cortez.java;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author glenbertcortez
 */
public class DASHBOARD extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DASHBOARD.class.getName());
    private static final String DB_PATH = "C://Users//CL2-PC//Documents//cortezdb.accdb";
    private static final String DB_URL = "jdbc:ucanaccess://" + DB_PATH;

    /**
     * Creates new form DASHBOARD
     */
    public DASHBOARD() {
        initComponents();
        setTableAppearance(); // Ayusin ang itsura ng table
        loadStudentData();   // Auto-load data mula sa database
    }
    
    // Ayusin lapad ng columns at taas ng rows
    private void setTableAppearance() {
        jTable1.setRowHeight(28);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(110); // ID
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(260); // Name
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);  // Year
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(130); // Section
    }
    
    // READ — Kunin at ipakita ang lahat mula sa 'register' table
    private void loadStudentData() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Burahin ang mga sample row
        
        String sql = "SELECT student_id, name, year, section FROM register ORDER BY student_id";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("student_id"),
                    rs.getString("name"),
                    rs.getString("year"),
                    rs.getString("section")
                };
                model.addRow(row);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Failed to load data:\n" + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Refresh button
    private void refreshAction() {
        loadStudentData();
        JOptionPane.showMessageDialog(this, "List updated", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Add button — pumunta sa Register form
    private void addAction() {
        new REGITER().setVisible(true);
        this.dispose();
    }
    
    // Edit button — pipili muna ng row
    private void editAction() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first!", "Reminder", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Kunin ang detalye ng pinili — gagamitin sa Edit form
        String id = jTable1.getValueAt(selectedRow, 0).toString();
        String name = jTable1.getValueAt(selectedRow, 1).toString();
        String year = jTable1.getValueAt(selectedRow, 2).toString();
        String section = jTable1.getValueAt(selectedRow, 3).toString();
        
        JOptionPane.showMessageDialog(this, 
            "Selected:\nID: " + id + "\nName: " + name, 
            "Edit Preview", 
            JOptionPane.INFORMATION_MESSAGE);
        // Dito ilalagay ang Edit form kapag nagawa na
    }
    
    // Delete button — pipili muna ng row
    private void deleteAction() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first!", "Reminder", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String studentId = jTable1.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Delete this student?\nID: " + studentId, 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        // Burahin sa parehong table
        String delRegister = "DELETE FROM register WHERE student_id = ?";
        String delUsers = "DELETE FROM Users WHERE username = (SELECT username FROM register WHERE student_id = ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmt1 = conn.prepareStatement(delRegister);
                 PreparedStatement pstmt2 = conn.prepareStatement(delUsers)) {
                
                pstmt1.setString(1, studentId);
                pstmt1.executeUpdate();
                
                pstmt2.setString(1, studentId);
                pstmt2.executeUpdate();
                
                conn.commit();
                JOptionPane.showMessageDialog(this, "Deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadStudentData();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Delete failed:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Logout button
    private void logoutAction() {
        new LOGIN().setVisible(true);
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnlogout = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnadd = new javax.swing.JButton();
        btnrefresh = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "MAIN DASHBOARD", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 18))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 48)); // NOI18N
        jLabel1.setText("STUDENT MANAGEMENT SYSTEM");
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 50));

        btnlogout.setBackground(new java.awt.Color(153, 153, 153));
        btnlogout.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnlogout.setText("Logout");
        btnlogout.addActionListener(this::btnlogoutActionPerformed);
        jPanel1.add(btnlogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 510, 140, 50));

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Year", "Section"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setViewportView(jScrollPane1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 620, 460));

        btnadd.setBackground(new java.awt.Color(0, 204, 51));
        btnadd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnadd.setText("Add");
        btnadd.addActionListener(this::btnaddActionPerformed);
        jPanel1.add(btnadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, 140, 70));

        btnrefresh.setBackground(new java.awt.Color(255, 255, 0));
        btnrefresh.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnrefresh.setText("Refresh");
        btnrefresh.addActionListener(this::btnrefreshActionPerformed);
        jPanel1.add(btnrefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 100, 140, 70));

        jButton5.setBackground(new java.awt.Color(255, 102, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton5.setText("Edit");
        jButton5.addActionListener(this::jButton5ActionPerformed);
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 260, 140, 70));

        btndelete.setBackground(new java.awt.Color(255, 51, 0));
        btndelete.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btndelete.setText("Delete");
        btndelete.addActionListener(this::btndeleteActionPerformed);
        jPanel1.add(btndelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 340, 140, 70));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 590));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefreshActionPerformed
        // TODO add your handling code here:
        loadStudentData();  // ← Tawagin ang function
        JOptionPane.showMessageDialog(this, "List updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnrefreshActionPerformed

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        // TODO add your handling code here:
        new REGITER().setVisible(true); // Buksan ang Register form
        this.dispose(); // Isara ang Dashboard
    }//GEN-LAST:event_btnaddActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
    
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Select a student first!", "Reminder", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Kunin ang data mula sa napiling row
    String id = jTable1.getValueAt(selectedRow, 0).toString();
    String name = jTable1.getValueAt(selectedRow, 1).toString();
    String year = jTable1.getValueAt(selectedRow, 2).toString();
    String section = jTable1.getValueAt(selectedRow, 3).toString();
    
    // Buksan ang EDIT form at ipasa ang data
    new EDIT(id, name, year, section).setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        // Get selected row
    int selectedRow = jTable1.getSelectedRow();
    
    // Check if no row is selected
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a student first!", "Reminder", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Get the Student ID from selected row
    String studentId = jTable1.getValueAt(selectedRow, 0).toString();
    String studentName = jTable1.getValueAt(selectedRow, 1).toString();
    
    // Show confirmation dialog with Yes/No options
    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to delete this student?\n\nID: " + studentId + "\nName: " + studentName,
        "Confirm Delete",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );
    
    // If user selects NO → do nothing
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }
    
    // Database connection
    final String DB_PATH = "C://Users//CL2-PC//Documents//cortezdb.accdb";
    final String DB_URL = "jdbc:ucanaccess://" + DB_PATH;
    
    // SQL statements — delete from both tables
    String deleteRegister = "DELETE FROM register WHERE student_id = ?";
    String deleteUsers = "DELETE FROM Users WHERE username = ?";
    
    try (java.sql.Connection conn = java.sql.DriverManager.getConnection(DB_URL)) {
        conn.setAutoCommit(false);
        
        try (java.sql.PreparedStatement pstmtRegister = conn.prepareStatement(deleteRegister);
             java.sql.PreparedStatement pstmtUsers = conn.prepareStatement(deleteUsers)) {
            
            // Delete from register table
            pstmtRegister.setString(1, studentId);
            pstmtRegister.executeUpdate();
            
            // Delete from Users table (username = student_id)
            pstmtUsers.setString(1, studentId);
            pstmtUsers.executeUpdate();
            
            conn.commit();
            
            JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Refresh the list automatically after delete
            loadStudentData();
        }
    } catch (java.sql.SQLException ex) {
        JOptionPane.showMessageDialog(this, "Delete failed:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnlogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlogoutActionPerformed
        // TODO add your handling code here:
        // Kapag tama ang login:
            new LOGIN().setVisible(true); // Pumunta agad sa Dashboard
            this.dispose(); // Isara ang Login form
    }//GEN-LAST:event_btnlogoutActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new DASHBOARD().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnadd;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnlogout;
    private javax.swing.JButton btnrefresh;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    
}
