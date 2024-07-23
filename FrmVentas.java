/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package referenciales.ventas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import librerias.DocumentoNumerico;
import librerias.Propiedades;

/**
 *
 * @author PC
 */
public class FrmVentas extends javax.swing.JFrame {

    /**
     * Creates new form FrmVentas
     */
    public FrmVentas() {
        initComponents();
        setTitle("Ventas");
        setLocationRelativeTo(null);
        cargarCombo("SELECT descripcion FROM tiposdocumentos ORDER BY codigotipodocumento;", cmbTipoDocumento);
        cargarCombo("SELECT descripcion FROM condiciones ORDER BY codigocondicion;", cmbCondicion);
        getFecha();
        txtCantidad.setDocument(new DocumentoNumerico("."));
    }

    private void cargarCombo(String sql, JComboBox combo) {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = DriverManager.getConnection(Propiedades.url, Propiedades.usuario, Propiedades.clave);
            sentencia = conexion.prepareStatement(sql);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                String descripcion = resultado.getString(1);
                combo.addItem(descripcion);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar los datos en el combobox.\n" + "Mensaje: " + e.getMessage());
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No se pudo cerrar el resulset.");
            }
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No se pudo cerrar el resulset.");
            }
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No se pudo cerrar el resulset.");
            }
        }
    }

    private void getFecha() {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = DriverManager.getConnection(Propiedades.url, Propiedades.usuario, Propiedades.clave);
            sentencia = conexion.prepareStatement("SELECT CURDATE();");
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                String fecha = resultado.getString(1);
                txtFecha.setText(fecha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo recuperar la fecha.\n"
                    + "Mensaje: " + e.getMessage());
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No se pudo cerrar el resulset.");
            }
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No se pudo cerrar el resulset.");
            }
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No se pudo cerrar el resulset.");
            }
        }
    }

    private void generarNumeroVenta() {
        String codigoSucursal = "001";
        String puntoExpedicion = "001";
        String tipoDocumento = cmbTipoDocumento.getSelectedItem().toString();
        String[] parametros = {tipoDocumento};
        String codigoTipoDocumento = recuperarCodigo("SELECT codigotipodocumento FROM tiposdocumentos WHERE descripcion = ?;", parametros);
        String[] parametros2 = {codigoTipoDocumento, codigoSucursal, puntoExpedicion};
        String numeroVenta = recuperarCodigo("SELECT IF(MAX(numeroventa) IS NULL, 1, MAX(numeroventa) + 1) AS numeroventa FROM ventas WHERE codigotipodocumento = ? AND codigosucursal = ? AND codigopuntoexpedicion = ?;", parametros2);
        txtSucursal.setText(codigoSucursal);
        txtPuntoExpedicion.setText(puntoExpedicion);
        txtNumeroVenta.setText(numeroVenta);
    }

    private String recuperarCodigo(String sql, String[] parametros) {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        String codigo = "";
        try {
            conexion = DriverManager.getConnection(Propiedades.url, Propiedades.usuario, Propiedades.clave);
            sentencia = conexion.prepareStatement(sql);
            for (int contador = 0; contador < parametros.length; contador++) {
                sentencia.setString(contador + 1, parametros[contador]);
            }
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                codigo = resultado.getString(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo recuperar el codigo.\n" + "Mensaje: " + e.getMessage());
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No se pudo cerrar el resulset.");
            }
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No se pudo cerrar el resulset.");
            }
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No se pudo cerrar el resulset.");
            }
        }
        return codigo;
    }

    private void recuperarDatos(String codigoInterno) {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = DriverManager.getConnection(Propiedades.url, Propiedades.usuario, Propiedades.clave);
            sentencia = conexion.prepareStatement("SELECT * FROM vmercaderias WHERE codigointerno = ?;");

            sentencia.setString(1, codigoInterno);
            resultado = sentencia.executeQuery();

            resultado.next();

            String codBarras = resultado.getString("codigobarras");
            String descripcion = resultado.getString("descripcion");
            String precioventaminorista = resultado.getString("precioventaminorista");
            String stockactual = resultado.getString("stockactual");
            String unidamedida = resultado.getString("unidadmedida");

            txtCodigoBarra.setText(codBarras);
            txtDescripcion.setText(descripcion);
            txtStock.setText(stockactual);
            txtPrecio.setText(precioventaminorista);
            txtUnidadMedida.setText(unidamedida);

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "No se pudo recuperar los datos Mensaje:" + error.getMessage());
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No se pudo cerrar el resulset");
            }
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No se pudo cerrar el resulset");
            }
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "No se pudo cerrar el resulset");
            }
        }
    }

    private void habilitarControles(boolean habilitar) {
        txtCodigoBarra.setEditable(habilitar);
        txtCodigoInterno.setEditable(habilitar);
    }

    private void agregarDetalles() {
        String codigoInterno = txtCodigoInterno.getText().trim();
        if (codigoInterno.isEmpty()) {
            JOptionPane.showMessageDialog(null, "EL  CAMPO CODIGO INTERNO NO PUEDE ESTAR VACIO");
            txtCodigoInterno.requestFocus();
            return;
        }

        String descripcion = txtDescripcion.getText().trim();
        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "EL  CAMPO DESCRIPCION NO PUEDE ESTAR VACIO");
            txtDescripcion.requestFocus();
            return;
        }

        String unidamedida = txtUnidadMedida.getText().trim();
        if (unidamedida.isEmpty()) {
            JOptionPane.showMessageDialog(null, "EL  CAMPO UNIDAD DE MEDIDA NO PUEDE ESTAR VACIO");
            txtUnidadMedida.requestFocus();
            return;
        }

        String cantidadString = (txtCantidad.getText().trim());
        if (cantidadString.isEmpty()) {
            JOptionPane.showMessageDialog(null, "EL  CAMPO CANTIDAD NO PUEDE ESTAR VACIO");
            txtCantidad.requestFocus();
            return;
        }

        String precioString = (txtPrecio.getText().trim());
        if (precioString.isEmpty()) {
            JOptionPane.showMessageDialog(null, "EL  CAMPO PRECIO NO PUEDE ESTAR VACIO");
            txtPrecio.requestFocus();
            return;
        }

        double cantidad = Double.parseDouble(cantidadString);
        double precio = Double.parseDouble(precioString);
        double subtotal = cantidad * precio;

        DefaultTableModel modelo = (DefaultTableModel) tablaDetalleVenta.getModel();
        Object[] item = {codigoInterno, descripcion, cantidad, precio, subtotal};
        modelo.addRow(item);

    }

    private void limpiarCampos() {
        txtCodigoBarra.setText("");
        txtCodigoInterno.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtUnidadMedida.setText("");
        txtCantidad.setText("");
    }

    private void filtrar() {
        String textoParaFiltrar = txtCodigoBarra.getText().trim();
        if (textoParaFiltrar.isEmpty()) {
            ((TableRowSorter) tablaDetalleVenta.getRowSorter()).setRowFilter(null);
        } else {
            ((TableRowSorter) tablaDetalleVenta.getRowSorter()).setRowFilter(RowFilter.regexFilter("(?i)" + textoParaFiltrar));
        }
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
        jLabel1 = new javax.swing.JLabel();
        txtSucursal = new javax.swing.JTextField();
        txtPuntoExpedicion = new javax.swing.JTextField();
        txtNumeroVenta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        txtCodigoUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbTipoDocumento = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbCondicion = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoCliente = new javax.swing.JTextField();
        txtRazonSocial = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtRUC = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnBuscarCliente = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDetalleVenta = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtCodigoInterno = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtCodigoBarra = new javax.swing.JTextField();
        btnBuscarMercaderia = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        txtDescripcion = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        txtUnidadMedida = new javax.swing.JTextField();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Numero de Venta:");

        txtSucursal.setEditable(false);
        txtSucursal.setEnabled(false);

        txtPuntoExpedicion.setEditable(false);
        txtPuntoExpedicion.setEnabled(false);

        txtNumeroVenta.setEditable(false);
        txtNumeroVenta.setEnabled(false);

        jLabel2.setText("Fecha:");

        txtFecha.setEditable(false);
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setEnabled(false);

        txtCodigoUsuario.setEditable(false);
        txtCodigoUsuario.setEnabled(false);

        jLabel3.setText("Usuario:");

        txtUsuario.setEditable(false);
        txtUsuario.setEnabled(false);

        jLabel4.setText("Tipo de Documento:");

        cmbTipoDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoDocumentoActionPerformed(evt);
            }
        });

        jLabel5.setText("Condicion:");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS DEL CLIENTE"));

        jLabel6.setText("Nombre o Razon Social:");

        jLabel7.setText("Cedula de Identidad:");

        txtCodigoCliente.setEditable(false);

        txtRazonSocial.setEditable(false);

        txtCedula.setEditable(false);

        jLabel8.setText("RUC:");

        txtRUC.setEditable(false);

        jLabel9.setText("Direccion:");

        txtDireccion.setEditable(false);

        jLabel10.setText("Email:");

        txtEmail.setEditable(false);

        btnBuscarCliente.setText("BUSCAR");
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscarCliente))
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Unidad", "Cantidad", "Precio", "SubTotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaDetalleVenta);
        if (tablaDetalleVenta.getColumnModel().getColumnCount() > 0) {
            tablaDetalleVenta.getColumnModel().getColumn(0).setResizable(false);
            tablaDetalleVenta.getColumnModel().getColumn(1).setResizable(false);
            tablaDetalleVenta.getColumnModel().getColumn(1).setPreferredWidth(200);
            tablaDetalleVenta.getColumnModel().getColumn(2).setResizable(false);
            tablaDetalleVenta.getColumnModel().getColumn(3).setResizable(false);
            tablaDetalleVenta.getColumnModel().getColumn(4).setResizable(false);
            tablaDetalleVenta.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel11.setText("LIQUIDACION IVA");

        jLabel12.setText("5%");

        jLabel13.setText("10%");

        jLabel14.setText("TOTAL IVA");

        jLabel15.setText("TOTAL VENTA");

        jLabel16.setText("Codigo:");

        jLabel17.setText("Cod. de Barra:");

        jLabel18.setText("Cantidad:");

        txtCodigoInterno.setEditable(false);

        txtCodigoBarra.setEditable(false);

        btnBuscarMercaderia.setText("BUSCAR");
        btnBuscarMercaderia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarMercaderiaActionPerformed(evt);
            }
        });

        btnAgregar.setText("AGREGAR");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        txtDescripcion.setEditable(false);

        txtPrecio.setEditable(false);

        txtStock.setEditable(false);

        jLabel19.setText("Descripcion:");

        jLabel20.setText("Precio:");

        jLabel21.setText("STOCK:");

        jButton4.setText("GUARDAR");

        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jButton6.setText("ELIMINAR");

        jLabel22.setText("Unidad de Medida:");

        txtUnidadMedida.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField16))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCodigoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUsuario))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPuntoExpedicion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumeroVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodigoInterno))
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodigoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(btnBuscarMercaderia)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtStock)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUnidadMedida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addGap(11, 11, 11)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPuntoExpedicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cmbTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cmbCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(txtCodigoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscarMercaderia))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(jLabel20)
                        .addComponent(jLabel21)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtCodigoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar)
                    .addComponent(jButton4)
                    .addComponent(btnCancelar)
                    .addComponent(jButton6)
                    .addComponent(jLabel22)
                    .addComponent(txtUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jLabel12)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbTipoDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoDocumentoActionPerformed
        generarNumeroVenta();
    }//GEN-LAST:event_cmbTipoDocumentoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        txtCodigoUsuario.setText("1");
        txtUsuario.setText("ADMINISTRADOR SISTEMA");
    }//GEN-LAST:event_formWindowOpened

    private void btnBuscarMercaderiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMercaderiaActionPerformed
        FrmBuscadorMercaderias buscarMercaderias = new FrmBuscadorMercaderias(null, true);
        buscarMercaderias.setVisible(true);
        String codigoInterno = buscarMercaderias.getSelecionado(0);
        txtCodigoInterno.setText(codigoInterno);
        habilitarControles(false);
        recuperarDatos(codigoInterno);
        txtCantidad.requestFocus();
    }//GEN-LAST:event_btnBuscarMercaderiaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        habilitarControles(false);
        limpiarCampos();
        filtrar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        agregarDetalles();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        FrmClientes clientes = new FrmClientes(null, true);
        clientes.setVisible(true);
        txtCodigoCliente.setText(clientes.getSeleccionado(0));
        txtCedula.setText(clientes.getSeleccionado(1));
        txtRUC.setText(clientes.getSeleccionado(2));
        txtRazonSocial.setText(clientes.getSeleccionado(3));
        txtDireccion.setText(clientes.getSeleccionado(4));
        txtEmail.setText(clientes.getSeleccionado(5));
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

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
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarMercaderia;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> cmbCondicion;
    private javax.swing.JComboBox<String> cmbTipoDocumento;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTable tablaDetalleVenta;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCodigoBarra;
    private javax.swing.JTextField txtCodigoCliente;
    private javax.swing.JTextField txtCodigoInterno;
    private javax.swing.JTextField txtCodigoUsuario;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNumeroVenta;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtPuntoExpedicion;
    private javax.swing.JTextField txtRUC;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JTextField txtStock;
    private javax.swing.JTextField txtSucursal;
    private javax.swing.JTextField txtUnidadMedida;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
