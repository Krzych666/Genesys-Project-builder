/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.builder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author krzysztof.g
 */
public class DatabaseHolderTest {

    /**
     * DatabaseHolderTest
     */
    public DatabaseHolderTest() {
    }

    /**
     * setUpClass
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * tearDownClass
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * setUp
     */
    @Before
    public void setUp() {
    }

    /**
     * tearDown
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of creator method, of class DatabaseHolder.
     */
    @Test
    public void testCreator() {
        System.out.println("creator");
        Enums.Enmuerations.LifedomainValue lifedomain = null;
        Enum what = null;
        DatabaseHolder.creator(lifedomain, what);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of classLeftModify method, of class DatabaseHolder.
     */
    @Test
    public void testClassLeftModify() {
        System.out.println("classLeftModify");
        String expResult = "";
        String result = DatabaseHolder.classLeftModify();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recognizeClassDo method, of class DatabaseHolder.
     */
    @Test
    public void testRecognizeClassDo() {
        System.out.println("recognizeClassDo");
        Enums.Enmuerations.LifedomainValue lifedomain = null;
        String type = "";
        Boolean add = null;
        DatabaseHolder.recognizeClassDo(lifedomain, type, add);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchFreeClassSpot method, of class DatabaseHolder.
     */
    @Test
    public void testSearchFreeClassSpot() {
        System.out.println("searchFreeClassSpot");
        DatabaseHolder.searchFreeClassSpot();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of skillsSeparatorRepalcer method, of class DatabaseHolder.
     */
    @Test
    public void testSkillsSeparatorRepalcer() {
        System.out.println("skillsSeparatorRepalcer");
        String skills = "";
        String expResult = "";
        String result = DatabaseHolder.skillsSeparatorRepalcer(skills);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadSpeciesToHold method, of class DatabaseHolder.
     */
    @Test
    public void testLoadSpeciesToHold() {
        System.out.println("loadSpeciesToHold");
        int selSpecies = 0;
        DatabaseHolder.loadSpeciesToHold(selSpecies);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadCultureToHold method, of class DatabaseHolder.
     */
    @Test
    public void testLoadCultureToHold() {
        System.out.println("loadCultureToHold");
        int selCulture = 0;
        DatabaseHolder.loadCultureToHold(selCulture);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadClassToHold method, of class DatabaseHolder.
     */
    @Test
    public void testLoadClassToHold() {
        System.out.println("loadClassToHold");
        int selClass = 0;
        int a = 0;
        DatabaseHolder.loadClassToHold(selClass, a);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadHeroToHold method, of class DatabaseHolder.
     */
    @Test
    public void testLoadHeroToHold() {
        System.out.println("loadHeroToHold");
        int selHero = 0;
        DatabaseHolder.loadHeroToHold(selHero);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadProgressToHold method, of class DatabaseHolder.
     */
    @Test
    public void testLoadProgressToHold() {
        System.out.println("loadProgressToHold");
        int selProgress = 0;
        DatabaseHolder.loadProgressToHold(selProgress);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadRosterToHold method, of class DatabaseHolder.
     */
    @Test
    public void testLoadRosterToHold() {
        System.out.println("loadRosterToHold");
        int selRoster = 0;
        DatabaseHolder.loadRosterToHold(selRoster);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadBattleToHold method, of class DatabaseHolder.
     */
    @Test
    public void testLoadBattleToHold() {
        System.out.println("loadBattleToHold");
        int selBattle = 0;
        DatabaseHolder.loadBattleToHold(selBattle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
