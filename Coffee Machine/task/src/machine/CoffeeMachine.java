package machine;

import java.util.HashMap;
import java.util.Scanner;

class Machine {
    int watter;
    int milk;
    int beans;
    int money;
    int cups;

    HashMap<String, CoffeeRecipe> listOfRecipe = new HashMap<>();

    public static class CoffeeRecipe {
        private final int water;
        private final int milk;
        private final int beans;
        private final int coast;

        public CoffeeRecipe(int water, int milk, int beans, int coast) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.coast = coast;
        }

    }



    public void initMachine(int water, int milk, int beans, int money, int cups) {
        listOfRecipe.put("espresso", new CoffeeRecipe(250,0,16,4));
        listOfRecipe.put("latte", new CoffeeRecipe(350,75,20,7));
        listOfRecipe.put("cappuccino", new CoffeeRecipe(200,100 ,12,6));
        this.watter = water;
        this.milk = milk;
        this.beans = beans;
        this.money = money;
        this.cups = cups;
    }

    public void fillMachine(int water, int milk, int beans, int money, int cups) {
        this.watter += water;
        this.milk += milk;
        this.beans += beans;
        this.money += money;
        this.cups += cups;
    }

    private String countPossibleCups(String coffeeRecipe, int cups) {
        int maxWatterCups = listOfRecipe.get(coffeeRecipe).water != 0 ? watter/listOfRecipe.get(coffeeRecipe).water : Integer.MAX_VALUE;
        int maxMilkCups = listOfRecipe.get(coffeeRecipe).milk != 0 ? watter/listOfRecipe.get(coffeeRecipe).milk : Integer.MAX_VALUE;
        int maxBeansCups = listOfRecipe.get(coffeeRecipe).beans != 0 ? watter/listOfRecipe.get(coffeeRecipe).beans : Integer.MAX_VALUE;
        if (maxWatterCups < cups) {
            return "Sorry, not enough water!";
        } else if (maxBeansCups < cups) {
            return "Sorry, not enough beans!";
        } else if (maxMilkCups < cups) {
            return "Sorry, not enough milk!";
        }
        return "";
    }


    private String buyCoffee(String coffeeRecipe, int cups) {
        CoffeeRecipe coffee = listOfRecipe.get(coffeeRecipe);
        String possibleCups = countPossibleCups(coffeeRecipe, cups);
        if (possibleCups.equals("")) {
            System.out.println("I have enough resources, making you a coffee!");
            watter -= (coffee.water * cups);
            milk -= (coffee.milk * cups);
            beans -= (coffee.beans * cups);
            money += (coffee.coast * cups);
            this.cups -= cups;
            return "";
        } else {
            return possibleCups;
        }

    }

    public String buyCoffee(int input, int cups) {
        switch (input) {
            case 1:
                return buyCoffee("espresso", cups);
            case 2:
                return buyCoffee("latte", cups);
            case 3:
                return buyCoffee("cappuccino", cups);
            default:
                return "";
        }
    }

    public int getWatter() {
        return watter;
    }

    public void setWatter(int watter) {
        this.watter = watter;
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    public int getBeans() {
        return beans;
    }

    public void setBeans(int beans) {
        this.beans = beans;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCups() {
        return cups;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    @Override
    public String toString() {
        return String.format("The coffee machine has:%n%d ml of water%n%d ml of milk%n%d g of coffee beans%n%d disposable cups%n%d$ of money", watter, milk, beans, cups, money);
    }
}

public class CoffeeMachine {

    public static void main(String[] args) {
        Machine machine = new Machine();
        Scanner scanner = new Scanner(System.in);
        machine.initMachine(400,540,120,550,9);
        String input = "";
        while (!input.equals("exit")) {
            System.out.println("Write action(buy, fill, take, remaining, exit):");
            input = scanner.next();
            switch (input) {
                case "buy":
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
                    System.out.println("back - to main menu: ");
                    String buyInput = scanner.next();
                    if (!buyInput.equals("back")) {
                        System.out.println(machine.buyCoffee(Integer.parseInt(buyInput), 1));
                    }
                    break;
                case "fill":
                    System.out.println("Write how many ml of water you want to add: ");
                    int watter = scanner.nextInt();
                    System.out.println("Write how many ml of milk you want to add: ");
                    int milk = scanner.nextInt();
                    System.out.println("Write how many grams of coffee beans you want to add: ");
                    int beans = scanner.nextInt();
                    System.out.println("Write how many disposable cups of coffee you want to add:");
                    int cups = scanner.nextInt();
                    machine.fillMachine(watter, milk, beans, 0, cups);
                    break;
                case "take":
                    System.out.printf("I gave you $%d%n", machine.getMoney());
                    machine.setMoney(0);
                    break;
                case "remaining":
                    System.out.println(machine);
                    break;
                default:
                    break;
            }
        }
    }
}
