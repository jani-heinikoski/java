package com.kranaatinheitinkomppania;

public class Character {
    private String name;
    public WeaponBehavior wb;

    public Character(String name, int wepID) {
        this.name = name;
        switch (wepID) {
            case 1:
                this.wb = new KnifeBehavior();
                break;
            case 2:
                this.wb = new AxeBehavior();
                break;
            case 3:
                this.wb = new SwordBehavior();
                break;
            case 4:
                this.wb = new ClubBehavior();
                break;
        }

    }
    public void fight() {
        System.out.print(this.name + " fights with ");
        wb.useWeapon();
        System.out.println();
    }
}

class WeaponBehavior {
    private String weaponName;

    public WeaponBehavior(String weapon) {
        this.weaponName = weapon;
    }

    public void useWeapon() {
        System.out.print("weapon " + this.weaponName);
    }
}

class King extends Character {
    public King(int wepID) {
        super("King", wepID);
    }
}

class Queen extends Character {
    public Queen(int wepID) {
        super("Queen", wepID);
    }
}

class Knight extends Character {
    public Knight(int wepID) {
        super("Knight", wepID);
    }
}

class Troll extends Character {
    public Troll(int wepID) {
        super("Troll", wepID);
    }
}

class SwordBehavior extends WeaponBehavior {
    public SwordBehavior() {
        super("Sword");
    }
}

class ClubBehavior extends WeaponBehavior {
    public ClubBehavior() {
        super("Club");
    }
}

class AxeBehavior extends WeaponBehavior {
    public AxeBehavior() {
        super("Axe");
    }
}

class KnifeBehavior extends WeaponBehavior {
    public KnifeBehavior() {
        super("Knife");
    }
}