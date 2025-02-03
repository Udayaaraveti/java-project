import java.util.Random;
import java.util.Scanner;
import java.util.*
;public class QuizApp {
    static Scanner sc = new Scanner(System.in);
    static int prizeMoney = 0;
    static boolean used5050 = false, usedSkip = false, usedAudiencePoll = false;

    static String[] questions = {
        "What does JRE stand for?",
        "What is the default value of a Boolean variable in Java?",
        "Which keyword is used to create an object in Java?",
        "Which method is used to find the length of a string in Java?",
        "What is the superclass of all classes in Java?",
        "Which package contains the Scanner class?",
        "Which of the following is a valid data type in Java?",
        "What is the size of int in Java?",
        "Which of the following is not a primitive data type in Java?",
        "Which of the following is correct way to declare a variable in Java?"
    };

    static String[][] options = {
        {"A) Java RunTime Environment", "B) Java RunTime Execution", "C) Java RealTime Environment", "D) None"},
        {"A) false", "B) true", "C) null", "D) 0"},
        {"A) object", "B) new", "C) create", "D) undefined"},
        {"A) length()", "B) size()", "C) length", "D) count()"},
        {"A) Object", "B) String", "C) Class", "D) System"},
        {"A) java.util", "B) java.io", "C) java.lang", "D) java.net"},
        {"A) boolean", "B) int", "C) char", "D) String"},
        {"A) 16", "B) 32", "C) 54", "D) 128"},
        {"A) int", "B) char", "C) String", "D) double"},
        {"A) int x= 10;", "B) int x= 10;", "C) x=10;", "D) int x= ‘10’"}
    };

    static String[] correctAnswers = {"A", "A", "B", "A", "A", "A", "B", "B", "C", "A"};

    static void showRules() {
        System.out.println("Welcome to the Quiz Game!");
        System.out.println("Rules:");
        System.out.println("1. There are " + questions.length + " questions, each with 4 options.");
        System.out.println("2. You have 3 lifelines: 50-50, Skip, Audience Poll.");
        System.out.println("3. Each lifeline can be used only once.");
        System.out.println("4. You can quit anytime and take home your winnings.");
        System.out.println("5. A wrong answer will end the game.");
        System.out.println("Do you accept the rules? (yes/no)");
        if (!sc.next().equalsIgnoreCase("yes")) {
            System.out.println("Exiting the game. Thank you!");
            System.exit(0);
        }
    }

    static boolean useLifeline(int qIndex) {
        boolean usedLifeline = false;
        while (true) {
            System.out.println("Available lifelines: ");
            if (!used5050) System.out.println("1. 50-50");
            if (!usedSkip) System.out.println("2. Skip");
            if (!usedAudiencePoll) System.out.println("3. Audience Poll");
            System.out.println("4. Quit Game");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    if (!used5050) {
                        used5050 = true;
                        System.out.println("50-50 Lifeline used! Two wrong options removed.");
                        // Display 50-50 options
                        Random rand = new Random();
                        int correctIndex = -1;
                        for (int j = 0; j < options[qIndex].length; j++) {
                            if (options[qIndex][j].startsWith(correctAnswers[qIndex])) {
                                correctIndex = j;
                                break;
                            }
                        }
                        int wrongIndex;
                        do {
                            wrongIndex = rand.nextInt(4);
                        } while (wrongIndex == correctIndex);
                        System.out.println("Remaining options:");
                        System.out.println(options[qIndex][correctIndex]);
                        System.out.println(options[qIndex][wrongIndex]);
                        usedLifeline = true;
                    } else {
                        System.out.println("You already used 50-50.");
                    }
                    break;

                case 2:
                    if (!usedSkip) {
                        usedSkip = true;
                        System.out.println("Skipping question! Moving to the next question.");
                        return true; // Skip the question
                    } else {
                        System.out.println("You already used Skip.");
                    }
                    break;

                case 3:
                    if (!usedAudiencePoll) {
                        usedAudiencePoll = true;
                        System.out.println("Audience Poll used! Most people chose " + correctAnswers[qIndex]);
                        usedLifeline = true;
                    } else {
                        System.out.println("You already used Audience Poll.");
                    }
                    break;

                case 4:
                    System.out.println("You have chosen to quit the game.");
                    System.out.println("Total winnings: Rs." + prizeMoney);
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Try again.");
            }
            if (usedLifeline) return false;
        }
    }

    static void startQuiz() {
        for (int i = 0; i < questions.length; i++) {
            System.out.println("\nQuestion " + (i + 1) + ": " + questions[i]);
            for (String opt : options[i]) {
                System.out.println(opt);
            }

            // Check if lifelines are available before asking
            System.out.println("Do you want to use a lifeline? (yes/no)");
            if (sc.next().equalsIgnoreCase("yes")) {
                boolean skipped = useLifeline(i);
                if (skipped) continue; // Move to next question if skipped
            }

            System.out.println("Enter your answer (A/B/C/D) or 'quit' to exit: ");
            String answer = sc.next();
            if (answer.equalsIgnoreCase("quit")) {
                System.out.println("You quit the game. Total winnings: Rs." + prizeMoney);
                return;
            }
            if (answer.equalsIgnoreCase(correctAnswers[i])) {
                prizeMoney += 1000;
                System.out.println("Correct! You won Rs." + prizeMoney);
            } else {
                System.out.println("Wrong! The correct answer was " + correctAnswers[i]);
                System.out.println("Total winnings: Rs." + prizeMoney);
                return;
            }
        }
        System.out.println("Congratulations! You completed the quiz. Total winnings: Rs." + prizeMoney);
    }

    public static void main(String[] args) {
        showRules();
        startQuiz();
    }
}
