import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {290, 270, 250, 300, 400, 300, 200, 100};
    public static int[] heroesDamage = {25, 15, 10, 0, 5, 10, 0, 10,};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Witcher", "Thor"};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            round();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();
        showStatistics();
        medic();
        Golem();
        Lucky();
        Wither();
        Thor();
    }

    public static void medic() {
        Random random = new Random();
        int randomIndex = random.nextInt(10, 50);
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesAttackType[i].equals("Medic")) {
                continue;
            }
            if (heroesHealth[i] < 100 && heroesDamage[i] > 0) ;
            heroesHealth[i] += randomIndex;
            break;
        }
    }

    public static void Golem() {
        int golem = bossDamage / 5;
        if (heroesHealth[4] > 0 && bossHealth > 0) {
            heroesHealth[4] -= golem;
            bossDamage -= golem;
        } else {
            bossDamage = 50;
        }
    }

    public static void Lucky() {
        Random random = new Random();
        boolean isLuckyEvaded = random.nextBoolean();
        if (heroesHealth[5] > 0) {
            if (!(isLuckyEvaded)) {
                heroesHealth[5] = heroesHealth[5] + bossDamage;
                if (heroesHealth[5] > 130) {
                    heroesHealth[5] = heroesHealth[5] - bossDamage;
                } else if (isLuckyEvaded) {
                    heroesHealth[5] = heroesHealth[5];
                }
            }
        }
    }

    public static void Wither() {
        if (heroesHealth[6] > 0)
            for (int i = 0; i < heroesAttackType.length; i++) {
                if (heroesHealth[i] == 0) ;
                heroesHealth[i] += heroesHealth[6];
                heroesHealth[6] = 0;
                break;
            }
    }

    public static void Thor() {
        Random random = new Random();
        boolean stun = random.nextBoolean();
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[7] > 0 && stun == true) {
                bossDamage = 0;
                System.out.println("Boss is stun!");
                break;
            } else if (stun == false) {
                bossDamage = 50;
            }
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = damage * coeff;
                    System.out.println("Critical damage: " + heroesAttackType[i] + " " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " ----------------");
        /*String defence;
        if(bossDefence == null) {
            defence = "None";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "None" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}

/*Добавить 4-го игрока Medic, у которого есть способность лечить после каждого раунда на N-ное количество единиц здоровья только одного из членов команды, имеющего здоровье менее 100 единиц. Мертвых героев медик оживлять не может, и лечит он до тех пор пока жив сам. Медик не участвует в бою, но получает урон от Босса. Сам себя медик лечить не может.
ДЗ на сообразительность:

        **Добавить n-го игрока, Golem, который имеет увеличенную жизнь но слабый удар. Принимает на себя 1/5 часть урона исходящего от босса по другим игрокам.
        *Добавить n-го игрока, Lucky, имеет шанс уклонения от ударов босса.
**Добавить еще игрока Witcher, не наносит урон боссу, но получает урон от босса. Имеет 1 шанс оживить первого погибшего героя, отдав ему свою текущюю жизнь, при этом погибает сам.
        *Добавить n-го игрока, Thor, удар по боссу имеет шанс оглушить босса на 1 раунд, вследствие чего босс пропускает 1 раунд и не наносит урон героям. // random.nextBoolean(); - true, false
        ...Свернуть


medic:

Способность: лечит одного члена команды на N единиц здоровья после каждого раунда.
        Ограничения:
Лечит только тех, у кого здоровье менее 100 единиц.
Не может лечить мертвых героев.
Лечит только пока сам жив.
Не может лечить самого себя.
Медик не участвует в бою, но получает урон от босса.
        Golem:

Способность: имеет увеличенное здоровье, но слабый удар.
        Особенность: принимает на себя 1/5 часть урона, направленного на других игроков от босса.
        Lucky:

Способность: имеет шанс уклонения от ударов босса.
Witcher:

Способность: не наносит урон боссу, но получает урон от босса.
Особенность: имеет один шанс оживить первого погибшего героя, передавая ему свою текущую жизнь и умирая сам.
Thor:

Способность: удар по боссу имеет шанс оглушить его на 1 раунд.
        Механика: при успешном оглушении босс пропускает 1 раунд и не наносит урон героям (вероятность успеха определяется с помощью random.nextBoolean();).*/
