package customized;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class CustomTextfield extends JComponent implements FocusListener {
	private JTextField tf;
	private JSeparator separador;
	private Color color = Herramientas.white;
	private Color noSelColor = Herramientas.gray.darker();
	private String notSelectedText;
	private boolean isSelectioned;

	public CustomTextfield(String notSelectedText) {
		setLayout(null);
		setNotSelectedText(notSelectedText);
		setTf(new JTextField(notSelectedText));
		setSeparador(new JSeparator());
		setCursor(Herramientas.TEXT_CURSOR);

		getTf().setBorder(null);
		getTf().addFocusListener(this);
		getTf().setFont(Herramientas.FUENTE_COOLVETICA);
		getTf().setForeground(Herramientas.white);
		getTf().setBackground(Herramientas.black);
		getSeparador().setForeground(Herramientas.white);
		getSeparador().setBackground(Herramientas.black);
		setSelectioned(false);

		add(getTf());
		add(getSeparador());
	}

	public void setEditable(boolean b) {
		getTf().setEditable(b);
	}

	public void setEnabled(boolean b) {
		getTf().setEnabled(b);
		if (b) {
			getSeparador().setForeground(color);
		} else {
			getSeparador().setForeground(getTf().getDisabledTextColor());
		}
	}

	public Font getFont() {
		return getTf().getFont();
	}

	public void setFont(Font f) {
		getTf().setFont(f);
	}

	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		getTf().setBounds(0, 0, width, height - 1);
		getSeparador().setBounds(0, height - 1, width, 1);
	}

	public void setForeground(Color c) {
		getTf().setForeground(c);
		getSeparador().setForeground(c);
	}

	public void setBounds(Rectangle b) {
		super.setBounds(b);
		getTf().setBounds(0, 0, b.width, b.height - 1);
		getSeparador().setBounds(0, b.height - 1, b.width, 1);
	}

	public void setText(String text) {
		getTf().setText(text);
	}

	public String getText() {
		String total = "";
		for (String parcial : getTf().getText().replace('.', '-').split("-"))
			total += parcial;
		return total;
	}

	public void setFontSize(float size) {
		setFont(getFont().deriveFont(size));
	}

	public JSeparator getSeparador() {
		return separador;
	}

	public void setSeparador(JSeparator separador) {
		this.separador = separador;
	}

	public JTextField getTf() {
		return tf;
	}

	public void setTf(JTextField tf) {
		this.tf = tf;
	}

	public void actualizarColor(Color c) {
		color = c;
		if (isSelectioned()) {
			getSeparador().setForeground(c);
			getTf().setForeground(c);
		}
	}

	public String getNotSelectedText() {
		return notSelectedText;
	}

	public void setNotSelectedText(String notSelectedText) {
		this.notSelectedText = notSelectedText;
	}

	public boolean isSelectioned() {
		return isSelectioned;
	}

	public void setSelectioned(boolean isSelectioned) {
		this.isSelectioned = isSelectioned;
		if (isSelectioned) {
			getTf().setForeground(color);
			getSeparador().setForeground(color);
			getTf().setEditable(true);
			if (getText().equals(notSelectedText)) {
				setText("");
			}
		} else {
			getTf().setForeground(getDisabledColor());
			getSeparador().setForeground(getDisabledColor());
			getTf().setEditable(false);
			if (getText().equals("")) {
				setText(notSelectedText);
			}
		}
	}

	public Color getDisabledColor() {
		return noSelColor;
	}

	public void setDisabledColor(Color noSelColor) {
		this.noSelColor = noSelColor;
	}

	@Override
	public void focusGained(FocusEvent e) {
		setSelectioned(true);
	}

	@Override
	public void focusLost(FocusEvent e) {
		setSelectioned(false);

	}
}
