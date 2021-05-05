package net.davoleo.unipr;

import java.util.Scanner;

class HelloWorld {
    //campo dati
    private int x;

    //Costruttore
    public HelloWorld() {

    }

    //Metodo
    public void print() {

    }

    //Metodo Main (chiamato all'inizio dell'avvio del programma)
    public static void main(String[] args) {

        System.out.print("Quanti numeri vuoi dare? (> 0): ");

        //Crea un oggetto di tipo Scanner e lo assegna alla reference scanner
        Scanner scanner = new Scanner(System.in);

        int count;
        do {
            count = scanner.nextInt();
            if (count > 0)
                break;
            else
                System.out.println("Valore non valido. Ripetere");
        }
        while (true);

        int sum = 0;
        int num = 0;
        for (int i = 0; i < count; i++) {
            System.out.print("Inserisci un numero: ");
            num = scanner.nextInt();
            sum += num;
        }

        float average = (float) sum / num;
        System.out.println("La media è: " + average + " | La somma è " + sum);

        Fraction fraction1 = new Fraction(2, 3);
        fraction1.print(System.out);

        Rectangle rect = new Rectangle(5, 7);
        Rectangle.Square square = new Rectangle.Square(4);
        System.out.println(rect.area());

        Object o1 = new Rectangle(2, 3);

        //Compile-time Error
        o1.area();
        // OK at compile-time (equals is a method of Object)
        o1.equals();
        //Quale equals richiama? (a run-time)
        //Regola:
        //Controlla per prima la classe d'origine dell'oggetto dell'oggetto puntato (in questo caso Rectangle)
        //In questo caso se Rectangle contiene equals -> lo chiama 
        //Altrimenti cerca equals in una delle superclassi di Rectangle

        //Per chiamare il metodo area devo downcastare o1 a Rectangle
        //quindi:
        ((Rectangle) o1).area();
        //OK a compile-time
        //OK a run-time (visto che la sua classe d'origine è Rectangle e quindi è possibile fare il downcasting)
        ((String) o1).area();
        //Errore a compile-time (perché area non è un metodo di String)
        ((Rectangle.Square) o1).area();
        //OK a compile-time (perché area è un metodo di quadrato)
        //Errore a run-time (ClassCastException o1 che è un Rectangle non può essere castato a Square perché è una sottoclasse)

        scanner.close();
    }
}