package Question6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageDownloaderApp extends JFrame {
    private JTextField urlTextField;
    private JButton downloadButton;
    private JButton pauseButton;
    private JButton cancelButton;
    private JProgressBar progressBar;
    private JPanel imagePanel;

    private ExecutorService executorService;
    private boolean isPaused;
    private boolean isCanceled;
    private JButton downloadButton1;
    private JButton downloadButton2;

    public ImageDownloaderApp() {
        setTitle("Image Downloader");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        setupLayout();
        setupListeners();

        executorService = Executors.newFixedThreadPool(5);
        isPaused = false;
        isCanceled = false;
    }

    private void initComponents() {
        urlTextField = new JTextField(40);
        downloadButton = new JButton("Download");
        pauseButton = new JButton("Pause");
        cancelButton = new JButton("Cancel");
        progressBar = new JProgressBar();
        imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(0, 4, 10, 10));

        // Set fonts and styles
        Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        downloadButton.setFont(buttonFont);
        pauseButton.setFont(buttonFont);
        cancelButton.setFont(buttonFont);
        progressBar.setStringPainted(true); // Show progress string
        progressBar.setFont(buttonFont);
        urlTextField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel inputPanel = createInputPanel();
        JPanel progressPanel = createProgressPanel();
        JPanel centerPanel = createCenterPanel();

        mainPanel.add(inputPanel);
        mainPanel.add(progressPanel);
        mainPanel.add(centerPanel);

        // Add some padding
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(mainPanel);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(0, 1)); // Use a grid layout to stack components vertically

        // First URL input panel
        JPanel urlPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel urlLabel1 = new JLabel("URL: ");
        JTextField urlTextField1 = new JTextField(40);
        downloadButton1 = new JButton("Download"); // Initialize instance variable

        urlPanel1.add(urlLabel1);
        urlPanel1.add(urlTextField1);
        urlPanel1.add(downloadButton1);

        downloadButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = urlTextField1.getText().trim();
                if (!url.isEmpty()) {
                    downloadImage(url, downloadButton1); // Pass the button as an argument
                    urlTextField1.setText(""); // Clear text field after download
                    progressBar.setValue(0); // Reset progress bar
                    downloadButton1.setEnabled(false); // Disable download button during download
                }
            }
        });

        // Second URL input panel
        JPanel urlPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel urlLabel2 = new JLabel("URL: ");
        JTextField urlTextField2 = new JTextField(40);
        downloadButton2 = new JButton("Download"); // Initialize instance variable

        urlPanel2.add(urlLabel2);
        urlPanel2.add(urlTextField2);
        urlPanel2.add(downloadButton2);

        downloadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = urlTextField2.getText().trim();
                if (!url.isEmpty()) {
                    downloadImage(url, downloadButton2); // Pass the button as an argument
                    urlTextField2.setText(""); // Clear text field after download
                    progressBar.setValue(0); // Reset progress bar
                    downloadButton2.setEnabled(false); // Disable download button during download
                }
            }
        });

        inputPanel.add(urlPanel1);
        inputPanel.add(urlPanel2);

        return inputPanel;
    }

    private JPanel createProgressPanel() {
        JPanel progressPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        progressPanel.add(progressBar);
        progressPanel.add(pauseButton);
        progressPanel.add(cancelButton);
        return progressPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        return centerPanel;
    }



    private void setupListeners() {
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] urls = urlTextField.getText().split("\\s+");
                for (String url : urls) {
                    downloadImage(url.trim(), null);
                }
                urlTextField.setText(""); // Clear text field after download
                progressBar.setValue(0); // Reset progress bar
                downloadButton.setEnabled(false); // Disable download button during download
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = !isPaused;
                pauseButton.setText(isPaused ? "Resume" : "Pause");
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isCanceled = true;
                progressBar.setValue(0); // Reset progress bar
                isPaused = false;
                pauseButton.setText("Pause");
                downloadButton.setEnabled(true); // Enable download button
                JOptionPane.showMessageDialog(ImageDownloaderApp.this, "Download canceled.");
                isCanceled = false;
            }
        });
    }

    private void downloadImage(String urlString, JButton downloadButton) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                int totalBytesRead = 0;
                int fileSize = 0;

                try {
                    URL url = new URL(urlString);
                    URLConnection connection = url.openConnection();
                    fileSize = connection.getContentLength();
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    byte[] buf = new byte[1024];
                    int n;
                    totalBytesRead = 0;
                    ByteArrayOutputStream out = new ByteArrayOutputStream(); // Create new ByteArrayOutputStream for each download

                    while (-1 != (n = in.read(buf))) {
                        if (isCanceled || Thread.currentThread().isInterrupted()) {
                            Thread.interrupted();
                            in.close();
                            out.close();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    downloadButton.setEnabled(true); // Re-enable the download button
                                }
                            });
                            return;
                        }

                        while (isPaused) { // Wait until the pause is lifted
                            try {
                                Thread.sleep(100); // Adjust sleep time as needed
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }

                        out.write(buf, 0, n);
                        totalBytesRead += n;
                        int progress = (int) ((double) totalBytesRead / fileSize * 100);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                progressBar.setValue(progress);
                            }
                        });
                        try {
                            Thread.sleep(50);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    out.close(); // Close the ByteArrayOutputStream here
                    in.close();
                    final byte[] response = out.toByteArray(); // Get the byte array after it's closed
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            ImageIcon imageIcon = new ImageIcon(response);
                            JLabel imageLabel = new JLabel(imageIcon);
                            imagePanel.add(imageLabel);
                            JOptionPane.showMessageDialog(null, "Image downloaded successfully from URL: " + urlString);
                            revalidate();
                            repaint();
                            downloadButton.setEnabled(true); // Re-enable download button after download completes

                            try {
                                BufferedImage image = ImageIO.read(new ByteArrayInputStream(response));
                                saveImage(image);
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                catch (IOException e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            JOptionPane.showMessageDialog(null, "Failed to download image from URL: " + urlString + "\nError: " + e.getMessage());
                            downloadButton.setEnabled(true);
                        }
                    });
                    e.printStackTrace();
                }
                finally {
                    int progress = (int) ((double) totalBytesRead / fileSize * 100);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            progressBar.setValue(0); // Reset progress bar
                        }
                    });
                }
            }
        });
    }

    private void saveImage(BufferedImage image) {
        try {

            String downloadsFolder = System.getProperty("user.home") + File.separator + "Downloads";


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String fileName = dateFormat.format(new Date()) + ".png";


            Path outputPath = Paths.get(downloadsFolder, fileName);
            ImageIO.write(image, "png", outputPath.toFile());

        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Use system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ImageDownloaderApp imageDownloader = new ImageDownloaderApp();
                imageDownloader.setLocationRelativeTo(null); // Center the frame on the screen
                imageDownloader.setVisible(true);
            }
        });
    }
}