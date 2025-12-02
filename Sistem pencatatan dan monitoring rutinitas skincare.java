import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SkincareMonitoringApp extends JFrame {

    public static HashMap<String, String> jadwalMingguan = new HashMap<>();

    public SkincareMonitoringApp() {
        setTitle("Skincare Monitoring");
        setSize(430, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Gradient background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(245, 223, 255),
                        0, getHeight(), new Color(255, 228, 240)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Skincare Monitoring Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(105, 60, 135));
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

        // Tombol menu dengan warna & ikon berbeda
        JButton isiBtn = createMenuButton("📝 Isi Jadwal Skincare", new Color(255, 220, 230));
        JButton lihatBtn = createMenuButton("📅 Lihat Jadwal Mingguan", new Color(220, 240, 255));
        JButton remindBtn = createMenuButton("⏰ Pengingat Hari Ini", new Color(230, 255, 220));

        isiBtn.addActionListener(e -> new PageIsiJadwal().setVisible(true));
        lihatBtn.addActionListener(e -> new PageLihatJadwal().setVisible(true));
        remindBtn.addActionListener(e -> new PageReminder().setVisible(true));

        panel.add(title);
        panel.add(Box.createVerticalStrut(15)); // spasi antar tombol
        panel.add(isiBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lihatBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(remindBtn);

        add(panel);
    }

    // Tombol estetika dengan warna & ikon
    private JButton createMenuButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setPreferredSize(new Dimension(300, 50));
        btn.setMaximumSize(new Dimension(300, 50));
        btn.setBackground(bgColor);
        btn.setForeground(new Color(80, 40, 110));
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(150, 100, 180), 2, true));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        new SkincareMonitoringApp().setVisible(true);
    }
}

// ===========================================================
// PAGE 1 : ISI JADWAL SKINCARE
// ===========================================================

class PageIsiJadwal extends JFrame {
    public PageIsiJadwal() {
        setTitle("Isi Jadwal Skincare");
        setSize(430, 550);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 6, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(255, 240, 250));

        String[] hari = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
        JTextField[] field = new JTextField[7];

        for (int i = 0; i < 7; i++) {
            JLabel label = new JLabel(hari[i]);
            label.setFont(new Font("Serif", Font.BOLD, 18));
            field[i] = new JTextField();
            field[i].setFont(new Font("SansSerif", Font.PLAIN, 16));

            if (SkincareMonitoringApp.jadwalMingguan.get(hari[i]) != null) {
                field[i].setText(SkincareMonitoringApp.jadwalMingguan.get(hari[i]));
            }

            panel.add(label);
            panel.add(field[i]);
        }

        JButton saveBtn = new JButton("Simpan");
        saveBtn.setBackground(new Color(235, 180, 225));
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 16));

        JButton backBtn = new JButton("⟵ Kembali");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        backBtn.setBackground(new Color(210, 170, 230));
        backBtn.setForeground(Color.BLACK);

        saveBtn.addActionListener(e -> {
            for (int i = 0; i < 7; i++) {
                SkincareMonitoringApp.jadwalMingguan.put(hari[i], field[i].getText());
            }
            JOptionPane.showMessageDialog(this, "Jadwal berhasil disimpan!");
        });

        backBtn.addActionListener(e -> dispose());

        panel.add(saveBtn);
        panel.add(backBtn);

        add(panel);
    }
}

// ===========================================================
// PAGE 2 : LIHAT JADWAL MINGGUAN
// ===========================================================

class PageLihatJadwal extends JFrame {
    public PageLihatJadwal() {
        setTitle("Jadwal Mingguan");
        setSize(430, 500);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Serif", Font.PLAIN, 18));
        area.setBackground(new Color(255, 240, 250));

        StringBuilder sb = new StringBuilder();
        String[] hari = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};

        for (String h : hari) {
            sb.append(h).append(" : ").append(
                    SkincareMonitoringApp.jadwalMingguan.getOrDefault(h, "(kosong)")
            ).append("\n\n");
        }

        area.setText(sb.toString());

        JButton backBtn = new JButton("⟵ Kembali");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        backBtn.setBackground(new Color(200, 155, 225));
        backBtn.setForeground(Color.BLACK);
        backBtn.addActionListener(e -> dispose());

        add(new JScrollPane(area), BorderLayout.CENTER);
        add(backBtn, BorderLayout.SOUTH);
    }
}

// ===========================================================
// PAGE 3 : PENGINGAT HARI INI
// ===========================================================

class PageReminder extends JFrame {
    public PageReminder() {
        setTitle("Pengingat Hari Ini");
        setSize(430, 300);
        setLocationRelativeTo(null);

        String hariIniInggris = java.time.LocalDate.now().getDayOfWeek().name();

        HashMap<String, String> mapHari = new HashMap<>();
        mapHari.put("MONDAY", "Senin");
        mapHari.put("TUESDAY", "Selasa");
        mapHari.put("WEDNESDAY", "Rabu");
        mapHari.put("THURSDAY", "Kamis");
        mapHari.put("FRIDAY", "Jumat");
        mapHari.put("SATURDAY", "Sabtu");
        mapHari.put("SUNDAY", "Minggu");

        String hariIni = mapHari.getOrDefault(hariIniInggris, "(tidak diketahui)");

        String jadwal = SkincareMonitoringApp.jadwalMingguan.getOrDefault(hariIni, "(belum diisi)");

        JLabel label = new JLabel("Skincare untuk " + hariIni + ": " + jadwal);
        label.setFont(new Font("Serif", Font.BOLD, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton backBtn = new JButton("⟵ Kembali");
        backBtn.setBackground(new Color(190, 140, 210));
        backBtn.setForeground(Color.BLACK);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        backBtn.addActionListener(e -> dispose());

        add(label, BorderLayout.CENTER);
        add(backBtn, BorderLayout.SOUTH);
    }
}
