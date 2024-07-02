package formularios;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class MensagemCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Estilização das células
        if (row % 2 == 0) {
            cellComponent.setBackground(new Color(240, 240, 240));
        } else {
            cellComponent.setBackground(Color.WHITE);
        }

        // Estilo das bordas
        if (column == 0) {
            ((JLabel) cellComponent).setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            ((JLabel) cellComponent).setHorizontalAlignment(SwingConstants.LEFT);
        }

        if (isSelected) {
            cellComponent.setBackground(new Color(175, 238, 238));
        }

        return cellComponent;
    }
}
