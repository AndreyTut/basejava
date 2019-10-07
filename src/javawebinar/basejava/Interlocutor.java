package javawebinar.basejava;

public class Interlocutor {
    private String name;

    public Interlocutor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public synchronized void askQuestion(Interlocutor interlocutor) throws InterruptedException {
        System.out.println(name + " ask question to " + interlocutor.getName());
        Thread.sleep(100);
        interlocutor.giveAnswer(this);
    }

    public synchronized void giveAnswer(Interlocutor interlocutor) {
        System.out.println(name + " answered to " + interlocutor.getName());
    }

    public static void main(String[] args) throws InterruptedException {
        Interlocutor alfa = new Interlocutor("Alfa");
        Interlocutor beta = new Interlocutor("Beta");
        Thread thread1 = new Thread(() -> {
            try {
                alfa.askQuestion(beta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                beta.askQuestion(alfa);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
    }
}
