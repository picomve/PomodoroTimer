package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    private int secondsRemaining = 25 * 60;
    private final Timer timer;
    private final JLabel timerLabel;
    private final JButton startBtn;

    public App() {
        JFrame frame = new JFrame("Java Pomodoro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250);
        frame.getContentPane().setBackground(new Color(30, 30, 30));

        // Layout ayarı
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(30, 30, 30));

        // Zaman Etiketi
        timerLabel = new JLabel("25:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 60));
        timerLabel.setForeground(new Color(255, 87, 87)); // Pomodoro Kırmızısı
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Butonlar
        startBtn = new JButton("Başlat");
        JButton resetBtn = new JButton("Sıfırla");

        styleButton(startBtn);
        styleButton(resetBtn);

        // Zamanlayıcı Mantığı
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (secondsRemaining > 0) {
                    secondsRemaining--;
                    updateLabel();
                } else {
                    timer.stop();
                    timerLabel.setText("00:00");
                    JOptionPane.showMessageDialog(frame, "Odaklanma süresi bitti! Mola ver.");
                }
            }
        });

        startBtn.addActionListener(e -> {
            if (!timer.isRunning()) {
                timer.start();
                startBtn.setText("Duraklat");
            } else {
                timer.stop();
                startBtn.setText("Devam Et");
            }
        });

        resetBtn.addActionListener(e -> {
            timer.stop();
            secondsRemaining = 25 * 60;
            updateLabel();
            startBtn.setText("Başlat");
        });

        // Elemanları Ekleme
        panel.add(Box.createVerticalGlue());
        panel.add(timerLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(startBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(resetBtn);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setLocationRelativeTo(null); // Ekranın ortasında açılır
        frame.setVisible(true);
    }

    private void updateLabel() {
        int mins = secondsRemaining / 60;
        int secs = secondsRemaining % 60;
        timerLabel.setText(String.format("%02d:%02d", mins, secs));
    }

    private void styleButton(JButton btn) {
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(150, 40));
    }

    public static void main(String[] args) {
        // Modern görünüm için sistem temasını kullan
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(App::new);
    }
}