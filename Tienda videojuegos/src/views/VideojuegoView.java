package views;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 * Pantalla de gestión de videojuegos (alta, consulta, modificación y baja).
 * <p>
 * Solo muestra y lee datos: la lógica está en
 * {@link controllers.VideojuegoController}.
 * <p>
 * Nota: por cómo se armó el formulario en NetBeans, el campo del título
 * se llama {@code textFieldNombre} y el del género {@code textFieldApellido}.
 *
 * @author Postiglione
 */
public class VideojuegoView extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VideojuegoView.class.getName());

    /**
     * Crea la pantalla. El combo "Disponible" queda solo de lectura porque
     * la disponibilidad la cambian los alquileres, no el usuario.
     */
    public VideojuegoView() {
        initComponents();
        comboBoxDisponible.setEnabled(false);
    }

    /** @return título escrito por el usuario, sin espacios al costado */
    public String getTitulo() {
        return textFieldNombre.getText().trim();
    }

    /** @param titulo título a mostrar */
    public void setTitulo(String titulo) {
        textFieldNombre.setText(titulo);
    }

    /** @return género escrito por el usuario */
    public String getGenero() {
        return textFieldApellido.getText().trim();
    }

    /** @param genero género a mostrar */
    public void setGenero(String genero) {
        textFieldApellido.setText(genero);
    }

    /** @return precio tal como lo escribió el usuario (texto) */
    public String getPrecioTexto() {
        return textFieldPrecio.getText().trim();
    }

    /** @param precio precio a mostrar */
    public void setPrecioTexto(String precio) {
        textFieldPrecio.setText(precio);
    }

    /** @return plataforma elegida en el combo */
    public String getPlataforma() {
        return (String) comboBoxPlataforma.getSelectedItem();
    }

    /** @param plataforma plataforma a seleccionar en el combo */
    public void setPlataforma(String plataforma) {
        comboBoxPlataforma.setSelectedItem(plataforma);
    }

    /** @param disponible muestra "SI" o "NO" en el combo de disponibilidad */
    public void setDisponible(boolean disponible) {
        comboBoxDisponible.setSelectedItem(disponible ? "SI" : "NO");
    }

    /**
     * Borra los campos del formulario y la selección de la tabla.
     */
    public void limpiarCampos() {
        textFieldNombre.setText("");
        textFieldApellido.setText("");
        textFieldPrecio.setText("");
        comboBoxPlataforma.setSelectedIndex(0);
        comboBoxDisponible.setSelectedItem("SI");

        jTable2.clearSelection();
    }

    /**
     * @return id del videojuego seleccionado en la tabla, o -1 si no hay
     *         ninguno seleccionado
     */
    public int getIdSeleccionado() {

        int fila = jTable2.getSelectedRow();

        if (fila == -1) {
            return -1;
        }

        return Integer.parseInt(jTable2.getValueAt(fila, 0).toString());
    }

    /** @param listener acción del botón AGREGAR */
    public void agregarListenerAgregar(ActionListener listener) {
        buttonAgregar.addActionListener(listener);
    }

    /** @param listener acción del botón MODIFICAR */
    public void agregarListenerModificar(ActionListener listener) {
        buttonModificar.addActionListener(listener);
    }

    /** @param listener acción del botón ELIMINAR */
    public void agregarListenerEliminar(ActionListener listener) {
        buttonEliminar.addActionListener(listener);
    }

    /** @param listener acción del botón CANCELAR */
    public void agregarListenerCancelar(ActionListener listener) {
        buttonCancelar.addActionListener(listener);
    }

    /** @param listener acción al hacer clic en una fila de la tabla */
    public void agregarListenerTabla(MouseAdapter listener) {
        jTable2.addMouseListener(listener);
    }

    /** @param modelo datos a mostrar en la tabla */
    public void setModeloTabla(DefaultTableModel modelo) {
        jTable2.setModel(modelo);
    }

    /** @param mensaje texto a mostrar en un cuadro de diálogo */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Pide confirmación antes de eliminar.
     *
     * @return {@code true} si el usuario eligió "Sí"
     */
    public boolean confirmarEliminacion() {

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar el videojuego seleccionado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        return opcion == JOptionPane.YES_OPTION;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        labelTitulo = new javax.swing.JLabel();
        buttonAgregar = new javax.swing.JButton();
        buttonModificar = new javax.swing.JButton();
        buttonEliminar = new javax.swing.JButton();
        buttonCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textFieldNombre = new javax.swing.JTextField();
        labelApellido = new javax.swing.JLabel();
        textFieldApellido = new javax.swing.JTextField();
        labelPlataforma = new javax.swing.JLabel();
        labelPrecio = new javax.swing.JLabel();
        textFieldPrecio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        comboBoxPlataforma = new javax.swing.JComboBox<>();
        comboBoxDisponible = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestion de videojuegos");
        setResizable(false);

        labelTitulo.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        labelTitulo.setText("GESTION VIDEOJUEGOS");

        buttonAgregar.setText("AGREGAR");

        buttonModificar.setText("MODIFICAR");

        buttonEliminar.setText("ELIMINAR");
        buttonEliminar.addActionListener(this::buttonEliminarActionPerformed);

        buttonCancelar.setText("CANCELAR");

        jLabel1.setText("Título:");

        labelApellido.setText("Género:");

        labelPlataforma.setText("Plataforma:");

        labelPrecio.setText("Precio:");

        jLabel2.setText("Disponible:");

        comboBoxPlataforma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PC", "Playstation 5", "Playstation 4", "Xbox One", "Xbox Series X/S", "Nintendo Switch", "Nintendo Switch 2" }));
        comboBoxPlataforma.addActionListener(this::comboBoxPlataformaActionPerformed);

        comboBoxDisponible.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Titulo", "Género", "Precio", "Plataforma", "Disponible"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelPlataforma)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(comboBoxPlataforma, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBoxDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(labelTitulo)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labelTitulo)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPlataforma, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxPlataforma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAgregar)
                    .addComponent(buttonModificar)
                    .addComponent(buttonEliminar)
                    .addComponent(buttonCancelar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonEliminarActionPerformed

    private void comboBoxPlataformaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxPlataformaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxPlataformaActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new VideojuegoView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAgregar;
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonEliminar;
    private javax.swing.JButton buttonModificar;
    private javax.swing.JComboBox<String> comboBoxDisponible;
    private javax.swing.JComboBox<String> comboBoxPlataforma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel labelApellido;
    private javax.swing.JLabel labelPlataforma;
    private javax.swing.JLabel labelPrecio;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JTextField textFieldApellido;
    private javax.swing.JTextField textFieldNombre;
    private javax.swing.JTextField textFieldPrecio;
    // End of variables declaration//GEN-END:variables
}
