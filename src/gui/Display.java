package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Display extends JFrame {

    public static Random random = new Random();
    public int idWord;
    public List<String> dictionary; //List to store the words from the csv.
    private JComboBox<String> languageComboBox;
    private JComboBox<String> modeComboBox;
    private JButton startButton;
    private JButton restartButton;
    private JTextArea outputArea;
    private JPanel wordPanel;
    private Timer showTimer;
    private Timer hideTimer;
    private int wordIndex;
    private JLabel currentWordLabel;
    private List<String> wordsDisplayed; //List to store the words that are shown.

    public Display() {
        setTitle("Memory Word Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Main panel.
        JPanel panel = new JPanel(new BorderLayout());

        //Language and mode panels.
        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.add(new JLabel("Select Language:"));
        languageComboBox = new JComboBox<>(new String[]{"Español", "English", "Deutsch", "Italiano", "Français"});
        topPanel.add(languageComboBox);
        topPanel.add(new JLabel("Select Mode:"));
        modeComboBox = new JComboBox<>(new String[]{"Increasing Mode", "Concrete Mode"});
        topPanel.add(modeComboBox);
        panel.add(topPanel, BorderLayout.NORTH);

        //Start game button.
        startButton = new JButton("Start Game");
        panel.add(startButton, BorderLayout.CENTER);

        //Output Area.
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        panel.add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        //Restart button.
        restartButton = new JButton("Restart");
        restartButton.setVisible(false);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        panel.add(restartButton, BorderLayout.SOUTH);

        //ActionListener to StartButton;
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        add(panel);
    }

    private void startGame() {
        int languageIndex = languageComboBox.getSelectedIndex();
        int modeIndex = modeComboBox.getSelectedIndex();
        outputArea.setText(""); //Clean the output.

        switch (languageIndex) {
            case 0:
                outputArea.append("(1.Modo en aumento, 2.Modo concreto).\n¿Qué modo quieres?\n");
                break;
            case 1:
                outputArea.append("(1.Increasing mode, 2.Concrete mode).\nWhich mode do you want?\n");
                break;
            case 2:
                outputArea.append("(1.Erhöhungsmodus, 2.Betonmodus).\nWelchen Modus möchten Sie?\n");
                break;
            case 3:
                outputArea.append("(1.Modalità crescente, 2.Modalità calcestruzzo).\nQuale modalità desideri?\n");
                break;
            case 4:
                outputArea.append("(1.Mode croissant, 2.Mode béton).\nQuel mode souhaitez-vous?\n");
                break;
        }

        //Remove start button and show words.
        Container parent = startButton.getParent();
        parent.remove(startButton);
        parent.revalidate();
        parent.repaint();

        //Show restart button.
        restartButton.setVisible(true);

        //Create and add word panel.
        wordPanel = new JPanel(new GridBagLayout());
        parent.add(wordPanel, BorderLayout.CENTER);
        parent.revalidate();
        parent.repaint();

        wordsDisplayed = new ArrayList<>();
        //Relative path.
        String userDir = System.getProperty("user.dir");
        String relativePath = "src/gui/words.csv";
        String absolutePath = userDir + File.separator + relativePath;

        //Method to read the words from the CSV.
        dictionary = readWordsFromCSV(absolutePath);

        //Mode selection.
        switch (modeIndex) {
            case 0:
                randomGenIncrease();
                break;
            case 1:
                randomGenConcrete();
                break;
            default:
                outputArea.append("Invalid mode selected.\n");
                break;
        }
    }

    private void randomGenIncrease() {
        outputArea.append("Increasing mode selected.\n");
    }

    private void randomGenConcrete() {
        String input = JOptionPane.showInputDialog(this, "Enter the number of words to display:");
        outputArea.append("Concrete mode selected.\n");
        try {
            int n = Integer.parseInt(input);
            if (n <= 0) {
                outputArea.append("Please enter a positive number.\n");
                return;
            }

            wordIndex = 0;
            //Timer starts with 1-second delay for the first word.
            showTimer = new Timer(1000, new ActionListener() { 
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (wordIndex < n) {
                        if (currentWordLabel != null) {
                            wordPanel.remove(currentWordLabel);
                            wordPanel.revalidate();
                            wordPanel.repaint();
                        }

                        int idWord = random.nextInt(1292);
                        String word = dictionary.get(idWord);
                        wordsDisplayed.add(word);
                        currentWordLabel = new JLabel(word, SwingConstants.CENTER);
                        currentWordLabel.setFont(new Font("Arial", Font.BOLD, 36));
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.weightx = 1.0;
                        gbc.weighty = 1.0;
                        gbc.anchor = GridBagConstraints.CENTER;
                        wordPanel.add(currentWordLabel, gbc);
                        wordPanel.revalidate();
                        wordPanel.repaint();
                        //After the first word, set delay to 4 seconds.
                        wordIndex++;
                        if (wordIndex == 1) {
                            showTimer.setDelay(4000);
                        }
                        //Timer to hide the word 0.5 seconds before the next word.
                        hideTimer = new Timer(3500, new ActionListener() { 
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                wordPanel.remove(currentWordLabel);
                                wordPanel.revalidate();
                                wordPanel.repaint();
                                hideTimer.stop();
                            }
                        });
                        hideTimer.setRepeats(false);
                        hideTimer.start();
                    } else {
                        showTimer.stop();
                        askWords(); 
                    }
                }
            });
            showTimer.setInitialDelay(0); 
            showTimer.start();
        } catch (NumberFormatException e) {
            outputArea.append("Invalid input. Please enter a valid integer.\n");
        }
    }

    private void askWords() {
        int correctCount = 0;
        for (int i = 0; i < wordsDisplayed.size(); i++) {
            String userInput = JOptionPane.showInputDialog(this, "Enter word " + (i + 1) + ":");
            if (userInput != null && userInput.equals(wordsDisplayed.get(i))) {
                correctCount++;
            }
        }
        JOptionPane.showMessageDialog(this, "You remembered " + correctCount + " out of " + wordsDisplayed.size() + " words correctly.");

        outputArea.append("You remembered " + correctCount + " out of " + wordsDisplayed.size() + " words correctly.\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());

        wordsDisplayed.clear();
    }

    private List<String> readWordsFromCSV(String fileName) {
        List<String> words = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 2) {
                    String word = parts[1].trim().replaceAll("\"", ""); 
                    words.add(word);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
        return words;
    }

    private void restartGame() {
        if (showTimer != null) {
            showTimer.stop();
        }
        if (hideTimer != null) {
            hideTimer.stop();
        }

        if (wordPanel != null) {
            wordPanel.removeAll();
            wordPanel.revalidate();
            wordPanel.repaint();
        }

        restartButton.setVisible(false);

        Container parent = restartButton.getParent();
        parent.add(startButton, BorderLayout.CENTER);
        parent.revalidate();
        parent.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Display().setVisible(true);
            }
        });
    }
}
