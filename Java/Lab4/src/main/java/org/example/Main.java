package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("konradJPA");

        initData(emf);

        while (true) {
            if (!showMenu(emf))
                break;
        }

        emf.close();
    }

    private static boolean showMenu(EntityManagerFactory emf) {
        System.out.println();
        System.out.println("Co chcesz zrobic?");
        System.out.println("1. Wyswietl baze danych");
        System.out.println("2. Dodaj wieze");
        System.out.println("3. Dodaj maga");
        System.out.println("4. Usun wieze");
        System.out.println("5. Usun maga");
        System.out.println("6. Wyswietl magow z levelem wiekszym niz zadany");
        System.out.println("7. Wyjdz");

        return readAction(emf);
    }

    private static boolean readAction(EntityManagerFactory emf) {
        Scanner scanner = new Scanner(System.in);
        int action = scanner.nextInt();

        switch (action) {
            case 1 -> {
                showDataBase(emf);
                return true;
            }
            case 2 -> {
                addTower(emf);
                return true;
            }
            case 3 -> {
                addMage(emf);
                return true;
            }
            case 4 -> {
                removeTower(emf);
                return true;
            }
            case 5 -> {
                removeMage(emf);
                return true;
            }
            case 6 -> {
                showMagesWithLevelGreatherThan(emf);
                return true;
            }
            case 7 -> {
                return false;
            }

        }

        return false;
    }

    private static void showMagesWithLevelGreatherThan(EntityManagerFactory emf) {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = emf.createEntityManager();

        System.out.println("Podaj level od ktorego zaczac szukac maga");
        int level = scanner.nextInt();

        String queryString = "SELECT m FROM Mage m WHERE m.level > :level";
        Query query = em.createQuery(queryString, Mage.class);
        query.setParameter("level", level);
        List<Mage> mages = query.getResultList();

        for(Mage mage: mages) {
            System.out.println("Mage: " + mage.getName() + ", " + mage.getLevel());
        }

        em.close();
    }
    private static void removeTower(EntityManagerFactory emf) {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = emf.createEntityManager();
        Tower tower;
        while (true) {
            System.out.println("Podaj nazwe wiezy do ktorej ma dolaczyc mag:");
            String towerName = scanner.nextLine();

            tower = em.find(Tower.class, towerName);
            if (tower == null)
                System.out.println("Wieza o podanej nazwie nie istnieje");
            else break;
        }

        String queryString = "SELECT m FROM Mage m WHERE m.tower.name LIKE :name";
        Query query = em.createQuery(queryString, Mage.class);
        query.setParameter("name", tower.getName());
        List<Mage> mages = query.getResultList();

        for (Mage mage: mages) {
            em.getTransaction().begin();
            em.remove(mage);
            tower.getMages().remove(mage);
            em.getTransaction().commit();
        }

        em.getTransaction().begin();
        em.remove(tower);
        em.getTransaction().commit();

        em.close();
    }

    private static void removeMage(EntityManagerFactory emf) {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = emf.createEntityManager();
        Mage mage;
        while (true) {
            System.out.println("Podaj nazwe maga do usuniecia:");
            String mageName = scanner.nextLine();

            mage = em.find(Mage.class, mageName);
            if (mage == null)
                System.out.println("Mag o podanej nazwie nie istnieje");
            else break;
        }
        Tower tower = em.find(Tower.class, mage.getTower().getName());
        tower.getMages().remove(mage);

        em.getTransaction().begin();
        em.remove(mage);
        em.getTransaction().commit();

        em.close();
    }

    private static void addMage(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        Tower tower;
        while (true) {
            System.out.println("Podaj nazwe wiezy do ktorej ma dolaczyc mag:");
            String towerName = scanner.nextLine();

            tower = em.find(Tower.class, towerName);
            if (tower == null)
                System.out.println("Wieza o podanej nazwie nie istnieje");
            else break;
        }

        System.out.println("Podaj imie oraz level maga:");
        String mageName = scanner.next();
        int level = scanner.nextInt();

        Mage mage = new Mage(mageName, level, tower);
        tower.getMages().add(mage);

        em.getTransaction().begin();
        em.persist(mage);
        em.getTransaction().commit();

        em.close();
    }

    private static void addTower(EntityManagerFactory emf) {
        System.out.println();
        System.out.println("Podaj nazwe i wysokosc wiezy:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        int height = scanner.nextInt();
        Tower tower = new Tower(name, height);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(tower);
        em.getTransaction().commit();
        em.close();
    }

    private static void showDataBase(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();

        String queryString = "SELECT t FROM Tower t";
        Query query = em.createQuery(queryString, Tower.class);
        List<Tower> towers = query.getResultList();

        for (Tower tower : towers) {
            System.out.println("Tower: " + tower.getName() + ", " + tower.getHeight() + "m");
            queryString = "SELECT m FROM Mage m WHERE m.tower.name LIKE :name";
            query = em.createQuery(queryString, Mage.class);
            query.setParameter("name", tower.getName());
            List<Mage> mages = query.getResultList();
            for (Mage mage : mages) {
                System.out.println("\tMage: " + mage.getName() + ", " + mage.getLevel());
            }
        }
        System.out.println();

        em.close();
    }

    private static void initData(EntityManagerFactory emf) {
        Tower firstTower = new Tower("First Tower", 50);
        Mage Mirek = new Mage("Mirek", 10, firstTower);
        firstTower.getMages().add(Mirek);
        Mage Marek = new Mage("Marek", 20, firstTower);
        firstTower.getMages().add(Marek);
        Tower secondTower = new Tower("Second Tower", 100);
        Mage Zoltan = new Mage("Zoltan", 30, secondTower);
        firstTower.getMages().add(Zoltan);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(Mirek);
        em.persist(Marek);
        em.persist(Zoltan);
        em.persist(firstTower);
        em.persist(secondTower);
        em.getTransaction().commit();

        em.close();
    }
}