package server;

import org.junit.Assert;
import org.junit.Test;
public class TestParameters {

  @Test
  public void checkForNoRealCaseNumber() {
    Parameter twoInts = new Parameter("KEYW:1:2",0);
    Assert.assertFalse(twoInts.isCorrect);
  }

  @Test
  public void checkParametersTwoIntsCorrect() {
    Parameter twoInts = new Parameter("KEYW:1:2",1);
    Assert.assertTrue(twoInts.isCorrect);
  }

  @Test
  public void checkParametersTwoIntsShortKeyword() {
    Parameter twoInts = new Parameter("KEY:1:2",1);
    Assert.assertFalse(twoInts.isCorrect);
  }

  @Test
  public void checkParametersTwoIntsLetters() {
    Parameter twoInts = new Parameter("KEYW:1:b",1);
    Assert.assertFalse(twoInts.isCorrect);

    Parameter twoInts2 = new Parameter("KEYW:a:2",1);
    Assert.assertFalse(twoInts2.isCorrect);
  }

  @Test
  public void checkParametersTwoIntsNoColon() {
    Parameter twoInts = new Parameter("KEYW11",1);
    Assert.assertFalse(twoInts.isCorrect);

    Parameter twoInts2 = new Parameter("KEYW:22",1);
    Assert.assertFalse(twoInts2.isCorrect);
  }

  @Test
  public void checkParametersNames() {
    Parameter name = new Parameter("KEYW:SomeRandomName",3);
    Assert.assertTrue(name.isCorrect);

    Parameter otherName = new Parameter("KEYW:AnotherRandomName",3);
    Assert.assertTrue(otherName.isCorrect);
  }


  @Test
  public void checkParametersNamesNoColonsAllowed() {
    Parameter name = new Parameter("KEYW:SomeRandom:Name",3);
    Assert.assertTrue(name.isCorrect);
    Assert.assertEquals(name.wordOne, "SomeRandomName");
  }

  @Test
  public void checkParametersNamesNoSpacesAllowed() {
    Parameter name = new Parameter("KEYW:SomeRandom Name",3);
    Assert.assertTrue(name.isCorrect);
    Assert.assertEquals(name.wordOne, "SomeRandomName");
  }

  @Test
  public void checkParametersNamesNotOnlySpacesAllowed() {
    Parameter name = new Parameter("KEYW: ",3);
    Assert.assertFalse(name.isCorrect);
  }
}
