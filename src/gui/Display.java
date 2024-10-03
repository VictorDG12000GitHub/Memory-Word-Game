package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    private List<String> currentSequence; //List to store the current sequence of words.
    private Map<String, Map<String, String>> translations;
    private int sequenceLength;

    public Display() {
        setTitle("Memory Word Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Load translations from JSON.
        loadTranslations();

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

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        add(panel);
    }

    private void loadTranslations() {
        translations = new HashMap<>();
        JSONParser parser = new JSONParser();
        try {
            String absolutePath = System.getProperty("user.dir") + "/src/gui/translations.json"; 
            InputStream inputStream = new FileInputStream(absolutePath);
            Reader reader = new InputStreamReader(inputStream);
            JSONObject jsonTranslations = (JSONObject) parser.parse(reader);

            for (Object langObj : jsonTranslations.keySet()) {
                String lang = (String) langObj;
                JSONObject langTranslations = (JSONObject) jsonTranslations.get(lang);
                Map<String, String> langMap = new HashMap<>();
                for (Object keyObj : langTranslations.keySet()) {
                    String key = (String) keyObj;
                    String value = (String) langTranslations.get(key);
                    langMap.put(key, value);
                }
                translations.put(lang, langMap);
            }

            System.out.println("Traducciones cargadas correctamente: " + translations);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading translations: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startGame() {
        int languageIndex = languageComboBox.getSelectedIndex();
        int modeIndex = modeComboBox.getSelectedIndex();
        outputArea.setText(""); //Clean the output.

        //Get selected language code (es, en, de, it, fr).
        String languageCode;
        switch (languageIndex) {
            case 0:
                languageCode = "es";
                break;
            case 1:
                languageCode = "en";
                break;
            case 2:
                languageCode = "de";
                break;
            case 3:
                languageCode = "it";
                break;
            case 4:
                languageCode = "fr";
                break;
            default:
                outputArea.append("Selección de idioma inválida.\n");
                return;
        }

        if (!translations.containsKey(languageCode)) {
            System.err.println("No se encontraron traducciones para el idioma: " + languageCode);
            return;
        }

        //Display start game message based on selected language.
        String startGameMessage = translations.get(languageCode).get("startGameMessage");
        outputArea.append(startGameMessage + "\n");
        updateUIComponents(languageCode);

        //Remove start button and show words.
        Container parent = startButton.getParent();
        parent.remove(startButton);
        parent.revalidate();
        parent.repaint();

        //Show restart button.
        restartButton.setVisible(true);

        //Create and add word panel..
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
                sequenceLength = 1; 
                randomGenIncrease(languageCode);
                break;
            case 1:
                randomGenConcrete(languageCode);
                break;
            default:
                outputArea.append(translations.get(languageCode).get("invalidModeSelection") + "\n");
                break;
        }
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

    private void randomGenIncrease(String languageCode) {
        String increasingModeMessage = translations.get(languageCode).get("increasingMode");
        outputArea.append(increasingModeMessage + "\n");

        wordIndex = 0;
        List<List<String>> allSequences = new ArrayList<>(); // Lista para almacenar todas las secuencias generadas

        Timer[] hideCurrentSequenceTimer = new Timer[1]; // Array para almacenar el temporizador de ocultar secuencia actual

        showTimer = new Timer(4500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Eliminar el JLabel anterior si existe
                if (currentWordLabel != null) {
                    wordPanel.remove(currentWordLabel);
                    currentWordLabel = null; // Liberar referencia al JLabel anterior
                    wordPanel.revalidate();
                    wordPanel.repaint();
                }

                currentSequence = new ArrayList<>(); // Lista para la secuencia actual
                // Generar la secuencia de palabras
                for (int i = 0; i < sequenceLength; i++) {
                    int idWord = random.nextInt(dictionary.size());
                    String word = dictionary.get(idWord);
                    currentSequence.add(word);
                }

                allSequences.add(currentSequence); // Agregar la secuencia actual a la lista de todas las secuencias

                // Construir la cadena para mostrar todas las secuencias
                StringBuilder allSequencesDisplay = new StringBuilder();
                for (List<String> seq : allSequences) {
                    StringBuilder sequenceToDisplay = new StringBuilder();
                    for (String w : seq) {
                        sequenceToDisplay.append(w).append(" ");
                    }
                    allSequencesDisplay.append(sequenceToDisplay.toString().trim()).append("\n");
                }

                currentWordLabel = new JLabel(allSequencesDisplay.toString().trim(), SwingConstants.CENTER);
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
                

                // Iniciar temporizador para ocultar la secuencia actual
                hideCurrentSequenceTimer[0] = new Timer(3500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        wordPanel.remove(currentWordLabel);
                        wordPanel.revalidate();
                        wordPanel.repaint();
                        hideCurrentSequenceTimer[0].stop();

                        // Preguntar por cada palabra de la secuencia actual
                        int correctAnswers = 0;
                        for (int j = 0; j < currentSequence.size(); j++) {
                            String userInput = JOptionPane.showInputDialog(null,
                                    translations.get(languageCode).get("enterWord") + " " + (j + 1) + ":");
                            if (userInput != null && userInput.equals(currentSequence.get(j))) {
                                correctAnswers++;
                            }
                        }
                        System.out.println(correctAnswers + " de " + currentSequence.size() + ".");
                        

                        // Reiniciar el temporizador principal para mostrar la nueva secuencia
                        showTimer.restart();
                    }
                });
                hideCurrentSequenceTimer[0].setRepeats(false);
                hideCurrentSequenceTimer[0].start();

                showTimer.stop();
            }
        });
        showTimer.setInitialDelay(570);
        showTimer.start();
    }

    private void randomGenConcrete(String languageCode) {
        String input = JOptionPane.showInputDialog(this, translations.get(languageCode).get("enterNumberOfWords"));
        try {
            int n = Integer.parseInt(input);
            if (n <= 0) {
                outputArea.append(translations.get(languageCode).get("invalidNumber") + "\n");
                return;
            }

            wordIndex = 0;
            currentSequence = new ArrayList<>();

            showTimer = new Timer(4500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentWordLabel != null) {
                        wordPanel.remove(currentWordLabel);
                        wordPanel.revalidate();
                        wordPanel.repaint();
                    }

                    if (wordIndex < n) {
                        int idWord = random.nextInt(dictionary.size());
                        String word = dictionary.get(idWord);
                        currentSequence.add(word);
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

                        hideTimer = new Timer(4000, new ActionListener() {
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

                        wordIndex++;
                    } else {
                        showTimer.stop();
                        askWords(languageCode);
                    }
                }
            });
            showTimer.setInitialDelay(570);
            showTimer.start();
        } catch (NumberFormatException e) {
            outputArea.append(translations.get(languageCode).get("invalidNumber") + "\n");
        }
    }

    private void askWords(String languageCode) {
        int correctCount = 0;        
        List<String> userResponses = new ArrayList<>(); // Lista para almacenar las respuestas del usuario

        for (int i = 0; i < currentSequence.size(); i++) {
            String userInput = JOptionPane.showInputDialog(this, translations.get(languageCode).get("enterWord") + " " + (i + 1) + ":");
            if(userInput != null) {
            	userResponses.add(userInput); // Almacenar la respuesta del usuario
            	if (userInput.equals(currentSequence.get(i))) {
                    correctCount++;
                }
            }

        }
        JOptionPane.showMessageDialog(this, translations.get(languageCode).get("rememberedWords")
                .replace("{0}", String.valueOf(correctCount))
                .replace("{1}", String.valueOf(currentSequence.size())));

        outputArea.append(translations.get(languageCode).get("rememberedWords")
                .replace("{0}", String.valueOf(correctCount))
                .replace("{1}", String.valueOf(currentSequence.size())) + "\n");
        
        
        StringBuilder comparison = new StringBuilder();
        for (int i = 0; i < currentSequence.size(); i++) {
            String word = currentSequence.get(i);
            String userResponse = userResponses.size() > i ? userResponses.get(i) : ""; // Si no hay respuesta, dejar en blanco
            if(i!=0) {
            	comparison.append("\n");
            }
            comparison.append("Tu respuesta ").append(i + 1).append(": ").append(userResponse).append("\nPalabra ").append(i + 1).append(": ").append(word);
        }

        // Mostrar las palabras correctas y la comparación en una ventana emergente
        JOptionPane.showMessageDialog(this, translations.get(languageCode).get("correctWordsComparisonMessage")
                .replace("{0}", comparison.toString().trim()));

        // También mostrar la comparación en el área de salida
        outputArea.append(translations.get(languageCode).get("correctWordsComparisonMessage")
                .replace("{0}", comparison.toString().trim()) + "\n");

        
        
        currentSequence.clear();
        sequenceLength = 1;

        restartGame();
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

    private void updateUIComponents(String languageCode) {
        startButton.setText(translations.get(languageCode).get("startGameButton"));
        restartButton.setText(translations.get(languageCode).get("restartButton"));
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
