package javawebinar.basejava;

public class Interlocutor {
    private String name;

    public Interlocutor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public synchronized void askQuestion(Interlocutor interlocutor) {
        System.out.println(name + " ask question to " + interlocutor.getName());
        interlocutor.giveAnswer(this);
    }

    public synchronized void giveAnswer(Interlocutor interlocutor) {
        System.out.println(name + " answered to " + interlocutor.getName());
    }

    public static void main(String[] args) throws InterruptedException {
        Interlocutor alfa = new Interlocutor("Alfa");
        Interlocutor beta = new Interlocutor("Beta");
        Thread thread1 = new Thread(() -> alfa.askQuestion(beta));
        Thread thread2 = new Thread(() -> beta.askQuestion(alfa));
        thread1.start();
        thread2.start();
    }
}
