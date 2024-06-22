package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Display extends JFrame {
	
	public static Random random = new Random();
	public int idWord;
    public static final String[] dictionary= {
    	    "A", "Ability", "Able", "Above", "Abroad", "Absence", "Absolute", "Abstract", "Abuse", "Academic",
    	    "Accept", "Access", "Accident", "Accommodation", "Accompany", "Accomplish", "According", "Account",
    	    "Accurate", "Accuse", "Achieve", "Achievement", "Acid", "Acknowledge", "Acquire", "Across", "Act",
    	    "Action", "Active", "Activity", "Actor", "Actress", "Actual", "Actually", "Ad", "Adapt", "Add",
    	    "Addition", "Additional", "Address", "Adequate", "Adjust", "Administration", "Admire", "Admission",
    	    "Admit", "Adopt", "Adult", "Advance", "Advanced", "Advantage", "Adventure", "Advertising", "Advice",
    	    "Advise", "Affect", "Afford", "Afraid", "After", "Afternoon", "Again", "Against", "Age", "Agency",
    	    "Agenda", "Agent", "Aggressive", "Ago", "Agree", "Agreement", "Ahead", "Aid", "Aim", "Air", "Aircraft",
    	    "Airline", "Airport", "Alarm", "Album", "Alcohol", "Alive", "All", "Ally", "Almost", "Alone", "Along",
    	    "Already", "Alright", "Also", "Alter", "Alternative", "Although", "Always", "Amazing", "Ambition",
    	    "Ambitious", "Among", "Amount", "Analysis", "Analyze", "Ancient", "And", "Anger", "Angle", "Angry",
    	    "Animal", "Anniversary", "Announce", "Annual", "Another", "Answer", "Anxiety", "Anxious", "Any",
    	    "Anybody", "Anyone", "Anything", "Anyway", "Anywhere", "Apart", "Apartment", "Apology", "Apparent",
    	    "Apparently", "Appeal", "Appear", "Appearance", "Apple", "Application", "Apply", "Appoint", "Appointment",
    	    "Appreciate", "Approach", "Appropriate", "Approval", "Approve", "Approximately", "Arab", "Architect",
    	    "Architecture", "Area", "Argue", "Argument", "Arise", "Arm", "Armed", "Army", "Around", "Arrange",
    	    "Arrangement", "Arrest", "Arrival", "Arrive", "Art", "Article", "Artist", "Artistic", "As", "Asian",
    	    "Aside", "Ask", "Asleep", "Aspect", "Assault", "Assert", "Assess", "Assessment", "Asset", "Assign",
    	    "Assignment", "Assist", "Assistance", "Assistant", "Associate", "Association", "Assume", "Assumption",
    	    "Assure", "At", "Athlete", "Athletic", "Atmosphere", "Attach", "Attack", "Attempt", "Attend", "Attention",
    	    "Attitude", "Attract", "Attraction", "Attractive", "Attribute", "Audience", "Author", "Authority",
    	    "Available", "Average", "Avoid", "Award", "Aware", "Awareness", "Away", "Awful", "Baby", "Back",
    	    "Background", "Backyard", "Bacteria", "Bad", "Badly", "Bag", "Balance", "Ball", "Ban", "Band",
    	    "Bank", "Bar", "Barely", "Bargain", "Barrier", "Base", "Baseball", "Basic", "Basically", "Basis",
    	    "Basket", "Basketball", "Bath", "Bathroom", "Battery", "Battle", "Beach", "Be", "Bear", "Beat",
    	    "Beautiful", "Beauty", "Because", "Become", "Bed", "Bedroom", "Beer", "Before", "Begin", "Beginning",
    	    "Behavior", "Behind", "Being", "Belief", "Believe", "Bell", "Belong", "Below", "Belt", "Bench",
    	    "Bend", "Beneath", "Benefit", "Beside", "Besides", "Best", "Bet", "Better", "Between", "Beyond",
    	    "Bible", "Big", "Bike", "Bill", "Billion", "Bind", "Biological", "Bird", "Birth", "Birthday",
    	    "Bit", "Bite", "Black", "Blade", "Blame", "Blanket", "Blind", "Block", "Blood", "Blow", "Blue",
    	    "Board", "Boat", "Body", "Bomb", "Bond", "Bone", "Book", "Boom", "Boot", "Border", "Born",
    	    "Borrow", "Boss", "Both", "Bottle", "Bottom", "Boundary", "Bowl", "Box", "Boy", "Boyfriend",
    	    "Brain", "Branch", "Brand", "Brave", "Bread", "Break", "Breakfast", "Breast", "Breath", "Breathe",
    	    "Brick", "Bridge", "Brief", "Bright", "Brilliant", "Bring", "British", "Broad", "Broken", "Brother",
    	    "Brown", "Brush", "Buck", "Budget", "Build", "Building", "Bullet", "Bunch", "Burn", "Bus", "Business",
    	    "Busy", "But", "Butter", "Button", "Buy", "Buyer", "By", "Cabin", "Cabinet", "Cable", "Cake", "Calculate",
    	    "Call", "Calm", "Camera", "Camp", "Campaign", "Campus", "Can", "Canadian", "Cancel", "Cancer", "Candidate",
    	    "Cap", "Capability", "Capable", "Capacity", "Capital", "Captain", "Capture", "Car", "Card", "Care",
    	    "Career", "Careful", "Carefully", "Carrier", "Carry", "Case", "Cash", "Cast", "Cat", "Catch", "Category",
    	    "Cattle", "Cause", "Cease", "Ceiling", "Celebrate", "Celebration", "Celebrity", "Cell", "Center",
    	    "Central", "Century", "CEO", "Ceremony", "Certain", "Certainly", "Chain", "Chair", "Chairman", "Challenge",
    	    "Chamber", "Champion", "Chance", "Change", "Channel", "Chapter", "Character", "Characteristic", "Characterize",
    	    "Charge", "Charity", "Chart", "Chase", "Cheap", "Check", "Cheek", "Cheese", "Chef", "Chemical", "Chest",
    	    "Chicken", "Chief", "Child", "Childhood", "Chinese", "Chip", "Chocolate", "Choice", "Choose", "Christian",
    	    "Christmas", "Church", "Cigarette", "Circle", "Circumstance", "Cite", "Citizen", "City", "Civil",
    	    "Civilian", "Claim", "Class", "Classic", "Classroom", "Clean", "Clear", "Clearly", "Client", "Climate",
    	    "Climb", "Clinic", "Clinical", "Clock", "Close", "Closely", "Closer", "Clothes", "Clothing", "Cloud",
    	    "Club", "Clue", "Cluster", "Coach", "Coal", "Coalition", "Coast", "Coastal", "Coat", "Code", "Coffee",
    	    "Cognitive", "Cold", "Collapse", "Collar", "Colleague", "Collect", "Collection", "Collective", "College",
    	    "Colonel", "Colonial", "Color", "Column", "Combination", "Combine", "Come", "Comedy", "Comfort",
    	    "Comfortable", "Command", "Commander", "Comment", "Commercial", "Commission", "Commit", "Commitment",
    	    "Committee", "Common", "Communicate", "Communication", "Community", "Company", "Compare", "Comparison",
    	    "Compete", "Competition", "Competitive", "Competitor", "Complaint", "Complete", "Completely", "Complex",
    	    "Component", "Compose", "Composition", "Comprehensive", "Computer", "Concentrate", "Concentration", "Concept",
    	    "Concern", "Concerned", "Concert", "Conclude", "Conclusion", "Concrete", "Condition", "Conduct", "Conference",
    	    "Confidence", "Confident", "Confirm", "Conflict", "Confront", "Confuse", "Confusion", "Congress",
    	    "Connect", "Connection", "Conscious", "Consciousness", "Consent", "Consequently", "Consider", "Considerable",
    	    "Consideration", "Consist", "Consistent", "Constant", "Constantly", "Constitute", "Constitution",
    	    "Construct", "Construction", "Consult", "Consultant", "Consume", "Consumer", "Consumption", "Contact",
    	    "Contain", "Container", "Contemporary", "Content", "Contest", "Context", "Continental", "Continue",
    	    "Continued", "Continuous", "Contract", "Contrast", "Contribution", "Control", "Convenient", "Convention",
    	    "Conventional", "Conversation", "Convert", "Conviction", "Convince", "Cook", "Cookie", "Cooking", 
    	    "Cool","Dance", "Danger", "Dare", "Dark", "Data", "Date", "Daughter", "Day", "Dead", "Deal",
    	    "Death", "Debate", "Debt", "Decade", "Decide", "Decision", "Declare", "Decrease", "Deep", "Defense",
    	    "Define", "Degree", "Delay", "Deliver", "Demand", "Democracy", "Demonstrate", "Density", "Department", "Depend",
    	    "Depression", "Derive", "Describe", "Description", "Design", "Desire", "Desk", "Desperate", "Despite", "Destroy"};
    private JComboBox<String> languageComboBox;
    private JComboBox<String> modeComboBox;
    private JButton startButton;
    private JTextArea outputArea;

    public Display() {
        setTitle("Memory Word Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior con selección de idioma y modo
        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.add(new JLabel("Select Language:"));
        languageComboBox = new JComboBox<>(new String[]{"Español", "English", "Deutsch", "Italiano", "Français"});
        topPanel.add(languageComboBox);
        topPanel.add(new JLabel("Select Mode:"));
        modeComboBox = new JComboBox<>(new String[]{"Increasing Mode", "Concrete Mode"});
        topPanel.add(modeComboBox);
        panel.add(topPanel, BorderLayout.NORTH);

        // Botón de inicio
        startButton = new JButton("Start Game");
        panel.add(startButton, BorderLayout.CENTER);

        // Área de salida
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        panel.add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        // Agregar ActionListener al botón
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // Añadir panel al frame
        add(panel);
    }

    private void startGame() {
        int languageIndex = languageComboBox.getSelectedIndex();
        int modeIndex = modeComboBox.getSelectedIndex();
        outputArea.setText(""); // Limpiar área de salida

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
        // Aquí va la lógica del modo en aumento
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

            outputArea.append("Concrete mode selected. Displaying " + n + " words:\n");
            for (int i = 0; i < n; i++) {
                int idWord = random.nextInt(dictionary.length);
                outputArea.append(dictionary[idWord] + "\n");
            }
        } catch (NumberFormatException e) {
            outputArea.append("Invalid input. Please enter a valid integer.\n");
        }
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
