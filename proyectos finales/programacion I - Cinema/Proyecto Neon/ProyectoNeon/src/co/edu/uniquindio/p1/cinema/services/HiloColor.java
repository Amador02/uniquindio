package co.edu.uniquindio.p1.cinema.services;

import javax.swing.JLabel;

import co.edu.uniquindio.p1.cinema.view.ParteSuperior;

public class HiloColor extends Thread {

	private ParteSuperior pS;
	private JLabel jLabel;

	public HiloColor(ParteSuperior pS, JLabel jLabel) {
		this.pS = pS;
		this.jLabel = jLabel;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		setName("Stellar Cinema RGB");
		while (true) {
			System.out.print("");
			pS.obtenerColLabels(jLabel);
			try {
				sleep(25);
			} catch (InterruptedException e1) {
				stop();
			}
		}
	}
}